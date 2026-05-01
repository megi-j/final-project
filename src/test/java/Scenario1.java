import ge.tbc.models.PlaceOrderForPet;
import ge.tbc.steps.PlaceOrderApiStep;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Scenario1 {
    @Test
    public void placeOrderTest(){
        RestAssured.useRelaxedHTTPSValidation();
        PlaceOrderForPet request = new PlaceOrderForPet(
                11,
                10,
                1,
                "2026-05-01T19:22:22.917Z",
                "placed",
                true
        );


        PlaceOrderApiStep step = new PlaceOrderApiStep();
        Response response = step
                .postOrderForPet(request)
                .getResponse();




        Assert.assertEquals(response.getStatusCode(), 200, "Status Code is not 200");


        PlaceOrderForPet responseBody =
                response.as(PlaceOrderForPet.class);

        Assert.assertEquals(responseBody.getId(), 11);
        Assert.assertEquals(responseBody.getPetId(), 10);
        Assert.assertEquals(responseBody.getQuantity(), 1);
        Assert.assertEquals(responseBody.getStatus(), "placed");
    }
}
