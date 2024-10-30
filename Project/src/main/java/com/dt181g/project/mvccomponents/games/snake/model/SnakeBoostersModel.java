package com.dt181g.project.mvccomponents.games.snake.model;

import java.util.Random;

import com.dt181g.project.mvccomponents.BaseModel;
import com.dt181g.project.support.AppConfigProject;

public abstract class SnakeBoostersModel implements BaseModel {
    private int[][] gameGrid;
    private final Random randomizer = new Random();
    private boolean isBoosterEaten = true;
    private int spawnCountDown;

    /**
     * Initializes the booster on startup and concurrent initializations.
     */
    public void initializeBooster(int[][] booster) {
        // Sets the color of the booster to background color
        booster[0][2] = 0;
        // Allows the booster to be spawned on a different cell
        this.isBoosterEaten = true;
        // Sets the countdown for allowing the new booster to be spawned
        this.spawnCountDown = randomizer.nextInt(AppConfigProject.UPPER_SPAWNING_BOUND);
    }

    /**
     * Spawns a booster at random coordinates on the game grid if the booster is not already present.
     * The spawning occurs when the countdown reaches zero.
     * Resets the countdown when a booster is spawned.
     */
    protected void spawnBooster(int[][] snake, int[][] booster) {
        if (this.isBoosterEaten && this.spawnCountDown == 0) {
            booster = randomizeBoosterLocation(booster);
            while (boosterSpawnedOnSnake(snake, booster)) {
                booster = randomizeBoosterLocation(booster);
            }

            this.isBoosterEaten = false;
        }

        if (this.spawnCountDown > 0) { this.spawnCountDown--; }
    }

    private int[][] randomizeBoosterLocation(final int[][] booster) {
        booster[0] = new int[]{
            this.randomizer.nextInt(gameGrid.length),
            this.randomizer.nextInt(gameGrid.length),
            AppConfigProject.COLOR_CHERRY_INT
        };

        return booster;
    }

    private boolean boosterSpawnedOnSnake(final int[][] snake, int[][] booster) {
        for (int[] bodyPart : snake) {
            if (bodyPart[0] == booster[0][0] && bodyPart[1] == booster[0][1]) {
                return true;
            }
        }
        return false;
    }

    protected void addSpeed(SnakeModel snakeModel, int speed) {
        snakeModel.setSpeed(speed);
    }

    public void setGameGrid(final int[][] gameGrid) {
        this.gameGrid = gameGrid;
    }

    protected abstract void eatBooster(SnakeModel snakeModel);
    public abstract int[][] getBooster();
    protected abstract int getBoosterColor();
}
