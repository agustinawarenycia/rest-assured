package tests;



import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import io.restassured.response.Response;


/*Lineas que van a servir:
 * .extract()
	.asString() -> para pasar a texto lo que tengamos
 * Datos que van a servir
 * 
 * Tambien tengo filtros donde pueda capturar los response antes de ser enviados a la BD
 *
 * En ves de mandar el json directamente puedo crear un createUserRequest y mandar como String
 *
 * */


public class restPruebas {


    @Before
    public void setup() {

        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void loginTest() {

        given()
                .body("{\r\n"
                        + "    \"email\": \"eve.holt@reqres.in\",\r\n"
                        + "    \"password\": \"cityslicka\"\r\n"
                        + "}")
                .post("login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());

    }

    @Test
    public void listSingleUser() {
        given()
                .get("users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2));
    }

    @Test
    public void deleteUserTest() {
        given()
                .delete("users/2")
                .then()
                .statusCode(200);
    }

    @Test
    public void pathUserTest() {
     String nameUpdated =   given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch("users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .extract().jsonPath()
                .getString("name");
     assertThat(nameUpdated, equalTo("morpheus"));
    }
    @Test
    public void putUserTest() {
        String JObUpdated =   given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch("users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .extract().jsonPath()
                .getString("name");
        assertThat(JObUpdated, equalTo("zion resident"));
    }

    @Test
    public void getAllUsersTests(){
        Response response = given()
                .get("/user?page=2");
        Headers headers = response.getHeaders();
        int statusCode = response.getStatusCode();
        String body = response.getBody().asString();
        String contentType = response.getContentType();

        assertThat(statusCode, equalTo(200));
        System.out.println("body:" + body);
        System.out.println("content type" + contentType);
        System.out.println("Headers"+ headers.toString());
    }
/*
    @Test
    public void getAllusers(){
        String response =
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract()
                .body()
                .asString();



        int page = from(response).get("page");
        int totalPages = from(response).get("total_pages");

        int idFirstUser = from(response).get("data[0].id");

        System.out.println("page:" + page);
        System.out.println("total pages:" + totalPages);
        System.out.println("id first user: " + idFirstUser);

        List<Map> userWithIdGreaterThan10 = from(response).get("data.findAll { user-> user.id>10 }");
        String email = userWithIdGreaterThan10.get(0).get("email").toString();
        List<Map> user = from(response).get("data.findAll { user -> user.id >10 && user.last_name ==Howell } ");
        int id = Integer.parseInt(user.get(0).get("id").toString());
    }

    @Test
    public void createUSer() {

        String response = given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .post("users")
                .then()
                .extract()
                .body()
                .asString();

        User user = from(response).getObject("", User.class);
        System.out.println(user.getId());
        System.out.println(user.getJob());

    }*/


    @Test
    public void registerUser() {

        Sesion user = new Sesion();
        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");

        ResponseRegister response = given()
                .when()
                .body(user)
                .post("register")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .body()
                .as(ResponseRegister.class);

        assertThat(response.getId(), equalTo(4));
        assertThat(response.getToken(), equalTo("QpwL5tke4Pnpja7X4"));
    }


}
