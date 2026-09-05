package com.animerecomender.data;

import com.animerecomender.model.UserPreferences;
import java.sql.*;


public class UserPreferencesRepository {

    // get a user's genre preferences
    public UserPreferences getUserPreferences(int userId) {

        UserPreferences userPreferences = new UserPreferences();

        String sql = """
                    SELECT g.name, up.preference_id
                    FROM user_preferences up
                    JOIN genres g ON up.genre_id = g.genre_id
                    WHERE up.user_id = ?
                    ORDER BY g.genre_id
                    """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try(ResultSet resultSet = statement.executeQuery()) {

                while(resultSet.next()) {

                    String genre = resultSet.getString("name");
                    int preference = resultSet.getInt("preference_id");

                    userPreferences.setGenrePreference(genre, preference);
                }
            }

        } catch(SQLException e) {
            System.err.println("Database error while fetching preferences for user_id " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }

        return userPreferences;
    }


    // add a genre preference for a user
    public void addPreference(int userId, int genreId, int preferenceId) {

        String sql = """
                    INSERT INTO user_preferences (user_id, genre_id, preference_id)
                    VALUES (?, ?, ?)
                    """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, genreId);
            statement.setInt(3, preferenceId);

            statement.executeUpdate();

        } catch(SQLException e) {
            System.err.println("Database error while adding preference for user_id " + userId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }


    // update a user's genre preference
    public void updatePreference(int userId, int genreId, int preferenceId) {

        String sql = """
                    UPDATE user_preferences
                    SET preference_id = ?
                    WHERE user_id = ? AND genre_id = ?
                    """;

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, preferenceId);
            statement.setInt(2, userId);
            statement.setInt(3, genreId);

            statement.executeUpdate();

        } catch(SQLException e) {
            System.err.println("Database error while updating preference for user_id " + userId + ", genre_id " + genreId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}