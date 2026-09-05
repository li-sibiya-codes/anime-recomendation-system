package com.animerecomender.data;

import com.animerecomender.model.Anime;
import com.animerecomender.model.AnimeStatus;
import java.sql.*;
import java.util.*;

public class AnimeRepository {

    public static ArrayList<Anime> GetAllAnime() {

        ArrayList<Anime> animeList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM anime
                ORDER BY anime_id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int animeId = resultSet.getInt("anime_id");
                String title = resultSet.getString("title");

                AnimeStatus status = AnimeStatus.valueOf(
                        resultSet.getString("status").toUpperCase()
                );

                int episodes = resultSet.getInt("episodes");
                double rating = resultSet.getDouble("rating");

                List<String> genres = getGenresForAnime(animeId, conn);

                Anime anime = new Anime(title, genres, status, rating, episodes);

                animeList.add(anime);
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }

        return animeList;
    }

    private static List<String> getGenresForAnime(
            int animeId,
            Connection conn) throws SQLException {

        List<String> genres = new ArrayList<>();

        String genreSql = """
                SELECT g.name
                FROM genres g
                JOIN anime_genres ag
                    ON g.genre_id = ag.genre_id
                WHERE ag.anime_id = ?
                ORDER BY g.genre_id
                """;

        try (PreparedStatement statement = conn.prepareStatement(genreSql)) {

            statement.setInt(1, animeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    genres.add(resultSet.getString("name"));
                }
            }
        }

        return genres;
    }

    public static Anime getAnimeById(int animeId) {

        String sql = """
                SELECT *
                FROM anime
                WHERE anime_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, animeId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String title = resultSet.getString("title");

                    AnimeStatus status = AnimeStatus.valueOf(
                            resultSet.getString("status").toUpperCase()
                    );

                    int episodes = resultSet.getInt("episodes");
                    double rating = resultSet.getDouble("rating");

                    List<String> genres =
                            getGenresForAnime(animeId, conn);

                    return new Anime(title, genres, status, rating, episodes);
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Error fetching anime by ID " + animeId + ": " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}