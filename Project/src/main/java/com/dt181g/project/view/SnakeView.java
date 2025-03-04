package com.dt181g.project.view;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.dt181g.project.support.AppConfigSnake;

/**
 * Represents the main view component of the Snake game.
 *
 * <p>
 * This class is responsible for rendering the game interface, which includes
 * the start menu, game grid, and controls menu. It's utilizing a CardLayout to switch
 * between different views.
 * </p>
 * @author Joel Lansgren
 */
public class SnakeView implements IView {
    private final JPanel mainPanel = new JPanel();
    private final CardLayout snakeViewCL = new CardLayout();

    /**
     * Constructs the SnakeMainView by setting the card layout and background.
     */
    public SnakeView() {
        mainPanel.setLayout(snakeViewCL);
    }

    /**
     * Sets the different views for the Snake game.
     * @param startMenuView the JPanel for the start menu
     * @param singlePlayerView the JPanel for the single-player game
     * @param howToView the JPanel for the how-to menu
     */
    public void setViews(
        final JPanel startMenuView,
        final JPanel singlePlayerView,
        final JPanel howToView
    ) {
        mainPanel.add(startMenuView, AppConfigSnake.START);
        mainPanel.add(singlePlayerView, AppConfigSnake.SINGLE_PLAYER);
        mainPanel.add(howToView, AppConfigSnake.HOW_TO);
    }

    /*===============================
    * Show methods
    ==============================*/

    /**
     * Displays the start menu with game options.
     */
    public void showStartMenu() {
        snakeViewCL.show(mainPanel, AppConfigSnake.START);
    }

    /**
     * Displays the single-player view.
     */
    public void showSinglePlayerView() {
        snakeViewCL.show(mainPanel, AppConfigSnake.SINGLE_PLAYER);
    }

    /**
     * Displays the how-to menu for the game.
     */
    public void showHowToView() {
        snakeViewCL.show(mainPanel, AppConfigSnake.HOW_TO);
    }

    /*=============================
    * Getters
    =============================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
