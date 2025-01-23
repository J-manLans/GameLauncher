package com.dt181g.laboration_3.model.games.snake;

import java.util.ArrayList;
import java.util.List;

import com.dt181g.laboration_3.model.games.common.IGameModel;
import com.dt181g.laboration_3.supportChange.AppConfigLab3;

/**
 * The SnakeModel class represents the model for the Snake game.
 * It maintains the state of the snake and the game grid.
 *
 * <p>
 * This class implements the GameModel interface and provides
 * methods to initialize the snake, update the game grid, and clear the grid.
 * It also includes getters for game metadata such as title, icon path,
 * and grid size.
 * </p>
 *
 * <p>
 * It's not yet fully implemented, as of now the snake is not controllable
 * and no food is spawning for the player to eat
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeModel implements IGameModel {
    private final String comingSoon = " COMING SOON ";
    private final String title;
    private final String iconPath = AppConfigLab3.PATH_TO_ICONS + AppConfigLab3.SNAKE_ICON;
    private final int gridSize = AppConfigLab3.SNAKE_CELL_COUNT;
    private final List<Object> gameAssets = new ArrayList<>();
    // A snake that has length of the comingSoon string and stores
    // y and x-coordinates in each of its body parts.
    private final int[][] snake = new int[comingSoon.length()][2];
    private final int[][] snakeGrid = new int[gridSize][gridSize];

    public SnakeModel(final String title) {
        this.title = title;
    }

    /**
     * Initializes the snake's position on the game grid.
     */
    public void initializeSnake() {
        // Set snakes tail position.
        snake[0][0] = gridSize / 2;  // y-coordinate (row).
        snake[0][1] = gridSize / 2 - (comingSoon.length() / 2);  // x-coordinate (col).

        // Builds body and head
        for (int i = 1; i < snake.length; i++) {
            snake[i][0] = snake[0][0];  // same y-coordinate as the tail.
            snake[i][1] = snake[0][1] + i;  // increase x-coordinate with 1.
        }

        // Overlay the snake on the grid.
        for (final int[] part : snake) {
            snakeGrid[part[0]][part[1]] = 1;
        }
        this.gameAssets.add(comingSoon);
        this.gameAssets.add(snakeGrid);
    }

    /**
     * Updates the game grid based on the current state of the snake.
     *
     * <p>
     * This method first updates the position of the snake using
     * the {@link #updateSnake()} method. It then clears the current
     * state of the snake grid and redraws the snake in its new position.
     * </p>
     */
    public void updateGameGrid() {
        // Removes the old grid.
        this.gameAssets.remove(snakeGrid);
        this.updateSnake();

        // Clears the grid and then draws the new snake on it.
        this.clearSnakeGrid();
        for (final int[] part : this.snake) {
            this.snakeGrid[part[0]][part[1]] = 1;
        }

        // Adds the updated grid.
        this.gameAssets.add(snakeGrid);
    }

    /**
     * Helper method to update the position of the snake by shifting its body parts.
     *
     * <p>
     * This method moves the tail and body of the snake forward
     * based on its current state. The head of the snake moves in the
     * current direction, and if it reaches the end of the grid, it
     * wraps around to the opposite side. This method is called by
     * {@link #updateGameGrid()} to update the snake's position before
     * refreshing the grid.
     * </p>
     */
    private void updateSnake() {
        for (int i = 0; i < this.snake.length; i++) {
            // Updates tail and body.
            if (i < this.snake.length - 1) {
                this.snake[i][1] = this.snake[i + 1][1];
            } else {
                // Updates the head.
                if (this.snake[i][1] != this.snakeGrid.length - 1) {
                    this.snake[i][1]++;
                } else {  // When snake part reach end of the grid it wraps around.
                    this.snake[i][1] = 0;
                }
            }
        }
    }

    /**
     * Helper method to clear the game grid by setting all values to zero.
     *
     * <p>
     * This method is called to reset the grid before drawing
     * the updated position of the snake.
     * </p>
     */
    public void clearSnakeGrid() {
        for (int i = 0; i < this.snakeGrid.length; i++) {
            for (int j = 0; j < this.snakeGrid[i].length; j++) {
                this.snakeGrid[i][j] = 0;
            }
        }
    }

    /*==============================
     * Getters
     ==============================*/

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getIconPath() {
       return iconPath;
    }

    /**
     * Returns the game assets used by the view
     * @return the game assets
     */
    public List<Object> getGameAssets() {
        return gameAssets;
    }
}
