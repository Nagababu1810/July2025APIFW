package com.qa.api.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utiles.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateAUserTest extends BaseTest{

	@Test
	public void updateuserTest() {
	//create user
	User user=User.builder()
			.name("nagababu18")
			.email(StringUtils.getRandomEmailid())
			.gender("male")
			.status("active")
			.build();
	
   Response responsepost=restClient.post(BASE_URL_GOREST,GOREST_USERS_ENDPOINT,user,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);
   int userId =responsepost.jsonPath().getInt("id");
   System.out.println("new user id : "+ userId);
   
   //get the same user using same id
   Response responseget=restClient.get(BASE_URL_GOREST,GOREST_USERS_ENDPOINT+"/"+userId,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);
  
   Assert.assertEquals(responseget.jsonPath().getInt("id"),userId);
   
   //update the same user using user id
   user.setStatus("inactive");
   user.setName("babu");
   
   Response responseput=restClient.put(BASE_URL_GOREST,GOREST_USERS_ENDPOINT+"/"+userId ,user,null,null,AuthType.BEARER_TOKEN, ContentType.JSON);
   
   Assert.assertEquals(responseput.jsonPath().getString("status"),user.getStatus());
   Assert.assertEquals(responseput.jsonPath().getString("name"), user.getName());
   
   
	}
}
