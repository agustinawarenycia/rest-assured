package tests;

import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
/*Lineas que van a servir
 * .extract()
.asString() -> para pasar a texto lo que tengamos
 * 
 * */



public class restPruebas {
	
	
	public void loginTest() {
		
		
		RestAssured
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
				.statusCode(201)
				.body("token", notNullValue());

	}
		
	@Test
	public void listUsers() {
		
		
		RestAssured
				.given()
				.log()
				.all()
				.contentType(ContentType.JSON)
				.get("https://reqres.in/api/users/2")
				.then()
				.log()
				.all()
				.statusCode(200)
				.body("data.id", equalTo(2));
				

	}
	
	
	
	
}
