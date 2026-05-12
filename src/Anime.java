public class Anime {
    private final String title;
    private final String genre;        
    private Status status;
    private int episodes;

    public Anime(String title, String genre, Status status, int episodes) {
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.episodes = episodes;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    } 

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }
}
