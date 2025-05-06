package smokeTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;


public class PaymentsTest extends BaseTest {
    //  to store payment changes today
    private static String createdPaymentId;

    // Override setup for specific base URI for this test class
    @BeforeClass
    void setUpURI() {
        RestAssured.baseURI = paymentsEndpoint;

    }

    @Test()
    public void topUpBalance() {  // post 1
        String requestBody = """
                    {
                      "customerId": "d215b5f8-0249-4dc5-89a3-51fd148cfb41",
                      "totalPrice": 70
                    }
                """;

        Response response = given(getSpec())
                //.contentType(ContentType.JSON)
                //.header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/payments/credit"); // Post request to create a new bookstore

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201); // 201 Created expected

        // storing the retunr id from response
        createdPaymentId = response.jsonPath().getString("creditEntryId");


        System.out.println("Created customer ID: " + createdPaymentId);
    }

    @Test
    public void getPaymentById() {
        Response response =
                given(getSpec())
                .when()
                .get("/payments/" + "6f483071-779b-46db-b4b9-174392e5bb80"); // get for payments order ID

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 201 Created expected

    }

    @Test
    public void getHisoryPaymentById() {
        Response response =

                // in changes
                given(getSpec())
                .when()
                .get("/payments/credit/history/" + "6f483071-779b-46db-b4b9-174392e5bb80"); // get for credit history info by customer ID

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 201 Created expected

    }
}