
---

## 📝 **Step-by-Step Manual for Using OOP in Your Test Automation**

### **Step 1: Create the BaseTest Class**
The `BaseTest` class holds common configurations and setup logic shared across multiple test classes.

#### **1.1 Define `BaseTest.java`**

This class will:
- Store common values (like the API token).
- Setup the `baseURI` for RestAssured.
- Share common logic for all test cases.

```java
package rekindle;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

/**
 * BaseTest provides shared setup for all test classes.
 * It demonstrates OOP principles:
 * ✅ Inheritance – other tests inherit this class to reuse setup.
 * ✅ Encapsulation – token is protected, not public.
 * ✅ Abstraction – hides implementation details from the child test classes.
 */
public class BaseTest {

    // ✅ Encapsulation: Protected token field so subclasses can access it.
    protected String token = "eyJ0eXAiOiJKV1QiLCJub25jZ..."; // truncated for readability

    /**
     * ✅ Set up base URI and any shared configurations here.
     * Runs once before any test method in classes extending BaseTest.
     */
    @BeforeClass
    public void setUp() {
        // ✅ Set base URI for API calls (reuse in all child classes)
        RestAssured.baseURI = "http://localhost:8183/api/v1";
        
        // Optional: Relaxed HTTPS validation for local testing environments
        RestAssured.useRelaxedHTTPSValidation();
    }
}
```

### **Explanation of `BaseTest.java` (OOP Principles)**
1. **Inheritance**:  
   The `BaseTest` class can be extended by any test class that requires the setup (`BookstoreTest` extends `BaseTest`). This means no need to repeat setup in every individual test class.
   
2. **Encapsulation**:  
   The `token` is protected. Only subclasses can directly access the token. This keeps your token secure while still making it reusable across tests.

3. **Abstraction**:  
   The setup logic (base URI, token setup) is abstracted away from the individual test classes. This means you don’t need to reconfigure it in every test — you can focus on writing test cases.

### **Step 2: Create the BookstoreTest Class**
The `BookstoreTest` class is where you define your actual test cases for the bookstore API.

#### **2.1 Define `BookstoreTest.java`**

This class will:
- Use the setup from `BaseTest`.
- Perform actual API calls.
- Verify the expected response status.

```java
package rekindle;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

/**
 * BookstoreTest demonstrates:
 * ✅ Inheritance – it extends BaseTest and inherits common setup (token, baseURI).
 * ✅ Code Reuse – test logic only focuses on the specific test scenario.
 */
public class BookstoreTest extends BaseTest {

    @Test
    public void fetchAllBookStores() {
        // ✅ Using inherited token and base URI from BaseTest
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // Token inherited from BaseTest
                .when()
                .get("/bookstores"); // Relative endpoint

        // Assert response status code
        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 200 OK expected
    }

    @Test(dependsOnMethods = {"fetchAllBookStores"})
    public void fetchBookStoreById() {
        String bookstoreId = "d215b5f8-0249-4dc5-89a3-51fd148cfb45";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/bookstores/" + bookstoreId); // Fetch specific bookstore by ID

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 200 OK expected
    }

    @Test(dependsOnMethods = {"fetchAllBookStores"})
    public void createBookStore() {
        String requestBody = """
            {
                "name": "New Bookstore",
                "location": "123 Main Street",
                "owner": "John Doe"
            }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/bookstores"); // Post request to create a new bookstore

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201); // 201 Created expected
    }
}
```

### **Explanation of `BookstoreTest.java` (OOP Principles)**
1. **Inheritance**:  
   By extending `BaseTest`, the `BookstoreTest` inherits all the setup methods and values (like `token`). This means `BookstoreTest` doesn't need to manually set up the base URL or the token every time.

2. **Code Reuse**:  
   The test methods (`fetchAllBookStores`, `fetchBookStoreById`, `createBookStore`) can focus purely on the logic of testing the API endpoints. No need to duplicate setup code.

---

## **Step 3: What It Would Look Like Without OOP (Manual Setup)**
If you **don’t** use inheritance and OOP, the logic for setting up the base URI and token would have to be repeated in **each test class**.

#### **Without OOP:**

```java
package rekindle;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookstoreTest {

    private String token;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8183/api/v1";
        token = "<your-token>"; // hardcoded in each test class
    }

    @Test
    public void fetchAllBookStores() {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token) // token passed manually
                .when()
                .get("/bookstores");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 200 OK expected
    }

    @Test(dependsOnMethods = {"fetchAllBookStores"})
    public void fetchBookStoreById() {
        String bookstoreId = "d215b5f8-0249-4dc5-89a3-51fd148cfb45";

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/bookstores/" + bookstoreId);

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200); // 200 OK expected
    }

    @Test(dependsOnMethods = {"fetchAllBookStores"})
    public void createBookStore() {
        String requestBody = """
            {
                "name": "New Bookstore",
                "location": "123 Main Street",
                "owner": "John Doe"
            }
        """;

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/bookstores");

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 201); // 201 Created expected
    }
}
```

### **Why This is Less Optimal:**
- **Code Duplication**: You must write the setup code (like `RestAssured.baseURI` and `token`) in every test class.
- **Harder to Maintain**: If you need to change the base URL or token, you must update every test class.
- **Mix of Logic**: The test class is cluttered with setup logic, making it harder to read and maintain.

---

## **Step 4: Token Truncation for Example Code**
In real test cases, you might have a long token like this:
```
eyJ0eXAiOiJKV1QiLCJub25jZSI6IkJXTEo2b0F3Tm1Vbk9pTFlDWWt5c18tU0dv...
```

To keep things clean and avoid exposing sensitive data in the documentation/examples:
- Keep the **first 30–40 characters** for clarity.
- Append `...` to indicate truncation.
- Optionally, you can show the **last 5–10 characters** if helpful.

```java
// ✅ This is how you'd display a truncated token safely in docs
protected String token = "eyJ0eXAiOiJKV1QiLCJub25jZ...qOmQw"; 
```

In **real code**, you'll use the **full token**, but in examples or documentation, we show only part of it for clarity and security.

---

### **Conclusion:**
1. **BaseTest** enables OOP principles (inheritance, encapsulation, abstraction) and helps you centralize common setup logic.
2. Without it, your code becomes repetitive, harder to maintain, and more prone to errors.
3. Always **truncate tokens** in examples for security while ensuring the real token is used in your actual test logic.
