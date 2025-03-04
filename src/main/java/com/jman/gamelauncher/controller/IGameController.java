package com.jman.gamelauncher.controller;

import java.util.function.Consumer;

import javax.swing.JPanel;

/**
 * Interface representing the controllers for the games within the game launcher application.
 * <p>
 * This interface defines the essential operations required for managing the lifecycle of a game, including
 * initializing the game view and listeners, resetting the game state, and handling the shutdown process.
 * Implementing classes must provide the game-specific logic for setting up the view,
 * processing user inputs, and managing the game's internal state.
 * </p>
 * @author Joel Lansgren
 */
interface IGameController {
    /**
     * Initializes the game view and sets up the necessary components for the game.
     * This method delivers the {@code closeGameClickListener} to the {@code initializeListeners} method and
     * displays the game view in the launcher when it is set up. It should also signal that all sounds have
     * been loaded (if used) before displaying the game. The implementation of this method will
     * vary depending on the specific game.
     * @param closeGameClickListener A callback that is invoked when the quit button is clicked in the game view,
     * ensuring proper shutdown and cleanup. Should be forwarded to the {@link #initializeListeners(Runnable)} method.
     * @param soundEffectsLoaded A string signaling to the {@link com.jman.gamelauncher.support.AudioManager} that all
     * sounds have been loaded (ignore it if sounds aren't a thing).
     * @param displayGameInLauncher A callback that is executed once the game view is created and set up to display it
     * in the launcher view. It takes the main view panel or start panel of the current game as its argument.
     */
    void initialize(
        final Runnable closeGameClickListener,
        final String soundEffectsLoaded,
        final Consumer<JPanel> displayGameInLauncher
    );

    /**
     * Initializes the event listeners for user interactions in the game.
     * It takes a closeGameClickListener callback that shall be attached to
     * the game's quit button as the last method in the lambda.
     *
     * <p>Example:
     * <pre>
     * startMenuView.addQuitBtnListener(() -> {
     *      closeGame();
     *      closeGameClickListener.run();
     * });
     * </pre></p>
     *
     * <p>This is because the closeGame method shall involve removing all listeners in the game, including
     * the ones from the IView's shared backBtn. And then in the closeGameClickListener callback it gets
     * re-added into the launcher.</p>
     *
     * @param closeGameClickListener A callback that is invoked when the quit button is clicked in the game view,
     * ensuring proper shutdown and cleanup.
     */
    void initializeListeners(Runnable closeGameClickListener);

    /**
     * Initiate the game, like displaying the start menu and prepping the game
     * state on start and return to the start menu.
     * */
    void initiateGameState();

    /** Starts the game. */
    void startGame();

    /**
     * Method that handles the proper shutdown of the game, including cleaning up resources,
     * stopping timers, and removing listeners.
     */
    void closeGame();

    /** Removes all listeners from the views. */
    void removeListeners();
}
