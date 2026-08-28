package com.animerecomender;
import com.animerecomender.model.Anime;
import com.animerecomender.model.AnimeStatus;
import com.animerecomender.model.User;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserAnimeStatus;
import com.animerecomender.model.UserPreferences;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // This is the entry point of the application.
        // We will initialize the AnimeManager and FileHandler here in the future.
        //gui goes here 

        ArrayList<Anime> animeList = AnimeRepository.readAnimeFromFile("anime_data.txt");

        // Testing
        Anime dOTSR = new Anime("Daemons of The Shadow Realm", "Action, Adventure, Supernatural", AnimeStatus.ONGOING, 15);
        
        //creating preferences for user
        UserPreferences preferences = new UserPreferences();

        preferences.setGenrePreference("Action", 9);
        preferences.setGenrePreference("Adventure", 8);
        preferences.setGenrePreference("Romance", 4);

        //creating user with preferences
        User user = new User(1, "John", "Doe", "john.doe@example.com", preferences);

        //Creating UserAnime object to associate user with anime
        UserAnime dOTSRInteraction = new UserAnime(dOTSR, UserAnimeStatus.WATCHING, 10, 8.5);

        //adding userAnime to user's list
        user.getUserAnimeList().add(dOTSRInteraction);

        //Testing the output
        System.out.println("User: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Anime: " + dOTSRInteraction.getAnime().getTitle());

        System.out.println("\nGenre Preferences:");
        System.out.println("Action: " + user.getPreferences().getGenrePreference("Action"));
        System.out.println("Adventure: " + user.getPreferences().getGenrePreference("Adventure"));
        System.out.println("Romance: " + user.getPreferences().getGenrePreference("Romance"));

        System.out.println("\nUser Anime Interaction:");

        for (UserAnime userAnime : user.getUserAnimeList()) {
            System.out.println("Anime Title: " + userAnime.getAnime().getTitle());
            System.out.println("Status: " + userAnime.getUserAnimeStatus());
            System.out.println("Episodes Watched: " + userAnime.getEpisodesWatched());
            System.out.println("Rating: " + userAnime.getRating());
        }
    }
}