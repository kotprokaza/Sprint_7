package com.yandex.scooter.tests;

import com.yandex.scooter.api.OrderApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderTest {
    private OrderApi orderApi = new OrderApi();
    
    @Test
    @Step("Тест создания заказа с цветом BLACK")
    public void testCreateOrderWithBlackColor() {
        String[] colors = {"BLACK"};
        
        Response response = orderApi.createOrder(
            "Иван", "Иванов", "Москва, ул. Ленина 1", 
            "Комсомольская", "+79991112233", 3,
            "2024-12-10", "Позвонить за час", colors
        );
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertNotNull("Должен вернуться track номер", response.body().jsonPath().getInt("track"));
    }
    
    @Test
    @Step("Тест создания заказа с цветом GREY")
    public void testCreateOrderWithGreyColor() {
        String[] colors = {"GREY"};
        
        Response response = orderApi.createOrder(
            "Петр", "Петров", "Санкт-Петербург, Невский пр. 10", 
            "Площадь Восстания", "+79992223344", 5,
            "2024-12-11", "Не звонить", colors
        );
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertNotNull("Должен вернуться track номер", response.body().jsonPath().getInt("track"));
    }
    
    @Test
    @Step("Тест создания заказа с двумя цветами")
    public void testCreateOrderWithTwoColors() {
        String[] colors = {"BLACK", "GREY"};
        
        Response response = orderApi.createOrder(
            "Сергей", "Сергеев", "Казань, ул. Баумана 5", 
            "Кремлевская", "+79993334455", 2,
            "2024-12-12", "Оставить у двери", colors
        );
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertNotNull("Должен вернуться track номер", response.body().jsonPath().getInt("track"));
    }
    
    @Test
    @Step("Тест создания заказа без указания цвета")
    public void testCreateOrderWithoutColor() {
        Response response = orderApi.createOrder(
            "Анна", "Аннова", "Екатеринбург, ул. Мира 15", 
            "Геологическая", "+79994445566", 1,
            "2024-12-13", "Позвонить в домофон", null
        );
        
        assertEquals("Неверный код ответа", 201, response.getStatusCode());
        assertNotNull("Должен вернуться track номер", response.body().jsonPath().getInt("track"));
    }
}
