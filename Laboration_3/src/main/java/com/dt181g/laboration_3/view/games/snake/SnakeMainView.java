package com.dt181g.laboration_3.view.games.snake;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.games.common.IGameView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents the main view component of the Snake game.
 *
 * <p>
 * This class is responsible for rendering the game interface, which includes
 * the start menu, game grid, and controls menu. It's utilizing a CardLayout to switch
 * between different views.
 * </p>
 *
 * <p>
 * This implementation is still under development, with features like settings
 * and multiplayer planned for future versions.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeMainView extends JPanel implements IGameView {
    // Shared button between menus
    private final JLabel snakeBackBtn = new JLabel("Back");

    private final CardLayout snakeViewCL = new CardLayout();
    private JLabel title = new JLabel();
    private final SnakeStartMenuView startMenu = new SnakeStartMenuView(this);
    private final SnakeSinglePlayerView singlePlayer = new SnakeSinglePlayerView(this);
    private final SnakeHowToView controlPanel = new SnakeHowToView(this);

    /** The panel this panel resides in, used for proper closing of the game. */
    private JPanel gamePanel;

    /**
     * Constructs a SnakeMainView with the specified title.
     *
     * @param title The title to be displayed on the view.
     */
    public SnakeMainView(final String title) {
        this.setLayout(snakeViewCL);

        // sets the title to the label, styles it and adds it to the start menu.
        this.title.setText(title);
        labelStyling(this.title, AppConfigLab3.TEXT_HEADING_2, false);
        this.startMenu.setTitle(this.title);

        labelBtn(snakeBackBtn, AppConfigLab3.COLOR_WHITE);

        // Adds the views to the cardLayout
        this.add(startMenu, "Start Menu");
        this.add(singlePlayer, "Snake");
        this.add(controlPanel, "Control Menu");
    }

    /*===============================
     * Show methods
     ==============================*/

    @Override
    public void showStartMenu() {
        this.snakeViewCL.show(this, "Start Menu");
    }

    /**
     * Displays the controls menu for the game.
     */
    public void showControlsMenu() {
        // Re-instate the shared back button
        this.controlPanel.setBackBtn();
        this.snakeViewCL.show(this, "Control Menu");
    }

    //===== Snake game =====

     /**
     * Starts the game and initializes game assets.
     *
     * @param gameAssets the list representing the current state of the game.
     */
    public void startGame(final List<Object> gameAssets) {
        this.snakeViewCL.show(this, "Snake");
        this.singlePlayer.setGameAssets(gameAssets);
        // The shared back button is re-instated in this method
        this.singlePlayer.startGame();
    }

    /**
     * Updates the game grid with the current state of the snake.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    public void updateGameGrid(final List<Object> gameAssets) {
        this.singlePlayer.updateGameGrid(gameAssets);
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
    public void addStartBtnListener(final MouseAdapter startBtnListener) {
        this.startMenu.addStartBtnListener(startBtnListener);
    }

    /**
     * Adds a mouse listener to the controls button.
     *
     * @param controlsBtnListener The mouse listener to be added to the controls button,
     * allowing for interaction when the button is clicked.
     */
    public void addControlsBtnListener(final MouseAdapter controlsBtnListener) {
        this.startMenu.addControlsBtnListener(controlsBtnListener);
    }

    /**
     * Adds a mouse listener to the quit button.
     *
     * @param quitBtnListener The mouse listener to be added to the quit button,
     * allowing for interaction when the button is clicked.
     */
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        this.startMenu.addQuitBtnListener(quitBtnListener);
    }

    /**
     * Adds a mouse listener to the back button.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }


    /**
     * Removes all mouse listeners from the game control buttons.
     * This method iterates through each button and removes any attached mouse listeners,
     * ensuring that no event handlers remain active on these buttons.
     */
    public void removeListeners() {
        this.startMenu.removeListeners();
        removeAllListenersFromButton(this.snakeBackBtn);
    }

    /**
     * Helper method that removes all mouse listeners from the specified button.
     *
     * @param button the JButton from which to remove all mouse listeners.
     */
    protected void removeAllListenersFromButton(JLabel button) {
        for (MouseListener listener : button.getMouseListeners()) {
            button.removeMouseListener(listener);
        }
    }

    /*==============================
    * Getters
    ==============================*/

    @Override
    public String getTitle() {
        return title.getText();
    }

    /**
     * Returns the start button.
     *
     * @return The JLabel representing the start game button.
     */
    public JLabel getStartBtn() {
        return this.startMenu.getStartBtn();
    }

    /**
     * Returns the multiplayer button.
     *
     * @return The JLabel representing the multiplayer button.
     */
    public JLabel getMultiplayerBtn() {
        return this.startMenu.getMultiplayerBtn();
    }

    /**
     * Returns the settings button.
     *
     * @return The JLabel representing the settings button.
     */
    public JLabel getSettingsBtn() {
        return this.startMenu.getSettingsBtn();
    }

    /**
     * Returns the controls button.
     *
     * @return The JLabel representing the controls button.
     */
    public JLabel getControlsBtn() {
        return this.startMenu.getControlsBtn();
    }

    /**
     * Returns the quit button.
     *
     * @return The JLabel representing the quit button.
     */
    public JLabel getQuitBtn() {
        return this.startMenu.getQuitBtn();
    }

    /**
     * Returns the back button in the controls menu.
     *
     * @return The JLabel representing the back button.
     */
    public JLabel getSnakeBackBtn() {
        return this.snakeBackBtn;
    }

    /*=========================================
    * Override methods (not Override getters)
    =========================================*/

    @Override
    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void closeGameView() {
        if (this.gamePanel != null) {
            this.clearGameView(this.gamePanel, this);
        }
    }
}
