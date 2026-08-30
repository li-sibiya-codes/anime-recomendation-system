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
        testWatchedAnimeIsExcluded();
        testNoOfRecommendations();
        testNoPreferences();

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

        System.out.println("Recommendations in order:");
        for (Anime anime : recommendations) {
            System.out.println(anime.getTitle());
        }
        System.out.println("\nPASS\n");

    }

    // tests if an Anime has been watched, if so it is excluded from recommendations 

    private static void testWatchedAnimeIsExcluded() {
        System.out.println("Test Four: Watched Anime Is Excluded");

        UserPreferences preferences = new UserPreferences();

        preferences.setGenrePreference("Action", 9);
        preferences.setGenrePreference("Comedy", 5);
        preferences.setGenrePreference("Romance", 3);

        User user = createTestUser(preferences);    

        Anime watchedAnime = new Anime("Watched Anime", List.of("Action"), AnimeStatus.COMPLETED, 8.0, 12);
        Anime unWatchedAnime = new Anime("Unwatched Comedy Anime", List.of("Comedy"), AnimeStatus.COMPLETED, 8.0, 12);

        // add action anime to the users watchlist

        UserAnime interaction = new UserAnime(watchedAnime, UserAnimeStatus.COMPLETED, 12, 9.0);

        user.getUserAnimeList().add(interaction);

        List<Anime> animeList = List.of(watchedAnime,unWatchedAnime);

        RecommendationEngine engine = new RecommendationEngine();

        List<Anime> recommendations = engine.recommendAnime(user, animeList, 2);

        // watched anime must NOT appear in recommendations
        assert !recommendations.contains(watchedAnime);

        //the unwatched anime MUST appear
        assert recommendations.contains(unWatchedAnime);

        System.out.println("PASS\n");
    }

    // tests that the engine returns the requested number of recommendations

    private static void testNoOfRecommendations() {
        System.out.println("Test Five: Number of Recommendations");

        UserPreferences preferences = new UserPreferences();

        preferences.setGenrePreference("Romance", 10);

        User user = createTestUser(preferences);

        List<Anime> animeList = new ArrayList<>();

        for(int i = 1; i <= 5; i++) {

        animeList.add(new Anime("Romance Anime" + i, List.of("Romance"), AnimeStatus.COMPLETED, 8.0, 12));
        }

        RecommendationEngine engine = new RecommendationEngine();

        // Requesting only 3 recommendations
        List<Anime> recs = engine.recommendAnime(user, animeList, 3);

        assert recs.size() == 3;

        recs = engine.recommendAnime(user, animeList, 10);

        assert recs.size() == 5;

        System.out.println("PASS\n");
    }

    private static void testNoPreferences() {
        System.out.println("Test Six: No Preferences");

        UserPreferences preferences = new UserPreferences();

        User user = createTestUser(preferences);

        Anime anime = new Anime("Test Anime", List.of("Action"), AnimeStatus.COMPLETED, 8.0, 12);

        List<Anime> animeList = List.of(anime);

        RecommendationEngine engine = new RecommendationEngine();

        List<Anime> recs =  engine.recommendAnime(user, animeList, 1);

        //Anime should still be returned, but it'll be 0 since the user has not specified any genre preferences.

        assert recs.size() == 1;
        assert recs.get(0) == anime;

        System.out.println("PASS\n");
    }

    private static User createTestUser(UserPreferences preferences) {
        return new User(100, "Test", "User", "test@example.com", preferences);
    }
}
