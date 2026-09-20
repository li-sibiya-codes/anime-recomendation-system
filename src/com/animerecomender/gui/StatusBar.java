package com.animerecomender.gui;

import java.awt.*;
import javax.swing.*;

/**
 * Shared status/error line shown at the bottom of the main window.
 *
 * Passed by reference into every section panel so any panel can report an
 * outcome ("Preferences updated.", "Anime not found.", "Database error.")
 * through one consistent place instead of scattering JOptionPane dialogs
 * across the codebase.
 */
public class StatusBar extends JPanel {

    private final JLabel label = new JLabel(" ");

    public StatusBar() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(label, BorderLayout.WEST);
    }

    public void showMessage(String message) {
        label.setText(message);
    }

    public void clear() {
        label.setText(" ");
    }
}
