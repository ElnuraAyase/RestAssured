package smokeTests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import models.Bookstore;
import models.Products;

import java.math.BigDecimal;

import static org.apache.http.HttpStatus.*;


import static io.restassured.RestAssured.*;



public class BookstoreTest extends BaseTest {


    private static String createdBookstoreId;  // upon Viktoryias comment to Consider to have CRUD test flow

    public static String getCreatedBookstoreId() {
        return createdBookstoreId;
    }

    private static String createdProductId;  // var to hold the ID for post,delete put

    public static String getCreatedProductId() {
        return createdProductId;
    }


    @BeforeClass
    void setUpURI(){
        RestAssured.baseURI = bookstoreEndpoint;
    }


    @Test
    public void fetchAllBookStores() {  // get 3
        given(getSpec())
        .when()
                .get("/bookstores")
        .then()
                .log().all()
                .statusCode(SC_OK);
    }

    @Test(dependsOnMethods = {"fetchAllBookStores"})
    public void fetchBookStoreById() { // get 1
        if (createdBookstoreId == null || createdBookstoreId.isEmpty()){
            throw new RuntimeException("created bookstore id is not null or empty !");
        }
//   String bookstoreId = "d215b5f8-0249-4dc5-89a3-51fd148cfb45";  // static ID fpr t esting
//changes upon Viktoryia's comment  to consider to use HttpStatus
        given(getSpec())
            .when()
                .get("/bookstores/" + createdBookstoreId)
            .then()
                .log().all()
            .statusCode(SC_OK); //changes upon Viktoryia's comment to consider using HttpStatus
    }

    @Test()
    public void createBookStore() {  // post 1

        Bookstore bookstore = new Bookstore("New Bookstore", true);


        String responseBody =
        given(getSpec())
                .body(bookstore) // added serialization
        .when()
                .post("/bookstores")
        .then()
                .log().all()
                .statusCode(SC_CREATED) //changes upon Viktoryia's comment to consider using HttpStatus
                .extract()
                .asString();


        createdBookstoreId = responseBody.replaceAll("\"", "");//reoving extra "" quots around  id


        System.out.println("Created bookstore ID: " + createdBookstoreId); // printed for debuging
    }

    @Test(dependsOnMethods = {"createBookStore"})
    public void updateBookStoreById(){ //  put 2

        // *** error was with this test, so need IF to check if the cretedBookstoreId is correct
        if (createdBookstoreId == null || createdBookstoreId.isEmpty()) {
            throw new RuntimeException("Created bookstore ID is null or empty! Please check createBookStore() method.");
        }
        Bookstore updatedBookstore = new Bookstore("Updated bookstore", true);


        System.out.println("Updating bookstore with ID: " + createdBookstoreId); // checked
        //added serialization changes upon Viktoryias comment
        System.out.println("Update Body: " + updatedBookstore);

                given()
                    .spec(getSpec())
                    .body(updatedBookstore)//added serialization changes upon Viktoryias comment
                .when()
                    .put("/bookstores/" + createdBookstoreId)
                .then()
                    .log().all()
                    .statusCode(SC_NO_CONTENT);//changes upon Viktoryia's comment to consider using HttpStatus

    }

    @Test(dependsOnMethods = {"updateBookStoreById", "deleteProductById"}) // can not delete product if the bookstore was deleted before , so  added "deleteProductId"
    public void deleteBookStoreId() { //delete 1


        given(getSpec())
            .when()
            .delete("/bookstores/" + createdBookstoreId)
        .then()
            .log().all()
            .statusCode(SC_NO_CONTENT); //changes upon Viktoryia's comment to consider using HttpStatus

    }


    // GET all products
    @Test
        public void fetchAllProducts() {

        given(getSpec())
        .when()
            .get("/bookstores/product")
        .then()
            .log().all()
            .statusCode(SC_OK);

    }


    // POST a new product
    @Test(dependsOnMethods = {"createBookStore"})
    public void createProduct() {  // post - new product
        BigDecimal price = new BigDecimal("19.99");  // used BigDecimal instead of double

        Products products = new Products("New Product", price, true);

        createdProductId =
        given(getSpec())
            .body(products) //added serialization changes upon Viktoryias comment
        .when()
            .post("/bookstores/" + createdBookstoreId + "/product")
        .then()
            .log().all()
            .statusCode(SC_CREATED)
                    .extract()
                    .asString()
                .replace("\"", "");;

        System.out.println(createdProductId);
    }


    // GET product by ID
    @Test(dependsOnMethods = {"fetchAllProducts"})
    public void fetchProductById() {  // get product by ID
        if (createdProductId ==null || createdProductId.isEmpty()){
            throw new RuntimeException("created product is null or emppty !");

        }


//        String productId = "d215b5f8-0249-4dc5-89a3-51fd148cfb47";  // Some product ID for testing



        given(getSpec())
        .when()
            .get("bookstores/product/" + createdProductId)//  changes made upon Viktoryias comment to use CRud test flow
        .then()
            .log().all()
            .statusCode(SC_OK);
    }

    //deleted the post from here to change

    // PUT (update) product by ID
    @Test(dependsOnMethods = {"createBookStore","createProduct"})
    public void updateProductById() {  // put (update) product
        if (createdProductId ==null || createdProductId.isEmpty()){
            throw new RuntimeException(" product is null or empty");
        }
        BigDecimal price = new BigDecimal("29.99");
        Products updatedProduct = new Products("Updated Product Name", price, true);

        given(getSpec())
            .body(updatedProduct) //added serialization changes upon Viktoryias comment
        .when()
            .put("/bookstores/product/" + createdProductId)
        .then()
                .log().all()
                .statusCode(SC_NO_CONTENT);


    }

// *** new changes , depends on update by prod ID
    @Test(dependsOnMethods = {"createBookStore", "createProduct", "updateProductById"})
    public void deleteProductById() {  // delete product 10


//        if (createdBookstoreId == null || createdBookstoreId.isEmpty()){
//            throw new RuntimeException("bookstore ID is null or empty ");
//        }
//        if (createdProductId == null || createdProductId.isEmpty()){
//            throw new RuntimeException((" product ID is null  or empty"));
//        }

        given(getSpec())
            .when()
            .delete("/bookstores/" + createdBookstoreId + "/product/" + createdProductId)
        .then()
            .log().all()
            .statusCode(SC_NO_CONTENT);
    }

    @Test
    public void createBookstoreWithNullValues(){
        Bookstore bookstore = new Bookstore(null, null);

        given(getSpec())
            .body(bookstore)
        .when()
            .post("/bookstores")
        .then()
            .log().all()
            .statusCode(SC_BAD_REQUEST);// 400

    }
}


