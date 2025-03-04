package com.dt181g.project.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.support.AppConfig;

/**
 * This is the ComingSoonGameView of the launcher.
 * It resides in the card layout of LauncherMainView and displays a message letting the user
 * know if a clicked game isn't fully implemented yet.
 * @author Joel Lansgren
 */
public class LauncherComingSoonView implements IView {
    private final JPanel mainPanel = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel comingSoonHeading = new JLabel(
        "This game is coming soon but is not yet fully implemented."
    );
    private final JLabel comingSoonSubHeading = new JLabel("Please, try again later.");
    private final JLabel comingSoonSmiley = new JLabel("┐(￣ヘ￣)┌");
    private int backBtnPosition;

    /** This constructs the ComingSoonGameView on the EDT */
    public LauncherComingSoonView() {
        SwingUtilities.invokeLater(() -> {
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setBackground(AppConfig.COLOR_DARKER_GREY);

            //Initiates components
            labelStyling(comingSoonHeading, AppConfig.TEXT_HEADING_1);
            labelStyling(comingSoonSubHeading, AppConfig.TEXT_HEADING_2);
            labelStyling(comingSoonSmiley, AppConfig.TEXT_HEADING_2);

            // Adds and place the components on the grid.
            gbc = resetGbc(gbc.gridy, gbc.gridx);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = AppConfig.INSET_BOTTOM_20;
            mainPanel.add(comingSoonHeading, gbc);

            gbc.gridy++;
            mainPanel.add(comingSoonSubHeading, gbc);

            gbc.gridy++;
            gbc.insets = AppConfig.INSET_BOTTOM_30;
            mainPanel.add(comingSoonSmiley, gbc);

            backBtnPosition = gbc.gridy + 1;
        });
    }

    /*=====================
    * Setters
    =====================*/

    /**
     * Re-adds the shared back button to the UnImplementedGameView.
     * Must be called each time the view is displayed to ensure
     * the button appears in the correct position.
     */
    public void setBackBtn() {
        gbc.gridy = backBtnPosition;
        mainPanel.add(backBtn, gbc);
    }

    /*=====================
    * Getters
    =====================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
