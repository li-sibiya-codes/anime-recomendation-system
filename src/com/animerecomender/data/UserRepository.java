package com.animerecomender.data;

import com.animerecomender.model.User;
import java.sql.*;

public class UserRepository {

    // Get a user by their ID
    public static User getUserById(int userId) {

        String sql = """
                    SELECT user_id, first_name, last_name, email, username
                    FROM users
                    WHERE user_id = ?
                    """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try(ResultSet resultSet = statement.executeQuery()) {

                if(resultSet.next()) {

                    int id = resultSet.getInt("user_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String username = resultSet.getString("username");

                    return new User(userId, firstName, lastName, email, username, null);
                }
            }

        } catch(SQLException e) {
            System.err.println("Database error while fetching user with ID " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    // Add a new user
    public void addUser(String firstName, String lastName, String email, String username) {

        String sql = """
                    INSERT INTO users (first_name, last_name, email, username)
                    VALUES (?, ?, ?, ?)
                    """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, username);

            statement.executeUpdate();

        } catch(SQLException e) {
            System.err.println("Database error while adding user: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Update a user's information
    public void updateUser(int userId, String firstName, String lastName, String email, String username) {

        String sql = """
                    UPDATE users
                    SET first_name = ?, last_name = ?, email = ?, username = ?
                    WHERE user_id = ?
                    """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, username);
            statement.setInt(5, userId);

            statement.executeUpdate();

        } catch(SQLException e) {
            System.err.println("Database error while updating user with ID " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}