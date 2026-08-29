package com.animerecomender;

import java.util.*;

import com.animerecomender.model.Anime;
import com.animerecomender.model.AnimeStatus;  
import com.animerecomender.model.User;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserAnimeStatus;
import com.animerecomender.model.UserPreferences;
import com.animerecomender.recomendation.RecommendationEngine;

public class RecommendationEngineTesting {

    public static void main(String[] args) {
        
        System.out.println("=================================");
        System.out.println("RECOMMENDATION ENGINE TESTS");
        System.out.println("=================================\n");

        testGenrePrefernces();
        testInvalidGenrePreference();
        testRecommendationsAreRanked();

        System.out.println("=================================");
        System.out.println("ALL TESTS COMPLETED");
        System.out.println("=================================\n");


    }

    private static void testGenrePrefernces() {

        System.out.println("Test One: Genre Preferences");

        UserPreferences preferences = new UserPreferences();

        preferences.setGenrePreference("Action", 9);
        preferences.setGenrePreference("Comedy", 5);
        preferences.setGenrePreference("Romance", 3);

        assert preferences.getGenrePreference("Action") == 9;
        assert preferences.getGenrePreference("Comedy") == 5;
        assert preferences.getGenrePreference("Romance") == 3;

        // A genre that has not been set should return 0.
        assert preferences.getGenrePreference("Horror") == 0;

        System.out.println("PASS\n");
    }

    private static void testInvalidGenrePreference() {
        System.out.println("Test Two: Invalid Genre Preference");

        UserPreferences preferences = new UserPreferences();

        boolean exceptionThrown = false;
        try {
            preferences.setGenrePreference("Action", 11); // Invalid rating
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        assert exceptionThrown;

        exceptionThrown = false;
        try {
            preferences.setGenrePreference("Comedy", -1); // Invalid rating
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }

        assert exceptionThrown;

        System.out.println("PASS\n");
    }

    private static void testRecommendationsAreRanked() {
        System.out.println("Test Three: Recommendations Are Ranked");

        UserPreferences preferences = new UserPreferences();
        
        preferences.setGenrePreference("Action", 9);
        preferences.setGenrePreference("Comedy", 5);
        preferences.setGenrePreference("Romance", 3);

        User user = createTestUser(preferences);

        Anime actionAnime = new Anime("Action Anime", List.of("Action"), AnimeStatus.ONGOING, 8.0, 12);
        Anime comedyAnime = new Anime("Comedy Anime", List.of("Comedy"), AnimeStatus.CANCELLED, 4.0, 10);
        Anime romanceAnime = new Anime("Romance Anime", List.of("Romance"), AnimeStatus.COMPLETED, 6.0, 8);

        List<Anime> animeList = List.of(actionAnime, comedyAnime, romanceAnime);

        RecommendationEngine engine = new RecommendationEngine();
        List<Anime> recommendations = engine.recommendAnime(user, animeList, 3);

        /*
        * Action has preference of 9
        * Comedy has preference of 5
        * Romance has preference of 3
        * 
        * Therefore, the expected order of recommendations should be:
        * A -> C -> R
        */

        assert recommendations.get(0).getTitle().equals("Action Anime");
        assert recommendations.get(1).getTitle().equals("Comedy Anime");    
        assert recommendations.get(2).getTitle().equals("Romance Anime");

        System.out.println("\nRecommendations in order:");
        for (Anime anime : recommendations) {
            System.out.println(anime.getTitle());
        }
        System.out.println("\nPASS\n");

    }

    private static User createTestUser(UserPreferences preferences) {
        return new User(100, "Test", "User", "test@example.com", preferences);
    }
}
