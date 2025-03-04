package com.dt181g.project.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.support.AppConfig;

/**
 * This is the AboutView of the launcher.
 * It resides in the card layout of LauncherMainView and displays som easy digested information
 * about how the launcher works.
 * @author Joel Lansgren
 */
public class LauncherAboutView implements IView {
    private final JPanel mainPanel = new JPanel();
    private GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel instructionsHeading = new JLabel("Instructions:");
    private final JLabel instructionsSelectGame = new JLabel(
        "Click on a game from the left side panel to start it"
    );
    private final JLabel instructionsExit = new JLabel("Use the \"Exit\" button to close the launcher");
    private final JLabel instructionsAbout = new JLabel("...Or the \"About\" button to open this screen");
    private final JLabel instructionsEnjoy = new JLabel("Enjoy!");
    private final JLabel instructionsSmiley = new JLabel("┑(＾▽＾)┍");
    private int backBtnPosition;

    /**
     * This constructs the AboutView on the EDT
     */
    public LauncherAboutView() {
        SwingUtilities.invokeLater(() -> {
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.setBackground(AppConfig.COLOR_DARKER_GREY);

            // Initiates components
            labelStyling(instructionsHeading, AppConfig.TEXT_HEADING_1);
            labelStyling(instructionsSelectGame, AppConfig.TEXT_SIZE_NORMAL);
            labelStyling(instructionsExit, AppConfig.TEXT_SIZE_NORMAL);
            labelStyling(instructionsAbout, AppConfig.TEXT_SIZE_NORMAL);
            labelStyling(instructionsEnjoy, AppConfig.TEXT_SIZE_NORMAL);
            labelStyling(instructionsSmiley, AppConfig.TEXT_HEADING_2);

            // Adds and place the components on the grid.
            gbc = resetGbc(gbc.gridy, gbc.gridx);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = AppConfig.INSET_BOTTOM_20;
            mainPanel.add(instructionsHeading, gbc);

            gbc.gridy++;
            mainPanel.add(instructionsSelectGame, gbc);

            gbc.gridy++;
            mainPanel.add(instructionsExit, gbc);

            gbc.gridy++;
            mainPanel.add(instructionsAbout, gbc);

            gbc.gridy++;
            mainPanel.add(instructionsEnjoy, gbc);

            gbc.gridy++;
            gbc.insets = AppConfig.INSET_BOTTOM_30;
            mainPanel.add(instructionsSmiley, gbc);

            backBtnPosition = gbc.gridy + 1;
        });
    }

    /*=====================
    * Setters
    =====================*/

    /**
     * Re-adds the shared back button to the AboutView.
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
