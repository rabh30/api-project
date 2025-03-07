package com.weatherapp.dao;

import com.weatherapp.config.DatabaseConfig;
import com.weatherapp.model.Weather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WeatherDAO {
    public void saveWeatherData(Weather weather) {
        String query = "INSERT INTO weather (city, temperature, humidity, conditions) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, weather.getCity());
            stmt.setDouble(2, weather.getTemperature());
            stmt.setInt(3, weather.getHumidity());
            stmt.setString(4, weather.getConditions());

            stmt.executeUpdate();
            System.out.println("Weather data saved successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
