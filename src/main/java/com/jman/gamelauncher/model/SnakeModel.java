package com.jman.gamelauncher.model;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.jman.gamelauncher.support.AppConfigSnake;
import com.jman.gamelauncher.support.BoosterPool;

/**
 * The SnakeModel class represents the model for the Snake game and maintains the state of the game data.
 *
 * <p>This class implements the {@link ISnakeBoosterTarget} interface and mainly provides
 * methods to initialize, control, update the snake and check for collisions either with boosters
 * or itself. Other than that it provides methods to apply booster effects.</p>
 * @author Joel Lansgren
 */
public class SnakeModel implements ISnakeBoosterTarget {
    private final Consumer<Integer> notifySpeedHasChanged;
    private final int initialLength = AppConfigSnake.INITIAL_LENGTH;
    private final int gridSize = AppConfigSnake.CELL_COUNT;
    private final Deque<Point> snake = new ArrayDeque<>();
    private Direction direction;
    private Direction pendingDirection;
    private boolean gameOver = true;
    private final AtomicBoolean speedBoosterEffect = new AtomicBoolean();
    private boolean grow;
    private ScheduledFuture<?> speedResetTask;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        return new Thread(r, "SpeedReset-Scheduler");
    });
    private final AtomicInteger speed = new AtomicInteger();
    private final AtomicInteger oldSpeed = new AtomicInteger();
    private final AtomicInteger newSpeed = new AtomicInteger();

    /**
     * Constructs the SnakeModel and stores the callback to notify when speed have changed.
     */
    public SnakeModel(final Consumer<Integer> notifySpeedHasChanged) {
        this.notifySpeedHasChanged = notifySpeedHasChanged;
    }

    /*==============================
    * Game Methods
    ==============================*/

    /**
     * Initializes the snake's starting position on the game grid.
     * It also set default values to variables used during the game.
     * @param updateGameGrid a callback that updates the views game grid with the snake
     */
    public void initializeSnakeData(final Runnable updateGameGrid) {
        // Clear the snake in case of a restart of the game so we have a clean slate.
        snake.clear();

        // Initialize the snake's tail position.
        final int startX = gridSize / 2 - (initialLength / 2);
        final int startY = gridSize / 2;

        snake.add(new Point(startX, startY)); // Add the tail position.

        // Builds the body, with the y-coordinate staying the same and x increasing by 1.
        for (int i = 1; i < initialLength; i++) {
            snake.add(new Point(startX + i, startY));
        }

        // Defaults the directions to right.
        direction = Direction.RIGHT;
        pendingDirection = Direction.RIGHT;
        gameOver = false; // Defaults the game over state to false.
        setSpeed(AppConfigSnake.SNAKE_TICK_DELAY); // Reset the speed of the snake.
        oldSpeed.set(AppConfigSnake.SNAKE_TICK_DELAY); // Reset the speed of the snake.
        newSpeed.set(AppConfigSnake.SNAKE_TICK_DELAY); // Reset the speed of the snake.

        updateGameGrid.run(); // Update the grid with the snake.
    }

    /**
     * Method to update the position of the snake by shifting its body parts.
     *
     * <p>The first step is to ensure that the pending direction isn't the
     * opposite of the old direction. This is done by using the enum's ordinal
     * method which returns the position of the constant.</p>
     *
     * <p>This method also checks for collision before adding the new head based
     * on the current one, wrapping around if necessary. If collision is detected
     * proper action is taken based on what's being collided with. If all goes well
     * the tail is removed if a cherry have not been eaten.</p>
     *
     * @param updateGameGrid a callback that updates the views game grid with the snake
     */
    public void updateSnakeData(final Runnable updateGameGrid) {
        final Point head = snake.peekLast();
        int newX = head.x;
        int newY = head.y;

        if (direction.ordinal() + pendingDirection.ordinal() != 3) {
            direction = pendingDirection;
        }

        switch (direction) {
            case RIGHT -> newX = (head.x != gridSize - 1) ? head.x + 1 : 0;
            case DOWN -> newY = (head.y != gridSize - 1) ? head.y + 1 : 0;
            case LEFT -> newX = (head.x != 0) ? head.x - 1 : gridSize - 1;
            case UP -> newY = (head.y != 0) ? head.y - 1 : gridSize - 1;
        }

        // Creates the new head so we can check for collision and add it to
        // the snake if all goes well.
        final Point newHead = new Point(newX, newY);

        checkCollision(newHead); // Check if we hit ourselves or stumble upon a booster.

        snake.addLast(newHead); // Adds the new head.

        if (grow) {
            grow = false; // Reset the boolean if a cherry was eaten so the snake don't continue to grow.
        } else {
            snake.removeFirst(); // Removes the tail if a cherry wasn't eaten.
        }

        updateGameGrid.run();
    }

    /**
     * Checks the new head to determine if the game is over or if a booster will be consumed.
     * @param newHead The new position of the snake's head that is being checked for collisions.
     */
    private void checkCollision(final Point newHead) {
        if (snake.contains(newHead)) {
            gameOver = true;
        } else {
            for (final Point p : BoosterPool.INSTANCE.getActiveBoosters().keySet()) {
                if (p.equals(newHead)) {
                    // Consume and add the booster to the placeholder
                    BoosterPool.INSTANCE.getActiveBoosters().get(p).accept(false);
                    break;
                }
            }
        }
    }

    /*==============================
    * Getters
    ==============================*/

    @Override
    public List<Point> getSnake() {
        // Since this can be modified both by the EDT and the boosterManagers Executor we sync it.
        synchronized (snake) {
            return List.copyOf(snake);
        }
    }

    /**
     * Checks if the game is over.
     * @return A boolean indicating if the game is over.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /*====================
    * Setters
    ===================*/

    /**
     * Sets the pending direction of the snake.
     * @param direction The new direction for the snake
     */
    public void setDirection(final Direction direction) {
        pendingDirection = direction;
    }

    @Override
    public void setGrow(final boolean isGrow) {
        grow = isGrow;
    }

    @Override
    public void increaseSpeed(final double speedMultiplier) {
        if (speedBoosterEffect.get()) {
            // If the speedBoosterEffect is active cherries gets a higher multiplier
            // to account for the lower effect otherwise...
            setSpeed((int) Math.max(50, speed.get() * (speedMultiplier - 0.1)));
        } else {
            // ...the normal multiplier are used.
            setSpeed((int) Math.max(50, speed.get() * speedMultiplier));
        }
    }

    @Override
    public void boostSpeed(final double speedMultiplier) {
        if (!speedBoosterEffect.get()) {
            oldSpeed.set(speed.get());
            newSpeed.set((int) Math.max(50, oldSpeed.get() * speedMultiplier));
            setSpeed(newSpeed.get());
            speedBoosterEffect.set(true);;

            // Schedule reset after 7 seconds
            speedResetTask = scheduler.schedule(() -> {
                setSpeed(oldSpeed.get() - (newSpeed.get() - speed.get()));
                speedBoosterEffect.set(false);
            }, 7, TimeUnit.SECONDS);
        }
    }

    /**
     * Cancels the speed boost effect if it is currently active.
     * This method checks if there is a pending speed reset task and cancels it if it is not already
     * completed. It also sets the {@code speedBoosterEffect} flag to {@code false}, indicating that
     * the speed boost is no longer active.
     */
    public void cancelSpeedBoost() {
        if (speedResetTask != null && !speedResetTask.isDone()) {
            speedResetTask.cancel(false);
        }
        speedBoosterEffect.set(false);
    }

    /**
     * Helper method to set the game tick speed.
     * @param speed the speed to set.
     */
    private void setSpeed(final int speed) {
        this.speed.set(speed);
        notifySpeedHasChanged.accept(this.speed.get());
    }

    /*===============================
    * Cleanup
    ===============================*/

    /**
     * Shuts down the scheduler when we exit the game.
     */
    public void shutdownScheduler() {
        scheduler.shutdownNow();
        scheduler = null;
    }
}
