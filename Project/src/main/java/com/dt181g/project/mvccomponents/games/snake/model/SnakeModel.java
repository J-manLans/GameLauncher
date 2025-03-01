package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.mvccomponents.IBaseModel;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.AppConfigProject.Direction;

/**
 * The SnakeModel class represents the model of the Snake.
 * It maintains the state of the snake, its position, direction,
 * and the overall game grid.
 *
 * <p>
 * This class provides methods to initialize the snake, update its position
 * based on user input, and check game conditions such as collision with itself.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeModel implements IBaseModel {
    private int[][] snake;
    private int headIndex = AppConfigProject.INITIAL_SNAKE_LENGTH - 1;
    private int[][] gameGrid;
    private Boolean allowChangesToDirection = true;
    private Direction currentDirection;
    private boolean isGameOver;
    private double speed = AppConfigProject.SNAKE_TICK_DELAY;

    /**
     * Initializes the snake model with the provided cleared game grid.
     * Resets the snake's state and prepares it for a new game.
     *
     * @param clearedGameGrid The cleared game grid to initialize the snake's position on.
     */
    public void initializeSnakeModel(final int[][] clearedGameGrid) {
        this.gameGrid = clearedGameGrid;
        this.initializeSnake();
        this.speed = AppConfigProject.SNAKE_TICK_DELAY;
        this.isGameOver = false;
        this.allowChangesToDirection = true;
        this.currentDirection = AppConfigProject.RIGHT;
    }

    /**
     * Initializes the snake's position on the game grid.
     * Sets the initial coordinates and color of the snake's segments.
     */
    private void initializeSnake() {
        // Re-initialize the snake
        this.snake = new int[AppConfigProject.INITIAL_SNAKE_LENGTH][AppConfigProject.SNAKE_ITEMS_PART_CONTENT];
        this.headIndex = AppConfigProject.INITIAL_SNAKE_LENGTH - 1;

        // Set snakes tail position.
        this.snake[0][0] = this.gameGrid.length / 2;  // Y-coordinate.
        this.snake[0][1] = this.gameGrid.length / 2 - (AppConfigProject.INITIAL_SNAKE_LENGTH / 2);  // X-coordinate.
        this.snake[0][2] = AppConfigProject.COLOR_SNAKE_INT;  // Color.

        // Builds body and head
        for (int i = 1; i < this.snake.length; i++) {
            this.snake[i][0] = this.snake[0][0];  // same y-coordinate as the tail.
            this.snake[i][1] = this.snake[0][1] + i;  // increase x-coordinate with 1 for each part.
            this.snake[i][2] = AppConfigProject.COLOR_SNAKE_INT;  // Color.
        }

        this.snake[headIndex][2] = AppConfigProject.COLOR_SNAKE_HEAD_INT;
    }

    /**
     * Updates the snake's position on the game grid.
     * Allows for changes in direction based on user input.
     *
     * @param updatedGameGrid The current state of the game grid.
     */
    public void updateSnakeOnGrid(final int[][] updatedGameGrid) {
        // Make taking input from keyboard possible again in the game-loop
        this.allowChangesToDirection = true;
        // This is done before clearing the grid to get the position of
        // possible boosters that the snake needs to interact with
        this.gameGrid = updatedGameGrid;
        this.moveSnake();
    }

    /**
     * Moves the snake based on the current direction.
     * Adjusts the body first and then moves the head according to the direction.
     * After moving, it checks the cell the head has moved into for its content.
     */
    protected void moveSnake() {
        this.moveSnakeBody();

        switch (this.currentDirection) {
            case UP -> { this.moveSnakeHeadNegDirection(0); }
            case LEFT -> { this.moveSnakeHeadNegDirection(1); }
            case DOWN -> { this.moveSnakeHeadPosDirection(0); }
            case RIGHT -> { this.moveSnakeHeadPosDirection(1); }
        }

        checkHeadCell();
    }

    /**
     * Helper method that  moves the body of the snake by shifting each segment's position
     * to the position of the next segment in the array.
     * This makes each body segment follow the one ahead of it,
     * ultimately following the head's previous position.
     * Does not move the head itself, only the body segments.
     */
    private void moveSnakeBody() {
        for (int i = 0; i < this.snake.length - 1; i++) {
            this.snake[i][0] = this.snake[i + 1][0];
            this.snake[i][1] = this.snake[i + 1][1];
        }
    }

    /**
     * Helper method that  moves the snake's head in a positive direction (DOWN or RIGHT).
     * Wraps around the grid if the head reaches the edge.
     *
     * @param yOrX The index indicating if the movement is in the Y (0) or X (1) direction.
     */
    private void moveSnakeHeadPosDirection(final int yOrX) {
        // Move the head: wraps around if it reaches the end of the grid
        this.snake[headIndex][yOrX] =
        (this.snake[headIndex][yOrX] + 1)
        % this.gameGrid.length;
    }

    /**
     * Helper method that  moves the snake's head in a negative direction (UP or LEFT).
     * Wraps around the grid if the head reaches the beginning.
     *
     * @param yOrX The index indicating if the movement is in the Y (0) or X (1) direction.
     */
    private void moveSnakeHeadNegDirection(final int yOrX) {
        // Move the head: wraps around if it reaches the beginning of the grid
        this.snake[headIndex][yOrX] =
        (this.snake[headIndex][yOrX] - 1 + this.gameGrid.length)
        % this.gameGrid.length;
    }

    /**
     * Checks the cell in front of the snake's head to determine if the game is over
     * or if a booster has been consumed.
     *
     * <p>
     * If the cell color matches {@link AppConfigProject#COLOR_SNAKE_INT}, the game ends.
     * If the cell color is non-zero (indicating a booster), the booster is consumed and
     * its effects are applied to the snake.
     * </p>
     */
    private void checkHeadCell() {
        int cellColor = this.gameGrid[this.snake[this.headIndex][0]][this.snake[this.headIndex][1]];

        if (cellColor == AppConfigProject.COLOR_SNAKE_INT) {
            isGameOver = true;
            allowChangesToDirection = false;
        } else if (cellColor != 0) {
            ManagerSnakeBooster.INSTANCE.eatAndResetBooster(this, cellColor);
        }
    }

    /**
     * Updates the snake's state with the provided expanded snake array.
     *
     * @param expandedSnake The new state of the snake.
     */
    public void updateSnake(int[][] expandedSnake) {
        this.snake = expandedSnake;
        this.headIndex = this.snake.length - 1;
    }

    /*====================
     * Getters
     ===================*/

    /**
     * Returns the current state of the snake as a 2D array.
     * Each element represents a segment of the snake, containing
     * its position coordinates and color.
     *
     * @return A 2D integer array representing the snake's segments.
     */
    public int[][] getSnake() {
        return this.snake;
    }

    /**
     * Returns the current speed of the snake.
     *
     * @return A double representing the snake's speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Checks if the game is over.
     *
     * @return A boolean indicating if the game is over.
     */
    public boolean getGameOverState() {
        return this.isGameOver;
    }

    /*====================
     * Setters
     ===================*/

    /**
     * Sets the current direction of the snake.
     *
     * <p>
     * This method updates the snake's direction only if the new direction
     * has been set in the snake itself ({@code AllowEntranceToChangeDirection}
     * makes sure of this) and is not opposite to the current direction
     * (e.g., UP to DOWN).
     * </p>
     * @param direction The new direction for the snake (must not be opposite
     * to the current direction).
     * @param restart Indicates whether to ignore the direction change restriction.
     */
    public void setDirection(final Direction direction, final boolean restart) {
        // Prevent illegal moves.
        if (
            (
                this.currentDirection == Direction.UP && direction == Direction.DOWN
                || this.currentDirection == Direction.DOWN && direction == Direction.UP
                || this.currentDirection == Direction.LEFT && direction == Direction.RIGHT
                || this.currentDirection == Direction.RIGHT && direction == Direction.LEFT
            )
            && !restart
        ) { return; }

        // Makes sure the updates have been reflected in the game grid before allowing changes
        // in direction again.
        if (this.allowChangesToDirection) {
            this.currentDirection = direction;
            this.allowChangesToDirection = false;
        }
    }

    /**
     * Sets the speed of the snake.
     *
     * @param speed The new speed for the snake.
     */
    public void setSpeed(final double speed) {
        this.speed = speed;
    }
}
