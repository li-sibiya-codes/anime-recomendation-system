package com.animerecomender;
 
import com.animerecomender.data.AnimeRepository;
import com.animerecomender.gui.GUI;
import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.model.UserPreferences;
import com.animerecomender.recomendation.RecommendationEngine;
import com.animerecomender.service.AnimeManager;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
 
/**
* PLEASE USE CLI.java INSTEAD OF Main.java for now
* The GUI is not fully implemented yet, and the CLI is the only way to interact with the system.
* this main class is just a placeholder to launch the GUI, which is not fully functional yet.
 */
public class Main {
 
    public static void main(String[] args) {
 
        ArrayList<Anime> animeList = AnimeRepository.getAllAnime();
        AnimeManager animeManager = new AnimeManager(animeList);
 
        UserPreferences preferences = new UserPreferences();
        User user = new User(
                1,
                "GUI",
                "User",
                "gui@example.com",
                "guiuser",
                preferences
        );
 
        RecommendationEngine recommendationEngine = new RecommendationEngine();
 
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI(animeManager, user, recommendationEngine);
            gui.setVisible(true);
        });
    }
}
 
