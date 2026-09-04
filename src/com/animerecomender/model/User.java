package com.animerecomender.model;
import java.util.*;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private UserPreferences preferences;
    private List<UserAnime> userAnimeList;

    public User (int userId, String firstName, String lastName, String email, String username, UserPreferences preferences) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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
