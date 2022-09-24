package resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    private static RequestSpecification req;

    public RequestSpecification requestSpecification() throws IOException {
        RestAssured.baseURI="https://rahulshettyacademy.com";

        if (req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("request_response_log.txt"));
            req =new RequestSpecBuilder().setBaseUri(getGlobalPropertyValue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return req;
    }

    public static String getGlobalPropertyValue(String key) throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/test/java/resources/global.properties");
        properties.load(fileInputStream);
        return properties.getProperty(key);
    }

    public static String getJsonPath(Response response, String key) {
        String responseString = response.asString();
        JsonPath js = new JsonPath(responseString);
        return js.get(key).toString();
    }
}
