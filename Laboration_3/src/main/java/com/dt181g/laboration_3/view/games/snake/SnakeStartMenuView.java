package com.dt181g.laboration_3.view.games.snake;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.supportChange.AppConfigLab3;

/**
 * Represents the start menu view for the Snake game.
 * This view class provides interactive menu options that allow
 * the user to start a game, access settings, view controls, and quit the game.
 *
 * <p>
 * Components and button labels are styled according to the main view's styling
 * methods and are displayed using a grid layout.
 * </p>
 *
 * <p>
 * The buttons include Start Game, Multiplayer, Settings, Controls, and Quit, each
 * with configurable listeners.
 * </p>
 *
 * <p>Interactions are handled through listeners that can be added or removed.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeStartMenuView extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeMainView snakeView;
    private final JLabel startBtn = new JLabel("Start Game");
    private final JLabel multiplayerBtn = new JLabel("Multiplayer");
    private final JLabel settingsBtn = new JLabel("Settings");
    private final JLabel controlsBtn = new JLabel("How to \"Snake\"");
    private final JLabel quitBtn = new JLabel("Quit");

    /**
     * Constructs the start menu view, initializing buttons and styling.
     *
     * @param snakeView The main view component for shared layout and styling access.
     */
    protected SnakeStartMenuView(SnakeMainView snakeView) {
        this.setLayout(new GridBagLayout());
        this.snakeView = snakeView;

        this.snakeView.labelBtn(startBtn, AppConfigLab3.COLOR_WHITE);
        this.snakeView.labelBtn(multiplayerBtn, AppConfigLab3.COLOR_DARK_GREY);
        this.snakeView.labelBtn(settingsBtn, AppConfigLab3.COLOR_DARK_GREY);
        this.snakeView.labelBtn(controlsBtn, AppConfigLab3.COLOR_WHITE);
        this.snakeView.labelBtn(quitBtn, AppConfigLab3.COLOR_WHITE);

        // Adds and place the components on the grid.
        this.gbc.gridy = 1;
        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;

        this.add(startBtn, this.gbc);

        this.gbc.gridy++;
        this.add(multiplayerBtn, this.gbc);

        this.gbc.gridy++;
        this.add(settingsBtn, this.gbc);

        this.gbc.gridy++;
        this.add(controlsBtn, this.gbc);

        this.gbc.gridy++;
        this.add(quitBtn, this.gbc);

        // Sets the background color
        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
    }

    /**
     * Sets the title label at the top of the menu view.
     *
     * @param title The title label to be displayed at the top.
     */
    protected void setTitle(JLabel title) {
        this.gbc.gridy = 0;
        this.add(title, this.gbc);
    }

    /*==============================
     * Listener methods
     ==============================*/

    /**
     * Adds a mouse listener to the start button.
     *
     * @param startBtnListener The mouse listener to be added to the start button,
     * allowing for interaction when the button is clicked.
     */
    protected void addStartBtnListener(final MouseAdapter startBtnListener) {
        this.startBtn.addMouseListener(startBtnListener);
    }

    /**
     * Adds a mouse listener to the controls button.
     *
     * @param controlsBtnListener The mouse listener to be added to the controls button,
     * allowing for interaction when the button is clicked.
     */
    protected void addControlsBtnListener(final MouseAdapter controlsBtnListener) {
        this.controlsBtn.addMouseListener(controlsBtnListener);
    }

    /**
     * Adds a mouse listener to the quit button.
     *
     * @param quitBtnListener The mouse listener to be added to the quit button,
     * allowing for interaction when the button is clicked.
     */
    protected void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.quitBtn.addMouseListener(quitBtnListener);
    }

    /**
     * Removes all listeners from each button to prevent memory leaks or
     * unintended actions when switching views.
     */
    protected void removeListeners() {
        this.snakeView.removeAllListenersFromButton(this.startBtn);
        this.snakeView.removeAllListenersFromButton(this.multiplayerBtn);
        this.snakeView.removeAllListenersFromButton(this.settingsBtn);
        this.snakeView.removeAllListenersFromButton(this.quitBtn);
    }

    /*==============================
     * Getters
     ==============================*/
    /**
     * Retrieves the Start Game button component.
     *
     * @return The JLabel representing the Start Game button.
     */
    protected JLabel getStartBtn() {
        return startBtn;
    }

    /**
     * Retrieves the Multiplayer button component.
     *
     * @return The JLabel representing the Multiplayer button.
     */
    protected JLabel getMultiplayerBtn() {
        return multiplayerBtn;
    }

    /**
     * Retrieves the Settings button component.
     *
     * @return The JLabel representing the Settings button.
     */
    protected JLabel getSettingsBtn() {
        return settingsBtn;
    }

    /**
     * Retrieves the Controls button component.
     *
     * @return The JLabel representing the Controls button.
     */
    protected JLabel getControlsBtn() {
        return controlsBtn;
    }

    /**
     * Retrieves the Quit button component.
     *
     * @return The JLabel representing the Quit button.
     */
    protected JLabel getQuitBtn() {
        return quitBtn;
    }
}
