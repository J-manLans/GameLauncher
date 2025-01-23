package com.dt181g.laboration_3.view.games.snake;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.supportChange.AppConfigLab3;

/**
 * Represents the controls menu view for the Snake game.
 *
 * <p>
 * This class displays the instructions on how to control the snake and
 * provides a back button for navigation.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeHowToView extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeMainView snakeView;
    private final JLabel controlsMainLabel = new JLabel("CONTROLS:");
    private final JLabel controlsSubLabel = new JLabel("W,S,A,D or arrow keys to move the snake around");
    private final JLabel instructionsMainLabel = new JLabel("INSTRUCTIONS:");
    private final JLabel instructionsSubLabel = new JLabel("Eat whatever booster that appear on the grid to gain it's effect");

    /**
     * Initializes the SnakeControlsView with the specified main view.
     *
     * <p>
     * This constructor sets the layout, styles the control labels,
     * and adds them to the panel. It also applies background color and border styling.
     * </p>
     *
     * @param snakeView the main view of the Snake game, used to retrieve shared components
     * and styling methods.
     */
    protected SnakeHowToView(final SnakeMainView snakeView) {
        super(new GridBagLayout());

        // Initiate components
        this.snakeView = snakeView;

        this.snakeView.labelStyling(controlsMainLabel, AppConfigLab3.TEXT_HEADING_2, false);
        this.snakeView.labelStyling(controlsSubLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);
        this.snakeView.labelStyling(instructionsMainLabel, AppConfigLab3.TEXT_HEADING_2, false);
        this.snakeView.labelStyling(instructionsSubLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);

        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;
        this.gbc.gridy = 0;
        this.add(controlsMainLabel, gbc);

        this.gbc.gridy++;
        this.add(controlsSubLabel, gbc);

        this.gbc.gridy++;
        this.add(instructionsMainLabel, gbc);

        this.gbc.gridy++;
        this.add(instructionsSubLabel, gbc);
        this.gbc.insets = AppConfigLab3.RESET_INSETS;

        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
        this.setBorder(AppConfigLab3.CONTROLS_BORDER);
    }

    /**
     * Re-adds the shared back button to the menu.
     * Must be called each time the menu is displayed to ensure
     * the button appears in the correct position.
     */
    protected void setBackBtn() {
        this.gbc.gridy++;
        this.add(snakeView.getSnakeBackBtn(), gbc);
        this.gbc.insets = AppConfigLab3.RESET_INSETS;
    }
}
