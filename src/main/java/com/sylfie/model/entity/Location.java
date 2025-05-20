package com.sylfie.model.entity;

public enum Location {
    Cuba("Cuba"),
    Ukraine("Ukraine"),
    Spain("Spain"),
    SouthAfrica("South Africa"),
    Antarctica("Antarctica"),
    Japan("Japan"),
    USA("USA");

    String name;

    Location(String name) {
        this.name = name;
    }

}
