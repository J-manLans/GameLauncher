package com.dt181g.laboration_3.support;

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
 *
 * @author Joel Lansgren
 */
public final class AppConfigLab3 {
    // Prevent instantiation
    private AppConfigLab3() { throw new IllegalStateException("Utility class"); }

    /* ---------------------------------------
    Paths to game assets.
    ------------------------------------------ */

    /** Path to game icons folder. */
    public static final String PATH_TO_ICONS = "/icons/";

    /** Extension to snake image. */
    public static final String SNAKE_ICON = "snake.jpeg";

    /** Path to images folder */
    public static final String PATH_TO_IMAGES = "/img/";

    /** Launcher backgrounds array */
    public static final String[] LAUNCHER_BACKGROUNDS = {
        "GameLauncher01.png",
        "GameLauncher02.png",
        "GameLauncher03.png"
    };

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
    public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);

    /** Dark grey color. */
    public static final Color COLOR_DARK_GREY = new Color(40, 40, 40);

    /** Darker gray color. */
    public static final Color COLOR_DARKER_GREY = new Color(30, 30, 30);

    /** A white color. */
    public static final Color COLOR_WHITE = new Color(255, 245, 238);

    /** Accent color for the snake game. */
    public static final Color COLOR_SNAKE_GAME_ACCENT = new Color(255, 184, 77);

    /* ---------------------------------------
    Borders.
    ------------------------------------------ */

    /** Creates space underneath the element. */
    public static final Border BOTTOM_SPACER_30 = BorderFactory.createEmptyBorder(0, 0, 30, 0);

    /** Creates space to the right and beneath the element. */
    public static final Border RIGHT_BOTTOM_CORNER_SPACER_30 = BorderFactory.createEmptyBorder(0, 0, 30, 30);

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

    /** Yet another GridBag inset. */
    public static final Insets INSET_TOP_30_BOTTOM_20 = new Insets(30, 0, 20, 0);

    /** Yet another GridBag inset. */
    public static final Insets INSET_LEFT_BOTTOM_CORNER_30 = new Insets(0, 30, 30, 0);


    /* ---------------------------------------
    GameLauncherView Settings.
    ------------------------------------------ */

    /** Indicates wether I'm debugging or not. */
    public static final boolean DEBUG_MODE = false;

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

    /** Size of the grid layout for the snake. */
    public static final Dimension SNAKE_GRID_SIZE = new Dimension(760, 760);

    /** Size of the controls panel. */
    public static final Dimension SNAKE_CONTROLS_SIZE = new Dimension(400, 400);

    /** Number of cells in the grid. */
    public static final int SNAKE_CELL_COUNT = 38;

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
