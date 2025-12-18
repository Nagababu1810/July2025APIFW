package com.qa.api.utiles;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class SchemaValidator {

	
	
	public static boolean validateschema(Response response,String SchemaFileName) {
		
		try {
		response.then().assertThat().body(matchesJsonSchemaInClasspath(SchemaFileName));
		System.out.println("schema validation is passed for :"+SchemaFileName);
		return true;
		}
		catch(Exception e) {
			System.out.println("schema Validation is failed for :"+SchemaFileName);
			return false;
		}
	}
}
