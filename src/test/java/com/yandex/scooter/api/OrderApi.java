package com.yandex.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    
    @Step("Создание заказа")
    public Response createOrder(String firstName, String lastName, String address, 
                                String metroStation, String phone, int rentTime, 
                                String deliveryDate, String comment, String[] color) {
        
        String colorsJson = "";
        if (color != null && color.length > 0) {
            colorsJson = "\"color\": [";
            for (int i = 0; i < color.length; i++) {
                colorsJson += "\"" + color[i] + "\"";
                if (i < color.length - 1) colorsJson += ", ";
            }
            colorsJson += "]";
        }
        
        String requestBody = String.format(
            "{\"firstName\": \"%s\", \"lastName\": \"%s\", \"address\": \"%s\", " +
            "\"metroStation\": \"%s\", \"phone\": \"%s\", \"rentTime\": %d, " +
            "\"deliveryDate\": \"%s\", \"comment\": \"%s\"%s%s}",
            firstName, lastName, address, metroStation, phone, rentTime,
            deliveryDate, comment,
            (colorsJson.isEmpty() ? "" : ", "),
            colorsJson
        );
        
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post("/api/v1/orders");
    }
    
    @Step("Получение списка заказов")
    public Response getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .get("/api/v1/orders");
    }
}
