package com.yandex.scooter.tests;

import com.yandex.scooter.api.OrderApi;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import static org.junit.Assert.*;

public class OrderListTest {
    private OrderApi orderApi = new OrderApi();
    
    @Test
    @Step("Тест получения списка заказов")
    public void testGetOrderList() {
        Response response = orderApi.getOrderList();
        
        assertEquals("Неверный код ответа", 200, response.getStatusCode());
        assertNotNull("Должен вернуться список заказов", response.body().jsonPath().getList("orders"));
        assertTrue("Список заказов должен быть не пустым", 
                   response.body().jsonPath().getList("orders").size() > 0);
    }
}
