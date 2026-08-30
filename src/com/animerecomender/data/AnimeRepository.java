package com.animerecomender.data;
import java.io.*;
import java.util.*;

import com.animerecomender.model.Anime;
import com.animerecomender.model.AnimeStatus;


public class AnimeRepository {
    // This class will handle file operations such as reading and writing anime data to a file.
    // Implementation will be added later.
    public static ArrayList<Anime> readAnimeFromFile(String filename) {
        ArrayList<Anime> animeList = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                
                String[] parts = line.split("\\|");

                int animeId = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                String[] genreArray = parts[2].trim().split(",\\s*");
                List<String> genres = Arrays.asList(genreArray);
                AnimeStatus status = AnimeStatus.valueOf(parts[3].trim().toUpperCase());
                double rating;
                try {
                    rating = Double.parseDouble(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format for rating: " + parts[4].trim());
                    rating = 0.0; // Default to 0.0 if parsing fails
                }
                int episodes;
                try{
                        episodes = Integer.parseInt(parts[5].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for episodes: " + parts[5].trim());
                        episodes = 0; // Default to 0 if parsing fails
                    }

                if (parts.length == 6) {
                    Anime anime = new Anime(title, genres, status, rating, episodes);
                    animeList.add(anime);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            sc.close();
        
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
        return animeList;
    }

     public static void saveAnimeToFile(String filename, ArrayList<Anime> animeList) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Anime anime : animeList) {
                writer.write(anime.getAnimeId() + "," + anime.getTitle() + "," + String.join(", ", anime.getGenres()) + "," + anime.getStatus() + "," + anime.getRating() + "," + anime.getEpisodes() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
        }
    }
}