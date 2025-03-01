package com.dt181g.laboration_3.factories;

import com.dt181g.laboration_3.view.games.common.IGameView;


/**
 * A factory interface for creating instances of {@link IGameView}.
 * <p>
 * This interface defines a factory method for creating views
 * associated with a specific title.
 * </p>
 */
@FunctionalInterface
public interface GameViewFactory {
    /**
     * Creates a new instance of {@link IGameView} with the specified title.
     *
     * @param title the title of the game, used to identify the view.
     * @return A new instance of {@link IGameView}.
     */
    IGameView create(final String title);
}
