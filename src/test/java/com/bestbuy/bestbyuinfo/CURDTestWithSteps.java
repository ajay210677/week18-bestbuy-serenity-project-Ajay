package com.bestbuy.bestbyuinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import com.bestbuy.model.StoresPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CURDTestWithSteps extends TestBase {
//------------------------------Products----------------------------//
    static String productName = "White Light" + TestUtils.getRandomValue();
    static String storeName = "White Light" + TestUtils.getRandomValue();
    static long id;

    @Title("Get all Products")
    @Test
    public void test01() {
        SerenityRest.given()
                .when()
                .get(EndPoints.productsGet)
                .then()
                .log().all().statusCode(200);
    }

    @Title("Create new Product")
    @Test
    public void test02() {
        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(productName);
        productsPojo.setType("HeadLight");
        productsPojo.setPrice(7.95);
        productsPojo.setShipping(0);
        productsPojo.setUpc("0987654");
        productsPojo.setDescription("Compatible with 4x4 cars only");
        productsPojo.setManufacturer("Philips");
        productsPojo.setModel("WHT150");
        productsPojo.setUrl("https://whitelight4x4.com");
        productsPojo.setImage("imgwhtlgt4x4.jpg");
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post(EndPoints.productsPost)
                .then().log().all().statusCode(201);
    }

    @Title("Verify new product's created in application")
    @Test
    public void test03() {
        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        HashMap<String, Object> productsMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.productsGet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + productName + p2);
        Assert.assertThat(productsMap, hasValue(productName));
        id = (int) productsMap.get("id");
    }

    @Title("Update the product and verify the product information is updated")
    @Test
    public void test04() {
        productName = productName + "_updated";

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(productName);
        productsPojo.setType("HeadLight");
        productsPojo.setPrice(7.95);
        productsPojo.setShipping(0);
        productsPojo.setUpc("0987654");
        productsPojo.setDescription("Compatible with 4x4 cars only");
        productsPojo.setManufacturer("Philips");
        productsPojo.setModel("WHT150");
        productsPojo.setUrl("https://whitelight4x4.com");
        productsPojo.setImage("imgwhtlgt4x4.jpg");
        SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("id", id)
                .body(productsPojo)
                .when()
                .put(EndPoints.productsPatch)
                .then().log().all().statusCode(200);

        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        HashMap<String, Object> productsMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.productsGet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + productName + p2);
        Assert.assertThat(productsMap, hasValue(productName));
    }

    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test05 (){
        SerenityRest.given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.productsDelete)
                .then().statusCode(200);
        SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.productsGetById)
                .then()
                .statusCode(404);
    }

//------------------------------Stores----------------------------//
    @Title("Get all Stores")
    @Test
    public void test06() {
        SerenityRest.given()
                .when()
                .get(EndPoints.storesGet)
                .then()
                .log().all().statusCode(200);
    }

    @Title("Create new Store")
    @Test
    public void test07() {
        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(storeName);
        storesPojo.setType("Bulb");
        storesPojo.setAddress("10");
        storesPojo.setAddress2("Downing Street");
        storesPojo.setCity("WestMinister");
        storesPojo.setState("London");
        storesPojo.setZip("EC1B 2JL");
        storesPojo.setLat(0);
        storesPojo.setLng(0);
        storesPojo.setHours("Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8");
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(storesPojo)
                .when()
                .post(EndPoints.storesPost)
                .then().log().all().statusCode(201);
    }

    @Title("Verify new store's created in application")
    @Test
    public void test08() {
        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storesMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.storesGet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + storeName + p2);
        Assert.assertThat(storesMap, hasValue(storeName));
        id = (int) storesMap.get("id");
    }

    @Title("Update the store and verify the product information is updated")
    @Test
    public void test09() {
        storeName = storeName + "_updated";

        StoresPojo storesPojo = new StoresPojo();
        storesPojo.setName(storeName);
        storesPojo.setType("Bulb");
        storesPojo.setAddress("10 Downing Street");
        storesPojo.setAddress2("");
        storesPojo.setCity("WestMinister");
        storesPojo.setState("London");
        storesPojo.setZip("EC1B 2JL");
        storesPojo.setLat(0);
        storesPojo.setLng(0);
        SerenityRest.given().log().all()
                .header("Content-Type", "application/json; charset=UTF-8")
                .pathParam("id", id)
                .body(storesPojo)
                .when()
                .put(EndPoints.storesPatch)
                .then().log().all().statusCode(200);

        String p1 = "data.findAll{it.name = '";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storesMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.storesGet)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + storeName + p2);
        Assert.assertThat(storesMap, hasValue(storeName));
    }

    @Title("Delete the store and verify if the product is deleted")
    @Test
    public void test10() {
        SerenityRest.given()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.storesDelete)
                .then().statusCode(200);
        SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .get(EndPoints.storesGetById)
                .then()
                .statusCode(404);
    }
}
