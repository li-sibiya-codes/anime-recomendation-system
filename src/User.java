import java.util.*;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserPreferences preferences;
    private List<UserAnime> userAnimeList;

    public User (int userId, String firstName, String lastName, String email, UserPreferences preferences) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.preferences = preferences;
        this.userAnimeList = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public List<UserAnime> getUserAnimeList() {
        return userAnimeList;
}


}
