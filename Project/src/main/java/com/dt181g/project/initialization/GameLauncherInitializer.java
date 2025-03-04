package com.dt181g.project.initialization;

import java.util.concurrent.CountDownLatch;

import com.dt181g.project.controller.LauncherController;

/**
 * Singleton for initializing and running the game launcher.
 * This class manages the creation and coordination of the view, model,
 * and controller for the game launcher using the singleton pattern.
 * <p>
 * It uses an enum-based singleton implementation to ensure that only one
 * instance of the launcher is created during the application's lifecycle.
 * </p>
 * <p>
 * This class is thread-safe because the JVM handles the creation of the
 * enum instance and guarantees its uniqueness.
 * </p>
 * @author Joel Lansgren
 */
public enum GameLauncherInitializer {
    INSTANCE;
    private LauncherController launcherController;
    private final CountDownLatch latch = new CountDownLatch(1);

    /**
     * This method creates an instance of the LauncherController if it has not already been instantiated.
     * The package private status ensures only test classes have access to it, enabling isolated tests to
     * ensure only one controller instance can be created.
     */
    LauncherController createAndReturnLauncherController() {
        if (launcherController == null) {
            launcherController = new LauncherController();
        }
        return launcherController;
    }

    /**
     * Initializes and runs the game launcher.
     *
     * <p>The method then waits until {@link #countDown()} is called, allowing the
     * main thread to stay awake until explicitly shut down. This is mainly for
     * adding an extra concurrent process with synchronization to the project.</p>
     */
    public void runLauncher() {
        createAndReturnLauncherController().initialize();

        try {
            latch.await();
            System.exit(0);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * Signal from the launcher to shut down.
     *
     * <p>Calling this method decreases the latch count, allowing {@link #runLauncher()}
     * to complete and initiate the application shutdown.</p>
     */
    public void countDown() {
        this.latch.countDown();
    }
}
