package com.animerecomender.model;
import java.util.*;

public class UserPreferences {
    // Store user preferences for anime genres, ratings, and other attributes
    // KEY: Genre, VALUE: Rating (0-10)
    private Map<String, Integer> preferredGenres;

    public UserPreferences() {
        this.preferredGenres = new HashMap<>();
    }
    //Sets the user's preference for a specific genre
    // genre = the genre of anime (e.g., "Action", "Romance") the user is rating
    // rating = the user's rating for that genre (0-10)

    public void setGenrePreference(String genre, double rating) {
        //make sure genre is valid
        if(genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be null or empty.");
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        preferredGenres.put(genre, (int) Math.round(rating));
    }

    // Retrieves the user's preference for a specific genre
    
    public int getGenrePreference(String genre) {
        if (genre == null) {
            return 0; // Default to 0 if genre is null
        }
        return preferredGenres.getOrDefault(genre, 0); // Default to 0 if genre not found
    }

    // Returns an unmodifiable view of the user's genre preferences

    public Map<String, Integer> getPreferences() {
        return Collections.unmodifiableMap(preferredGenres);
    }
}
