package com.animerecomender.gui;
 
import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.recomendation.RecommendationEngine;
import com.animerecomender.service.AnimeManager;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 
/**
 * Recommendation results section.
 *
 * CONTRADICTION FIXED: the original panel had no control for "how many
 * recommendations", even though the only existing engine method,
 * recommendAnime(User, List<Anime>, int), requires that count as its
 * third argument. A JSpinner has been added for it.
 *
 * Calls RecommendationEngine directly (as its own object, not through
 * AnimeManager) since AnimeManager has no recommendation-related method
 * at all — this is the only existing way to get recommendations.
 */
public class RecommendationPanel extends JPanel {
 
    private final AnimeManager animeManager;
    private final User user;
    private final RecommendationEngine recommendationEngine;
    private final StatusBar statusBar;
 
    private final JSpinner countSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
    private final JButton getRecommendationsButton = new JButton("Get Recommendations");
    private final DefaultTableModel tableModel;
    private final JTable recommendationTable;
 
    public RecommendationPanel(AnimeManager animeManager, User user,
                                RecommendationEngine recommendationEngine, StatusBar statusBar) {
        this.animeManager = animeManager;
        this.user = user;
        this.recommendationEngine = recommendationEngine;
        this.statusBar = statusBar;
 
        setLayout(new BorderLayout(8, 8));
 
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Number of recommendations:"));
        topPanel.add(countSpinner);
        topPanel.add(getRecommendationsButton);
        add(topPanel, BorderLayout.NORTH);
 
        tableModel = new DefaultTableModel(new Object[]{"Title", "Genres", "Rating"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        recommendationTable = new JTable(tableModel);
        add(new JScrollPane(recommendationTable), BorderLayout.CENTER);
 
        getRecommendationsButton.addActionListener(e -> onGetRecommendations());
    }
 
    private void onGetRecommendations() {
        if (user.getPreferences().getPreferences().isEmpty()) {
            statusBar.showMessage("Set some genre preferences first.");
            return;
        }
 
        int count = (Integer) countSpinner.getValue();
 
        List<Anime> results = recommendationEngine.recommendAnime(
                user, animeManager.getAnimeList(), count);
 
        tableModel.setRowCount(0);
        for (Anime anime : results) {
            tableModel.addRow(new Object[]{
                    anime.getTitle(),
                    String.join(", ", anime.getGenres()),
                    anime.getRating()
            });
        }
 
        statusBar.showMessage(results.isEmpty() ? "No recommendations found." : "Recommendations loaded.");
    }
}