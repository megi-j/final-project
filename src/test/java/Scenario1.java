import ge.tbc.models.PlaceOrderForPet;
import ge.tbc.steps.PlaceOrderApiStep;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;



public class Scenario1 {
    @Test
    public void placeOrderTest(){
        RestAssured.useRelaxedHTTPSValidation();
        PlaceOrderForPet request = new PlaceOrderForPet(
                8,
                10,
                1,
                "2026-05-01T19:22:22.917Z",
                "placed",
                true
        );

        PlaceOrderApiStep step = new PlaceOrderApiStep();
        //POST
        Response postResponse = step
                .postOrderForPet(request)
                .getResponse();

        PlaceOrderForPet postResponseBody = postResponse.as(PlaceOrderForPet.class);
         //POST assertions
        Assert.assertEquals(postResponseBody.getId(), request.getId());
        Assert.assertEquals(postResponseBody.getPetId(), request.getPetId());
        Assert.assertEquals(postResponseBody.getQuantity(), request.getQuantity());
        Assert.assertEquals(postResponseBody.getStatus(), request.getStatus());
        Assert.assertEquals(postResponse.getStatusCode(), 200, "Status Code is not 200");


        int orderId = postResponseBody.getId();
        //GET
        Response getResponse = step
                .getOrderById(orderId)
                        .getResponse();



        PlaceOrderForPet getResponseBody = getResponse.as(PlaceOrderForPet.class);

        //GET Assertions
        Assert.assertEquals(getResponseBody.getId(), orderId);
        Assert.assertEquals(getResponseBody.getPetId(), 10);
        Assert.assertEquals(getResponseBody.getQuantity(), 1);
        Assert.assertEquals(getResponseBody.getStatus(), "placed");
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Status code is not 200");

        //DELETE
        Response deleteResponse = step
                .deteleteOrderById(orderId)
                .getResponse();

        // DELETE assertions
        Assert.assertEquals(deleteResponse.getStatusCode(), 200, "Status code is not 200");
    }
}
