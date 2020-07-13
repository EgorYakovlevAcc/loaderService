package com.model;

public enum  UserType {
    PORTER("PORTER"),
    CUSTOMER("CUSTOMER");

    private String typeName;

    UserType(String typeName) {
        this.typeName = typeName;
    }
}
