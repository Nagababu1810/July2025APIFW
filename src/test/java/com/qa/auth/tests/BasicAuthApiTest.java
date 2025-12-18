package com.qa.auth.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class BasicAuthApiTest extends BaseTest {

	@Test
	public void basicauthtest() {
		
	Response response =	restClient.get(BASE_URL_HEROKU_BASIC_AUTH, HEROKU_BASIC_AUTH_ENDPOINT, null, null, AuthType.BASIC_AUTH, ContentType.ANY);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.getBody().asString().contains("congrucations you have must credials"));
	}
	
}
