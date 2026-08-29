package com.animerecomender.recomendation;
import java.util.ArrayList;
import java.util.List;

import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserPreferences;

public class RecommendationEngine {

    // Store an anime and its calculated score for recommendation purposes
    // it's a record bc we only need a temp association of anime and score, no need for a full class

    public record AnimeScore(Anime anime, double score) {
        public Anime getAnime() {
            return anime;
        }

        public double getScore() {
            return score;
        }
    }

    public List<Anime> recommendAnime(User user, List<Anime> animeList, int noOfRecommendations) {
        //validate the no of recommendations requested
        if (noOfRecommendations <= 0) {
            return new ArrayList<>(); // Return an empty list if the requested number is not positive
        }

        List<AnimeScore> scoredAnimeList = new ArrayList<>();
        
        // score each anime based on user preferences and add to scoredAnimeList
        for (Anime anime : animeList) {
            // Skip anime that the user has already watched
            if (userHasWatched(user, anime)) {
                continue;
            }
            // calculate how well this anime matches the user's preferences.
            double score = calculateScore(anime, user.getPreferences());
            
            scoredAnimeList.add(new AnimeScore(anime, score));
        }

        // Sort the scored anime list by score in descending order
        scoredAnimeList.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        // Double.compare(b.getScore(), a.getScore()) means
        // if b's score is greater than a's score, it will return a positive number,

        //store the final recommendations
        List<Anime> recommendations = new ArrayList<>();

        // Only return as many anime as requested, unless there are fewer available anime.

        int noToReturn = Math.min(noOfRecommendations, scoredAnimeList.size());
        
        for (int i = 0; i < noToReturn; i++) {
            recommendations.add(scoredAnimeList.get(i).getAnime());
        }

        // Return the top recommendations
        return recommendations;

    }
    /**
     * Calculates how well an anime matches the user's preferences.
     *
     * Currently the score is based on genre preferences.
     *
     * For anime with multiple genres, the average preference
     * of the matching genres is used. This prevents anime from
     * receiving a higher score simply because they have more genres.
     *
     * Example:
     *
     * User preferences:
     * Action = 8
     * Comedy = 6
     *
     * Anime genres:
     * Action, Comedy
     *
     * Score:
     * (8 + 6) / 2 = 7
     *
     * @param anime the anime being scored
     * @param preferences the user's preferences
     * @return the anime's recommendation score
     */

    private double calculateScore(Anime anime, UserPreferences preferences) {
        //get anime genres
        double score = 0.0;
        int matchedGenres = 0;

        // check each genre of the anime against the user's preferences and add to score
        for (String genre : anime.getGenres()) {

            String trimmedGenre = genre.trim();

            int genrePreference = preferences.getGenrePreference(trimmedGenre);
            // pref of 0 means:
            // - user gave the genre a rating of 0
            // - user has no preference for the genre
            if (genrePreference > 0) {
                score += genrePreference;
                matchedGenres++;
            }
        }

        if (matchedGenres == 0) {
            return 0.0;
        }

        /*// get rating preference and add to score
        score += preferences.getRatingPreference(anime.getRating());

        // get status preference and add to score
         score += preferences.getStatusPreference(anime.getStatus());

        // get episodes preference and add to score
        score += preferences.getEpisodesPreference(anime.getEpisodes());

        // get other preferences and add to score (if implemented in the future)
        // otherwise just return the score based on the above preferences */

        // return the average score for the matched genres
        return score/matchedGenres; 
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