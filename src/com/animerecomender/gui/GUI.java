package com.animerecomender.gui;
 
import javax.swing.*;
import java.awt.*;
 
import com.animerecomender.model.User;
import com.animerecomender.recomendation.RecommendationEngine;
import com.animerecomender.service.AnimeManager;
 
/**
 * Main application window.
 *
 * Architecture: GUI -> AnimeManager (anime data) and
 * GUI -> RecommendationEngine (recommendations), both operating on a
 * single in-memory User for this session. No panel contains SQL, DB
 * credentials, or recommendation/business logic — they collect input
 * and delegate to AnimeManager / RecommendationEngine / the existing
 * model classes.
 *
 * CONTRADICTION FIXED: the original file imported
 * "com.animerecomender.AnimeManager" and referenced an unimported
 * "RecommendationEngine" — neither resolves, since the real classes
 * live in com.animerecomender.service.AnimeManager and
 * com.animerecomender.recomendation.RecommendationEngine. It also
 * exposed a no-arg GUI() constructor that Main.java called, but that
 * constructor never existed. Both are fixed below; see the
 * Contradictions table in the accompanying analysis for the full list.
 *
 * DESIGN NOTES (flagged, not hidden):
 * - Swing (JFrame/JPanel) is used since no GUI framework was specified.
 * - Sections are JTabbedPane tabs rather than one stacked vertical
 *   panel — easiest to keep unstyled for now, and each tab can be
 *   restyled independently later without touching the others.
 */
public class GUI extends JFrame {
 
    private final AnimeManager animeManager;
    private final User user;
    private final RecommendationEngine recommendationEngine;
 
    private final StatusBar statusBar;
 
    public GUI(AnimeManager animeManager, User user, RecommendationEngine recommendationEngine) {
        super("Anime Recommendation System");
        this.animeManager = animeManager;
        this.user = user;
        this.recommendationEngine = recommendationEngine;
 
        this.statusBar = new StatusBar();
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
 
        JTabbedPane tabs = new JTabbedPane();
 
        UserPanel userPanel = new UserPanel(user, statusBar);
        AnimeBrowserPanel browserPanel = new AnimeBrowserPanel(animeManager, statusBar);
        PreferencesPanel preferencesPanel = new PreferencesPanel(animeManager, user, statusBar);
        WatchStatusPanel watchStatusPanel = new WatchStatusPanel(user, statusBar, browserPanel);
        RecommendationPanel recommendationPanel = new RecommendationPanel(animeManager, user, recommendationEngine, statusBar);
 
        tabs.addTab("User", userPanel);
        tabs.addTab("Browse Anime", browserPanel);
        tabs.addTab("Preferences", preferencesPanel);
        tabs.addTab("Watch Status", watchStatusPanel);
        tabs.addTab("Recommendations", recommendationPanel);
 
        add(tabs, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
 
        setSize(900, 650);
        setLocationRelativeTo(null);
    }
}
 
