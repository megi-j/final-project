import ge.tbc.data.Constants;
import ge.tbc.models.PlaceOrderForPet;
import ge.tbc.steps.PlaceOrderApiStep;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scenario1NegativeTest {
    @Test
    public void placeOrderWithInvalidId(){
        RestAssured.useRelaxedHTTPSValidation();

        String invalidBody = """
            {
              "id": "test",
              "petId": 10,
              "quantity": 1,
              "shipDate": "2026-05-01T19:22:22.917Z",
              "status": "placed",
              "complete": true
            }
            """;
        PlaceOrderApiStep step = new PlaceOrderApiStep();
        Response postResponse = step
                .postOrderForPet(invalidBody)
                .getResponse();


        Assert.assertEquals(postResponse.getStatusCode(), 500);
        Assert.assertEquals(postResponse.jsonPath().getString("message"), "something bad happened");
    }
    @Test
    public void placeOrderWithInvalidPetId(){
        RestAssured.useRelaxedHTTPSValidation();

        String invalidBody = """
            {
              "id": 6,
              "petId": "test",
              "quantity": 1,
              "shipDate": "2026-05-01T19:22:22.917Z",
              "status": "placed",
              "complete": true
            }
            """;
        PlaceOrderApiStep step = new PlaceOrderApiStep();
        Response postResponse = step
                .postOrderForPet(invalidBody)
                .getResponse();


        Assert.assertEquals(postResponse.getStatusCode(), 500);
        Assert.assertEquals(postResponse.jsonPath().getString("message"), "something bad happened");
    }
}
