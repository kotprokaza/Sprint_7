package com.yandex.scooter.api;

import com.yandex.scooter.models.CourierModel;
import com.yandex.scooter.utils.Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierApi {
    
    @Step("Создание курьера")
    public Response createCourier(CourierModel courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .body(courier)  // Jackson автоматически сериализует объект в JSON
                .when()
                .post(Endpoints.CREATE_COURIER);
    }
    
    @Step("Логин курьера")
    public Response loginCourier(CourierModel courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .body(courier)
                .when()
                .post(Endpoints.LOGIN_COURIER);
    }
    
    @Step("Удаление курьера")
    public Response deleteCourier(String courierId) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(Endpoints.BASE_URL)
                .when()
                .delete(Endpoints.DELETE_COURIER + courierId);
    }
}
