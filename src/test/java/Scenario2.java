import ge.tbc.models.CreateUser;
import ge.tbc.steps.CreateUserApiStep;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Scenario2 {
    private CreateUserApiStep step;
    private String username;
    private String password = "Aa123123#";

    @BeforeMethod
    public void setUp(){
        RestAssured.useRelaxedHTTPSValidation();
        step = new CreateUserApiStep();
        username = "user_" + System.currentTimeMillis();
    }
    @Test
    public void createUserTest() {
        CreateUser request = new CreateUser(
                3,
                username,
                "megi",
                "jabanashvili",
                "jabanashvilimegi@gmail.com",
                "Aa123123#",
                "595783283",
                1
        );

        //POST
        Response postResponse = step
                .postCreateUser(request)
                .getResponse();

        Assert.assertEquals(postResponse.getStatusCode(), 200, "Status Code is not 200");

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
        CreateUser originalUser = new CreateUser(
                3,
                username,
                "megi",
                "jabanashvili",
                "jabanashvilimegi@gmail.com",
                "Aa123123#",
                "595783283",
                1
        );
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
    @Test
    public void loginUserTest(){

        CreateUser user = new CreateUser(
                3,
                username,
                "megi",
                "jabanashvili",
                "jabanashvilimegi@gmail.com",
                password,
                "595783283",
                1
        );
        step.postCreateUser(user);
        Assert.assertEquals(step.getResponse().getStatusCode(), 200);

        Response loginResponse = step
                .getLoginUser(username, password)
                .getResponse();

        Assert.assertEquals(
                loginResponse.getStatusCode(),
                200,
                "Status code is not 200"
        );

        String message = loginResponse.jsonPath().getString("message");
        Assert.assertNotNull(
                message,
                "Login message is null"
        );
        Assert.assertFalse(
                message.isEmpty(),
                "Login message is empty"
        );
        System.out.println("Login message: " + message);
    }
    @Test
    public void logoutUserTest(){
        //GET
        Response logoutResponse = step
                .getLogoutUser()
                .getResponse();

        Assert.assertEquals(logoutResponse.getStatusCode(), 200, "Status code is not 200");
        String message = logoutResponse.jsonPath().getString("message");

        Assert.assertEquals(
                message,
                "ok",
                "Logout message is not 'ok'"
        );

        System.out.println("Logout message: " + message);

    }
    @Test
    public void deleteUserTest(){

        CreateUser user = new CreateUser(
                3,
                username,
                "megi",
                "jabanashvili",
                "jabanashvilimegi@gmail.com",
                password,
                "595783283",
                1
        );
        step.postCreateUser(user);
        Assert.assertEquals(step.getResponse().getStatusCode(), 200);



        //DELETE
        Response deleteResponse = step
                .deleteUser(username)
                .getResponse();


        Assert.assertEquals(
                deleteResponse.getStatusCode(),
                200,
                "Status code is not 200"
        );
        String message = deleteResponse.jsonPath().getString("message");


        Assert.assertEquals(
                message,
                username,
                "Delete message is not equal to deleted username"
        );

        System.out.println("Delete message: " + message);

    }
}