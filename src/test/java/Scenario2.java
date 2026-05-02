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
    @Test
    public void updatedUserTest(){
        RestAssured.useRelaxedHTTPSValidation();

        CreateUser originalUser = new CreateUser(
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

        // POST - მომხმარებლის შექმნა
        step.postCreateUser(originalUser);
        Assert.assertEquals(step.getResponse().getStatusCode(), 200);


        // ვცვლი მხოლოდ phone-ს
        CreateUser updatedUser = new CreateUser(
                originalUser.getId(),
                originalUser.getUsername(),
                originalUser.getFirstName(),
                originalUser.getLastName(),
                originalUser.getEmail(),
                originalUser.getPassword(),
                "+995595783283",
                originalUser.getUserStatus()
        );

        // PUT
        Response putResponse = step
                .putUserPhone(originalUser.getUsername(), updatedUser)
                .getResponse();

        Assert.assertEquals(putResponse.getStatusCode(),
                200,
                "PUT method did not return status code 200");

        //GET
        Response getResponse = step
                .getUserByUsername(originalUser.getUsername())
                .getResponse();

        CreateUser getResponseBody = getResponse.as(CreateUser.class);
        //GET assertions
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Status code is not 200");
        Assert.assertEquals(getResponseBody.getPhone(), "+995595783283", "phone number does not updated correctly");
        System.out.println(getResponseBody);

    }

}