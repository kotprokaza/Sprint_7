package com.yandex.scooter.api;

import com.yandex.scooter.models.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    
    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }
    
    @Step("Логин курьера")
    public Response loginCourier(String login, String password) {
        String requestBody = String.format("{\"login\": \"%s\", \"password\": \"%s\"}", login, password);
        
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post("/api/v1/courier/login");
    }
    
    @Step("Удаление курьера")
    public Response deleteCourier(String courierId) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}
