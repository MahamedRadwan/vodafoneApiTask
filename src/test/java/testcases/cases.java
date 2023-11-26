package testcases;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class cases {


    @BeforeMethod
    void baseUrl() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @Test
    public void getAllPosts() {
        Response getresponse = RestAssured.get("posts");
        int statusCode = getresponse.statusCode();
        String body = getresponse.getBody().asString();
        System.out.println("Status Code:  " + statusCode);
        System.out.println("Response Body :  " + body);
    }

    @Test
    public void getUserTitle() {
        Response getresponse = RestAssured.get("posts/1?userId=1");

        int statusCode = getresponse.statusCode();
        String title = getresponse.body().path("title").toString();
        System.out.println("Status Code:  " + statusCode);
        System.out.println("User One First Post Title :  " + title);
    }


    @Test
    public void getUserWithId() {
        Response getresponse = RestAssured.get("users/1");
        int statusCode = getresponse.statusCode();
        String body = getresponse.getBody().asString();
        System.out.println("Status Code:  " + statusCode);
        System.out.println("Response Body :  " + body);
    }

    @Test
    public void createPost() {
        String jsonString = "{\"title\": \"test test \", \"body\": \"test test test \" , \"userId\": \"1\"}";
        JsonObject requestBody = JsonParser.parseString(jsonString).getAsJsonObject();
        Response response = RestAssured.given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("posts");

        response.then().body("id", equalTo(101));
        response.then().statusLine(equalTo("HTTP/1.1 201 Created"));

    }

    @Test
    public void deletePost() {
        Response response = RestAssured.given().when().delete("posts/50");
        response.then().statusLine(equalTo("HTTP/1.1 200 OK"));


    }

}
