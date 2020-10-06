package com.demo.primalitycheck;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@IntegrationTest
@DisplayName("primality-check endpoint")
class PrimalityCheckIntegrationTest {

    @Value("${local.server.port}")
    private int serverPort;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    @DisplayName("Should respond with a json, telling if the provided number is a prime and the next prime")
    void success() {
        given().queryParam("number", 2)
                .when().get("/primality-check")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("isPrime", is(true))
                .body("nextPrime", is(2));
    }

    @Test
    @DisplayName("Should return 400 if the 'number' parameter is too big")
    void inputIsTooBig() {
        given().queryParam("number", "9223372036854775808")
                .when().get("/primality-check")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Parameter 'number' must be between -2^63 and 2^63-1. Received: 9223372036854775808"));
    }

    @Test
    @DisplayName("Should return 400 if the 'number' parameter is not an integer")
    void inputIsNotAnInteger() {
        given().queryParam("number", "abc")
                .when().get("/primality-check")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Parameter 'number' must be an integer"));
    }

    @Test
    @DisplayName("Should return 400 if the 'number' parameter is missing")
    void inputIsMissing() {
        given().when().get("/primality-check")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Missing required parameter 'number'"));
    }
}
