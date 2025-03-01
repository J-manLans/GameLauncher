package com.dt181g.project.support;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * A utility class that provides application-wide configuration constants
 * and settings for the game launcher.
 * <p>
 * This class includes paths to game icons, game titles, UI settings,
 * snake game configurations, font styles, color definitions, and helper methods
 * for styling UI components.
 * </p>
 * <p>
 * The class is not meant to be instantiated; all fields and methods are static.
 * </p>
 */
public final class AppConfigProject {
    // Prevent instantiation
    private AppConfigProject() { throw new IllegalStateException("Utility class"); }

    /* ---------------------------------------
    Paths to game assets.
    ------------------------------------------ */

    /** Path to game icons folder. */
    public static final String PATH_TO_ICONS = "/icons/";

    /** Extension to snake image. */
    public static final String ICON_SNAKE = "snake.jpeg";

    /** Path to images folder */
    public static final String PATH_TO_IMAGES = "/img/";

    /** Launcher backgrounds array */
    public static final String[] LAUNCHER_BACKGROUNDS = {
        "GameLauncher01.png",
        "GameLauncher02.png",
        "GameLauncher03.png"
    };

    public static final String PATH_TO_SOUNDS = "/sounds/";

    public static final String SOUND_EFFECT_CHERRY = "cherry.wav";
    public static final String SOUND_EFFECT_SPEED = "speed.wav";
    public static final String SOUND_EFFECT_SILENCE = "keep_alive.wav";



    /* ---------------------------------------
    Game titles.
    ------------------------------------------ */
    /** Snake title. */
    public static final String SNAKE_TITLE = "SNAKE(S)";

    /* ---------------------------------------
    Fonts.
    ------------------------------------------ */

    // Sizes

    /** Normal text size. */
    public static final int TEXT_SIZE_NORMAL = 16;

    /** Heading 1. */
    public static final int TEXT_HEADING_1 = 25;

    /** Heading 2. */
    public static final int TEXT_HEADING_2 = 20;

    /* ---------------------------------------
    Colors.
    ------------------------------------------ */


