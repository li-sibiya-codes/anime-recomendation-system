package com.animerecomender.gui;
 
import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.service.AnimeManager;
import java.awt.*;
import java.util.Map;
import java.util.TreeSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 
/**
 * Genre preference editing.
 *
 * CONTRADICTION FIXED (was checkbox-only): the existing backend model,
 * UserPreferences, is a Map<String, Integer> — genre name to a 0-10
 * preference rating, via setGenrePreference(String, double) and
 * getGenrePreference(String). A checkbox (in/out) cannot represent that.
 * This panel now collects a genre + a 0-10 rating and writes it with
 * the existing setGenrePreference call, then shows the existing
 * preferences (via getPreferences()) in a table.
 *
 * The genre choices offered are derived from the genres that actually
 * appear on the loaded Anime objects (no invented Genre enum), matching
 * how the CLI tells the user to "enter a genre exactly as it appears in
 * the anime data." The combo box is also editable so a genre not yet
 * present in the currently loaded anime list can still be entered, same
 * as the CLI allows.
 */
public class PreferencesPanel extends JPanel {
 
    private final AnimeManager animeManager;
    private final User user;
    private final StatusBar statusBar;
 
    private final JComboBox<String> genreCombo = new JComboBox<>();
    private final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
    private final JButton saveButton = new JButton("Save Preference");
 
    private final DefaultTableModel tableModel;
    private final JTable preferencesTable;
 
    public PreferencesPanel(AnimeManager animeManager, User user, StatusBar statusBar) {
        this.animeManager = animeManager;
        this.user = user;
        this.statusBar = statusBar;
 
        setLayout(new BorderLayout(8, 8));
 
        genreCombo.setEditable(true);
 
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreCombo);
        formPanel.add(new JLabel("Preference (0-10):"));
        formPanel.add(ratingSpinner);
        formPanel.add(saveButton);
        add(formPanel, BorderLayout.NORTH);
 
        tableModel = new DefaultTableModel(new Object[]{"Genre", "Preference"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        preferencesTable = new JTable(tableModel);
        add(new JScrollPane(preferencesTable), BorderLayout.CENTER);
 
        saveButton.addActionListener(e -> onSave());
 
        refreshGenreChoices();
        refreshPreferencesTable();
    }
 
    /** Rebuilds the genre dropdown from the genres actually present on the loaded anime. */
    private void refreshGenreChoices() {
        TreeSet<String> genres = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Anime anime : animeManager.getAnimeList()) {
            genres.addAll(anime.getGenres());
        }
        genreCombo.removeAllItems();
        for (String genre : genres) {
            genreCombo.addItem(genre);
        }
    }
 
    private void onSave() {
        Object selected = genreCombo.getEditor().getItem();
        String genre = selected == null ? "" : selected.toString().trim();
        int rating = (Integer) ratingSpinner.getValue();
 
        if (genre.isEmpty()) {
            statusBar.showMessage("Genre cannot be empty.");
            return;
        }
 
        try {
            user.getPreferences().setGenrePreference(genre, rating);
            statusBar.showMessage("Preference saved: " + genre + " = " + rating + "/10");
            refreshPreferencesTable();
        } catch (IllegalArgumentException ex) {
            statusBar.showMessage("Invalid preference: " + ex.getMessage());
        }
    }
 
    /** Re-reads user.getPreferences().getPreferences() (existing method) into the table. */
    private void refreshPreferencesTable() {
        tableModel.setRowCount(0);
        Map<String, Integer> preferences = user.getPreferences().getPreferences();
        for (Map.Entry<String, Integer> entry : preferences.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }
}