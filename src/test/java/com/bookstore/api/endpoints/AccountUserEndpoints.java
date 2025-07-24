package com.bookstore.api.endpoints;

import static io.restassured.RestAssured.given;

import com.bookstore.api.payload.AccountUser;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class AccountUserEndpoints {


	public static Response createUser(AccountUser payload)
	{
		Response response=given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.body(payload)
		.when()
		.post(Routes.postUser_url);
		return response;
	}
	
	public static Response getUser(String userId) {
		Response response=given()
				.pathParam("UUID",userId)
				.when()
				.get(Routes.getUser_url);
		
		return response;
	}
	public static Response deleteUser(String userId) {
		Response response=given()
				.pathParam("UUID",userId)
				.when()
				.delete(Routes.deleteUser_url);
		
		return response;
	}
}
