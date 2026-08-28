public class UserAnime {
    //represents one users interaction with one anime
    private Anime anime;
    private UserAnimeStatus status;
    private int episodesWatched;
    private double rating; 

    public UserAnime(Anime anime, UserAnimeStatus status, int episodesWatched, double rating) {
        this.anime = anime;
        this.status = status;
        this.episodesWatched = episodesWatched;
        this.rating = rating;
    }

    // Getters and setters for each field
    public Anime getAnime() {
        return anime;
    }

    public UserAnimeStatus getUserAnimeStatus() {
        return status;
    }

    public void setUserAnimeStatus(UserAnimeStatus status) {
        this.status = status;
    }

    public int getEpisodesWatched() {
        return episodesWatched;
    }

    public void setEpisodesWatched(int episodesWatched) {
        if (episodesWatched < 0) {
            throw new IllegalArgumentException("Episodes watched cannot be negative.");
        }
        this.episodesWatched = episodesWatched;
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
}