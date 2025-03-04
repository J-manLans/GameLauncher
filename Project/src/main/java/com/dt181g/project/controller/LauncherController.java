package com.dt181g.project.controller;

import java.awt.Toolkit;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.SwingUtilities;

import com.dt181g.project.initialization.GameLauncherInitializer;
import com.dt181g.project.model.LauncherModel;
import com.dt181g.project.support.AppConfig;
import com.dt181g.project.support.AppConfigLauncher;
import com.dt181g.project.support.AudioManager;
import com.dt181g.project.support.TimedEventQueue;
import com.dt181g.project.view.LauncherAboutView;
import com.dt181g.project.view.LauncherComingSoonView;
import com.dt181g.project.view.LauncherFrame;
import com.dt181g.project.view.LauncherMainView;
import com.dt181g.project.view.LauncherSideView;
import com.dt181g.project.view.LauncherStartView;

/**
 * The controller class for managing the game launcher.
 *
 * <p>This class acts as the intermediary between the different views and
 * {@link LauncherModel}, coordinating interactions and ensuring that user
 * actions in the view trigger the appropriate responses in the model.</p>
 *
 * @author Joel lansgren
 */
public class LauncherController {
    private final LauncherModel launcherModel;
    private final LauncherSideView sideView;
    private final LauncherMainView mainView;
    private final LauncherStartView startView;
    private final LauncherAboutView aboutView;
    private final LauncherComingSoonView comingSoonView;
    private final LauncherFrame launcherFrame;

    /**
     * Constructs a new GameLauncherController with the specified model and views.
     * Each view with their individual setup is performed on the EDT inside their constructors.
     */
    public LauncherController() {
        launcherModel = new LauncherModel();
        sideView = new LauncherSideView();
        mainView = new LauncherMainView();
        startView = new LauncherStartView();
        aboutView = new LauncherAboutView();
        comingSoonView = new LauncherComingSoonView();
        launcherFrame = new LauncherFrame(sideView.getMainPanel(), mainView.getMainPanel());
    }

    /**
     * This method initializes the game launcher.
     *
     * <p>It does so by adding game buttons to the launcherSideView, setting up the launcherMainView
     * views in its card layout and initializing listeners.
     * It also displays the launcher and may include debugging logic if enabled.</p>
     */
    public void initialize() {
        if (AppConfigLauncher.DEBUG_MODE) {
            Toolkit.getDefaultToolkit().getSystemEventQueue().push(new TimedEventQueue());
        }

        SwingUtilities.invokeLater(() -> {
            addGameBtnsToSideView();
            mainView.setViews(startView.getMainPanel(), aboutView.getMainPanel(), comingSoonView.getMainPanel());
            initializeListeners();
            launcherFrame.showFrame();
        });

        AudioManager.INSTANCE.keepAudioAlive();
    }

    /**
     * Helper method that sets up the game buttons in the launcherSideView.
     *
     * <p>It loops over the game titles stored in the model and add a button
     * for each title with a button listener. In addition to that it adds a spacer below each
     * button except the last one.</p>
     */
    private void addGameBtnsToSideView() {
        final List<String> titles = launcherModel.getTitleList();
        boolean addSpacer = true;

        for (int i = 0; i < titles.size(); i++) {
            final String title = titles.get(i);

            // Don't add spacer if it's the last game
            if (i == titles.size() -1) {
                addSpacer = false;
            }

            sideView.addGameBtn(
                title,
                addSpacer,
                () -> createGame(title)
            );
        }
    }

    /** Initializes the views listeners. */
    private void initializeListeners() {
        sideView.addScrollSpeedListener(this::handleScrollSpeed);

        sideView.addExitBtnListener(this::exitLauncher);

        startView.addAboutBtnListener(() -> {
                aboutView.setBackBtn();
                mainView.ShowAboutPanel();
            }
        );

        startView.addBackBtnListener(mainView::ShowStartView);
    }

    /**
     * Adjusts the scroll position based on wheel rotation, scaled by the speed multiplier.
     *
     * @param wheelRotation either up (-1) or down (1) based on scroll direction
     */
    private void handleScrollSpeed(final int wheelRotation) {
        sideView.setScrollPosition(
            sideView.getScrollPosition() + (wheelRotation * AppConfigLauncher.SCROLL_SPEED_MULTIPLIER)
        );
    }

    /**
     * Exits the game launcher application.
     *
     * <p>This method terminates the application by calling the
     * {@link GameLauncherInitializer#INSTANCE.countDown()} method of the
     * GameLauncherInitializer singleton which in turn count down its latch
     * which releases the main thread to call System.exit(0).</p>
     */
    private void exitLauncher() {
        GameLauncherInitializer.INSTANCE.countDown();
    }

    /*=========================================================
    * Methods used by the game Buttons through their listener
    =========================================================*/

    /**
     * Starts the game clicked in the games list in the launcher and sets it to active.
     *
     * <p>Whenever new games are added they need a switch case in this method.</p>
     * @param gameTitle the clicked games title.
     */
    private void createGame(final String gameTitle) {
        switch (gameTitle) {
            case AppConfig.SNAKE_TITLE -> {
                instantiateGameController(
                    SnakeController::new,
                    this::closeGame
                );
            } default -> {
                // By default, if the game isn't fully implemented yet
                // a panel informing the user will show in its place.
                comingSoonView.setBackBtn();
                mainView.ShowComingSoonGamePanel();
            }
        }
    }

    /**
     * Instantiates a game controller and sets up necessary callbacks for integration with the launcher.
     *
     * <p> This method creates a new game controller using the provided factory and configures callbacks
     * for displaying the game in the launcher and handling its closure. The back button is shared
     * between the game views and the launcher view, so the listener is removed temporarily while
     * the game is active, allowing the game to handle its own back button behavior. Once the game
     * is closed, the listener will be restored for the launcher view.</p>
     *
     * @param gameControllerFactory A functional interface used to create the game controller.
     * @param closeGameClickListener A callback that is invoked when the quit button is clicked in the game view,
     * ensuring proper shutdown and cleanup.
     */
    private void instantiateGameController(
        final Supplier<IGameController> gameControllerFactory,
        final Runnable closeGameClickListener
    ) {
        // Removes back button listener as the game view handles its own back button behavior
        // and removes the games list from view.
        mainView.removeListenerFromBackBtn();
        sideView.hidePanel();

        gameControllerFactory.get().initialize(
            closeGameClickListener,
            AppConfig.SOUND_EFFECTS_LOADED,
            mainView::ShowSelectedGame
        );
    }

    /**
     * Closes the currently active game and resets its state.
     *
     * <p>This method marks the game as inactive in the model and updates the UI. Since the game
     * and launcher share the same back button, the back button listener for returning to
     * the launcher is re-added after the game is closed.</p>
     */
    private void closeGame() {
        // Re-adds back button listener for returning to the launcher and resets the color
        mainView.setColorOnBackBtn();
        mainView.addBackBtnListener(mainView::ShowStartView);
        // And displays the startView
        mainView.ShowStartView();
        sideView.showPanel();

        AudioManager.INSTANCE.shutdownAudio();
    }
}
