package com.jman.gamelauncher.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jman.gamelauncher.support.AppConfig;
import com.jman.gamelauncher.support.AppConfigSnake;

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
 * @author Joel Lansgren
 */
public class SnakeStartMenuView implements IView{
    private final JPanel mainPanel = new JPanel();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLabel title = new JLabel();
    private final JButton singlePlayerBtn = new JButton("Start Game");
    private final JButton multiplayerBtn = new JButton("Multiplayer");
    private final JButton settingsBtn = new JButton("Settings");
    private final JButton howToBtn = new JButton("How to \"Snake\"");
    private final JButton quitBtn = new JButton("Quit");

    /**
     * Constructs the start menu view, initializing buttons and styling.
     * @param title The title of the game passed in from the launcher.
     */
    public SnakeStartMenuView() {
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(AppConfig.COLOR_DARKER_GREY);

        // Initiate components
        this.title.setText(AppConfig.SNAKE_TITLE);
        labelStyling(this.title, AppConfig.TEXT_HEADING_2);
        buttonStyler(singlePlayerBtn, AppConfig.COLOR_WHITE);
        buttonStyler(multiplayerBtn, AppConfig.COLOR_DARK_GREY);
        buttonStyler(settingsBtn, AppConfig.COLOR_DARK_GREY);
        buttonStyler(howToBtn, AppConfig.COLOR_WHITE);
        buttonStyler(quitBtn, AppConfig.COLOR_WHITE);
        setColorOnBackBtn(AppConfigSnake.COLOR_ACCENT);


        // Adds and place the components on the grid.
        gbc.gridy = 0;
        gbc.insets = AppConfig.INSET_BOTTOM_20;
        mainPanel.add(this.title, gbc);

        gbc.gridy++;
        mainPanel.add(singlePlayerBtn, gbc);

        gbc.gridy++;
        mainPanel.add(multiplayerBtn, gbc);

        gbc.gridy++;
        mainPanel.add(settingsBtn, gbc);

        gbc.gridy++;
        mainPanel.add(howToBtn, gbc);

        gbc.gridy++;
        mainPanel.add(quitBtn, gbc);
    }

    /*==============================
    * Listener methods
    ==============================*/

    /**
     * Adds a {@link ButtonListener} to the singlePlayerBtn for starting a single player game and
     * setting the backBtn in it.
     * @param startSingleGame a callback that will initiate and start the single player game and
     * sets the backBtn to it.
     */
    public void addStartBtnListener(final Runnable startSingleGame) {
        singlePlayerBtn.addMouseListener(new ButtonListener(startSingleGame, singlePlayerBtn.getForeground()));
    }

    /**
     * Adds a {@link ButtonListener} to the howToBtn for navigating to the howToView and setting the backBtn in it.
     * @param setBackBtnAndShowHowToView a callback that will display the HowToView when the aboutBtn is clicked and
     * sets the backBtn to it.
     */
    public void addHowToBtnListener(final Runnable setBackBtnAndShowHowToView) {
        howToBtn.addMouseListener(new ButtonListener(setBackBtnAndShowHowToView, howToBtn.getForeground()));
    }

    /**
     * Adds a {@link ButtonListener} to the quitBtn for closing the game.
     * @param closeGameListener a callback that will execute two close methods in both the game
     * and launcher controller.
     */
    public void addQuitBtnListener(final Runnable closeGameListener) {
        quitBtn.addMouseListener(new ButtonListener(closeGameListener, quitBtn.getForeground()));
    }

    /**
     * Removes all listeners from each button to prevent memory leaks or
     * unintended actions when switching games. Should be called as part of the close game
     * method in the controller.
     */
    public void removeListeners() {
        removeAllListenersFromButton(singlePlayerBtn);
        removeAllListenersFromButton(multiplayerBtn);
        removeAllListenersFromButton(settingsBtn);
        removeAllListenersFromButton(quitBtn);
        removeListenerFromBackBtn();
    }

    /**
     * Helper method that removes all mouse listeners from the specified button.
     * @param button the JButton from which to remove all listeners.
     */
    private void removeAllListenersFromButton(final JButton button) {
        for (final MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
    }

    /*=============================
    * Getters
    =============================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
