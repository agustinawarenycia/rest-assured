package tests;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;


public class GetandPostExamples {

	@Test
	public void testGet() {
		
		baseURI = "https://api.aniapi.com";
		
		//String que traiga solo el atributo nombre
		
		given().
			get("/v1/anime/21").
		then().
			statusCode(200).
			log().all();
		
		
		
	}
	
	
	
}
