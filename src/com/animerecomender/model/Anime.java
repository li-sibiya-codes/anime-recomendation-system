package com.animerecomender.model;
import java.util.List;
public class Anime {
    private int animeId;
    private String title;
    private List<String> genres;
    private AnimeStatus status;
    private int episodes;
    private double rating;

    public Anime(String title, List<String> genres, AnimeStatus status, double rating, int episodes) {
        this.title = title;
        this.genres = genres;
        this.status = status;
        this.rating = rating;
        this.episodes = episodes;
    }

    public int getAnimeId() {
        return animeId;
    }

    public void setAnimeId(int animeId) {
        this.animeId = animeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public AnimeStatus getStatus() {
        return status;
    }

    public void setStatus(AnimeStatus status) {
        this.status = status;
    }
    
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        this.rating = rating;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        if (episodes < 0) {
            throw new IllegalArgumentException("Episodes cannot be negative.");
        }
        this.episodes = episodes;
    }
}
