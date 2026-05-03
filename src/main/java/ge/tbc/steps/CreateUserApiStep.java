package ge.tbc.steps;

import ge.tbc.data.Constants;
import ge.tbc.models.CreateUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserApiStep {
     private Response response;

     public CreateUserApiStep postCreateUser(CreateUser userRequest){
         response = RestAssured.given()
                 .baseUri(Constants.BASE_URL)
                 .contentType(ContentType.JSON)
                 .accept(ContentType.JSON)
                 .body(userRequest)
                 .when()
                 .post("user");
         return this;
     }
    public Response getResponse() {
        return response;
    }
    public CreateUserApiStep getUserByUsername(String username){
         response = RestAssured.given()
                 .baseUri(Constants.BASE_URL)
                 .accept(ContentType.JSON)
                 .when()
                 .get("user/" + username);
         return this;
    }
    public  CreateUserApiStep putUserPhone(String username, CreateUser updateRequest){
         response = RestAssured.given()
                 .baseUri(Constants.BASE_URL)
                 .contentType(ContentType.JSON)
                 .accept(ContentType.JSON)
                 .body(updateRequest)
                 .when()
                 .put("user/" + username);
         return this;
    }
    public CreateUserApiStep getLoginUser(String username, String password){
         response = RestAssured.given()
                 .baseUri(Constants.BASE_URL)
                 .accept(ContentType.JSON)
                 .when()
                 .get("user/login?username=" + username + "&password=" + password);
         return this;

    }
}
