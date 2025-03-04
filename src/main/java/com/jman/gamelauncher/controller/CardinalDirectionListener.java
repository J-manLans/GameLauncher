package com.jman.gamelauncher.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import com.jman.gamelauncher.model.Direction;


/**
 * Listens for keyboard input to control the movement in the game.
 *
 * <p>It updates the direction based on key presses for movement
 * in all four cardinal directions.</p>
 *
 * @author Joel Lansgren
 */
public class CardinalDirectionListener extends KeyAdapter {
    private final Consumer<Direction> setDirection;

    /**
     * Constructs a MovementListener for games.
     *
     * @param setDirection The callback to the method in the model that sets the direction.
     */
    public CardinalDirectionListener(final Consumer<Direction> setDirection) {
        this.setDirection = setDirection;
    }

    /**
     * Invoked when a key is pressed. Updates the direction based on the
     * key code of the pressed key by evoking the callback.
     *
     * @param e The KeyEvent containing information about the key press.
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> setDirection.accept(Direction.UP);
            case KeyEvent.VK_S -> setDirection.accept(Direction.DOWN);
            case KeyEvent.VK_A -> setDirection.accept(Direction.LEFT);
            case KeyEvent.VK_D -> setDirection.accept(Direction.RIGHT);
            case KeyEvent.VK_UP -> setDirection.accept(Direction.UP);
            case KeyEvent.VK_DOWN -> setDirection.accept(Direction.DOWN);
            case KeyEvent.VK_LEFT -> setDirection.accept(Direction.LEFT);
            case KeyEvent.VK_RIGHT -> setDirection.accept(Direction.RIGHT);
        }
    }
}

