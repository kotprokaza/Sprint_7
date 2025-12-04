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
        System.out.println("Попытка отмены заказа с track: " + trackId);
        
        // ВАРИАНТ 1: Полный объект заказа (как в документации обычно)
        // return given()
        //         .header("Content-type", "application/json")
        //         .baseUri(Endpoints.BASE_URL)
        //         .body(String.format("{\"track\": %d}", trackId))
        //         .when()
        //         .put(Endpoints.CANCEL_ORDER);
        
        // ВАРИАНТ 2: С query параметром вместо тела
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .queryParam("track", trackId)
                .when()
                .put(Endpoints.CANCEL_ORDER);
        
        // ВАРИАНТ 3: POST вместо PUT
        // return given()
        //         .header("Content-type", "application/json")
        //         .baseUri(Endpoints.BASE_URL)
        //         .body(String.format("{\"track\": %d}", trackId))
        //         .when()
        //         .post(Endpoints.CANCEL_ORDER);
        
        // ВАРИАНТ 4: Другой endpoint (например, с track в URL)
        // return given()
        //         .header("Content-type", "application/json")
        //         .baseUri(Endpoints.BASE_URL)
        //         .when()
        //         .delete(Endpoints.CANCEL_ORDER + "/" + trackId);
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
