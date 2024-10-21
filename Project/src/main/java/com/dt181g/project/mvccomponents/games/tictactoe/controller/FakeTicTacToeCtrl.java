
package com.dt181g.project.mvccomponents.games.tictactoe.controller;

import javax.swing.Timer;

import com.dt181g.project.mvccomponents.games.GameController;
import com.dt181g.project.mvccomponents.games.GameModel;
import com.dt181g.project.mvccomponents.games.GameView;
import com.dt181g.project.mvccomponents.games.tictactoe.model.FakeTicTacToeModel;
import com.dt181g.project.mvccomponents.games.tictactoe.view.FakeTicTacToeView;
import com.dt181g.project.support.DebugLogger;

/**
 * A mock controller for the Tic Tac Toe game, used for testing game icons
 * in the GameLauncherView.
 *
 * <p>
 * This class implements the GameCtrl interface and provides a
 * simple way to control a Tic Tac Toe game view for testing purposes.
 * It allows for resetting the game and includes a placeholder for
 * initializing listeners.
 * </p>
 */
public class FakeTicTacToeCtrl implements GameController {
    DebugLogger logger = DebugLogger.INSTANCE;
    private final FakeTicTacToeView ticTacToeView;
    private final FakeTicTacToeModel ticTacToeModel;
    private final String title;
    private Timer gameLoop = new Timer(0, null);
    private boolean isRunning = true;

    /**
     * Constructs a FakeTicTacToeCtrl with the specified game title, view and model.
     *
     * @param title the tic tac toe game title
     * @param ticTacToePanelView the view representing the Tic Tac Toe game
     * @param ticTacToeModel the model representing the game's state
     */
    public FakeTicTacToeCtrl(final String title, final GameView ticTacToeView, final GameModel ticTacToeModel) {
        this.ticTacToeView = (FakeTicTacToeView) ticTacToeView;
        this.ticTacToeModel = (FakeTicTacToeModel) ticTacToeModel;
        this.title = title;

        this.initializeListeners();
    }

    /**
     * Initializes listeners for user interactions.
     */
    @Override
    public void initializeListeners() {
    }

    /**
     * Resets the Tic Tac Toe game by invoking the reset method on the game view.
     */
    @Override
    public void initiateGame() {
        this.ticTacToeView.ShowStartMenu();
        logger.logInfo(title +
            " has been initiated.\n"
        );
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void closeGame() {
        this.ticTacToeView.closeGameView();
    }

    @Override
    public boolean getIsRunning() {
        return isRunning;
    }

    @Override
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public Timer getGameLoop() {
        return gameLoop;
    }
}
