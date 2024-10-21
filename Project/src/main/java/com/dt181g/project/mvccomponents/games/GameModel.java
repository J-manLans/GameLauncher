package com.dt181g.project.mvccomponents.games;

/**
 * An interface representing the essential model of a game.
 * <p>
 * This interface provides methods to retrieve the game's icon path,
 * title, and grid size. Any class implementing this interface
 * must provide concrete implementations for these methods.
 * </p>
 */
public interface GameModel {
   /**
     * Retrieves the path to the game's icon.
     *
     * @return A {@code String} representing the file path to the game's icon.
     */
   String getIconPath();

   /**
     * Retrieves the title of the game.
     *
     * @return A {@code String} containing the title of the game.
     */
   String getTitle();
}
