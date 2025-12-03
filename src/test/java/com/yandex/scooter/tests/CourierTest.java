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

    @Before
    @Step("Подготовка тестовых данных")
    public void setUp() {
        courierApi = new CourierApi();
        testCourier = DataGenerator.getRandomCourier();
    }

    @Test
    @Step("Тест успешного создания курьера")
    public void testCreateCourierSuccess() {
        Response response = courierApi.createCourier(testCourier);
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertTrue("Поле 'ok' должно быть true", response.body().jsonPath().getBoolean("ok"));
    }

    @Test
    @Step("Тест создания двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        // Первое создание
        courierApi.createCourier(testCourier);
        
        // Второе создание с теми же данными
        Response response = courierApi.createCourier(testCourier);
        
        assertEquals("Должна быть ошибка 409", 409, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Этот логин уже используется. Попробуйте другой.", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест успешного логина курьера")
    public void testLoginCourierSuccess() {
        // Создаём курьера через API
        courierApi.createCourier(testCourier);
        
        // Пробуем залогиниться
        Response response = courierApi.loginCourier(testCourier);
        
        assertEquals("Неверный код ответа", 200, response.getStatusCode());
        assertNotNull("Должен вернуться id курьера", response.body().jsonPath().getString("id"));
        
        // Сохраняем id для удаления в @After
        courierId = response.body().jsonPath().getString("id");
    }

    @Test
    @Step("Тест логина с неправильным логином")
    public void testLoginWithWrongLogin() {
        // Создаём курьера
        courierApi.createCourier(testCourier);
        
        // Пробуем залогиниться с неправильным логином
        CourierModel wrongLoginCourier = new CourierModel(
            "wrong_" + testCourier.getLogin(),
            testCourier.getPassword(),
            testCourier.getFirstName()
        );
        
        Response response = courierApi.loginCourier(wrongLoginCourier);
        
        assertEquals("Должна быть ошибка 404", 404, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Учетная запись не найдена", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест логина с неправильным паролем")
    public void testLoginWithWrongPassword() {
        // Создаём курьера
        courierApi.createCourier(testCourier);
        
        // Пробуем залогиниться с неправильным паролем
        CourierModel wrongPasswordCourier = new CourierModel(
            testCourier.getLogin(),
            "wrongPassword",
            testCourier.getFirstName()
        );
        
        Response response = courierApi.loginCourier(wrongPasswordCourier);
        
        assertEquals("Должна быть ошибка 404", 404, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Учетная запись не найдена", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест логина без логина")
    public void testLoginWithoutLogin() {
        CourierModel noLoginCourier = new CourierModel(
            "",
            testCourier.getPassword(),
            testCourier.getFirstName()
        );
        
        Response response = courierApi.loginCourier(noLoginCourier);
        
        assertEquals("Должна быть ошибка 400", 400, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Недостаточно данных для входа", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест логина без пароля")
    public void testLoginWithoutPassword() {
        CourierModel noPasswordCourier = new CourierModel(
            testCourier.getLogin(),
            "",
            testCourier.getFirstName()
        );
        
        Response response = courierApi.loginCourier(noPasswordCourier);
        
        assertEquals("Должна быть ошибка 400", 400, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Недостаточно данных для входа", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест создания курьера без логина")
    public void testCreateCourierWithoutLogin() {
        CourierModel courier = DataGenerator.getCourierWithoutLogin();
        
        Response response = courierApi.createCourier(courier);
        
        assertEquals("Должна быть ошибка 400", 400, response.getStatusCode());
        assertEquals("Неверное сообщение об ошибке", 
                     "Недостаточно данных для создания учетной записи", 
                     response.body().jsonPath().getString("message"));
    }

    @Test
    @Step("Тест создания курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        CourierModel courier = DataGenerator.getCourierWithoutPassword();
        
        Response response = courierApi.createCourier(courier);
        
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
