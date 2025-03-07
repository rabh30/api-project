package com.weatherapp.model;

public class Weather {
    private String city;
    private double temperature;
    private int humidity;
    private String conditions;

    public Weather(String city, double temperature, int humidity, String conditions) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.conditions = conditions;
    }

    public String getCity() { return city; }
    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public String getConditions() { return conditions; }
}
