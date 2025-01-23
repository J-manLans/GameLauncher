package com.dt181g.laboration_3.controller.common;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.dt181g.laboration_3.support.AppConfigLab3;

/**
 * Listener for menu buttons that provides visual feedback and triggers actions on mouse events.
 * <p>
 * This class extends {@link MouseAdapter} to handle mouse interactions with a {@link JLabel}
 * representing a button. It updates the button's appearance on hover and executes a specified
 * action when the button is clicked.
 * </p>
 *
 * @author Joel Lansgren
 */
public class MenuButtonListener extends MouseAdapter {
    private final JLabel button;
    private final Runnable action;
    private Color orgColor;

    /**
     * Constructs a MenuButtonListener for the specified button with the associated action to execute.
     *
     * @param button the JLabel representing the button
     * @param action the action to execute when the button is clicked
     */
    public MenuButtonListener(final JLabel button, final Runnable action) {
        this.button = button;
        this.action = action;
        // This ensures the right color always are added to orgColor
        // if the listener isn't added with the EventQueue thread
        SwingUtilities.invokeLater(() -> {
            orgColor = button.getForeground();
        });
    }

    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param isHovered true if the button is hovered, false otherwise
     */
    public void updateButtonAppearance(final boolean isHovered) {
        button.setOpaque(isHovered);
        button.setForeground(isHovered ? AppConfigLab3.COLOR_DARKER_GREY : orgColor);
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        // Makes sure the label button is transparent and has
        // the right text color on the next startup.
        button.setOpaque(false);
        button.setForeground(orgColor);
        action.run();
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
        updateButtonAppearance(true);
    }

    @Override
    public void mouseExited(final MouseEvent e) {
        updateButtonAppearance(false);
    }

    /**
     * Returns the action
     * @return the runnable action
     */
    protected Runnable getAction() {
        return this.action;
    }
}
