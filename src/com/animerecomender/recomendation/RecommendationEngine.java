package com.animerecomender.recomendation;
import java.util.ArrayList;
import java.util.List;

import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserPreferences;

public class RecommendationEngine {
    public record AnimeScore(Anime anime, double score) {
        public Anime getAnime() {
            return anime;
        }

        public double getScore() {
            return score;
        }
    }

    public List<Anime> recommendAnime(User user, List<Anime> animeList, int noOfRecommendations) {

        List<AnimeScore> scoredAnimeList = new ArrayList<>();
    
        for (Anime anime : animeList) {
            if (userHasWatched(user, anime)) {
                continue; // Skip anime that the user has already watched
            }

            double score = calculateScore(anime, user.getPreferences());
            
            scoredAnimeList.add(new AnimeScore(anime, score));
        }

        // Sort the scored anime list by score in descending order
        scoredAnimeList.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        //get top no. of recommendations
        List<Anime> recommendations = new ArrayList<>();
        for (int i = 0; i < Math.min(noOfRecommendations, scoredAnimeList.size()); i++) {
            recommendations.add(scoredAnimeList.get(i).getAnime());
        }

        // Return the top recommendations
        return recommendations;

    }

    private double calculateScore(Anime anime, UserPreferences preferences) {
        String[] genres = anime.getGenre().split(",\\s*");
        double score = 0.0;

        // Calculate score based on genre preferences
        for (String genre : genres) {
            score += preferences.getGenrePreference(genre.trim());
        }

        // get rating preference and add to score

        // get status preference and add to score

        // get episodes preference and add to score

        // get other preferences and add to score (if they exist)
        return score;
    }

    private boolean userHasWatched(User user, Anime anime) {
        for (UserAnime userAnime : user.getUserAnimeList()) {
            if (userAnime.getAnime().getAnimeId() == anime.getAnimeId()) {
                return true;
            }
        }
        return false;
    }
    
}