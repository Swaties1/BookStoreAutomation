package com.bookstore.api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import com.bookstore.api.payload.AccountUser;

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