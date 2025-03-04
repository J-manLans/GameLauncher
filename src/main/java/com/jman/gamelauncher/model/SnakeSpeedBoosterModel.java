package com.jman.gamelauncher.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import com.jman.gamelauncher.support.AppConfig;
import com.jman.gamelauncher.support.AppConfigSnake;
import com.jman.gamelauncher.support.AudioManager;

/**
 * Represents a speed booster in the game, which temporarily boosts the snake's speed when consumed.
 * This class extends {@link BoosterModel} and defines the specific behavior of a speed booster.
 *
 * <p>Upon activation, we make sure its new position isn't occupied by the snake.
 * When consumed, it triggers the {@code boostSpeed()} method on {@link ISnakeBoosterTarget}.</p>
 *
 * <p>The booster also manages its own sound effect, which is preloaded on creation
 * and played upon consumption.</p>
 *
 * @author Joel Lansgren
 */
public final class SnakeSpeedBoosterModel extends BoosterModel {
    private final ISnakeBoosterTarget snakeTarget;
    private final Color color = AppConfigSnake.COLOR_SPEED_BOOSTER;
    private final String soundEffect = AppConfigSnake.SOUND_EFFECT_SPEED;
    private Random randomizer = new Random();

    /**
     * Creates a new SpeedBooster instance and preload its sound effect.
     * @param snakeTarget The target snake that will interact with the booster.
     */
    public SnakeSpeedBoosterModel(final ISnakeBoosterTarget snakeTarget) {
        super("SpeedBooster");
        this.snakeTarget = snakeTarget;
        loadSoundEffect();
    }

    /**
     * {@inheritDoc}
     *
     * If the new booster position is occupied by the snake we do nothing.
     * @param newPosition The position where the booster should appear.
     */
    @Override
    public void activate(final Point newPosition) {
        if (snakeTarget.getSnake().contains(newPosition)) { return; }
        super.activate(newPosition);
    }

    @Override
    void spawnCoolDown() {
        try {
            Thread.sleep(randomizer.nextLong(AppConfig.BOOSTER_SPAWN_MIN_DELAY, AppConfig.BOOSTER_SPAWN_MAX_DELAY));
        } catch (final InterruptedException e) {
            System.out.println("interrupted");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Applies the effect of the speed booster, temporarily boosting its speed.
     */
    @Override
    void applyEffect() {
        snakeTarget.boostSpeed(0.5);
    }

    @Override
    void loadSoundEffect() {
        AudioManager.INSTANCE.queueSoundEffect(soundEffect);
    }

    @Override
    void playSoundEffect() {
        AudioManager.INSTANCE.playSoundEffect(soundEffect);
    }

    @Override
    public Color getBoosterColor() {
        return new Color(color.getRGB());
    }
}
