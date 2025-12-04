package com.yandex.scooter.models;

public class CourierModel {
    private String login;
    private String password;
    private String firstName;

    // Конструктор по умолчанию (нужен для Jackson)
    public CourierModel() {}

    // Конструктор с параметрами
    public CourierModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Геттеры и сеттеры
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
