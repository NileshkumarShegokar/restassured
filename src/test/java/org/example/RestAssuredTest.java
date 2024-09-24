package org.example;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

class RestAssuredTest {

    // Base URI of the API
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";

    static {
	// Setting Base URI
	baseURI = BASE_URI;
    }

    @Test
    void testGetPosts() {
	// Sending GET request to /posts endpoint
	given()
		.when()
		.get("/posts")
		.then()
		.statusCode(200)  // Assert that the status code is 200
		.body("size()", greaterThan(0)) // Assert that the response contains a non-empty array
		.body("[0].userId", equalTo(1)); // Assert that the userId of the first post is 1
    }

    @Test
    void testCreatePost() {
	// Preparing the request
	RequestSpecification request = given()
		.header("Content-Type", "application/json")
		.body("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}");

	// Sending POST request to /posts endpoint
	Response response = request.post("/posts");

	// Asserting the response status code and content
	response.then().statusCode(201)
		.body("title", equalTo("foo"))
		.body("body", equalTo("bar"))
		.body("userId", equalTo(1));
    }

    @Test
    void testUpdatePost() {
	// Preparing the request
	RequestSpecification request = given()
		.header("Content-Type", "application/json")
		.body("{\"id\":1,\"title\":\"foo updated\",\"body\":\"bar updated\",\"userId\":1}");

	// Sending PUT request to /posts/1 endpoint
	Response response = request.put("/posts/1");

	// Asserting the response status code and content
	response.then().statusCode(200)
		.body("title", equalTo("foo updated"))
		.body("body", equalTo("bar updated"));
    }

    @Test
    void testDeletePost() {
	// Sending DELETE request to /posts/1 endpoint
	given()
		.when()
		.delete("/posts/1")
		.then()
		.statusCode(200); // Assert that the status code is 200
    }
}
