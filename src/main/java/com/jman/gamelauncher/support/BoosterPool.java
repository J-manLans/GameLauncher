package com.jman.gamelauncher.support;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.jman.gamelauncher.model.BoosterModel;

/**
 * A singleton object pool for managing the lifecycle of {@link BoosterModel}.
 * This class controls the activation and recycling of boosters used throughout different games.
 * It prevents duplicate booster positions and ensures proper synchronization.
 *
 * <p>This is implemented as an enum singleton to guarantee thread safety.</p>
 *
 * @author Joel Lansgren
 */
public enum BoosterPool {
    INSTANCE;
    private final Object boosterLock = new Object();
    private final List<BoosterModel> boosterPool = new ArrayList<>();
    private final List<BoosterModel> boosters = new ArrayList<>();
    private final List<Point> boosterPositions = new ArrayList<>();
    private final Random randomizer = new Random();
    private ScheduledFuture<?> scheduledBoosterTask;
    private ScheduledExecutorService  scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        return new Thread(r, "BoosterActivator-Scheduler");
    });
    private boolean isPaused = true;
    private int x;
    private int y;
    private final Runnable periodicBoosterSpawner = () -> {
        if (!isPaused) {
            x = randomizer.nextInt(AppConfigSnake.CELL_COUNT);
            y = randomizer.nextInt(AppConfigSnake.CELL_COUNT);
            Point newBoosterPosition = new Point(x, y);

            if (boosterPositions.contains(newBoosterPosition)) { return; }

            displayBooster(newBoosterPosition);
        }
    };

    /**
     * Initializes the booster pool with available boosters and sets a return method so they
     * can return themselves to the pool. It also add the boosters to another booster list
     * that will be used to deal provide active stats during runtime. After that it starts a
     * method to schedule booster activation with a random delay.
     * This method should only be called once per game instantiation.
     * @param boosters The list of booster instances to be managed.
     */
    public void initialize(final List<BoosterModel> boosters) {
        for (final BoosterModel booster : boosters) {
            booster.setReturnBoosterMethod(this::returnBooster);
            boosterPool.add(booster);
            this.boosters.add(booster);
        }

        scheduleBoosterActivationWithRandomDelay();
    }

    /**
     * Schedules booster activation with a random delay. This method ensures that
     * the boosters get new positions periodically added while the game is running.
     */
    private void scheduleBoosterActivationWithRandomDelay() {
        scheduledBoosterTask = scheduler.scheduleAtFixedRate(
            periodicBoosterSpawner,
            0,
            AppConfig.BOOSTER_SPAWN_MIN_DELAY,
            TimeUnit.MILLISECONDS
        );
    }

    /**
     * Retrieves an available booster and activates it at the given position.
     * Blocks if no booster is available until one is returned to the pool.
     * The method ensures no duplicate positions.
     */
    public void displayBooster(Point newBoosterPosition) {
        while (true) { // Wait until boosterPool is not empty.
            synchronized (boosterLock) {
                if (!boosterPool.isEmpty()) { break; }
                try { boosterLock.wait(); }
                catch (final InterruptedException e) { Thread.currentThread().interrupt(); }
            }

            if (isPaused) { return; } // If we are in pause mode, do nothing.
        }

        synchronized (boosterLock) {
            // Recheck pause after sleeping and possible wait time at the lock and check if the pool is empty
            if (isPaused || boosterPool.isEmpty()) { return; }
            final BoosterModel booster = boosterPool.remove(randomizer.nextInt(boosterPool.size()));
            booster.activate(newBoosterPosition);
            // We add the position to the list so we can double-check above if we
            // happen to get a booster with a position that already's present.
            boosterPositions.add(newBoosterPosition);
        }
    }

    /*==============================
    * Notify!
    ==============================*/

    /**
     * Returns a booster to the pool after consumption.
     *
     * <p>The boosters position is freed and any waiting threads are notified.</p>
     * @param booster The booster being returned.
     */
    private void returnBooster(final BoosterModel booster) {
        synchronized (boosterLock) {
            boosterPool.add(booster);
            boosterPositions.remove(booster.getPosition());
            boosterLock.notify();
        }
    }

    /*==============================
    * Setters
    ==============================*/

    /**
     * Toggles pause state. When paused, active boosters will be set to rest without
     * applying their effects. Paused boosters will just remain where they are.
     * @param isPaused Whether the boosters should be paused.
     */
    public void setPaused(final boolean isPaused) {
        this.isPaused = isPaused;
        boosters.stream()
        .filter(BoosterModel::isActive)
        .forEach(booster -> booster.consume(isPaused));
    }

    /**
     * Resets the booster activation by canceling the current scheduled task (if not already completed)
     * and rescheduling it with a new random delay. If the task is currently sleeping or waiting,
     * it will be interrupted and canceled.
     */
    public void resetBoosterActivation() {
        if (!scheduledBoosterTask.isDone()) {
            scheduledBoosterTask.cancel(true); // Interrupt the task
        }
        scheduleBoosterActivationWithRandomDelay(); // Reschedule the task
    }

    /*==============================
    * Getters
    ==============================*/

    /**
     * Retrieves the positions and colors of all active boosters.
     *
     * <p>It returns a map where the keys are the positions of the active boosters, and the values are
     * the corresponding colors of the boosters.</p>
     * @return A map where each entry contains the position of an active booster as the key and
     * the corresponding booster color as the value.
     */
    public Map<Point, Color> getActiveBoosterData() {
        return boosters.stream()
        .filter(BoosterModel::isActive)
        .collect(Collectors.toMap(
            BoosterModel::getPosition,
            BoosterModel::getBoosterColor
        ));
    }

    /**
     * Retrieves a map of all active boosters with their positions and consume methods.
     *
     * <p>It returns a map where the keys are the positions of active boosters, and the values are
     * the associated methods (consuming the boosters) represented as Consumers.</p>
     * @return A map containing the positions of active boosters and their corresponding consume methods.
     */
    public Map<Point, Consumer<Boolean>> getActiveBoosters() {
        return boosters.stream()
        .filter(BoosterModel::isActive)
        .collect(Collectors.toMap(
            BoosterModel::getPosition,
            booster -> booster::consume
        ));
    }

    /*==============================
    * Shutdown
    ==============================*/

    /**
     * Shuts down the booster pool, deactivating all boosters and clean up resources.
     */
    public void shutdown() {
        synchronized (boosterLock) {
            isPaused = true;
            boosterPool.forEach(BoosterModel::shutdown);
            boosters.forEach(BoosterModel::shutdown);
            boosterPool.clear();
            boosters.clear();
            boosterPositions.clear();
        }
    }
}

