package com.yandex.scooter.tests;

import com.yandex.scooter.api.CourierApi;
import com.yandex.scooter.models.CourierModel;
import com.yandex.scooter.utils.DataGenerator;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourierTest {
    private CourierApi courierApi;
    private CourierModel testCourier;
    private String courierId;

    @Before  // УБРАТЬ @Step отсюда
    public void setUp() {
        courierApi = new CourierApi();
        testCourier = DataGenerator.getRandomCourier();
    }

    // Тесты остаются с @Step...

    @After  // УБРАТЬ @Step отсюда
    public void tearDown() {
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }
}