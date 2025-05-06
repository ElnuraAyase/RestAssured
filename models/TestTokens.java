package models;

import lombok.Getter; //@Getter to get

@Getter  //used


public class TestTokens {

    private final String valid;
    private final String invalid;
    private final String expired;
    private final String malformed;

    //  * constructor to change token types
    public TestTokens(String validToken) {
//        if (validToken == null || validToken.isEmpty()) {
//            throw new IllegalArgumentException("vllid token cannot be null or empty");
//        }
        this.valid = validToken;  // Set valid token
        this.invalid = makeInvalidToken(validToken);  // Derive invalid token
        this.expired = makeExpiredToken(validToken);  // Derive expired token
        this.malformed = "malformed.token.format";  // Simulated malformed token
    }

    //helper method to create an invalid token
    private String makeInvalidToken(String token) {
        return token.substring(0, Math.max(0, token.length() - 5)) + "elya21"; //changing the last part
    }

    // method expired token
    private String makeExpiredToken(String token) {
        return token + ".expired"; // + ".expired" for  expired token
    }
}
