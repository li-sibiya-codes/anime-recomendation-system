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
 * CONTRADICTION FIXED: this used to call `new GUI()`, but GUI has no
 * no-arg constructor (and never did — see the Contradictions table in
 * the accompanying analysis).
 *
 * There is currently no login/user-selection feature anywhere in the
 * backend (AnimeManager has no user methods, and CLI.java never calls
 * UserRepository at all). So, exactly like CLI.java's constructor does,
 * this builds one fixed in-memory User for the GUI session rather than
 * inventing a lookup/creation flow that doesn't exist yet. If a real
 * login is added later, only this wiring needs to change.
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
 
