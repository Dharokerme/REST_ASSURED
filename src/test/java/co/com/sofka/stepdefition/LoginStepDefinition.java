package co.com.sofka.stepdefition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginStepDefinition extends BasePage {

    private RequestSpecification request;
    private Response response;

    @Given("el usuario esta en la pagina de inicio de sesion con el correo de usuario {string} y la contrasena {string}")
    public void elUsuarioEstaEnLaPaginaDeInicioDeSesionConElCorreoDeUsuarioYLaContrasena(String name, String password) {
        try {
            generalSetUp();
            request =   given().body("{\n" +
                    "   \"email\": \"eve.holt@reqres.in\", \n" +
                    "   \"password\": \"cityslicka\"\n"+
                    "}");

        } catch (Exception e) {
            Assertions.fail(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    @When("el usuario presiona el boton de inicio de sesion")
    public void elUsuarioPresionaElBotonDeInicioDeSesion() {
        try{
            response = request.post(RESOURCE);
        } catch (Exception e){
            Assertions.fail(e.getMessage());
        }
    }

    @Then("el usuario deberia ver un codigo de repuesta exitoso y un token de respuesta")
    public void elUsuarioDeberiaVerUnCodigoDeRepuestaExitosoYUnTokenDeRespuesta() {
        try{
            response
                    .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("token",notNullValue());
        }catch (Exception e){
            Assertions.fail(e.getMessage());
        }
    }
}
