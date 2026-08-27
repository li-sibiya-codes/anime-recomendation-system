import java.util.*;

public class UserPreferences {
    private Map<String, Integer> preferredGenres;

    public UserPreferences() {
        this.preferredGenres = new HashMap<>();
    }

    public void setGenrePreference(String genre, int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        preferredGenres.put(genre, rating);
    }

    public int getGenrePreference(String genre) {
        return preferredGenres.getOrDefault(genre, 0); // Default to 0 if genre not found
    }

    public Map<String, Integer> getPreferences() {
        return preferredGenres;
    }
}
