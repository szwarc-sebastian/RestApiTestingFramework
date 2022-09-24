package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.ApiResourcesEnum;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinition extends Utils {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;

    TestDataBuild data = new TestDataBuild();

    @Given("AddPlaceAPI payload with name {string}, language {string} and address {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {
        requestSpecification = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String api, String method) {
        responseSpecification =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if (method.equalsIgnoreCase("POST")) {
            response = requestSpecification.when().post(ApiResourcesEnum.valueOf(api).getResource());
        }
        else if (method.equalsIgnoreCase("GET")) {
            response = requestSpecification.when().get(ApiResourcesEnum.valueOf(api).getResource());
        }

    }

    @Then("the API call got success with status code {int}")
    public void the_api_call_got_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
        assertEquals(getJsonPath(response, key), expectedValue);
    }

    @And("verify place_Id created maps to {string} using {string}")
    public void verifyPlace_IdCreatedMapsToUsing(String expectedName, String resource) throws IOException {
        String placeId = getJsonPath(response, "place_id");
        requestSpecification = given().spec(requestSpecification()).queryParam("place_id", placeId);
        user_calls_with_http_request(resource, "GET");
        String name = getJsonPath(response, "name");
        assertEquals(name, expectedName);
    }
}
