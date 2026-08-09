import java.io.*;
import java.util.*;


public class AnimeRepository {
    // This class will handle file operations such as reading and writing anime data to a file.
    // Implementation will be added later.
    public static ArrayList<Anime> readAnimeFromFile(String filename) {
        ArrayList<Anime> animeList = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                
                String[] parts = line.split(",");

                int animeId = Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                String genre = parts[2].trim();
                Status status = Status.valueOf(parts[3].trim().toUpperCase());
                int episodes;
                try{
                        episodes = Integer.parseInt(parts[4].trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for episodes: " + parts[4].trim());
                        episodes = 0; // Default to 0 if parsing fails
                    }

                if (parts.length == 5) {
                    Anime anime = new Anime(title, genre, status, episodes);
                    anime.setAnimeId(animeId);
                    animeList.add(anime);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
                sc.close();
            }
        
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
        return animeList;
    }

     public static void saveAnimeToFile(String filename, ArrayList<Anime> animeList) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Anime anime : animeList) {
                writer.write(anime.getAnimeId() + "," + anime.getTitle() + "," + anime.getGenre() + "," + anime.getStatus() + "," + anime.getEpisodes() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
        }
    }
}