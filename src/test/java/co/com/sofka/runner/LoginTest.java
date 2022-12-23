package co.com.sofka.runner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

class LoginTest {

    @Test
     void loginVersion1(){
        String response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "   \"email\": \"eve.holt@reqres.in\", \n" +
                        "   \"password\": \"cityslicka\"\n"+
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .extract().asString();
        System.out.println(response);
    }

     @Test
     void loginVersion2(){
         RestAssured
                 .given()
                 .contentType(ContentType.JSON)
                 .body("{\n" +
                         "   \"email\": \"eve.holt@reqres.in\", \n" +
                         "   \"password\": \"cityslicka\"\n"+
                         "}")
                 .post("https://reqres.in/api/login")
                 .then()
                 .statusCode(200)
                 .body("token",notNullValue());
     }

    @Test
    void loginVersion3(){
        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "   \"email\": \"eve.holt@reqres.in\", \n" +
                        "   \"password\": \"cityslicka\"\n"+
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token",notNullValue());
    }


    @Test
    void loginVersion4(){
        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "   \"email\": \"eve.holt@reqres.in\", \n" +
                        "   \"password\": \"cityslicka\"\n"+
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_MULTIPLE_CHOICES)
                .body("token",notNullValue());
    }
    @Test
    void loginVersion5(){
        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "   \"email\": \"peter@klaven\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("error",equalTo("Missing password"));
    }

    @Test
    void loginVersion6(){
        RestAssured
                .given()
                .log().all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "   \"email\": \"peter@klaven\"\n" +
                        "}")
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("password"));
    }


}
