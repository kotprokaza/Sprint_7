package com.yandex.scooter.utils;

import com.github.javafaker.Faker;
import com.yandex.scooter.models.CourierModel;
import com.yandex.scooter.models.OrderModel;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));
    
    public static CourierModel getRandomCourier() {
        return new CourierModel(
            faker.name().username().replaceAll("[^a-zA-Z0-9]", "") + System.currentTimeMillis(),
            faker.internet().password(8, 16, true, true, true),
            faker.name().firstName()
        );
    }
    
    public static CourierModel getCourierWithoutLogin() {
        return new CourierModel(
            "",
            faker.internet().password(),
            faker.name().firstName()
        );
    }
    
    public static CourierModel getCourierWithoutPassword() {
        return new CourierModel(
            faker.name().username() + System.currentTimeMillis(),
            "",
            faker.name().firstName()
        );
    }
    
    public static OrderModel getRandomOrder(List<String> colors) {
        return new OrderModel(
            faker.name().firstName(),
            faker.name().lastName(),
            faker.address().fullAddress(),
            faker.address().streetName(),
            faker.phoneNumber().phoneNumber(),
            faker.number().numberBetween(1, 10),
            "2024-12-15",
            faker.lorem().sentence(),
            colors
        );
    }
    
    public static OrderModel getOrderWithoutColor() {
        return getRandomOrder(null);
    }
    
    public static OrderModel getOrderWithBlackColor() {
        return getRandomOrder(Arrays.asList("BLACK"));
    }
    
    public static OrderModel getOrderWithGreyColor() {
        return getRandomOrder(Arrays.asList("GREY"));
    }
    
    public static OrderModel getOrderWithBothColors() {
        return getRandomOrder(Arrays.asList("BLACK", "GREY"));
    }
}
