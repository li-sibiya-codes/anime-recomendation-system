package com.animerecomender;

import java.util.*;

import com.animerecomender.model.Anime;
import com.animerecomender.model.AnimeStatus;  
import com.animerecomender.model.User;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserAnimeStatus;
import com.animerecomender.model.UserPreferences;
import com.animerecomender.recomendation.RecommendationEngine;

public class RecommenationEngineTesting {

    public static void main(String[] args) {
        
        System.out.println("=================================");
        System.out.println("RECOMMENDATION ENGINE TESTS");
        System.out.println("=================================\n");

        testGenrePrefernces();
        testInvalidGenrePreference();

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
    
}
