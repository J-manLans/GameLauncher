package com.dt181g.project.model;

import java.awt.Color;
import java.awt.Point;
import java.util.function.Consumer;

/**
 * Represents an abstract booster in the Snake game.
 * A booster is a background thread that waits to be activated,
 * applies an effect when consumed, and then returns itself to the booster pool.
 *
 * <p>The booster remains in a waiting state until activated. Once activated,
 * it waits to be consumed, applies its effect, plays a sound, and returns to the pool.</p>
 *
 * <p>This class follows a template method pattern where concrete subclasses
 * define the booster-specific effect and sound in the {@link #run()}
 * method which serves as the template.</p>
 *
 * @author Joel Lansgren
 */
public abstract class BoosterModel extends Thread {
    private final Point position = new Point();
    private Consumer<BoosterModel> returnBooster;
    private boolean isActive;
    private boolean isPause;
    private boolean running = true;

    /**
     * Constructs a new booster with the specified thread name and starts it immediately.
     * @param name the name of the booster thread.
     */
    BoosterModel(final String name) {
        super.setName(name);
        super.start();
    }

    /*==============================
    * Getters
    ==============================*/

    /**
     * Checks if the booster is currently active in the game.
     * @return {@code true} if the booster is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Gets the position of the booster on the game grid.
     * @return the current position of the booster.
     */
    public Point getPosition() {
        return new Point(position);
    }

    /*==============================
    * Setters
    ==============================*/

    /**
     * Sets the return mechanism for the booster. This allows the booster to
     * return itself to the booster pool after being consumed.
     * @param returnBooster a {@link Consumer} that handles returning the booster to the pool.
     */
    public void setReturnBoosterMethod(final Consumer<BoosterModel> returnBooster) {
        this.returnBooster = returnBooster;
    }

    /*==============================
    * Abstract methods
    ==============================*/

    /**
     * Defines the effect applied when the booster is consumed.
     */
    abstract void applyEffect();

    /**
     * Preloads any necessary sound effects for the booster.
     */
    abstract void loadSoundEffect();

    /**
     * Plays the booster-specific sound effect when consumed.
     */
    abstract void playSoundEffect();

    /**
     * Gets the color representation of the booster, used by the view when rendering.
     * @return the {@link Color} of the booster.
     */
    public abstract Color getBoosterColor();

    /*==============================
    * Notify!
    ==============================*/

    /**
     * Activates the booster by setting its position and waking up the thread.
     * This allows it to enter the active state and wait to be consumed.
     * @param newPosition the position in the grid where the booster should appear.
     */
    synchronized public void activate(final Point newPosition) {
        position.setLocation(newPosition);
        isActive = true;
        notify(); // Wake up the thread to let it advance to the active wait state
    }

    /**
     * Consumes the booster, let it trigger its effect and return itself to the pool if the game is not paused.
     * This makes sure its available for reactivation by the booster controller.
     * If the game is paused, the effect is skipped.
     * @param isPause whether the game is currently paused.
     */
    synchronized public void consume(final boolean isPause) {
        this.isPause = isPause;
        isActive = false;
        notify(); // Wake up the thread to let it do it's task
    }

    /**
     * Signals the thread to shut down gracefully.
     * The booster will exit its loop and stop execution.
     */
    synchronized public void shutdown() {
        running = false;
        isActive = !isActive;
        notify(); // Wake up the thread to exit
    }

    /*==============================
    * Run!
    ==============================*/

    /**
     * The main execution loop for the booster thread.
     * The thread waits in an inactive state until activated, then waits
     * to be consumed. When consumed, it applies its effect and returns
     * itself to the pool before repeating the cycle.
     */
    @Override
    public final void run() {
        while (running) {
            synchronized (this) {
                while (!isActive && running) {
                    try {
                        wait(); // Wait until activated or shutdown
                    } catch (final InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                if (running) {
                    while (isActive && running) {
                        try {
                            wait(); // Wait until consumed
                        } catch (final InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    if (!isPause) {
                        applyEffect();
                        playSoundEffect();
                    }

                    returnBooster.accept(this);
                }
            }
        }
    }
}
