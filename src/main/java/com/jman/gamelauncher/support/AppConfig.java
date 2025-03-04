package com.jman.gamelauncher.support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

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
 * @author Joel Lansgren
 */
public final class AppConfig {
    // Prevent instantiation
    private AppConfig() { throw new IllegalStateException("Utility class"); }

    /* =========================
    * Game titles
    ========================= */

    /** Snake title. */
    public static final String SNAKE_TITLE = "SNAKE(S)";

    /* =========================
    * Sounds
    ========================= */

    /** Path to sounds in the resources folder. */
    public static final String PATH_TO_SOUNDS = "/sounds/";

    /** A silent sound to keep the audio responsive. */
    public static final String SOUND_EFFECT_SILENCE = "keep_alive.wav";

    /** A silent sound to keep the audio responsive. */
    public static final String SOUND_EFFECTS_LOADED = "LOADED";

    /* =========================
    * Numbers
    ========================= */

    /** A Delay for how often to spawn boosters in milliseconds. */
    public static final int BOOSTER_SPAWN_MAX_DELAY = 7001;

    /** A Delay for how often to spawn boosters in milliseconds. */
    public static final int BOOSTER_SPAWN_MIN_DELAY = 1000;

    /* =========================
    * Fonts
    ========================= */

    /** The Monospace font used throughout the launcher */
    public static final String MONOSPACE = "Monospace";

    /** Normal text size. */
    public static final int TEXT_SIZE_NORMAL = 16;

    /** Heading 1. */
    public static final int TEXT_HEADING_1 = 25;

    /** Heading 2. */
    public static final int TEXT_HEADING_2 = 20;

    /* =========================
    * Colors
    ========================= */

    /** Dark grey color. */
    public static final Color COLOR_DARK_GREY = new Color(40, 40, 40);

    /** Darker gray color. */
    public static final Color COLOR_DARKER_GREY = new Color(30, 30, 30);

    /** A white color. */
    public static final Color COLOR_WHITE = new Color(255, 245, 238);

    /** Accent color for the snake game. */
    public static final Color COLOR_ACCENT = new Color(64, 224, 208);

    /** Transparent color for overlays etc. */
    public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 200);

    /* =========================
    * Borders
    ========================= */

    /** An empty border that's part of a compound border. */
    public static final Border LABEL_BTN_INNER_SPACE = BorderFactory.createEmptyBorder(10, 10, 10, 10);

    /** The width of the outer border thats the other part of the compound border. */
    public static final int LABEL_BTN_OUTER_BORDER_WIDTH = 3;

    /* =========================
    * Insets
    ========================= */

    /** Resetting the GridBag insets. */
    public static final Insets RESET_INSETS = new Insets(0, 0, 0, 0);

    /** A GridBag inset. */
    public static final Insets INSET_BOTTOM_20 = new Insets(0, 0, 20, 0);

    /** A GridBag inset. */
    public static final Insets INSET_BOTTOM_30 = new Insets(0, 0, 30, 0);

    /** A GridBag inset. */
    public static final Insets INSET_TOP_30_BOTTOM_20 = new Insets(30, 0, 20, 0);

    /** A GridBag inset. */
    public static final Insets INSET_TOP_20_BOTTOM_30 = new Insets(20, 0, 30, 0);

    /** A GridBag inset. */
    public static final Insets INSET_LEFT_BOTTOM_CORNER_30 = new Insets(0, 30, 30, 0);

    /* =========================
    * Dimensions
    ========================= */

    /** Dimension of 20 that can be used for a rigid area. */
    public static final Dimension HIGHT_20 = new Dimension(0, 20);
}
