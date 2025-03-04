package com.jman.gamelauncher.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.jman.gamelauncher.support.AppConfig;

/**
 * Listener for launcher buttons that provides visual feedback on hover events and executes a runnable on mouseReleased.
 * <p>
 * This class extends {@link MouseAdapter} to handle mouse interactions with a {@link JButton}.
 * It updates the button's appearance on hover and executes a runnable on
 * {@link #mouseReleased}.
 * </p>
 * @author Joel Lansgren
 */
class ButtonListener extends MouseAdapter {
    private final Color orgForegroundClr;
    private final Runnable clickAction;


    ButtonListener(final Runnable clickAction, final Color orgForegroundClr) {
        this.clickAction = clickAction;
        this.orgForegroundClr = orgForegroundClr;
    }

    /**
     * Helper method to update the appearance of the specified button based on hover state.
     *
     * @param isHovered true if the button is hovered, false otherwise
     */
    void updateButtonAppearance(final JButton btn, final boolean isHovered) {
        btn.setOpaque(isHovered);  // Will add the buttons background color
        btn.setForeground(isHovered ? AppConfig.COLOR_DARKER_GREY : orgForegroundClr);
    }

    /**
     * {@inheritDoc}
     * <p> Updates the appearance of the button that gets hovered. </p>
     */
    @Override
    public void mouseEntered(final MouseEvent e) {
        updateButtonAppearance((JButton) e.getSource(), true);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The buttons appearance will be reset in {@link #updateButtonAppearance} with the help of the stored original
     * foreground color.
     * </p>
     */
    @Override
    public void mouseExited(final MouseEvent e) {
        updateButtonAppearance((JButton) e.getSource(), false);
    }

    /**
     * {@inheritDoc}
     * <p> Starts the stored callback. </p>
     */
    @Override
    public void mouseReleased(final MouseEvent e) {
        clickAction.run();
    }
}
