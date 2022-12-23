package co.com.sofka.runner;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LoginTestSimple {

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "api/";
        RestAssured.filters(new RequestLoggingFilter(),new ErrorLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
     void loginVersion1(){
        String response = RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"eve.holt@reqres.in\", \n" +
                        "   \"password\": \"cityslicka\"\n"+
                        "}")
                .post("login")
                .then()
                .extract().asString();
        System.out.println(response);
    }

     @Test
     void loginVersion2(){
         RestAssured
                 .given()
                 .body("{\n" +
                         "   \"email\": \"eve.holt@reqres.in\", \n" +
                         "   \"password\": \"cityslicka\"\n"+
                         "}")
                 .post("login")
                 .then()
                 .statusCode(200)
                 .body("token",notNullValue());
     }

    @Test
    void loginVersion3(){
        RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"eve.holt@reqres.in\", \n" +
                        "   \"password\": \"cityslicka\"\n"+
                        "}")
                .post("login")
                .then()
                .statusCode(200)
                .body("token",notNullValue());
    }


    @Test
    void loginVersion4(){
        RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"eve.holt@reqres.in\", \n" +
                        "   \"password\": \"cityslicka\"\n"+
                        "}")
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_MULTIPLE_CHOICES)
                .body("token",notNullValue());
    }
    @Test
    void loginVersion5(){
        RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"peter@klaven\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error",equalTo("Missing password"));
    }

    @Test
    void loginVersion6(){
        RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"peter@klaven\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("password"));
    }

    @Test
    void loginVersion7(){
        String response = RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"peter@klaven\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .jsonPath()
                .getString("error");

        //Validacion usando Assertions de Junit
        Assertions.assertEquals("Missing password",response);
    }

    @Test
    void loginVersion8(){
        String response = RestAssured
                .given()
                .body("{\n" +
                        "   \"email\": \"peter@klaven\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .jsonPath()
                .getString("error");

        //Validacion usando Hamcrest
        assertThat(response,equalTo("Missing password"));
    }

}
