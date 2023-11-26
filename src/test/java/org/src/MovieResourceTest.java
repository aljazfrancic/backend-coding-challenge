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
public class MovieResourceTest {
    @Test
    @Order(1)
    public void testMovieGet() {
        get("/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(20));
    }

    @Test
    @Order(2)
    public void testMoviePost() {
        JsonObject requestParams = Json.createObjectBuilder()
                .add("id", "0")
                .add("title", "Funny Movie")
                .add("year", "2020")
                .add("description", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Dignissim enim sit amet venenatis urna cursus eget. Vivamus at augue eget arcu dictum.")
                .build();
        given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/movies")
                .then().
                statusCode(201);
    }

    @Test
    @Order(3)
    public void testMovieGetPage() {
        given()
                .pathParam("page", 1)
                .get("/movies/page/{page}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(5));
    }

    @Test
    @Order(4)
    public void testMovieSearch() {
        // TODO PARTIAL NAME SEARCH + MULTIPLE RESULTS + SEARCH DESCRIPTIONS + HANDLE NO RESULT???
    }

    @Test
    @Order(5)
    public void testMovieGetId() {
        given()
                .pathParam("id", 133093)
                .get("/movies/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("The Matrix"));
    }

    @Test
    @Order(6)
    public void testMoviePut() {
        JsonObject requestParams = Json.createObjectBuilder()
                .add("title", "Scary Movie")
                .add("year", "2012")
                .add("description", "BOO!")
                .build();
        given()
                .pathParam("id", 0)
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/movies/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("Scary Movie"))
                .body("year", equalTo(2012))
                .body("description", equalTo("BOO!"));
    }

    @Test
    @Order(7)
    public void testMovieDelete() {
        //check number of actors and movies before deletion
        get("/actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(23));
        get("/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(21));
        //test delete operation
        given()
                .pathParam("id", 0)
                .delete("/movies/{id}")
                .then()
                .statusCode(204);
        //check number of actors and movies after deletion
        get("/actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(23));
        get("/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(20));
    }
}
