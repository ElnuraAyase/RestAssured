package smokeTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Location;
import models.Orders;
import models.OrderItemData; //added to read new code
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.apache.http.HttpStatus.*;
import java.math.BigDecimal;

import java.util.List;

import static io.restassured.RestAssured.*;


public class OrdersTest extends BaseTest {

    protected static String createdPurchaseId; // old id for crud operation

    @BeforeClass
    void setUpURI(){
//        TestDataInitializer.initData();
     RestAssured.baseURI = ordersEndpoint;
    }


    @Test
    public void createOrders() {  // post 1

        BigDecimal price = new BigDecimal("50.00");
        BigDecimal subTotal = new BigDecimal("50.00");

//    fetching the created product with ID
      // hashed now  String productId = smokeTests.BookstoreTest.getCreatedProductId();

        OrderItemData item = new OrderItemData(
                "d215b5f8-0249-4dc5-89a3-51fd148cfb48", //uses the product id
                1,
                price,
                subTotal
        );


        Orders order = Orders.builder()
                .customerId("f5fabcb7-0a0f-4d90-8735-d7211797e713")  //1
                .bookstoreId("d215b5f8-0249-4dc5-89a3-51fd148cfb45") //2



                .price(subTotal) //3
                .items(List.of(item)) //4
                .address(new Location("Prosta 4", "Warsaw", "01-2323")) //5
                .build();
        


        createdPurchaseId =
                given(getSpec())
                    .body(order)  // Serialization of the order object
                .when()
                    .post("/orders")
                .then()
                    .log().all()
                    .statusCode(SC_OK)
                    .extract()
                    .asString();
        System.out.println("Created order ID: " + createdPurchaseId); // printed for debuging



    }

    @Test(dependsOnMethods = {"createOrders"})
    public void fetchOrdersById() { // get 1

        System.out.println(createdPurchaseId);
        Response response = given(getSpec())
                //.contentType(ContentType.JSON)
                //.header("Authorization","Bearer " + token)
                .when()
                .get("/orders/" + "6ec2d031-e557-4e18-9cc8-6d5d5208fc02");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 200 OK expected
    }
}
