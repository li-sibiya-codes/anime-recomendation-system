package com.animerecomender.data;

import com.animerecomender.model.User;
import com.animerecomender.model.Anime;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserAnimeStatus;

import java.util.*;
import java.sql.*;


public class UserAnimeRepository {

    public ArrayList<UserAnime> getUserAnimeByUserId(int userId) {
    
    ArrayList<UserAnime> userAnimeList = new ArrayList<>();
    
    String sql = """
                SELECT user_id, anime_id, status, episodes_watched, rating
                FROM user_anime
                WHERE user_id = ?
                ORDER BY anime_id
                """;

    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {
        
        statement.setInt(1, userId);

        try(ResultSet resultSet = statement.executeQuery()) {
            
            while(resultSet.next()) {
                
                int animeId = resultSet.getInt("anime_id");
                
                User user = UserRepository.getUserById(userId);
                Anime anime = AnimeRepository.getAnimeById(animeId);

                if (user == null || anime == null) {
                    System.err.println("User or Anime not found for user_id: " + userId + ", anime_id: " + animeId);
                    continue;
                }

                UserAnimeStatus status = UserAnimeStatus.valueOf(resultSet.getString("status").toUpperCase().replace(" ", "_"));
                
                int episodesWatched = resultSet.getInt("episodes_watched");
                
                Double rating = resultSet.getObject("rating", Double.class);

                UserAnime userAnime = new UserAnime(user, anime, status, episodesWatched, rating);

                userAnimeList.add(userAnime);
            }
        }
    } catch (SQLException e) {
        System.err.println("Database error while fetching user anime for user_id " + userId + ": " + e.getMessage());
        e.printStackTrace();
    }
    return userAnimeList;
}

// add an anime to a user's list
public void addUserAnime(int userId, int animeId, UserAnimeStatus status, int episodesWatched, Double rating) {
    String sql = """
                INSERT INTO user_anime (user_id, anime_id, status, episodes_watched, rating)
                VALUES (?, ?, ?, ?, ?)
                """;

    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {
        
        statement.setInt(1, userId);
        statement.setInt(2, animeId);
        statement.setString(3, status.name().replace("_", " "));
        statement.setInt(4, episodesWatched);

        if (rating == null) {
            statement.setNull(5, java.sql.Types.DOUBLE);
        } else {
            statement.setDouble(5, rating);
        }

        statement.executeUpdate();

    } catch (SQLException e) {
        System.err.println("Database error while adding user anime for user_id " + userId + ", anime_id " + animeId + ": " + e.getMessage());
        e.printStackTrace();
    }
}

// update a user's anime entry
public void updateUserAnime(int userId, int animeId, UserAnimeStatus status, int episodesWatched, Double rating) {
    String sql = """
                UPDATE user_anime
                SET status = ?, episodes_watched = ?, rating = ?
                WHERE user_id = ? AND anime_id = ?
                """;

    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {
        
        statement.setString(1, status.name().replace("_", " "));
        statement.setInt(2, episodesWatched);

        if (rating == null) {
            statement.setNull(3, java.sql.Types.DOUBLE);
        } else {
            statement.setDouble(3, rating);
        }
        statement.setInt(4, userId);
        statement.setInt(5, animeId);

        statement.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Database error while updating user anime for user_id " + userId + ", anime_id " + animeId + ": " + e.getMessage());
        e.printStackTrace();
    }
}
}


