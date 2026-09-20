package com.animerecomender.gui;
 
import com.animerecomender.model.Anime;
import com.animerecomender.model.User;
import com.animerecomender.model.UserAnime;
import com.animerecomender.model.UserAnimeStatus;
import java.awt.*;
import javax.swing.*;
 
/**
 * Watch-status editing for whichever anime is currently selected in
 * AnimeBrowserPanel.
 *
 * Receives selection updates through AnimeBrowserPanel.addSelectionListener
 * rather than reaching into that panel's table/model directly.
 *
 * CONTRADICTION FIXED: the Favourite checkbox has been removed.
 * UserAnime has no favourite field (user, anime, status, episodesWatched,
 * rating only) — see the Contradictions table in the accompanying
 * analysis.
 *
 * PERSISTENCE NOTE: there is no AnimeManager/UserAnimeManager method to
 * save a UserAnime — only a UserAnimeRepository with DB-backed
 * addUserAnime/updateUserAnime, which this GUI layer never calls
 * elsewhere and which isn't wired up to a live DB connection here. What
 * IS real and already exists is User.getUserAnimeList(), which returns
 * the live (mutable) list reference, not a copy. So "Save" adds/updates
 * an entry in that in-memory list directly — genuinely persisted for
 * the session, using only existing methods, with no invented backend
 * calls or fake success messages.
 */
public class WatchStatusPanel extends JPanel {
 
    private final User user;
    private final StatusBar statusBar;
 
    private final JLabel selectedAnimeLabel = new JLabel("No anime selected");
    private final JComboBox<UserAnimeStatus> statusCombo = new JComboBox<>(UserAnimeStatus.values());
    private final JSpinner episodesWatchedSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
    private final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10.0, 0.5));
    private final JButton saveButton = new JButton("Save Watch Info");
 
    private Anime selectedAnime;
 
    public WatchStatusPanel(User user, StatusBar statusBar, AnimeBrowserPanel browserPanel) {
        this.user = user;
        this.statusBar = statusBar;
 
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
 
        int row = 0;
        addRow(gbc, row++, new JLabel("Selected anime:"), selectedAnimeLabel);
        addRow(gbc, row++, new JLabel("Watch status:"), statusCombo);
        addRow(gbc, row++, new JLabel("Episodes watched:"), episodesWatchedSpinner);
        addRow(gbc, row++, new JLabel("Rating:"), ratingSpinner);
 
        gbc.gridx = 1;
        gbc.gridy = row;
        add(saveButton, gbc);
 
        saveButton.addActionListener(e -> onSave());
 
        browserPanel.addSelectionListener(anime -> {
            this.selectedAnime = anime;
            selectedAnimeLabel.setText(anime.getTitle());
            loadExistingEntryIfAny(anime);
        });
    }
 
    private void addRow(GridBagConstraints gbc, int row, JComponent label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        add(label, gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }
 
    /** If this anime is already in the user's list, show its current status/episodes/rating. */
    private void loadExistingEntryIfAny(Anime anime) {
        UserAnime existing = findExisting(anime);
        if (existing != null) {
            statusCombo.setSelectedItem(existing.getUserAnimeStatus());
            episodesWatchedSpinner.setValue(existing.getEpisodesWatched());
            ratingSpinner.setValue(existing.getRating() == null ? 0.0 : existing.getRating());
        }
    }
 
    private UserAnime findExisting(Anime anime) {
        for (UserAnime ua : user.getUserAnimeList()) {
            if (ua.getAnime().getAnimeId() == anime.getAnimeId()) {
                return ua;
            }
        }
        return null;
    }
 
    private void onSave() {
        if (selectedAnime == null) {
            statusBar.showMessage("Select an anime first.");
            return;
        }
 
        UserAnimeStatus status = (UserAnimeStatus) statusCombo.getSelectedItem();
        int episodesWatched = (Integer) episodesWatchedSpinner.getValue();
        double rating = (Double) ratingSpinner.getValue();
 
        UserAnime existing = findExisting(selectedAnime);
        if (existing != null) {
            existing.setUserAnimeStatus(status);
            existing.setEpisodesWatched(episodesWatched);
            existing.setRating(rating);
            statusBar.showMessage("Watch info updated for " + selectedAnime.getTitle() + ".");
        } else {
            UserAnime userAnime = new UserAnime(user, selectedAnime, status, episodesWatched, rating);
            user.getUserAnimeList().add(userAnime);
            statusBar.showMessage("Watch info saved for " + selectedAnime.getTitle() + ".");
        }
    }
}