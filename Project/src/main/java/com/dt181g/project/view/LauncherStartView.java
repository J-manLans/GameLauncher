package com.dt181g.project.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.support.AppConfig;

/**
 * This is the start view of the launcher.
 * It resides in the card layout of LauncherMainView, welcomes the user and set up listeners for its buttons.
 * @author Joel Lansgren
 */
public class LauncherStartView implements IView {
    private final JPanel mainPanel = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel WelcomeHeading = new JLabel("Welcome to the Game Launcher v2.0");
    private final JButton aboutBtn = new JButton("About");

    /**
     * Constructs the start view on the EDT
     */
    public LauncherStartView() {
        SwingUtilities.invokeLater(() -> {
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setBackground(AppConfig.COLOR_DARKER_GREY);

            // Initiates components
            labelStyling(WelcomeHeading, AppConfig.TEXT_HEADING_1);
            buttonStyler(aboutBtn, AppConfig.COLOR_ACCENT);

            // Adds and place the components on the grid.
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 1;
            gbc.weightx = 1;
            mainPanel.add(WelcomeHeading, gbc);

            gbc = resetGbc(gbc.gridy, gbc.gridx);
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.SOUTHWEST;
            gbc.insets = AppConfig.INSET_LEFT_BOTTOM_CORNER_30;
            mainPanel.add(aboutBtn, gbc);
        });
    }

    /*========================================
    * Listener Methods
    ========================================*/

    /**
     * Adds a {@link ButtonListener} to the aboutBtn navigating to the aboutView.
     * @param ShowAboutView a callback that will display the AboutView when the aboutBtn is clicked and set the backBtn
     * in it.
     */
    public void addAboutBtnListener(final Runnable ShowAboutView) {
        aboutBtn.addMouseListener(new ButtonListener(ShowAboutView, aboutBtn.getForeground()));
    }

    /*=====================
    * Getters
    =====================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
