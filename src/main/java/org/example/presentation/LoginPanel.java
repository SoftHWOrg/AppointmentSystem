package org.example.presentation;

import org.example.service.AuthService;

import javax.swing.*;
import java.awt.*;

/**
 * The login screen panel.
 * Displayed first when the application starts (US1.1).
 *
 * Layout:
 *  - Title label: "Appointment Scheduling System"
 *  - Email text field
 *  - Password field (JPasswordField — hides input)
 *  - Login button
 *  - Error label (shown in red when login fails)
 *
 * @author
 * @version 1.0
 */
public class LoginPanel extends JPanel {

    // TODO: Add field: MainFrame mainFrame     (for navigation after login)
    // TODO: Add field: AuthService authService
    // TODO: Add field: JTextField emailField
    // TODO: Add field: JPasswordField passwordField
    // TODO: Add field: JLabel errorLabel       (displays error messages)

    /**
     * Constructor — builds the login UI.
     *
     * TODO:
     *  1. Assign mainFrame and authService fields
     *  2. Set layout: e.g. new BorderLayout() or new GridBagLayout()
     *  3. Create a center panel with:
     *       - JLabel("Email:") + emailField (JTextField)
     *       - JLabel("Password:") + passwordField (JPasswordField)
     *       - loginButton (JButton("Login"))
     *       - errorLabel (JLabel(""), set foreground to Color.RED)
     *  4. Add ActionListener to loginButton → call handleLogin()
     *  5. Add a title label at the top
     *
     * @param mainFrame   the parent window (for navigation)
     * @param authService the authentication service
     */
    public LoginPanel(MainFrame mainFrame, AuthService authService) {
        // TODO: build the login UI
    }

    /**
     * Handles the login button click.
     *
     * TODO:
     *  1. Read email from emailField.getText()
     *  2. Read password from new String(passwordField.getPassword())
     *  3. Call authService.login(email, password)
     *  4. If login succeeds → call mainFrame.showPanel(MainFrame.DASHBOARD_PANEL)
     *  5. If login throws IllegalArgumentException → show error in errorLabel
     *     e.g. errorLabel.setText("Invalid email or password")
     */
    private void handleLogin() {
        // TODO: implement login handler
    }
}
