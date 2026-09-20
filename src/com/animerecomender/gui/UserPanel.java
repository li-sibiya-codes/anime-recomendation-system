package com.animerecomender.gui;
 
import com.animerecomender.model.User;
import java.awt.*;
import javax.swing.*;
 
/**
 * User identification section.
 *
 * SKELETON NOTE: there is no existing "look up / create user by username"
 * operation anywhere in the backend (AnimeManager has no user methods;
 * UserRepository only has getUserById(int) and unused instance-based
 * addUser/updateUser methods; CLI never calls any of them and instead
 * builds one fixed in-memory User at startup). So this panel no longer
 * pretends a "Select User" action persists or looks anything up — see
 * the Contradictions table in the accompanying analysis. It simply
 * displays the fields already present on the session's existing User
 * object (id, username, first name, last name, email), all of which are
 * real getters on the existing User model.
 */
public class UserPanel extends JPanel {
 
    private final User user;
    private final StatusBar statusBar;
 
    private final JLabel userIdValue = new JLabel();
    private final JLabel usernameValue = new JLabel();
    private final JLabel firstNameValue = new JLabel();
    private final JLabel lastNameValue = new JLabel();
    private final JLabel emailValue = new JLabel();
 
    public UserPanel(User user, StatusBar statusBar) {
        this.user = user;
        this.statusBar = statusBar;
 
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
 
        int row = 0;
        addRow(gbc, row++, new JLabel("User ID:"), userIdValue);
        addRow(gbc, row++, new JLabel("Username:"), usernameValue);
        addRow(gbc, row++, new JLabel("First name:"), firstNameValue);
        addRow(gbc, row++, new JLabel("Last name:"), lastNameValue);
        addRow(gbc, row++, new JLabel("Email:"), emailValue);
 
        refresh();
    }
 
    private void addRow(GridBagConstraints gbc, int row, JComponent label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        add(label, gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }
 
    /** Re-reads the current field values off the User object (all existing getters). */
    public void refresh() {
        if (user == null) {
            statusBar.showMessage("No user loaded.");
            return;
        }
        userIdValue.setText(String.valueOf(user.getUserId()));
        usernameValue.setText(user.getUsername());
        firstNameValue.setText(user.getFirstName());
        lastNameValue.setText(user.getLastName());
        emailValue.setText(user.getEmail());
    }
}