package com.qa.api.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.client.RestClient;
import com.qa.api.constants.AuthType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserTest extends BaseTest{

	@Test
	public void getAlluserstest() {

		Response response =restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("ok"));
		
	}
	
	@Test
	public void getAlluserswithqueryparmstest() {
		
		Map<String,String> Queryparms =new HashMap<String,String>();
		Queryparms.put("name","nagababu");
		Queryparms.putIfAbsent("status","active");

		Response response =restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, Queryparms, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("ok"));
	}
	
	@Test
	public void getSingleusertest() {
		
		String userId = "987678";
		Response response =restClient.get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/+/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("ok"));
	}
}
