package tests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class restPruebas {
	
	@Test
	public void loginTest() {
		
		
		String responde = RestAssured
				.given()
				.log()
				.all()
				.contentType(ContentType.JSON)
				.body("{\r\n"
						+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
						+ "    \"password\": \"cityslicka\"\r\n"
						+ "}")
				.post("https://reqres.in/api/login")
				.then()
				.log()
				.all()
				.extract()
				.asString();
		
		//System.out.print(responde);
				
	}
	
	
	
	
}
