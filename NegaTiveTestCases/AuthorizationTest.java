package NegaTiveTestCases;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import smokeTests.BaseTest;


import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;


public class AuthorizationTest extends BaseTest {


    private final String invalidToken = "invalid_token_123";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = bookstoreEndpoint;
    }
    @Test
    public void testAuthorizationWithValidToken() {
        given(getSpec())
                .when()
                .get("/bookstores")
                .then()
                .log().all()
                .statusCode(SC_OK);  //200
    }

    @Test
    public void testAuthorizationWithInvalidToken() {
//
//        RequestSpecification invalidSpec = new RequestSpecBuilder()
//                .setContentType(ContentType.JSON)
//                .addHeader("Authorization", "Bearer " + invalidToken)
//                .build();

        given()  // Use modified spec for invalid token
                .header("Authorization", "Bearer " + invalidToken)
                .when()
                .get("/bookstores")
                .then()
                .log().all()  //
                .statusCode(SC_FORBIDDEN);  //changed from 400

    }

    @Test
    public void testAuthorizationWithNoToken () {

        RequestSpecification noTokenSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();  // No Authorization header

            given()  //
                .when()
                .get("/bookstores")
            .then()
                .log().all()  //
                .statusCode(SC_FORBIDDEN);  //  403
    }
}
