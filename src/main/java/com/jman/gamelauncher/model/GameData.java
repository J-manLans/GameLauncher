package com.jman.gamelauncher.model;

/**
 * Holds metadata for a game, currently only its active status which by default should be false.
 * This class can be expanded in the future to include additional game properties like high scores, game icons,
 * descriptions etc.
 * @author Joel Lansgren
 */
class GameData {
    private final String title;

    GameData(String title) {
        this.title = title;
    }

    /*=====================
    * Getters
    =====================*/

    /**
     * This returns the title of the game.
     * @return The title of the game.
     */
    String getTitle() {
        return title;
    }
}
