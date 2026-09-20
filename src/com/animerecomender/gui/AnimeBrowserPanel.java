package com.animerecomender.gui;
 
import com.animerecomender.model.Anime;
import com.animerecomender.service.AnimeManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
 
/**
 * Anime search / browse / sort / details section.
 *
 * Talks only to AnimeManager (never AnimeRepository or SQL directly).
 * Exposes selection changes to other panels via a listener callback
 * (see addSelectionListener) instead of those panels reaching into this
 * one's internals — keeps WatchStatusPanel decoupled from table/model
 * details here.
 *
 * Wired to existing AnimeManager methods only:
 *   getAnimeList(), searchAnimeByTitle(title), searchAnimeById(id),
 *   sortAnimeByTitle(), sortByEpisodes().
 */
public class AnimeBrowserPanel extends JPanel {
 
    private final AnimeManager animeManager;
    private final StatusBar statusBar;
 
    private final JTextField searchField = new JTextField(20);
    private final JComboBox<String> searchModeCombo = new JComboBox<>(new String[]{"Title", "ID"});
    private final JButton searchButton = new JButton("Search");
    private final JButton showAllButton = new JButton("Show All");
    private final JButton sortByTitleButton = new JButton("Sort by Title");
    private final JButton sortByEpisodesButton = new JButton("Sort by Episodes");
 
    private final DefaultTableModel tableModel;
    private final JTable animeTable;
    private final JTextArea detailsArea = new JTextArea(6, 30);
 
    // Kept in row order alongside tableModel so a selected row can be
    // resolved back to the real Anime object.
    private final List<Anime> currentResults = new ArrayList<>();
    private final List<Consumer<Anime>> selectionListeners = new ArrayList<>();
 
    public AnimeBrowserPanel(AnimeManager animeManager, StatusBar statusBar) {
        this.animeManager = animeManager;
        this.statusBar = statusBar;
 
        setLayout(new BorderLayout(8, 8));
 
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search by:"));
        topPanel.add(searchModeCombo);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(showAllButton);
        topPanel.add(sortByTitleButton);
        topPanel.add(sortByEpisodesButton);
        add(topPanel, BorderLayout.NORTH);
 
        // ID, Title, Genres, Status, Episodes, Rating — the actual Anime fields.
        tableModel = new DefaultTableModel(new Object[]{"ID", "Title", "Genres", "Status", "Episodes", "Rating"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        animeTable = new JTable(tableModel);
        animeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(animeTable), BorderLayout.CENTER);
 
        detailsArea.setEditable(false);
        detailsArea.setBorder(BorderFactory.createTitledBorder("Selected Anime"));
        add(new JScrollPane(detailsArea), BorderLayout.SOUTH);
 
        wireEvents();
        populateTable(animeManager.getAnimeList());
    }
 
    private void wireEvents() {
        searchButton.addActionListener(this::onSearch);
        searchField.addActionListener(this::onSearch); // Enter key also triggers search
        showAllButton.addActionListener(e -> {
            populateTable(animeManager.getAnimeList());
            statusBar.showMessage(currentResults.size() + " anime loaded.");
        });
        sortByTitleButton.addActionListener(e -> {
            animeManager.sortAnimeByTitle();
            populateTable(animeManager.getAnimeList());
            statusBar.showMessage("Anime sorted by title.");
        });
        sortByEpisodesButton.addActionListener(e -> {
            animeManager.sortByEpisodes();
            populateTable(animeManager.getAnimeList());
            statusBar.showMessage("Anime sorted by number of episodes.");
        });
 
        animeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onRowSelected();
            }
        });
    }
 
    private void onSearch(ActionEvent e) {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            statusBar.showMessage("Enter a value to search.");
            return;
        }
 
        String mode = (String) searchModeCombo.getSelectedItem();
        List<Anime> results = new ArrayList<>();
 
        if ("ID".equals(mode)) {
            try {
                int id = Integer.parseInt(query);
                Anime found = animeManager.searchAnimeById(id);
                if (found != null) results.add(found);
            } catch (NumberFormatException ex) {
                statusBar.showMessage("Enter a valid whole number ID.");
                return;
            }
        } else {
            Anime found = animeManager.searchAnimeByTitle(query);
            if (found != null) results.add(found);
        }
 
        populateTable(results);
        statusBar.showMessage(results.isEmpty() ? "Anime not found." : results.size() + " result(s) found.");
    }
 
    private void populateTable(List<Anime> results) {
        currentResults.clear();
        currentResults.addAll(results);
        tableModel.setRowCount(0);
 
        for (Anime anime : results) {
            tableModel.addRow(new Object[]{
                    anime.getAnimeId(),
                    anime.getTitle(),
                    String.join(", ", anime.getGenres()),
                    anime.getStatus(),
                    anime.getEpisodes(),
                    anime.getRating()
            });
        }
    }
 
    private void onRowSelected() {
        int row = animeTable.getSelectedRow();
        if (row < 0 || row >= currentResults.size()) return;
 
        Anime selected = currentResults.get(row);
        detailsArea.setText(formatDetails(selected));
 
        notifySelectionListeners(selected);
    }
 
    private String formatDetails(Anime anime) {
        return "ID: " + anime.getAnimeId() + "\n"
             + "Title: " + anime.getTitle() + "\n"
             + "Genres: " + String.join(", ", anime.getGenres()) + "\n"
             + "Status: " + anime.getStatus() + "\n"
             + "Episodes: " + anime.getEpisodes() + "\n"
             + "Rating: " + anime.getRating();
    }
 
    /** Lets other panels (e.g. WatchStatusPanel) react to selection without depending on this panel's internals. */
    public void addSelectionListener(Consumer<Anime> listener) {
        selectionListeners.add(listener);
    }
 
    private void notifySelectionListeners(Anime anime) {
        for (Consumer<Anime> listener : selectionListeners) {
            listener.accept(anime);
        }
    }
}
 
