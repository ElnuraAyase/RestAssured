package smokeTests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import models.TestTokens;
import org.testng.annotations.BeforeClass;

/**
 * smokeTests.BaseTest provides shared setup for all test classes.
 */
public class BaseTest {
//token removed to env and gitignored
@Getter
public static String token = System.getenv("TOKEN");  //  changes upon Viktoryias comment to change the token


    protected String bookstoreEndpoint = "http://localhost:8183/api/v1";
    protected String customerEndpoint = "http://localhost:8184/api/v1";
    protected String ordersEndpoint = "http://localhost:8181/api/v1";
    protected String paymentsEndpoint = "http://localhost:8182/api/v1";


    protected static  String createdBookstoreId;
    protected static  String createdCustomerId;
    protected static  String createdPurchaseId;
    protected static  String createdPaymentId;
    protected static  String createdProductId;

// TODO : These three methods are not returning the desired values.

//    String bookstoreId = smokeTests.BookstoreTest.getCreatedBookstoreId();
//    String productId = smokeTests.BookstoreTest.getCreatedProductId();
//    String customerId =  smokeTests.CustomerTest.getCreatedCustomerId();




  public static String getCreatedBookstoreId() {
    return createdBookstoreId;
  }
  public static String getCreatedProductId() {
    return createdProductId;
  }
  public static String getCreatedCustomerId() {
    return createdCustomerId;
  }

  // protected RequestSpecification spec; //the default one

    @BeforeClass
    public RequestSpecification getSpec(){   //setSpec doesn't pass info to the response in other class
        return new RequestSpecBuilder()    // change void to return
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .build();

    }
//
//    public RequestSpecification getSpecWithToken(String token) {
//        return new RequestSpecBuilder()
//                .setContentType(ContentType.JSON)
//                .addHeader("Authorization", "Bearer " + token) // Use the passed token
//                .build();
//    }
}