package com.model.order;

public enum Status {
    CREATED("Создание заказа", 0),
    SEARCHING("Ведётся поиск", 1),
    IN_PROGRESS("Заказ на исполнении", 2),
    CLOESED("Заказ закрыт", 3);

    private String name;
    private Integer status_id;

    Status(String name, Integer status_id) {
        this.name = name;
        this.status_id = status_id;
    }
}
