package com.animerecomender.model;
public class Anime {
    private int animeId;
    private String title;
    private String genre;        
    private AnimeStatus status;
    private int episodes;

    public Anime(String title, String genre, AnimeStatus status, int episodes) {
        this.title = title;
        this.genre = genre;
        this.status = status;
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
