import java.util.*;

public class AnimeManager {

    private ArrayList<Anime> animeList;

    public AnimeManager() {
        animeList = new ArrayList<>();
    }

    public ArrayList<Anime> getAnimeList() {
        return animeList;
    }

     public void addAnime(Anime anime) {
        animeList.add(anime);
    }

    public void searchAnimeByTitle(String title) {
        for (Anime anime : animeList) {
            if (anime.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Found: " + anime.getTitle() + " - " + anime.getGenre() + " - " + anime.getStatus() + " - " + anime.getEpisodes() + " episodes");
                return;
            }
        }
        System.out.println("Anime not found: " + title);
    }

    

}
