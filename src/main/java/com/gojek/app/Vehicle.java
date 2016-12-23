package com.gojek.app;

/**
 * Created by rohit on 12/22/16.
 */
public class Vehicle {

    private String licensePlate;
    private String color;

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getColor() {
        return color;
    }

    public Vehicle(String licensePlate, String color) {
        this.licensePlate = licensePlate;
        this.color = color;
    }
}
