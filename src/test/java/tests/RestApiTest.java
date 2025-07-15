package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class RestApiTest {

    @Test
    @DisplayName("Успешное создание пользователя")
    void succesfulUserCreate() {

        String userData = "{\"name\": \"morpheus\", \"job\": \"leader\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(userData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"), "job", is("leader"));
    }

    @Test
    @DisplayName("Создание пользователя без указания имени")
    void unsuccesfulCreateUser() {

        String userData = "{\"name\", \"job\": \"\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(userData)
                .contentType(JSON)
                .log().uri()

                .when()
                    .post("https://reqres.in/api/users")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(400)
                    .body("message", is("Something went wrong"));
    }

    @Test
    @DisplayName("Изменить работу у пользователя morpheus")
    void updateJobUser() {

        String userData = "{\"name\": \"morpheus\", \"job\": \"doctor\"}";

        given()
                .header("x-api-key", "reqres-free-v1")
                .body(userData)
                .contentType(JSON)
                .log().uri()

                .when()
                    .put("https://reqres.in/api/users/2")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(200)
                    .body("name", is("morpheus"), "job", is("doctor"));


    }

    @Test
    @DisplayName("Проверить что длина массива data больше 1")
    void checkLengthData() {

        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()

                .when()
                    .get("https://reqres.in/api/users?page=2")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(200)
                    .body("data.size()", greaterThan(1));
    }

    @Test
    @DisplayName("Проверить что есть пользователь с именем Byron")
    void chechUserWithNameByron() {

        given()
                .header("x-api-key", "reqres-free-v1")
                .log().uri()

                .when()
                    .get("https://reqres.in/api/users?page=2")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(200)
                    .body("data.first_name", hasItem("Byron"));
    }


}
