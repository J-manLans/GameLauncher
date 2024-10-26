package com.dt181g.project.factories;

import com.dt181g.project.mvccomponents.BaseView;
import com.dt181g.project.mvccomponents.games.GameView;

/**
 * A factory interface for creating instances of {@link GameView}.
 * <p>
 * This interface defines a factory method for creating views
 * associated with a specific title.
 * </p>
 */
@FunctionalInterface
public interface GameViewFactory <T extends BaseView>{
    /**
     * Creates a new instance of {@link GameView} with the specified title.
     *
     * @param title the title of the game, used to identify the view.
     * @return A new instance of {@link GameView}.
     */
    T create();
}
