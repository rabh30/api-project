package com.weatherapp.service;

import com.google.gson.JsonObject;
import com.weatherapp.dao.WeatherDAO;
import com.weatherapp.model.Weather;
import com.weatherapp.util.APIClient;

public class WeatherService {
    private final WeatherDAO weatherDAO = new WeatherDAO();

    public void fetchAndSaveWeather(String city) {
        JsonObject weatherData = APIClient.getWeatherData(city);
        if (weatherData == null) {
            System.out.println("Failed to fetch weather data.");
            return;
        }

        String conditions = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        double temperature = weatherData.getAsJsonObject("main").get("temp").getAsDouble();
        int humidity = weatherData.getAsJsonObject("main").get("humidity").getAsInt();

        Weather weather = new Weather(city, temperature, humidity, conditions);
        weatherDAO.saveWeatherData(weather);
    }
}
