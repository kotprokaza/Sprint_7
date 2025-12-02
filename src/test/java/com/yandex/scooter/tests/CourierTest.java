package com.yandex.scooter.tests;

import com.yandex.scooter.api.CourierApi;
import com.yandex.scooter.models.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourierTest {
    private CourierApi courierApi;
    private String testLogin;
    private String courierId;

    @Before
    @Step("Подготовка тестовых данных")
    public void setUp() {
        courierApi = new CourierApi();
        testLogin = "courier_" + System.currentTimeMillis();
    }

    @Test
    @Step("Тест успешного создания курьера")
    public void testCreateCourierSuccess() {
        Courier courier = new Courier(testLogin, "password123", "Ivan");
        
        Response response = courierApi.createCourier(courier);
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertTrue("Поле 'ok' должно быть true", response.body().jsonPath().getBoolean("ok"));
    }

    @Test
    @Step("Тест создания двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        Courier courier = new Courier(testLogin, "password123", "Ivan");
        
        // Первое создание
        courierApi.createCourier(courier);
        
        // Второе создание с теми же данными
        Response response = courierApi.createCourier(courier);
        
        assertEquals("Должна быть ошибка 409", 409, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Этот логин уже используется. Попробуйте другой.", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест успешного логина курьера")
    public void testLoginCourierSuccess() {
        // Сначала создаём курьера
        Courier courier = new Courier(testLogin, "password123", "Ivan");
        courierApi.createCourier(courier);
        
        // Пробуем залогиниться
        Response response = courierApi.loginCourier(testLogin, "password123");
        
        assertEquals("Неверный код ответа", 200, response.getStatusCode());
        assertNotNull("Должен вернуться id курьера", response.body().jsonPath().getString("id"));
        
        // Сохраняем id для удаления в @After
        courierId = response.body().jsonPath().getString("id");
    }

    @After
    @Step("Очистка тестовых данных")
    public void tearDown() {
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }
}
