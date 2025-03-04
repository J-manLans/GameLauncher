package com.dt181g.project.controller;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.dt181g.project.model.Direction;
import com.dt181g.project.model.SnakeCherryBoosterModel;
import com.dt181g.project.model.SnakeModel;
import com.dt181g.project.model.SnakeSpeedBoosterModel;
import com.dt181g.project.support.AppConfig;
import com.dt181g.project.support.AppConfigSnake;
import com.dt181g.project.support.AudioManager;
import com.dt181g.project.support.BoosterPool;
import com.dt181g.project.view.SnakeHowToView;
import com.dt181g.project.view.SnakeSinglePlayerView;
import com.dt181g.project.view.SnakeStartMenuView;
import com.dt181g.project.view.SnakeView;


/**
 * This class serves as the controller for the Snake game.
 * It mediates the interaction between the snake views and models,
 * handling user inputs, updating the game state, and rendering
 * changes to the view.
 *
 * <p>This class implements the IGameController interface and defines various
 * mouse listeners for game controls, including starting the game and
 * showing controls.</p>
 *
 * <p>This program is under construction and more game mechanics will be
 * incorporated moving forward, as of now, the game only displays a
 * snake that endlessly roams the game grid.</p>
 *
 * @author Joel Lansgren
 */
class SnakeController implements IGameController {
    // MVC components
    private final SnakeView snakeView;
    private final SnakeStartMenuView startMenuView;
    private final SnakeSinglePlayerView singlePlayerView;
    private final SnakeHowToView howToView;
    private final SnakeModel snakeModel;

    // Game loop variables
    private Timer gameLoop;
    private boolean gameOn;

    /**
     * Constructs a SnakeController with the specified views, models and BoosterController.
     * It also initialize the BoosterPool with the list of boosters used in the game.
     */
    public SnakeController() {
        snakeView = new SnakeView();
        startMenuView = new SnakeStartMenuView();
        singlePlayerView = new SnakeSinglePlayerView();
        howToView = new SnakeHowToView();
        snakeModel = new SnakeModel(this::updateTimerDelay);
        BoosterPool.INSTANCE.initialize(List.of(
            new SnakeCherryBoosterModel(snakeModel),
            new SnakeSpeedBoosterModel(snakeModel)
        ));
    }

    /*==========================
    * Start Methods
    =========================*/

    /**
     * {@inheritDoc}
     *
     * <p> In the {@link SnakeController}, this method initializes the game by
     * setting views for the card layout, initializing the game grid in the
     * view for the snake game, creating the game loop and initiating the game environment. </p>
     */
    @Override
    public void initialize(
        final Runnable closeGameClickListener,
        final String soundEffectsLoaded,
        final Consumer<JPanel> displayGameInLauncher
    ) {
        // Sets the views for card layout
        snakeView.setViews(startMenuView.getMainPanel(), singlePlayerView.getMainPanel(), howToView.getMainPanel());
        singlePlayerView.initializeGridCell(); // Initializes the cells for the snakeGrid
        initializeListeners(closeGameClickListener); // Registers listeners
        displayGameInLauncher.accept(snakeView.getMainPanel()); // Displays the Snake main view in the launcher

        createGameLoop();
        initiateGameState();
    }

    @Override
    public void initializeListeners(final Runnable closeGameClickListener) {
        startMenuView.addStartBtnListener(() -> {
            singlePlayerView.setBackBtn();
            startGame();
        });

        startMenuView.addHowToBtnListener(() -> {
            howToView.setBackBtn();
            snakeView.showHowToView();
        });

        /*
        The order of the methods are important here. We first remove the listener on the backBtn from
        the game (closeGame()) and then re-add it in the launcher (closeGameClickListener.run()).
        */
        startMenuView.addQuitBtnListener(() -> {
            closeGame();
            closeGameClickListener.run();
        });

        startMenuView.addBackBtnListener(this::initiateGameState);

        singlePlayerView.addSnakeKeyListener(new CardinalDirectionListener(this::setDirection));

        singlePlayerView.addRestartBtnListener(this::startGame);
    }

    /**
     * Sets the current direction of the snake in the model.
     * @param direction The new direction for the snake.
     */
    private void setDirection(final Direction direction) {
        snakeModel.setDirection(direction);
    }

    /**
     * Creates the game loop that updates the game state at regular intervals.
     * If we abort the game the game loop stops and we pause the booster mechanics
     * and if it's game over we also show the game-over screen.
     */
    private void createGameLoop() {
        gameLoop = new Timer(AppConfigSnake.SNAKE_TICK_DELAY, e -> {
            if (gameOn && !snakeModel.isGameOver()) {
                snakeModel.updateSnakeData(this::updateGameGrid);
            } else {
                gameLoop.stop();
                if (snakeModel.isGameOver()) {
                    singlePlayerView.showGameOver(
                        snakeModel.getSnake().size(),
                        (AppConfigSnake.SECOND_IN_MS / gameLoop.getDelay())
                    );
                }
                BoosterPool.INSTANCE.setPaused(true);
                BoosterPool.INSTANCE.resetBoosterActivation();
                snakeModel.cancelSpeedBoost();
            }
        });
    }

    /**
     * This is updated via a callback from the snakeModel whenever the speed have been changed.
     *
     * <p>It then adjusts the game loop delay accordingly. It's essentially a simplified
     * Observer pattern with a push implementation.</p>
     * @param timerDelay The delay to be set on the gameLoop.
     */
    private void updateTimerDelay(final int timerDelay) {
        gameLoop.setDelay(timerDelay);
    }

    @Override
    public void initiateGameState() {
        snakeView.showStartMenu(); // Shows the start menu.
        singlePlayerView.hideGameOver(); // hides the gameOverPanel if it's visible.
        gameOn = false; // Stops the swing timer from executing.
    }

    /**
     * {@inheritDoc}
     *
     * <p>This initialize the snake, activate the booster mechanics, ensures the
     * single player view is properly set up in the card layout, starts the game loop
     * and waits for the AudioManager to load all sounds (The boosters preloaded their
     * sound effects when they were instantiated and the controller will wait until
     * all sounds are properly loaded).</p>
     */
    @Override
    public void startGame() {
        snakeModel.initializeSnakeData(this::updateGameGrid);
        BoosterPool.INSTANCE.setPaused(false);
        singlePlayerView.hideGameOver();
        snakeView.showSinglePlayerView();
        singlePlayerView.requestFocusInGameGrid();

        gameOn = true;
        gameLoop.start();

        // Prepare the AudioManager for playback if not done already.
        AudioManager.INSTANCE.queueSoundEffect(AppConfig.SOUND_EFFECTS_LOADED);
    }

    /**
     * Method that is passed into the SnakeModel as a callback for updating the game grid
     * with the fresh game data in the view.
     */
    private void updateGameGrid() {
        singlePlayerView.updateGameGrid(snakeModel.getSnake(), BoosterPool.INSTANCE.getActiveBoosterData());
    }

    /*==========================
    * Close Methods
    =========================*/

    /**
     * {@inheritDoc}
     * <p>
     * Stops the game loop timer if it is currently running, remove listeners and shut down other resources.
     * </p>
     */
    @Override
    public void closeGame() {
        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
        }
        removeListeners();

        BoosterPool.INSTANCE.shutdown();
        snakeModel.shutdownScheduler();
    }

    @Override
    public void removeListeners() {
        startMenuView.removeListeners();
        singlePlayerView.removeListeners();
    }
}
