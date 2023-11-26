package org.src;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PictureResourceTest {
    @Test
    @Order(1)
    public void testPictureGet() {
        get("/pictures")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(3));
    }

    @Test
    @Order(2)
    public void testPicturePost() {
        //TODO test multipart
    }

    @Test
    @Order(3)
    public void testPictureGetId() {
        given()
                .pathParam("id", 1)
                .get("/pictures/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @Order(4)
    public void testPictureGetDirectId() {
        //TODO test getting picture directly
    }

    @Test
    @Order(5)
    public void testPicturePut() {
        JsonObject movie = Json.createObjectBuilder()
                .add("id", 133093)
                .build();
        JsonObject requestParams = Json.createObjectBuilder()
                .add("movie", movie)
                .add("picture", "NjY2")
                .build();
        given()
                .pathParam("id", 3)
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/pictures/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(3))
                .body("movie", equalTo(133093))
                .body("picture", equalTo("NjY2"));
    }

    @Test
    @Order(6)
    public void testPictureDelete() {
        //check number of movies and pictures before deletion
        get("/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(20));
        get("/pictures")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(3));
        //test delete operation
        given()
                .pathParam("id", 3)
                .delete("/pictures/{id}")
                .then()
                .statusCode(204);
        //check number of movies and pictures after deletion
        get("/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(20));
        get("/pictures")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2));
    }
}
