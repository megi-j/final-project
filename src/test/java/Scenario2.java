import ge.tbc.models.CreateUser;
import ge.tbc.steps.CreateUserApiStep;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Scenario2 {
    @Test
    public void createUserTest() {
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

        String username = request.getUsername();

        //GET
        Response getResponse = step
                .getUserByUsername(username)
                .getResponse();

        CreateUser getResponseBody = getResponse.as(CreateUser.class);
        //GET assertions
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Status code is not 200");
        Assert.assertEquals(getResponseBody.getId(), 3);
        Assert.assertEquals(getResponseBody.getUsername(), username);
        Assert.assertEquals(getResponseBody.getFirstName(), "megi");
        Assert.assertEquals(getResponseBody.getLastName(), "jabanashvili");
        Assert.assertEquals(getResponseBody.getEmail(), "jabanashvilimegi@gmail.com");
        Assert.assertEquals(getResponseBody.getPassword(), "Aa123123#");
        Assert.assertEquals(getResponseBody.getPhone(), "595783283");
        Assert.assertEquals(getResponseBody.getUserStatus(), 1);

    }
}