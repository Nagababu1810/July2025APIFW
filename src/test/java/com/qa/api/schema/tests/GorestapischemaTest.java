package com.qa.api.schema.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utiles.SchemaValidator;
import com.qa.api.utiles.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GorestapischemaTest extends BaseTest{

	@BeforeClass
	public void gorestTokensetup() {
		
		ConfigManager.set("BearerToken", "0287c7d03e6da668c3d9dc905f4ed73e2b3fc39e63022d7337ae2f77c630074c");
	}
	
	@Test
	public void getUsersAPIschemaTest() {
		
	Response response= restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.ANY);
	Assert.assertTrue(SchemaValidator.validateschema(response, "getUserschema.json"));
	}
	
	
	@Test
	public void CreateUsersAPIschemaTest() {
		

		User user=User.builder()
				.name("nagababu")
				.email(StringUtils.getRandomEmailid())
				.gender("male")
				.status("active")
				.build();
		
		
	Response response= restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
	Assert.assertTrue(SchemaValidator.validateschema(response, "Createusersschema.json"));
	}
	
	
}
