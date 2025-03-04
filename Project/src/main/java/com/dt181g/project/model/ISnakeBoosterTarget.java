package com.dt181g.project.model;

import java.awt.Point;
import java.util.List;

/**
 * This interface is implemented by the entity that are to be the target of boosters - which is the {@link SnakeModel}.
 *
 * <p>It defines a contract with methods that allow boosters to interact with the target without
 * exposing its complete internal state.</p>
 *
 * <p>The implementing class should provide access to the snake, so boosters don't spawn on it,
 * and the methods used by the boosters. Additional methods needs to be added when new booster
 * types are added.</p>
 *
 * @author Joel Lansgren
 */
public interface ISnakeBoosterTarget {

    /**
     * Returns the snake as an unmodifiable list for overlaying on the views grid.
     * @return the snake
     */
    List<Point> getSnake();

    /*=======================
    * CherryBooster
    =======================*/

    /**
     * Sets the grow boolean to true, resulting in the tail not being removed.
     * <p>Used by the cherry booster.</p>
     */
    void setGrow(boolean isGrow);

    /**
     * If the speedBoosterEffect isn't active, the speed gets increased with the multiplier.
     * If it is, the multiplier is increased to take the lower effect into account.
     * Used by the cherry booster.
     * @param speedMultiplier The multiplier that will be used to calculate the
     * new speed.
     */
    void increaseSpeed(double speedMultiplier);

    /*=======================
    * SpeedBooster
    =======================*/

    /**
     * Boosts the speed of the snake.
     *
     * <p>This method is to used by the speed booster. If the booster effect isn't
     * active it goes ahead and doubles the speed, set a boolean to signal it's
     * effect is activated, then starts a scheduler that will reset the speed
     * after 7 seconds, taking any speed increases set by the cherry into
     * account.</p>
     * @param speedMultiplier The multiplier that will be used to calculate the
     * new speed.
     */
    void boostSpeed(double speedMultiplier);
}
