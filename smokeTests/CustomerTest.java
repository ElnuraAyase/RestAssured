package smokeTests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import models.Customers;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;


public class CustomerTest extends BaseTest {

    // Override setup for specific base URI for this test class
    @BeforeClass
    void setUpURI(){
        RestAssured.baseURI = customerEndpoint;
    }




    @Test()
    public void createCustomers() {  // post 1

        Customers customers = new Customers("77author", "Mark", "Twain");

        createdCustomerId =
                given(getSpec())
                        .body(customers) //added serialization changes upon Viktoryias comment
                .when()
                        .post("/customers") // Post request to create a new customers
                .then()
                    .log().all()
                    .statusCode(SC_CREATED)
                    .extract()
                            .path("customerId");


        System.out.println("Created customer ID: " + createdCustomerId); // printed for debuging
    }

    @Test
    public void fetchAllCustomers() {  // get all

                given(getSpec())
                .when()
                    .get("/customers") // Relative endpoint
                .then()
                .log().all()
                        .statusCode(SC_OK);

    }

    @Test(dependsOnMethods = {"createCustomers"})
    public void fetchCustomersById() { // get 1

        System.out.println(createdCustomerId);

                given(getSpec())
                .when()
                    .get("/customers/" + createdCustomerId) // Fetch specific cust by ID
                .then()
                .log().all()
                .statusCode(SC_OK);

    }

    // PUT (update) customers by ID

    @Test(dependsOnMethods = {"createCustomers"})
    public void updateCustomers() {  // post 1
        Customers customers = new Customers("login22", "Mark", "Gauda");

                given(getSpec())
                    .body(customers)
                .when()
                    .put("/customers/" + createdCustomerId)
                .then()
                    .log().all()
                    .statusCode(SC_NO_CONTENT);





        System.out.println("updated customer ID: " + createdCustomerId); // printed for debuging
    }
    // delete customers

    @Test(dependsOnMethods = {"createCustomers", "updateCustomers"})
    public void deleteProductById() {  // delete product 10

                given(getSpec())
                .when()
                    .delete("/customers/" + createdCustomerId)
                .then()
                    .log().all()
                    .statusCode(SC_OK);
    }

// checking unautho clients full scope tests
    @Test(dependsOnMethods = {"createCustomers"})
    public void unauthorizedCustomerAccess() {
        given()
                .auth().basic("invalid", "credentials")
                .log().all()
        .when()
            .get("/customers")
        .then()
            .log().all()
            .statusCode(SC_FORBIDDEN);  // 403 forbidden
    }

}
