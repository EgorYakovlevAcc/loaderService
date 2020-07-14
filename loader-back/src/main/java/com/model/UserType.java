package com.model;

public enum  UserType {
    PORTER("PORTER"),
    CUSTOMER("CUSTOMER");

    private String value;

    UserType(String value) {
        this.value = value;
    }
}
