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
                    anime.setTitle(parts[0].trim());
                    anime.setGenre(parts[1].trim());
                    anime.setStatus(Status.valueOf(parts[2].trim().toUpperCase()));
                    try{
                        anime.setEpisodes(Integer.parseInt(parts[3].trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for episodes: " + parts[3].trim());
                        anime.setEpisodes(0); // Default to 0 if parsing fails
                    }
                    animeList.add(anime);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        }
        return animeList;
    }
}