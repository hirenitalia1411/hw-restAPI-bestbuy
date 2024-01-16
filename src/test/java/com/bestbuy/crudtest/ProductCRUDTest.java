package com.bestbuy.crudtest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductCRUDTest extends TestBase {

    static String name = "Prime" + TestUtils.getRandomValue();
    static String type = "Testing";
    static int price = 1850;
    static int shipping = 1;
    static String upc = "JAVA";
    static String description = "API";
    static String manufacturer = "4Pillar";
    static String model = "Testing";
    static String url = "prime" + TestUtils.getRandomValue() + "@gmail.com";
    static String image = "prime.jpg";

    static int productID ;

    @Test
    public void test001() {

        System.out.println("============= Starting of Add Product==============");

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .when()
                .body(productPojo)
                .post();
        response.prettyPrint();
        response.then().statusCode(201);

        productID =response.then().extract().path("id");
        System.out.println("ID = " + productID);

        System.out.println("============= Ending of Add Product==============");
    }

    @Test
    public void test002() {

        System.out.println("============= Starting of Get Product==============");

        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/" + productID);
                response.then().statusCode(200);

        response.prettyPrint();

        System.out.println("============= Ending of Get Product==============");
    }

    @Test
    public void test003() {

        System.out.println("============= Starting of Update Product==============");

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name + "_123");
        productPojo.setType(type + "_good");

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(productPojo)
                .when()
                .patch("/" + productID);
        response.then().statusCode(200);

        response.prettyPrint();

        System.out.println("============= Ending of Update Product==============");
    }

    @Test
    public void test004() {

        System.out.println("============= Starting of Delete Product==============");

        given()
                .when()
                .delete("/" + productID)
                .then()
                .statusCode(200);

        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/" + productID);
        response.then().statusCode(404);

        System.out.println("============= Ending of Delete Product==============");
    }
}
