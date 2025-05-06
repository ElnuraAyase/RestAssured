package models;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Tokens {

    private String validToken;



    public Tokens(String validToken) {
        this.validToken = validToken;
    }

    public String getValid() {
        return validToken;  // Return the valid token
    }

    public String getInvalid() {
        // nvalid
        return "invalid_tokenElya21";
    }


    public RequestSpecification getSpecWithToken(String token) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token) // Use the passed token
                .build();
    }
}
