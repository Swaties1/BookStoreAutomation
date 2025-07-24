package com.bookstore.api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import com.bookstore.api.payload.AccountUser;


public class AccountAuthorizationEndpoint {
	public static Response verifyUserAuthorized(AccountUser payload) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.authorizedUser_url);
    }
}
