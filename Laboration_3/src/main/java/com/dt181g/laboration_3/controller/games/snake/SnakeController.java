package com.dt181g.laboration_3.controller.games.snake;

import com.dt181g.laboration_3.controller.common.MenuButtonListener;
import com.dt181g.laboration_3.controller.common.IController;
import com.dt181g.laboration_3.controller.games.common.IGameController;
import com.dt181g.laboration_3.model.games.common.IGameModel;
import com.dt181g.laboration_3.view.games.common.IGameView;
import com.dt181g.laboration_3.model.games.snake.SnakeModel;
import com.dt181g.laboration_3.supportChange.AppConfigLab3;
import com.dt181g.laboration_3.view.games.snake.SnakeMainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * The SnakeCtrl class serves as the controller for the Snake game.
 * It mediates the interaction between the SnakeView and SnakeModel,
 * handling user inputs, updating the game state, and rendering changes
 * to the view.
 *
 * <p>
 * This class implements the GameCtrl interface and defines various
 * mouse listeners for game controls, including starting the game and
 * showing controls.
 * </p>
 *
 * <p>
 * This program is under construction and more game mechanics will be
 * incorporated moving forward, as of now, the game only displays a
 * snake with a banner text informing more is to come.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeController implements IGameController, IController {
    private final SnakeMainView snakeMainView;
    private final SnakeModel snakeModel;
    private final String title;
    private Timer gameLoop;
    private boolean gameOn;

    /**
     * Constructs a SnakeCtrl with the specified game title, SnakeView and SnakeModel.
     *
     * @param title the snake game title
     * @param snakeMainView the view associated with the Snake game
     * @param snakeModel the model representing the game's logic
     */
    public SnakeController(final String title, final IGameView snakeMainView, final IGameModel snakeModel) {
        this.title = title;
        this.snakeMainView = (SnakeMainView) snakeMainView;
        this.snakeModel = (SnakeModel) snakeModel;
    }

    /*==========================
     * Start Methods
     =========================*/

    @Override
    public void initialize() {
        initializeListeners();
        createGameLoop();
    }

    @Override
    public void initializeListeners() {
        this.snakeMainView.addStartBtnListener(
            new MenuButtonListener(
                this.snakeMainView.getStartBtn(),
                this::startGame
            )
        );

        this.snakeMainView.addControlsBtnListener(
            new MenuButtonListener(
                this.snakeMainView.getControlsBtn(),
                snakeMainView::showControlsMenu
            )
        );

        this.snakeMainView.addSnakeBackBtnListener(
            new MenuButtonListener(
                this.snakeMainView.getSnakeBackBtn(),
                this::initiateGame
            )
        );
    }

    /**
     * Creates the game loop that updates the game state at regular intervals.
     */
    private void createGameLoop() {
        this.gameLoop = new Timer(AppConfigLab3.NUM_200, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (gameOn) {
                    snakeModel.updateGameGrid();
                    snakeMainView.updateGameGrid(snakeModel.getGameAssets());
                } else {
                    gameLoop.stop();
                }
            }
        });
    }

    @Override
    public void initiateGame() {
        // Shows the start menu
        this.snakeMainView.showStartMenu();
        // Resets the snake models 2D array
        this.snakeModel.clearSnakeGrid();
        // Stops the EDT thread from executing
        this.gameOn = false;
    }

    /**
     * Starts the game and initializes the game loop.
     * Prepares the game state and updates the view for gameplay.
     */
    private void startGame() {
        // Grid initialization.
        this.snakeModel.initializeSnake();
        this.snakeMainView.startGame(this.snakeModel.getGameAssets());

        // For controlling the loop
        this.gameOn = true;
        this.gameLoop.start();
    }

    /*==========================
     * Close Methods
     =========================*/

    @Override
    public void closeGame() {
        stopGameLoop();
        removeListeners();
        this.snakeMainView.closeGameView();
    }

    /**
     * Removes all listeners from the views.
     */
    private void removeListeners() {
        this.snakeMainView.removeListeners();
    }

    /**
     * Stops the game loop timer if it is currently running when closing the game.
     */
    private void stopGameLoop() {
        if (this.gameLoop != null) {
            this.gameLoop.stop();
            this.gameLoop = null;
        }
    }

    /*==========================
     * Getters
     =========================*/

    @Override
    public String getTitle() {
        return this.title;
    }
}
