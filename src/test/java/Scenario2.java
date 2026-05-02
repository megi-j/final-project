import ge.tbc.models.CreateUser;
import ge.tbc.steps.CreateUserApiStep;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scenario2 {
    @Test
    public void createUserTest(){
        RestAssured.useRelaxedHTTPSValidation();
        CreateUser request = new CreateUser(
                3,
                "meg1",
                "megi",
                "jabanashvili",
                "jabanashvilimegi@gmail.com",
                "Aa123123#",
                "595783283",
                1
        );
        CreateUserApiStep step = new CreateUserApiStep();
        //POST
        Response postResponse = step
                .postCreateUser(request)
                .getResponse();

        Assert.assertEquals(postResponse.getStatusCode(), 200, "Status Code is not 200");

    }
}
