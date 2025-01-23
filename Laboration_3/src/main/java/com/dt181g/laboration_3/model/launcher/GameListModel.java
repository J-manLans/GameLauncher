package com.dt181g.laboration_3.model.launcher;

import java.util.ArrayList;
import java.util.List;

import com.dt181g.laboration_3.controller.games.common.IGameController;
import com.dt181g.laboration_3.model.games.common.IGameModel;
import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.games.common.IGameView;

/**
 * The GameListModel class maintains a list of game models, their corresponding views and controllers.
 *
 * <p>
 * It initializes the game models, views and controllers at construction and provides methods
 * to access the titles, icon paths, and specific game models views or controllers by title.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameListModel {
    private final List<String> gameTitles = new ArrayList<>();
    private final List<String> iconPaths = new ArrayList<>();
    private final List<IGameModel> gameModels = new ArrayList<>();
    private final List<IGameView> gameViews = new ArrayList<>();
    private final List<IGameController> gameControllers = new ArrayList<>();
    private String activeGame;

    /**
     * Constructs a GameListModel and initializes the game icons and titles.
     * When new games are added their title and icon paths needs to be
     * added to the games array.
     */
    public GameListModel() {
        String[][] games = {
            {AppConfigLab3.SNAKE_TITLE, AppConfigLab3.SNAKE_ICON},
            {AppConfigLab3.SNAKE_TITLE, AppConfigLab3.SNAKE_ICON},
            {AppConfigLab3.SNAKE_TITLE, AppConfigLab3.SNAKE_ICON},
            {AppConfigLab3.SNAKE_TITLE, AppConfigLab3.SNAKE_ICON},
            {AppConfigLab3.SNAKE_TITLE, AppConfigLab3.SNAKE_ICON}
        };
        for (String[] game : games) {
            this.gameTitles.add(game[0]);
            this.iconPaths.add(AppConfigLab3.PATH_TO_ICONS + game[1]);
        }
    }

    /*=====================
     * Getters
     ====================*/

    /**
     * Returns the active game.
     * @return the active game
     */
    public String getActiveGame() {
        return this.activeGame;
    }

    /**
     * Returns the icon paths to the games.
     * @return a list of icon paths.
     */
    public List<String> getIconPaths() {
        return this.iconPaths;
    }

    /**
     * Returns the title of the games.
     * @return a list of game titles.
     */
    public List<String> getTitleList() {
        return this.gameTitles;
    }

    /**
     * Returns the models list.
     * @return the models list
     */
    public List<IGameModel> getGameModels() {
        return this.gameModels;
    }

    /**
     * Returns the views list.
     * @return the views list
     */
    public List<IGameView> getGameViews() {
        return this.gameViews;
    }

    /**
     * Returns the controllers list.
     * @return the controllers list
     */
    public List<IGameController> getGameControllers() {
        return this.gameControllers;
    }

    /**
     * Retrieves the GameModel associated with the specified title.
     *
     * <p>
     * If the title isn't present null is returned.
     * </p>
     * @param title the title of the game to retrieve the model for.
     * @return the GameModel associated with the title, or null if not found.
     */
    public IGameModel getGameModel(final String title) {
        return gameModels.stream()
        .filter(gameModel -> gameModel.getTitle().equals(title))
        .findFirst()
        .orElse(null);
    }

    /**
     * Retrieves the GameView associated with the specified title.
     *
     * <p>
     * This method searches through a collection of game views to find one that matches
     * the provided title. If a matching game view is found, it is returned; otherwise,
     * this method returns null.
     * </p>
     * @param title the title of the game to retrieve the view for.
     * @return the view associated with the title, or null if not found.
     */
    public IGameView getGameView(final String title) {
        return gameViews.stream()
        .filter(gameView -> gameView.getTitle().equals(title))
        .findFirst()
        .orElse(null);
    }

    /**
     * Retrieves the GameController associated with the specified title.
     *
     * <p>
     * This method searches through a collection of game controllers to find one that matches
     * the provided title. If a matching game controller is found, it is returned; otherwise,
     * this method returns null.
     * </p>
     * @param title the title of the game to retrieve the view for.
     * @return the controller associated with the title, or null if not found.
     */
    public IGameController getGameController(final String title) {
        return gameControllers.stream()
        .filter(gameController -> gameController.getTitle().equals(title))
        .findFirst()
        .orElse(null);
    }

    /*================
     * Setters
     ===============*/

     /**
      * Sets the active game.
      * @param activeGame the active game
      */
    public void setActiveGame(final String activeGame) {
        this.activeGame = activeGame;
    }

    /**
     * Adds a game model to the gameModels list
     * @param gameModel the model to add
     */
    public void addGameModel(IGameModel gameModel) {
        this.gameModels.add(gameModel);
    }

    /**
     * Adds a game view to the gameViews list
     * @param gameView the view to add
     */
    public void addGameView(IGameView gameView) {
        this.gameViews.add(gameView);
    }

    /**
     * Adds a game controller to the gameControllers list
     * @param gameController the controller to add
     */
    public void addGameController(IGameController gameController) {
        this.gameControllers.add(gameController);
    }
}
