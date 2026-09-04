package com.animerecomender.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class DatabaseConnection {

    private static final Map<String, String> ENV = loadEnv();

    private static final String URL = ENV.get("DB_URL");
    private static final String USER = ENV.get("DB_USER");
    private static final String PASSWORD = ENV.get("DB_PASSWORD");

        private static Map<String, String> loadEnv() {

        Map<String, String> values = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Path.of(".env"));

            for (String line : lines) {

                line = line.trim();

                // Ignore blank lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split("=", 2);

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    values.put(key, value);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not read .env file", e);
        }

        return values;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {

    try {
        Connection connection = getConnection();

        System.out.println("Database connected successfully!");

        connection.close();

    } catch (SQLException e) {
        System.out.println("Database connection failed.");
        e.printStackTrace();
    }
}

}
    

