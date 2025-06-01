package com.sylfie.model;

public enum ContentType {
    png("image/png"),
    jpeg("image/jpeg"),
    gif("image/gif");

    final String name;

    ContentType(String name) {
        this.name = name;
    }

    public static ContentType getByName(String name) {
        for (ContentType type : values()) {
            if (type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type: " + name);
    }

}
