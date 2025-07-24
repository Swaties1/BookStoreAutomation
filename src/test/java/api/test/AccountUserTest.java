package api.test;

import api.endpoints.*;
import api.payload.AccountUser;
import api.utilities.ExcelUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountUserTest {

    private static final Logger logger = LogManager.getLogger(AccountUserTest.class);

    private ExtentReports extent;
    private ExtentTest test;

    @BeforeSuite
    public void setupExtent() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @AfterSuite
    public void tearDownExtent() {
        extent.flush();
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        String excelPath = "src/test/resources/TestData.xlsx";
        return ExcelUtils.getExcelData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "userData")
    public void testCreateAndDeleteUser(String userName, String password) {
        test = extent.createTest("testCreateAndDeleteUser - " + userName);

        logger.info("===== Starting test for user: {}", userName);
        test.info("Starting test for user: " + userName);

        // 1. Build Payload
        AccountUser user = new AccountUser();
        user.setUserName(userName);
        user.setPassword(password);

        // 2. Create User
        logger.info("Creating user: {}", userName);
        test.info("Creating user: " + userName);

        Response createResponse = AccountUserEndpoints.createUser(user);
        Assert.assertTrue(createResponse.statusCode() == 200 || createResponse.statusCode() == 201,
                "Failed to create user: " + createResponse.asPrettyString());
        String userId = createResponse.path("userID");
        logger.info("User Created: {}", userId);
        test.pass("User Created with ID: " + userId);

        // 3. Generate Token
        Response tokenResponse = AuthEndpoints.generateToken(user);
        String token = tokenResponse.path("token");
        logger.info("Token received: {}", token);
        test.info("Token received");
        Assert.assertNotNull(token, "Token should not be null");

        // 4. Verify Authorization
        Response authResponse = AccountAuthorizationEndpoint.verifyUserAuthorized(user);
        Assert.assertEquals(authResponse.statusCode(), 200, "Authorization failed");
        test.pass("User authorized successfully");

        // 5. Get User by ID (before deletion)
        Response getResponse = AccountUserSecureEndpoints.getUserById(userId, token);
        Assert.assertEquals(getResponse.statusCode(), 200, "Failed to fetch user by ID before deletion");
        test.pass("Fetched user by ID before deletion");

        // 6. Delete User
        logger.info("Deleting user ID: {}", userId);
        test.info("Deleting user ID: " + userId);

        Response deleteResponse = AccountUserSecureEndpoints.deleteUserById(userId, token);
        Assert.assertEquals(deleteResponse.statusCode(), 204, "Failed to delete user");
        test.pass("User deleted successfully");

        // 7. Confirm user is deleted
        logger.info("Verifying deletion of user: {}", userId);
        test.info("Verifying deletion of user: " + userId);

        Response verifyDeleted = AccountUserSecureEndpoints.getUserById(userId, token);
        Assert.assertEquals(verifyDeleted.statusCode(), 401, "Expected 401 Unauthorized after user deletion");
        test.pass("Verified user deletion (received 401 as expected)");

        logger.info("===== Test completed for user: {} =====", userName);
        test.info("Test completed for user: " + userName);
    }
}