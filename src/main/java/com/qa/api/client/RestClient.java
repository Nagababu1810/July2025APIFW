package com.qa.api.client;

import java.io.File;
import java.util.Base64;
import java.util.Map;

import com.qa.api.constants.AuthType;
import com.qa.api.exceptions.APiExceptions;
import com.qa.api.manager.ConfigManager;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class RestClient {

	private ResponseSpecification responseSpec200 =expect().statusCode(200);
	private ResponseSpecification responseSpec200or201 =expect().statusCode(anyOf(equalTo(200),equalTo(201)));
	private ResponseSpecification responseSpec204 =expect().statusCode(204);
	
	private RequestSpecification setupRequest(String baseurl,AuthType authType,ContentType contentType) {
		
	RequestSpecification request =	RestAssured.given().log().all()
		.baseUri(baseurl)
		.contentType(contentType)
		.accept(contentType);
		
		switch(authType){
		case BEARER_TOKEN:
			request.header("Authorization","Bearer"+ConfigManager.get("BearerToken"));
			break;
		case BASIC_AUTH:
			request.header("Authorization","Basic" +generateBasicAuthToken());
			break;
		case API_KEY:
			request.header("x-api-key","Api-key");
			break;
		case NO_AUTH:
			System.out.println("auth is not requried");
			break;
			default:
				System.out.println("This authType is  not supported ..please pass the right auth");
				throw new APiExceptions("INvalied AUTH TYPE");
				
	}
		return request;
	
	}
	
	

	private String generateBasicAuthToken() {
		//admin:admin ==> YWRtaW46YWRtaW4=
		String credentails = ConfigManager.get("basicauthusername").trim() + ":" + ConfigManager.get("basicauthpassword").trim();
		return Base64.getEncoder().encodeToString(credentails.getBytes());
	}
	
	
	
	
	private void applyParams(RequestSpecification request, Map<String, String> queryParams, Map<String, String> pathParams) {
		if(queryParams!=null) {
			request.queryParams(queryParams);
		}
		if(pathParams!=null) {
			request.pathParams(pathParams);
		}
		
	}
	
	/**
	 * This method is used to call GET APIs..
	 * @param baseUrl
	 * @param endPoint
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return it returns the GET API call response..
	 */
	public Response get(String baseurl,String endpoint, Map<String,String> queryParams,Map<String,String> Pathparams,AuthType authType,ContentType contentType) {
		
		RequestSpecification request =setupRequest(baseurl,authType,contentType);
		applyParams(request, queryParams, Pathparams);
		
		Response response =request.urlEncodingEnabled(false).get(endpoint).then().spec(responseSpec200).extract().response();
		response.prettyPrint();
		return response;
		
		
	}
	/**
	 * this method is used to create a resource using post call method - accepts any type of request body 
	 * except the file type
	 * @param <T>
	 * @param baseUrl
	 * @param endPoint
	 * @param body
	 * @param queryParams
	 * @param pathParams
	 * @param authType
	 * @param contentType
	 * @return
	 */
public <T>Response post(String baseurl,String endpoint, T body ,Map<String,String> queryParams,Map<String,String> Pathparams,AuthType authType,ContentType contentType)
{
		
		RequestSpecification request =setupRequest(baseurl,authType,contentType);
		applyParams(request, queryParams, Pathparams);
		
		Response response =request.urlEncodingEnabled(false).body(body).post(endpoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
				
	}
/**
 * this method is used to create a resource using post call method - accepts only file type of request body 
 * except the file type
 * @param <T>
 * @param baseUrl
 * @param endPoint
 * @param body
 * @param queryParams
 * @param pathParams
 * @param authType
 * @param contentType
 * @return
 */	
public  Response post(String baseurl,String endpoint, File file ,Map<String,String> queryParams,Map<String,String> Pathparams,AuthType authType,ContentType contentType)
{
		
		RequestSpecification request =setupRequest(baseurl,authType,contentType);
		applyParams(request, queryParams, Pathparams);
		
		Response response =request.urlEncodingEnabled(false).body(file).post(endpoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
				
	}

/**
 * this method is used to get the access token for Oauth2.0 based apis.
 * @param baseUrl
 * @param endPoint
 * @param clientId
 * @param clientSecret
 * @param grantType
 * @param contentType
 * @return this will return a response with access token
 */public  Response post(String baseurl,String endpoint,String clientID,String ClientSecret ,String grantType,ContentType contentType)
{
	
	Response response =RestAssured.given().log().all()
	.contentType(contentType)
	.formParam("grant_type", grantType)
	.formParam("client_id", clientID)
	.formParam("client_secret",ClientSecret)
	.when()
	.post(baseurl+endpoint);
	
	response.prettyPrint();
	return response;	
	}


/**
 * this method is used to update a resource using put call method
 * @param <T>
 * @param baseUrl
 * @param endPoint
 * @param body
 * @param queryParams
 * @param pathParams
 * @param authType
 * @param contentType
 * @return
 */

public <T>Response put(String baseurl,String endpoint, T body ,Map<String,String> queryParams,Map<String,String> Pathparams,AuthType authType,ContentType contentType)
{
		
		RequestSpecification request =setupRequest(baseurl,authType,contentType);
		applyParams(request, queryParams, Pathparams);
		
		Response response =request.urlEncodingEnabled(false).body(body).put(endpoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
				
	}


/**
 * this method is used to update a resource using put call method
 * @param <T>
 * @param baseUrl
 * @param endPoint
 * @param body
 * @param queryParams
 * @param pathParams
 * @param authType
 * @param contentType
 * @return
 */
public <T>Response patch(String baseurl,String endpoint, T body ,Map<String,String> queryParams,Map<String,String> Pathparams,AuthType authType,ContentType contentType)
{
		
		RequestSpecification request =setupRequest(baseurl,authType,contentType);
		applyParams(request, queryParams, Pathparams);
		
		Response response =request.urlEncodingEnabled(false).body(body).patch(endpoint).then().spec(responseSpec200or201).extract().response();
		response.prettyPrint();
		return response;
				
	}

/**
 * this method is used to delete a resource using delete call method
 * @param <T>
 * @param baseUrl
 * @param endPoint
 * @param authType
 * @param contentType
 * @return
 */
public <T>Response delete(String baseurl,String endpoint, AuthType authType,ContentType contentType)
{
		
		RequestSpecification request =setupRequest(baseurl,authType,contentType);
		//applyparms(request, queryParams, Pathparams);
		
		Response response =request.urlEncodingEnabled(false).delete(endpoint).then().spec(responseSpec204).extract().response();
		response.prettyPrint();
		return response;
				
	}
}
