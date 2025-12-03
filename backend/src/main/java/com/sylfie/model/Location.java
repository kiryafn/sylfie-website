package com.sylfie.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;

    String name;

    String country;

    String city;

    @Column(precision = 9, scale = 6)
    BigDecimal latitude;

    @Column(precision = 9, scale = 6)
    BigDecimal longitude;

    public Location() {}

    public Location(String name, String country, String city, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
