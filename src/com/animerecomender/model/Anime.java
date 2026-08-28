package com.animerecomender.model;
public class Anime {
    private int animeId;
    private String title;
    private String genre;        
    private AnimeStatus status;
    private int episodes;
    private double rating;

    public Anime(String title, String genre, AnimeStatus status, double rating, int episodes) {
        this.title = title;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
