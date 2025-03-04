package com.jman.gamelauncher.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jman.gamelauncher.support.AppConfig;
import com.jman.gamelauncher.support.AppConfigSnake;

/**
 * Represents the how-to menu view for the Snake game.
 *
 * <p>
 * This class displays the instructions on how to control the snake and
 * provides a shared back button for navigation.
 * </p>
 * @author Joel Lansgren
 */
public class SnakeHowToView implements IView {
    private final JPanel mainPanel = new JPanel();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel controlsMainLabel = new JLabel("CONTROLS:");
    private final JLabel controlsSubLabel = new JLabel("W,S,A,D or arrow keys to move the snake around");
    private final JLabel instructionsMainLabel = new JLabel("INSTRUCTIONS:");
    private final JLabel instructionsSubLabel = new JLabel(
        "Eat whatever booster that appear on the grid to gain it's effect and grow as long as possible"
    );
    private final int backBtnPosition;

    /**
     * Initializes the SnakeHowToView.
     *
     * <p>
     * This constructor sets and styles the layout, attaches a callback to get the back button,
     * styles the control labels and adds them to the panel.
     * </p>
     */
    public SnakeHowToView() {
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(AppConfig.COLOR_DARKER_GREY);
        mainPanel.setBorder(AppConfigSnake.CONTROLS_BORDER);

        // Initiate components
        labelStyling(controlsMainLabel, AppConfig.TEXT_HEADING_2);
        labelStyling(controlsSubLabel, AppConfig.TEXT_SIZE_NORMAL);
        labelStyling(instructionsMainLabel, AppConfig.TEXT_HEADING_2);
        labelStyling(instructionsSubLabel, AppConfig.TEXT_SIZE_NORMAL);

        // Adds and place the components on the grid.
        gbc.insets = AppConfig.INSET_BOTTOM_20;
        gbc.gridy = 0;
        mainPanel.add(controlsMainLabel, gbc);

        gbc.gridy++;
        mainPanel.add(controlsSubLabel, gbc);

        gbc.gridy++;
        mainPanel.add(instructionsMainLabel, gbc);

        gbc.gridy++;
        mainPanel.add(instructionsSubLabel, gbc);
        gbc.insets = AppConfig.RESET_INSETS;

        backBtnPosition = gbc.gridy + 1;
    }

    /*=============================
    * Setters
    =============================*/

    /**
     * Re-adds the shared back button to the menu.
     * Must be called each time the menu is displayed to ensure
     * the button appears in the correct position.
     */
    public void setBackBtn() {
        gbc.gridy = backBtnPosition;
        mainPanel.add(backBtn, gbc);
    }

    /*=============================
    * Getters
    =============================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
