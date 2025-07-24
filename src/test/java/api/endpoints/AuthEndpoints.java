package api.endpoints;

import api.payload.AccountUser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class AuthEndpoints {

    public static Response generateToken(AccountUser payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.generateToken_url);
        return response;
    }
}