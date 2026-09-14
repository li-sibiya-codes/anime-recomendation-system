package com.animerecomender;

import com.animerecomender.data.AnimeRepository;
import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.model.UserPreferences;
import com.animerecomender.recomendation.RecommendationEngine;
import com.animerecomender.service.AnimeManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLI {

    private final Scanner scanner;
    private final AnimeManager animeManager;
    private final User user;
    private final RecommendationEngine recommendationEngine;

    public CLI() {

        scanner = new Scanner(System.in);

        // Load anime from the database
        ArrayList<Anime> animeList = AnimeRepository.getAllAnime();

        // Give the list to AnimeManager
        animeManager = new AnimeManager(animeList);

        // Create a user for the CLI session
        UserPreferences preferences = new UserPreferences();

        user = new User(
                1,
                "CLI",
                "User",
                "cli@example.com",
                "cliuser",
                preferences
        );

        recommendationEngine = new RecommendationEngine();
    }

    public void start() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       ANIME RECOMMENDATION SYSTEM");
        System.out.println("========================================");

        System.out.println();
        System.out.println("Welcome, " + user.getUsername() + "!");

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    browseAnime();
                    break;

                case 2:
                    searchAnime();
                    break;

                case 3:
                    sortAnime();
                    break;

                case 4:
                    viewAnimeDetails();
                    break;

                case 5:
                    managePreferences();
                    break;

                case 6:
                    viewPreferences();
                    break;

                case 7:
                    getRecommendations();
                    break;

                case 0:
                    running = false;
                    System.out.println();
                    System.out.println("Thank you for using the Anime Recommendation System!");
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void displayMainMenu() {

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("                MAIN MENU");
        System.out.println("----------------------------------------");
        System.out.println("1. Browse Anime");
        System.out.println("2. Search Anime");
        System.out.println("3. Sort Anime");
        System.out.println("4. View Anime Details");
        System.out.println("5. Set Genre Preferences");
        System.out.println("6. View My Preferences");
        System.out.println("7. Get Recommendations");
        System.out.println("0. Exit");
        System.out.println("PLEASE NOTE: ");
        System.out.println("1. When entering genres for preferences, please enter them exactly as they appear in the anime data.");
        System.out.println("2. When searching by title, you must enter the exact title as it appears in the anime data.");
        System.out.println("3. When you must set preferences before getting recommendations, otherwise the system will not be able to provide any recommendations.");
        System.out.println("----------------------------------------");
    }

    // ---------------------------------------------------------
    // BROWSE ANIME
    // ---------------------------------------------------------

    private void browseAnime() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("              BROWSE ANIME");
        System.out.println("========================================");

        List<Anime> animeList = animeManager.getAnimeList();

        if (animeList.isEmpty()) {
            System.out.println("No anime found.");
            return;
        }

        for (Anime anime : animeList) {
            printShortAnime(anime);
        }

        System.out.println();
        System.out.println("Total anime: " + animeList.size());
    }

    // ---------------------------------------------------------
    // SEARCH
    // ---------------------------------------------------------

    private void searchAnime() {

        boolean searching = true;

        while (searching) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              SEARCH ANIME");
            System.out.println("========================================");
            System.out.println("1. Search by title");
            System.out.println("2. Search by ID");
            System.out.println("0. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    searchByTitle();
                    break;

                case 2:
                    searchById();
                    break;

                case 0:
                    searching = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void searchByTitle() {

        System.out.print("\nEnter anime title: ");
        String title = scanner.nextLine();

        Anime anime = animeManager.searchAnimeByTitle(title);

        if (anime == null) {
            System.out.println("\nNo anime found with that title.");
            return;
        }

        System.out.println();
        printFullAnime(anime);
    }

    private void searchById() {

        int id = readInt("\nEnter anime ID: ");

        Anime anime = animeManager.searchAnimeById(id);

        if (anime == null) {
            System.out.println("\nNo anime found with that ID.");
            return;
        }

        System.out.println();
        printFullAnime(anime);
    }

    // ---------------------------------------------------------
    // SORTING
    // ---------------------------------------------------------

    private void sortAnime() {

        boolean sorting = true;

        while (sorting) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("              SORT ANIME");
            System.out.println("========================================");
            System.out.println("1. Sort by title");
            System.out.println("2. Sort by number of episodes");
            System.out.println("0. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    animeManager.sortAnimeByTitle();
                    System.out.println("\nAnime sorted by title.");
                    animeManager.displayAnimeList();
                    break;

                case 2:
                    animeManager.sortByEpisodes();
                    System.out.println("\nAnime sorted by number of episodes.");
                    animeManager.displayAnimeList();
                    break;

                case 0:
                    sorting = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ---------------------------------------------------------
    // VIEW ANIME DETAILS
    // ---------------------------------------------------------

    private void viewAnimeDetails() {

        int id = readInt("\nEnter anime ID: ");

        Anime anime = animeManager.searchAnimeById(id);

        if (anime == null) {
            System.out.println("\nAnime not found.");
            return;
        }

        printFullAnime(anime);
    }

    // ---------------------------------------------------------
    // USER PREFERENCES
    // ---------------------------------------------------------

    private void managePreferences() {

        boolean managing = true;

        while (managing) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("          GENRE PREFERENCES");
            System.out.println("========================================");
            System.out.println("1. Set genre preference");
            System.out.println("2. View current preferences");
            System.out.println("0. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    setGenrePreference();
                    break;

                case 2:
                    viewPreferences();
                    break;

                case 0:
                    managing = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void setGenrePreference() {

        System.out.println();
        System.out.println("Enter a genre exactly as it appears in the anime data.");
        System.out.println("Examples: Action, Comedy, Fantasy, Horror, Mystery");
        System.out.println();

        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();

        if (genre.isEmpty()) {
            System.out.println("Genre cannot be empty.");
            return;
        }

        double rating = readDouble("Preference rating (0-10): ");

        try {

            user.getPreferences().setGenrePreference(genre, rating);

            System.out.println();
            System.out.println("Preference saved:");
            System.out.println(genre + " = "
                    + user.getPreferences().getGenrePreference(genre)
                    + "/10");

        } catch (IllegalArgumentException e) {

            System.out.println();
            System.out.println("Invalid preference: " + e.getMessage());
        }
    }

    private void viewPreferences() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("           MY PREFERENCES");
        System.out.println("========================================");

        Map<String, Integer> preferences =
                user.getPreferences().getPreferences();

        if (preferences.isEmpty()) {
            System.out.println("You have not set any genre preferences yet.");
            return;
        }

        for (Map.Entry<String, Integer> entry : preferences.entrySet()) {

            System.out.println(
                    entry.getKey()
                    + " : "
                    + entry.getValue()
                    + "/10"
            );
        }
    }

    // ---------------------------------------------------------
    // RECOMMENDATIONS
    // ---------------------------------------------------------

    private void getRecommendations() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          RECOMMENDATIONS");
        System.out.println("========================================");

        if (user.getPreferences().getPreferences().isEmpty()) {

            System.out.println(
                    "You have not set any genre preferences yet."
            );

            System.out.println(
                    "Please set some preferences first."
            );

            return;
        }

        int number = readInt(
                "\nHow many recommendations would you like? "
        );

        if (number <= 0) {
            System.out.println("Please enter a number greater than 0.");
            return;
        }

        List<Anime> recommendations =
                recommendationEngine.recommendAnime(
                        user,
                        animeManager.getAnimeList(),
                        number
                );

        if (recommendations.isEmpty()) {

            System.out.println();
            System.out.println("No recommendations found.");
            return;
        }

        System.out.println();
        System.out.println("========== YOUR RECOMMENDATIONS ==========");

        int position = 1;

        for (Anime anime : recommendations) {

            System.out.println();
            System.out.println(
                    position + ". " + anime.getTitle()
            );

            System.out.println(
                    "   Genres: " + anime.getGenres()
            );

            System.out.println(
                    "   Rating: " + anime.getRating()
            );

            position++;
        }
    }

    // ---------------------------------------------------------
    // DISPLAY HELPERS
    // ---------------------------------------------------------

    private void printShortAnime(Anime anime) {

        System.out.println(
                anime.getAnimeId()
                + ". "
                + anime.getTitle()
        );
    }

    private void printFullAnime(Anime anime) {

        System.out.println("----------------------------------------");
        System.out.println("ID:       " + anime.getAnimeId());
        System.out.println("Title:    " + anime.getTitle());
        System.out.println("Genres:   " + anime.getGenres());
        System.out.println("Status:   " + anime.getStatus());
        System.out.println("Rating:   " + anime.getRating());
        System.out.println("Episodes: " + anime.getEpisodes());
        System.out.println("----------------------------------------");
    }

    // ---------------------------------------------------------
    // INPUT HELPERS
    // ---------------------------------------------------------

    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid whole number."
                );
            }
        }
    }

    private double readDouble(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            try {
                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    // ---------------------------------------------------------
    // MAIN
    // ---------------------------------------------------------

    public static void main(String[] args) {

        CLI cli = new CLI();
        cli.start();
    }
}