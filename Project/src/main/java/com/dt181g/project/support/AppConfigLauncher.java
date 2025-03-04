package com.dt181g.project.support;

import java.awt.Dimension;

/**
 * A utility class that provides configuration constants
 * and settings for the launcher.
 * <p>
 * This class includes specific UI settings tied to the launcher.
 * </p>
 * <p>
 * The class is not meant to be instantiated; all fields and methods are static.
 * </p>
 * @author Joel Lansgren
 */
public final class AppConfigLauncher {
    // Prevent instantiation
    private AppConfigLauncher() { throw new IllegalStateException("Utility class"); }

    /* =========================
    * Settings
    ========================= */

    /** Indicates wether I'm debugging or not. */
    public static final boolean DEBUG_MODE = false;

    /* =========================
    * Strings
    ========================= */

    /** An array of all existing game titles. All new games should be added here */
    public static final String[] GAMES = {
        AppConfig.SNAKE_TITLE,
        "Coming Soon"
    };

    /** Name of the start view for the card layout in the launcher. */
    public static final String START = "Start";

    /** Name of the about view for the card layout in the launcher. */
    public static final String ABOUT = "About";

    /** Name of the coming soon view for the card layout in the launcher. */
    public static final String COMING_SOON = "ComingSoon";

    /** Name of the game view for the card layout in the launcher. */
    public static final String SELECTED_GAME = "SelectedGame";

    /* =========================
    * Numbers
    ========================= */

    /** Scroll speed multiplier for setting the mouse scrolling a little bit faster in the scroll pane. */
    public static final int SCROLL_SPEED_MULTIPLIER = 20;

    /* =========================
    * Dimensions
    ========================= */

    /** Dimension for the game selector panel. */
    public static final Dimension GAME_SELECTOR_PANEL_DIMENSIONS = new Dimension(270, 940);

    /** Dimension for the game panel.*/
    public static final Dimension GAME_LAUNCHER_DIMENSIONS = new Dimension(1700, 940);
}
