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
                .body(order)  // Jackson автоматически сериализует объект в JSON
                .when()
                .post(Endpoints.CREATE_ORDER);
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
