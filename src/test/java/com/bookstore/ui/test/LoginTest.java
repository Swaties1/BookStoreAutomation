package com.bookstore.ui.test;

import com.bookstore.pageobjects.LoginPage;
import com.bookstore.ui.base.BaseTest;
import com.bookstore.api.utilities.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class LoginTest extends BaseTest {



    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.getExcelData("src/test/resources/LoginTestData.xlsx", "Sheet1");
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, String expectedResult) {
        logger.info("Opening login page for user: {}", username);
        driver.get(config.getProperty("baseUrl"));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("valid")) {
            logger.info("Asserting login success for valid user: {}", username);
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Login should be successful");
            Assert.assertEquals(loginPage.getLoggedInUser(), username, "Logged in user should match input");
        } else {
            logger.info("Asserting error message for invalid user: {}", username);
            String error = loginPage.getErrorMessage();
            Assert.assertTrue(error.contains("Invalid"), "Expected error message for invalid login.");
        }
    }
}