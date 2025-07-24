package com.bookstore.api.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class AccountUserSecureEndpoints {


	    public static Response getUserById(String userId, String token) {
	        return given()
	                .header("Authorization", "Bearer " + token)
	                .accept(ContentType.JSON)
	                .when()
	                .get(Routes.getUser_url + userId);
	    }

	    public static Response deleteUserById(String userId, String token) {
	        return given()
	                .header("Authorization", "Bearer " + token)
	                .accept(ContentType.JSON)
	                .when()
	                .delete(Routes.deleteUser_url + userId);
	    }
	}
