package com.dt181g.laboration_3.view.games.snake;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.supportChange.AppConfigLab3;

/**
 * Represents the single-player view for the Snake game.
 * This view class manages the graphical display of the game grid and the snake,
 * updating the snake's position as it moves and controlling the layout
 * and style of the grid cells.
 *
 * <p>
 * This class primarily interacts with {@link SnakeMainView} for shared view components
 * and relies on a 2D array to manage the snake's appearance in the grid.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeSinglePlayerView extends JPanel{
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final SnakeMainView snakeView;
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;
    private List<Object> gameAssets;

    /**
     * Constructs a new single-player view for the Snake game.
     * Sets up the layout and initializes the main components.
     *
     * @param snakeView The main view component that provides shared styling and layout.
     */
    protected SnakeSinglePlayerView(final SnakeMainView snakeView) {
        this.setLayout(new GridBagLayout());
        this.snakeView = snakeView;
        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
    }

    /**
     * Starts the Snake game by initializing and displaying the game grid.
     * Uses the provided game assets to set up the initial grid state.
     */
    protected void startGame() {
        String comingSoon = (String) gameAssets.get(0);
        int[][] snake2DArray = (int[][]) gameAssets.get(gameAssets.size() - 1);
        this.removeAll();

        if (snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(snake2DArray.length, snake2DArray.length));
            this.snakeGrid.setPreferredSize(AppConfigLab3.SNAKE_GRID_SIZE);
            this.snakeGridCells = new JPanel[snake2DArray.length][snake2DArray.length];
        }

        // Setting up the snake grid
        this.initializeGrid(snake2DArray, comingSoon);

        // Display settings
        this.gbc.gridy = 0;
        this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;
        this.add(this.snakeGrid, gbc);

        this.gbc.gridy++;
        this.gbc.insets = AppConfigLab3.RESET_INSETS;
        this.add(this.snakeView.getSnakeBackBtn(), gbc);
    }

    /**
     * Initializes the grid layout based on the snake's current state.
     * Each cell in the grid displays part of the snake or the background.
     *
     * @param snake2DArray A 2D array representing the snake's position in the grid.
     * @param comingSoon   A string representing the characters displayed inside the snake.
     */
    private void initializeGrid(final int[][] snake2DArray, final String comingSoon) {
        this.snakeGrid.removeAll();
        // used for displaying the text inside the snake
        int counter = 0;

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigLab3.COLOR_DARK_GREY));

                if (snake2DArray[i][j] == 1) {  // Displays the snake and the letters in the grid
                    JLabel comingSoonChar = new JLabel(String.valueOf(comingSoon.charAt(counter)));
                    this.styleSnakeBanner(comingSoonChar);

                    cell.setBackground(AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
                    cell.add(comingSoonChar);

                    counter++;
                } else {  // Background
                    cell.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
                }

                // Add the cells to the grid and then add the cells to a 2D array
                // used for the consecutive updates of the grid.
                this.snakeGrid.add(cell);
                this.snakeGridCells[i][j] = cell;
            }
        }
    }

    /**
     * Updates the game grid with the current state of the snake.
     *
     * @param gameAssets the 2D array representing the snake and a String just for fun.
     */
    protected void updateGameGrid(final List<Object> gameAssets) {
        String comingSoon = (String) gameAssets.get(0);
        int[][] snake2DArray = (int[][]) gameAssets.get(gameAssets.size() - 1);
        int counter = 0;

        for (int i = 0; i < snake2DArray.length; i++) {
            for (int j = 0; j < snake2DArray.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (snake2DArray[i][j] == 1) {  // Displays the snake and the letters in the grid
                    JLabel comingSoonChar = new JLabel(String.valueOf(comingSoon.charAt(counter)));
                    this.styleSnakeBanner(comingSoonChar);

                    this.snakeGridCells[i][j].setBackground(AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
                    this.snakeGridCells[i][j].add(comingSoonChar);

                    counter++;
                } else {  // Background
                    this.snakeGridCells[i][j].setBackground(AppConfigLab3.COLOR_DARKER_GREY);
                }
            }
        }

        this.snakeGrid.revalidate();
        this.snakeGrid.repaint();
    }

    /**
     * Helper method that styles the letters inside the snake.
     *
     * @param snakeChar A JLabel representing a single character within the snake's body
     */
    protected void styleSnakeBanner(final JLabel snakeChar) {
        this.snakeView.labelStyling(snakeChar, AppConfigLab3.TEXT_SIZE_NORMAL, true);
    }

    /**
     * Sets the game assets, including the snake's position and display text.
     * This data is used to update the visual representation of the snake in the grid.
     *
     * @param gameAssets A list containing a string and a 2D array representing the snake's position.
     */
    protected void setGameAssets(List<Object> gameAssets) {
        this.gameAssets = gameAssets;
    }
}
