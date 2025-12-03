package com.yandex.scooter.tests;

import com.yandex.scooter.api.OrderApi;
import com.yandex.scooter.models.OrderModel;
import com.yandex.scooter.utils.DataGenerator;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrderTest {
    private final OrderApi orderApi = new OrderApi();
    private final OrderModel order;
    private final String testDescription;

    public OrderTest(OrderModel order, String testDescription) {
        this.order = order;
        this.testDescription = testDescription;
    }

    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { DataGenerator.getOrderWithBlackColor(), "Заказ с цветом BLACK" },
            { DataGenerator.getOrderWithGreyColor(), "Заказ с цветом GREY" },
            { DataGenerator.getOrderWithBothColors(), "Заказ с двумя цветами" },
            { DataGenerator.getOrderWithoutColor(), "Заказ без указания цвета" }
        });
    }

    @Test
    @Step("Тест создания заказа: {testDescription}")
    public void testCreateOrder() {
        Response response = orderApi.createOrder(order);
        
        assertEquals("Неверный код ответа для " + testDescription, 
                     201, response.getStatusCode());
        assertNotNull("Должен вернуться track номер для " + testDescription, 
                     response.body().jsonPath().getInt("track"));
    }
}
