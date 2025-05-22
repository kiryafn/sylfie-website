package com.sylfie.model.entity;

public enum ContentType {
    png("image/png"),
    jpeg("image/jpeg"),
    gif("image/gif");

    final String name;

    ContentType(String name) {
        this.name = name;
    }
}