    /** Transparent color. */
    public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 150);

    /** Dark grey color. */
    public static final Color COLOR_DARK_GREY = new Color(40, 40, 40);

    /** Darker gray color. */
    public static final Color COLOR_DARKER_GREY = new Color(30, 30, 30);

    /** A white color. */
    public static final Color COLOR_WHITE = new Color(255, 245, 238);

    /** Accent color for the snake game. */
    public static final Color COLOR_SNAKE_GAME_ACCENT = new Color(255, 184, 77);

    /** Number in snakes body representing the accent color. */
    public static final int COLOR_SNAKE_INT = 1;

    /** Color for the cherry to the snake game. */
    public static final Color COLOR_SNAKE_GAME_CHERRY = new Color(167, 56, 68);

    /** Number in cherry's body representing the color. */
    public static final int COLOR_CHERRY_INT = 2;

    /** Color for the speed booster to the snake game. */
    public static final Color COLOR_SNAKE_GAME_SPEED = Color.YELLOW;

    /** Number in speed boosters body representing the color. */
    public static final int COLOR_SPEED_INT = 4;

    /** Snakes head color when in collision to mark its position. */
    public static final Color COLOR_SNAKE_HEAD = new Color(140, 100, 43);

    /** Number in snakes body representing the death color. */
    public static final int COLOR_SNAKE_HEAD_INT = 3;

    /* ---------------------------------------
    Borders.
    ------------------------------------------ */

    /** Creates space underneath the element. */
    public static final Border BOTTOM_SPACER_30 = BorderFactory.createEmptyBorder(0, 0, 30, 0);

    /** Removes borders. */
    public static final Border REMOVE_BORDER = BorderFactory.createEmptyBorder();

    /** A little bit thicker border. */
    public static final Border CONTROLS_BORDER = BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(200, 300, 200, 300),
        BorderFactory.createMatteBorder(10, 10, 10, 10, COLOR_SNAKE_GAME_ACCENT)
    );

    /** An empty border that's part of a compound border. */
    public static final Border LABEL_BTN_INNER_SPACE = BorderFactory.createEmptyBorder(10, 10, 10, 10);

    /** The width of the outer border thats the other part of the compound border. */
    public static final int LABEL_BTN_OUTER_BORDER_WIDTH = 3;

    /* ---------------------------------------
    Insets.
    ------------------------------------------ */

    /** Resetting the GridBag insets. */
    public static final Insets RESET_INSETS = new Insets(0, 0, 0, 0);

    /** A GridBag inset. */
    public static final Insets INSET_BOTTOM_20 = new Insets(0, 0, 20, 0);

    /** Another GridBag inset. */
    public static final Insets INSET_BOTTOM_30 = new Insets(0, 0, 30, 0);

    public static final Insets INSET_LEFT_BOTTOM_CORNER_30 = new Insets(0, 30, 30, 0);


    /* ---------------------------------------
    GameLauncherView Settings.
    ------------------------------------------ */

    /** Dimension for the game selector panel. */
    public static final Dimension GAME_SELECTOR_PANEL_DIMENSIONS = new Dimension(270, 940);

    /** Dimension for the game panel.*/
    public static final Dimension GAME_LAUNCHER_DIMENSIONS = new Dimension(1700, 940);

    /** A number of 200, free for use.*/
    public static final int NUM_200 = 200;

    /** Dimension for the game icons. */
    public static final Dimension GAME_ICON_SIZE = new Dimension(200, 90);

    /** Dimension of 20 that can be used for a rigid area. */
    public static final Dimension HIGHT_20 = new Dimension(0, 20);

    /** Dimension of 40 that can be used for a rigid area. */
    public static final Dimension HIGHT_40 = new Dimension(0, 40);

    /** Scroll speed multiplier for setting the mouse scrolling a little bit faster in the scroll pane. */
    public static final int SCROLL_SPEED_MULTIPLIER = 20;

    /* ---------------------------------------
    Snake settings.
    ------------------------------------------ */

    /** Length of the initial snake. */
    public static final int INITIAL_SNAKE_LENGTH = 10;

    /** Amounts of content in each body part of the snake (y and x coordinates and color). */
    public static final int SNAKE_ITEMS_PART_CONTENT = 3;

    /** Size of the grid layout for the snake. */
    public static final Dimension SNAKE_GRID_SIZE = new Dimension(760, 760);

    /** Number of cells in the grid. */
    public static final int SNAKE_CELL_COUNT = 38;

    /** Upper bounds number of maximum tics before spawning a new item. */
	public static final int UPPER_SPAWNING_BOUND = 20;

	public static int SNAKE_TICK_DELAY = 500;

    public static double SNAKE_SPEED_MULTIPLIER = 0.9;

    public static final int CHERRY_INDEX = 0;

    public static final int BASE_BOOSTER_DURATION = 50;

    public static final int SECOND_IN_MS = 1000;



    /* ---------------------------------------
    Steering.
    ------------------------------------------ */

    public static final Direction UP = Direction.UP;

    public static final Direction DOWN = Direction.DOWN;

    public static final Direction LEFT = Direction.LEFT;

    public static final Direction RIGHT = Direction.RIGHT;


    public static enum Direction {
        UP, DOWN, LEFT, RIGHT;
    }

    /* ---------------------------------------
    ANSI COLOR CODES for console output.
    ------------------------------------------ */

    /** Reset color. */
    public static final String ANSI_RESET = DebugColor.RESET.ansiCode;

    /** Black color. */
    public static final String ANSI_BLACK = DebugColor.BLACK.ansiCode;

    /** Red color. */
    public static final String ANSI_RED = DebugColor.RED.ansiCode;

    /** Green color. */
    public static final String ANSI_GREEN = DebugColor.GREEN.ansiCode;

    /** Yellow color. */
    public static final String ANSI_YELLOW = DebugColor.YELLOW.ansiCode;

    /** Blue color. */
    public static final String ANSI_BLUE = DebugColor.BLUE.ansiCode;

    /** Cyan color. */
    public static final String ANSI_CYAN = DebugColor.CYAN.ansiCode;

    /** White color. */
    public static final String ANSI_WHITE = DebugColor.WHITE.ansiCode;

    /** Magenta color. */
    public static final String ANSI_MAGENTA = DebugColor.MAGENTA.ansiCode;

    /**
     * An enum representing ANSI color codes for console output.
     */
    private enum DebugColor {
        BLACK("\u001B[30m"), RED("\u001B[31m"), GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"), BLUE("\u001B[34m"), CYAN("\u001B[36m"),
        WHITE("\u001B[37m"), MAGENTA("\u001b[35m"), RESET("\u001B[0m");

        private final String ansiCode;

        /**
         * Constructs a new color with the given ANSI escape code.
         *
         * @param ansiCode the ANSI escape code for the color
         */
        DebugColor(final String ansiCode) {
            this.ansiCode = ansiCode;
        }
    }
}
