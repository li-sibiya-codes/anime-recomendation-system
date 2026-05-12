import java.io.*;
import java.util.*;


public class FileHandler {
    // This class will handle file operations such as reading and writing anime data to a file.
    // Implementation will be added later.
    public static ArrayList<Anime> readAnimeFromFile(String filename) {
        ArrayList<Anime> animeList = new ArrayList<>();

        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                
                String[] parts = line.split(",");

                if (parts.length == 4) {
                    Anime anime = new Anime();
                    anime.setAnimeId(animeList.size() + 1); // Assign a unique ID based on the current list size
                    anime.setTitle(parts[1].trim());
                    anime.setGenre(parts[2].trim());
                    anime.setStatus(Status.valueOf(parts[3].trim().toUpperCase()));
                    try{
                        anime.setEpisodes(Integer.parseInt(parts[4].trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for episodes: " + parts[4].trim());
                        anime.setEpisodes(0); // Default to 0 if parsing fails
                    }
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

     public static ArrayList<Anime> saveAnimeToFile(String filename, ArrayList<Anime> animeList) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (Anime anime : animeList) {
                writer.write(anime.getAnimeId() + "," + anime.getTitle() + "," + anime.getGenre() + "," + anime.getStatus() + "," + anime.getEpisodes() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filename);
        }
        return animeList;
    }
}