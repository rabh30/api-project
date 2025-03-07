package com.weatherapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/weather_db";
    private static final String USER = "root";
    private static final String PASSWORD = "saurabh";

    // Method to establish database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to save weather data to MySQL
    public static void saveWeatherData(String city, JsonObject weatherData) {
        String query = "INSERT INTO weather_data (city, temperature, humidity, description, timestamp) " +
                       "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP) " +
                       "ON DUPLICATE KEY UPDATE " +
                       "temperature = VALUES(temperature), " +
                       "humidity = VALUES(humidity), " +
                       "description = VALUES(description), " +
                       "timestamp = CURRENT_TIMESTAMP";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            double temperature = weatherData.getAsJsonObject("main").get("temp").getAsDouble();
            int humidity = weatherData.getAsJsonObject("main").get("humidity").getAsInt();
            String description = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

            pstmt.setString(1, city);
            pstmt.setDouble(2, temperature);
            pstmt.setInt(3, humidity);
            pstmt.setString(4, description);
            
            pstmt.executeUpdate();
            System.out.println("Weather data saved/updated in database!");

        } catch (SQLException e) {
            System.err.println("Error saving weather data: " + e.getMessage());
        }
    }

}
