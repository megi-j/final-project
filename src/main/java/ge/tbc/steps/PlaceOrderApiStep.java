package ge.tbc.steps;

import ge.tbc.data.Constants;
import ge.tbc.models.PlaceOrderForPet;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PlaceOrderApiStep {
    private Response response;

    public PlaceOrderApiStep postOrderForPet(PlaceOrderForPet orderRequest){
        response = RestAssured.given()
                .baseUri(Constants.BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(orderRequest)
                .when()
                .post("/store/order");
        return this;
    }

    public Response getResponse() {
        return response;
    }
    public PlaceOrderApiStep getOrderById(int orderId){
        response = RestAssured.given()
                .baseUri(Constants.BASE_URL)
                .accept(ContentType.JSON)
                .when()
                .get("/store/order/" + orderId);
        return this;
    }

}
