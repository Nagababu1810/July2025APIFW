package com.qa.api.gorest.tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utiles.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateAUserTest extends BaseTest{
	
	@Test
	public void createUserTest() {
		
		String userjson ="{\r\n"
				+ "    \"name\": \"Nagababu\",\r\n"
				+ "    \"gender\": \"male\",\r\n"
				+ "    \"email\": \"nagababu52897.tester18@gmail.com\",\r\n"
				+ "    \"status\": \"active\"\r\n"
				+ "}";
		
		Response response =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userjson,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		response.prettyPrint();
	}
	
	@BeforeClass
	public void gorestTokensetup() {
		
		ConfigManager.set("BearerToken", "0287c7d03e6da668c3d9dc905f4ed73e2b3fc39e63022d7337ae2f77c630074c");
	}
	
	@DataProvider
	public Object [] [] getUserData() {
		return new Object [][]{
		{"babu","active","male"},
		{"dinesh","active","male"},
		{"ramesh","active","male"}
	};
	}
	
	@Test(dataProvider ="getUserData")
	public void createUserwithPojoTest(String name,String status,String gender) {
		
		//User user= new User("nagababu","mnb@gmail.com","male","status");
	
		User user=User.builder()
				.name(name)
				.email(StringUtils.getRandomEmailid())
				.gender(status)
				.status(gender)
				.build();
		
		Response response =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, user ,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 201);
		response.prettyPrint();
	}

	@Test
	public void createUserwithJsonFileTest() {
		
		
		File file =new File("/src/test/resources/json/User.json");
		
		Response response =restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, file ,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 201);
		response.prettyPrint();
	}
}
