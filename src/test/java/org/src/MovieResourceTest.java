package org.src;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.List;

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
        given()
                .pathParam("query", "matrix")
                .get("/movies/search/{query}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(1));
        given()
                .pathParam("query", "ata")
                .get("/movies/search/{query}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(1));
        given()
                .pathParam("query", "2003")
                .get("/movies/search/{query}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(1));
        given()
                .pathParam("query", "the")
                .get("/movies/search/{query}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(19)); //"the" is quite common in both descriptions and titles
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

    private static class MyRunnable implements Runnable {
        int myId;

        public MyRunnable(int id) {
            myId = id;
        }

        public void run() {
            JsonObject requestParams = Json.createObjectBuilder()
                    .add("id", myId)
                    .add("title", "Movie")
                    .add("year", "2020")
                    .add("description", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Dignissim enim sit amet venenatis urna cursus eget. Vivamus at augue eget arcu dictum.")
                    .build();
            given()
                    .header("Content-Type", "application/json")
                    .body(requestParams.toString())
                    .post("/movies")
                    .then().
                    statusCode(201);
            given()
                    .pathParam("id", myId)
                    .delete("/movies/{id}")
                    .then()
                    .statusCode(204);
        }
    }

    @Test
    @Order(8)
    public void testMovieConcurrent() throws InterruptedException {
        int numOfThreads = 500;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numOfThreads; i++) {
            threads.add(new Thread(new MyRunnable(i)));
        }
        for (int i = 0; i < numOfThreads; i++) {
            threads.get(i).start();
        }
        for (int i = 0; i < numOfThreads; i++) {
            threads.get(i).join();
        }
    }
}
