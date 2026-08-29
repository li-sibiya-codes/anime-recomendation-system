package com.animerecomender.service;
import java.util.*;

import com.animerecomender.model.Anime;
import com.animerecomender.model.AnimeStatus;

public class AnimeManager {

    private ArrayList<Anime> animeList;
    private int nextAnimeId;

    public AnimeManager(ArrayList<Anime> animeList) {
        this.animeList = new ArrayList<>(animeList);
        this.nextAnimeId = determineHighestId() + 1; // Start IDs from 1
    }

    //create
    public void addAnime(String title, List<String> genres, AnimeStatus status, double rating, int episodes) {
        Anime anime = new Anime(title, genres, status, rating, episodes);
        anime.setAnimeId(generateNextAnimeId());
        animeList.add(anime);
    }

    //read
    public void displayAnimeList() {
        for (Anime anime : animeList) {
            System.out.println("ID: " + anime.getAnimeId() + ", Title: " + anime.getTitle() + ", Genres: " + anime.getGenres() + ", Status: " + anime.getStatus() + ", Rating: " + anime.getRating() + ", Episodes: " + anime.getEpisodes());
        }
    }

    public Anime searchAnimeByTitle(String title) {
        for (Anime anime : animeList) {
            if (anime.getTitle().equalsIgnoreCase(title)) {
                return anime;
            }
        }
        return null; // Return null if not found
    }

    public Anime searchAnimeById(int id) {
        for (Anime anime : animeList) {
            if (anime.getAnimeId() == id) {
                return anime;
            }
        }
        return null; // Return null if not found
    }

    //update
    public boolean updateEpisodes(int id, int newEpisodes) {
        Anime anime = searchAnimeById(id);
        if (anime != null) {
            anime.setEpisodes(newEpisodes);
            return true;
        }
        return false;
    }

    //delete
    public boolean deleteAnime(int id) {
        Anime anime = searchAnimeById(id);
        if (anime != null) {
            animeList.remove(anime);
            return true;
        }
        return false;
    }

    //sorting
    public void sortAnimeByTitle() {
        Collections.sort(animeList, Comparator.comparing(Anime::getTitle));
    }

    public void sortByEpisodes() {
        Collections.sort(animeList, Comparator.comparingInt(Anime::getEpisodes));
    }

    //ID management
    private int determineHighestId() {
        int maxId = 0;
        for (Anime anime : animeList) {
            if (anime.getAnimeId() > maxId) {
                maxId = anime.getAnimeId();
            }
        }
        return maxId;
    }

    private int generateNextAnimeId() {
        return nextAnimeId++;
    }
    
    //getters

    public List<Anime> getAnimeList() {
        return Collections.unmodifiableList(animeList);
    }

}
