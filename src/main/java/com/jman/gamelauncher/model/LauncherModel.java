package com.jman.gamelauncher.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jman.gamelauncher.support.AppConfigLauncher;

/**
 * The GameListModel class manages a collection of games and their metadata.
 * It provides methods to access the list of game titles, the active game,
 * and allows setting the active status of games.
 * <p>
 * The class initializes the games map with default metadata during construction.
 * It also provides a method to update the active state of a game.
 * </p>
 * <p>
 * As of now the metadata have no usage since I restructured the controller a bit so active game
 * isn't relevant any more. But I'll let it be for future scalability.
 * </p>
 * @author Joel Lansgren
 */
public class LauncherModel {
    private final Map<String, GameData> games = new HashMap<>();

    /**
     * Constructs a GameListModel and initializes the games map with default metadata for each game.
     */
    public LauncherModel() {
        for (final String game : AppConfigLauncher.GAMES) {
           games.put(game, new GameData(game));
        }
    }

    /*=====================
    * Getters
    =====================*/

    /**
     * Returns a sorted list of all game titles.
     * @return a list of game titles sorted alphabetically
     */
    public List<String> getTitleList() {
        return games.keySet().stream()
            .sorted()
            .collect(Collectors.toList());
    }
}
