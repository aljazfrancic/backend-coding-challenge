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
public class MovieActorResourceTest {
    @Test
    @Order(1)
    public void testMovieActorGet() {
        get("/movie_actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(28));
    }

    @Test
    @Order(2)
    public void testMovieActorPost() {
        JsonObject requestParams = Json.createObjectBuilder()
                .add("movieId", "68646")
                .add("actorId", "206")
                .build();
        given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/movie_actors")
                .then().
                statusCode(201);
    }

    @Test
    @Order(3)
    public void testMovieActorGetId() {
        given()
                .pathParam("id", 1)
                .get("/movie_actors/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    @Order(4)
    public void testMovieActorPut() {
        JsonObject requestParams = Json.createObjectBuilder()
                .add("movieId", "137523")
                .add("actorId", "206")
                .build();
        given()
                .pathParam("id", 29)
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/movie_actors/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("movieId", equalTo(137523))
                .body("actorId", equalTo(206));
    }

    @Test
    @Order(5)
    public void testMovieActorDelete() {
        //check number of actors, movies and movie_actors before deletion
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
        get("/movie_actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(29));
        //test delete operation
        given()
                .pathParam("id", 29)
                .delete("/movie_actors/{id}")
                .then()
                .statusCode(204);
        //check number of actors, movies and movie_actors after deletion
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
        get("/movie_actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(28));
    }
}
