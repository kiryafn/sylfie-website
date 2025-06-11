package com.sylfie.model;

public enum Status {
    BOOKED("Booked"),
    COMPLETED( "Completed"),
    CANCELLED( "Cancelled");

    private String name;

    Status(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
