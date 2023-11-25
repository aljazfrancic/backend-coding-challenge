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
public class ActorResourceTest {
    @Test
    @Order(1)
    public void testActorGet() {
        get("/actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(23));
    }

    @Test
    @Order(2)
    public void testActorPost() {
        JsonObject requestParams = Json.createObjectBuilder()
                .add("id", "0")
                .add("firstName", "Famous")
                .add("lastName", "Actor")
                .add("bornDate", "1993-03-01")
                .build();
        given()
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/actors")
                .then().
                statusCode(201);
    }

    @Test
    @Order(3)
    public void testActorGetPage() {
        given()
                .pathParam("page", 1)
                .get("/actors/page/{page}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(5));
    }

    @Test
    @Order(4)
    public void testActorGetId() {
        given()
                .pathParam("id", 93)
                .get("/actors/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Brad"))
                .body("lastName", equalTo("Pitt"));
    }

    @Test
    @Order(5)
    public void testActorPut() {
        JsonObject requestParams = Json.createObjectBuilder()
                .add("firstName", "Aljaž")
                .add("lastName", "Frančič")
                .add("bornDate", "1993-03-01")
                .build();
        given()
                .pathParam("id", 0)
                .header("Content-Type", "application/json")
                .body(requestParams.toString())
                .put("/actors/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Aljaž"))
                .body("lastName", equalTo("Frančič"))
                .body("movies.size()", equalTo(0));
    }

    @Test
    @Order(6)
    public void testActorDelete() {
        //check number of actors and movies before deletion
        get("/actors")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(24));
        get("/movies")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(20));
        //test delete operation
        given()
                .pathParam("id", 0)
                .delete("/actors/{id}")
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
