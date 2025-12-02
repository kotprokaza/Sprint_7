package com.yandex.scooter.tests;

import com.yandex.scooter.api.CourierApi;
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
        Response response = courierApi.createCourier(testLogin, "password123", "Ivan");
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertTrue("Поле 'ok' должно быть true", response.body().jsonPath().getBoolean("ok"));
    }

    @Test
    @Step("Тест создания двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        // Первое создание
        courierApi.createCourier(testLogin, "password123", "Ivan");
        
        // Второе создание с теми же данными
        Response response = courierApi.createCourier(testLogin, "password123", "Ivan");
        
        assertEquals("Должна быть ошибка 409", 409, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Этот логин уже используется. Попробуйте другой.", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест успешного логина курьера")
    public void testLoginCourierSuccess() {
        // Сначала создаём курьера
        courierApi.createCourier(testLogin, "password123", "Ivan");
        
        // Пробуем залогиниться
        Response response = courierApi.loginCourier(testLogin, "password123");
        
        assertEquals("Неверный код ответа", 200, response.getStatusCode());
        assertNotNull("Должен вернуться id курьера", response.body().jsonPath().getString("id"));
        
        // Сохраняем id для удаления в @After
        courierId = response.body().jsonPath().getString("id");
    }

    @Test
    @Step("Тест логина с неправильным паролем")
    public void testLoginWithWrongPassword() {
        // Сначала создаём курьера
        courierApi.createCourier(testLogin, "correctPassword", "Ivan");
        
        // Пробуем залогиниться с неправильным паролем
        Response response = courierApi.loginCourier(testLogin, "wrongPassword");
        
        assertEquals("Должна быть ошибка 404", 404, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Учетная запись не найдена", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест создания курьера без обязательных полей")
    public void testCreateCourierWithoutRequiredFields() {
        // Пробуем создать курьера без логина
        Response response = courierApi.createCourier("", "password123", "Ivan");
        
        assertEquals("Должна быть ошибка 400", 400, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Недостаточно данных для создания учетной записи", 
                     response.body().jsonPath().getString("message"));
    }

    @After
    @Step("Очистка тестовых данных")
    public void tearDown() {
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }
}
