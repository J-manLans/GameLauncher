package com.jman.gamelauncher.model;

// NOTE: At them moment this have no direct usage. But I'll leave it as a reminder for future implementations.

/**
 * Holds metadata for a game, currently only its active status which by default should be false.
 * This class can be expanded in the future to include additional game properties like high scores, game icons,
 * descriptions etc.
 * @author Joel Lansgren
 */
class GameData {
    private boolean isActive = false;

    /*=====================
    * Getters
    =====================*/

    /**
     * This returns the active state of the game.
     * @return true if the game is active, false otherwise
     */
    boolean isActive() {
        return isActive;
    }

    /*=====================
    * Setters
    =====================*/

    /**
     * Sets the active status of the game.
     * @param isActive the active status to be set for the game
     */
    void setActiveState(final boolean isActive) {
        this.isActive = isActive;
    }
}
