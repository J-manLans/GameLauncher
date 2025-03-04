package com.jman.gamelauncher.support;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * A utility class that provides configuration constants
 * and settings for the Snake game.
 * <p>
 * This class includes specific UI settings tied to the snake game.
 * </p>
 * <p>
 * The class is not meant to be instantiated; all fields and methods are static.
 * </p>
 * @author Joel Lansgren
 */
public final class AppConfigSnake {
    // Prevent instantiation
    private AppConfigSnake() { throw new IllegalStateException("Utility class"); }

    /* =========================
    * Strings
    ========================= */

    /** Name of the start view for the card layout in the snake game. */
    public static final String START = "Start";

    /** Name of the how-to view for the card layout in the snake game. */
    public static final String HOW_TO = "HowTo";

    /** Name of the single player view for the card layout in the snake game. */
    public static final String SINGLE_PLAYER = "SinglePlayer";

    /* =========================
    * Sounds
    ========================= */

    /** The sound of the cherry booster when consumed. */
    public static final String SOUND_EFFECT_CHERRY = "cherry.wav";

    /** The sound of the speed booster when consumed. */
    public static final String SOUND_EFFECT_SPEED = "speed.wav";

    /* =========================
    * Numbers
    ========================= */

    /**
     * A number used for setting the grid dimension on the JPanel in the view
     * and calculating the size in the 2D array in the model
     */
    public static final int GRID_DIMENSION = 600;

    /** Number of cells in the grid. */
    public static final int CELL_COUNT = GRID_DIMENSION / 20;

    /** Initial length of the snake. */
    public static final int INITIAL_LENGTH = 15;

    /** Initial speed of the game loop. */
	public static final int SNAKE_TICK_DELAY = 200;

    /** A second in milliseconds. */
    public static final int SECOND_IN_MS = 1000;

    /** A speed multiplier to increase the speed of the snake proportional to its current speed. */
    public static final double SPEED_MULTIPLIER = 0.9;

    /* =========================
    * Colors
    ========================= */

    /** Accent color for the game. */
    public static final Color COLOR_ACCENT = new Color(255, 184, 77);

    /** Snakes head color. */
    public static final Color COLOR_SNAKE_HEAD = new Color(140, 100, 43);

    /** Color for the cherry to the snake game. */
    public static final Color COLOR_CHERRY_BOOSTER = new Color(167, 56, 68);

    /** Color for the speed booster to the snake game. */
    public static final Color COLOR_SPEED_BOOSTER = Color.YELLOW;

    /* =========================
    * Dimensions
    ========================= */

    /** Size of the grid layout for the snake. */
    public static final Dimension GRID_SIZE = new Dimension(GRID_DIMENSION, GRID_DIMENSION);

    /** Size of the controls panel. */
    public static final Dimension CONTROLS_SIZE = new Dimension(400, 400);

    /* =========================
    * Borders
    ========================= */

    /** A little bit thicker inner border with an empty outer border to simulate space. */
    public static final Border CONTROLS_BORDER = BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(200, 300, 200, 300),
        BorderFactory.createMatteBorder(10, 10, 10, 10, COLOR_ACCENT)
    );
}