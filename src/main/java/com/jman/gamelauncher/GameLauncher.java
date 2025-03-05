package com.jman.gamelauncher;

import com.jman.gamelauncher.controller.LauncherController;

/**
 * The main starting point for the project.
 * @author Joel Lansgren
 */
public final class GameLauncher {
    private GameLauncher() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * The main method serves as the entry point for the application.
     *
     * <p>It invokes the singleton instance of the game launcher issuer,
     * which encapsulates the logic required to start the game launcher.</p>
     * @param args command-line arguments (not used).
     */
    public static void main(final String... args) {
        new LauncherController().initialize();
    }
}
