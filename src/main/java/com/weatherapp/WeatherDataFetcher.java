// MAIN FILE

package com.weatherapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.weatherapp.util.APIClient;

public class WeatherDataFetcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter city name (or type 'exit' to quit): ");
            String city = scanner.nextLine();
            if (city.equalsIgnoreCase("exit")) break;

            JsonObject weatherData = APIClient.getWeatherData(city);
            if (weatherData != null) {
                DatabaseManager.saveWeatherData(city, weatherData);
                System.out.println("Weather data saved successfully!");

                // Fetch and display stored weather records
                fetchStoredWeatherData();
            }
        }
        scanner.close();
    }

    // Method to fetch and display stored weather records from MySQL
    private static void fetchStoredWeatherData() {
        String query = "SELECT * FROM weather_data";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nStored Weather Data:");
            System.out.printf("%-15s %-10s %-10s %-20s\n", "City", "Temp(°C)", "Humidity", "Description");
            System.out.println("----------------------------------------------------");

            while (rs.next()) {
                String city = rs.getString("city");
                double temperature = rs.getDouble("temperature");
                int humidity = rs.getInt("humidity");
                String description = rs.getString("description");

                System.out.printf("%-15s %-10.2f %-10d %-20s\n", city, temperature, humidity, description);
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("Error fetching stored weather data: " + e.getMessage());
        }
    }
}
