package com.dt181g.project.support;

/**
 * A utility class that provides configuration constants
 * and settings for the DebugLogger.
 * <p>
 * This class includes specific UI settings tied to the DebugLogger.
 * </p>
 * <p>
 * The class is not meant to be instantiated; all fields and methods are static.
 * </p>
 * @author Joel Lansgren
 */
public final class AppConfigDebug {
    // Prevent instantiation
    private AppConfigDebug() { throw new IllegalStateException("Utility class"); }


    /* =======================================
    * ANSI COLOR CODES for console output.
    ======================================= */

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
