package com.yandex.scooter.api;

import com.yandex.scooter.models.OrderModel;
import com.yandex.scooter.utils.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderApi {

    @Step("Создание заказа")
    public Response createOrder(OrderModel order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .body(order)
                .when()
                .post(Endpoints.CREATE_ORDER);
    }

    @Step("Отмена заказа")
    public Response cancelOrder(int trackId) {
        // Попробуйте разные варианты:
        // Вариант 1: PUT метод
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .body(String.format("{\"track\": %d}", trackId))
                .when()
                .put(Endpoints.CANCEL_ORDER);

        // Вариант 2: POST метод (если PUT не работает)
        // return given()
        //         .header("Content-type", "application/json")
        //         .baseUri(Endpoints.BASE_URL)
        //         .body(String.format("{\"track\": %d}", trackId))
        //         .when()
        //         .post(Endpoints.CANCEL_ORDER);

        // Вариант 3: С query параметром (если нужно)
        // return given()
        //         .header("Content-type", "application/json")
        //         .baseUri(Endpoints.BASE_URL)
        //         .queryParam("track", trackId)
        //         .when()
        //         .put(Endpoints.CANCEL_ORDER);
    }

    @Step("Получение списка заказов")
    public Response getOrderList() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .when()
                .get(Endpoints.GET_ORDER_LIST);
    }
}