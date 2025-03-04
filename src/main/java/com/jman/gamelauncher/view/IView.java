package com.jman.gamelauncher.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.jman.gamelauncher.support.AppConfig;

/**
 * Base interface for view components in the MVC architecture of the application.
 * <p>
 * This interface defines methods that all views implementing this interface needs to implement.
 * It also holds a shared backBtn that views can use for navigation.
 * Finally it contains common functionalities for views, such as styling buttons and labels.
 * All view component in the application should implement this interface.
 * </p>
 * @author Joel Lansgren
 */
interface IView {
    /*===========================================
    * Contract Methods
    ===========================================*/

    /**
     * This method ensures all views expose a JPanel instead of extending the class itself.
     * This provides better encapsulation and by hiding implementation details of JPanel
     * and only exposing what's needed for interaction, the result is a cleaner API.
     * @return
     */
    JPanel getMainPanel();

    /*===========================================
    * backBtn Methods
    ===========================================*/

    /** Shared back button between all views that implement IView*/
    final JButton backBtn = new JButton("Back");

    default void setColorOnBackBtn(final Color color) {
        buttonStyler(backBtn, color);
    }

    /**
     * Adds a {@link ButtonListener} to the back button that enables navigating back to the targeted menu and perform
     * additional computations if necessary.
     * @param goBack a callback that will display the selected view, usually the view we just came from
     */
    default void addBackBtnListener(final Runnable goBack) {
        backBtn.addMouseListener(new ButtonListener(goBack, backBtn.getForeground()));
    }

    /**
     * Helper method that removes all listeners from the back button.
     * This is used before starting up or closing a game to make the backBtn available
     * for the game or launcher itself.
     */
    default void removeListenerFromBackBtn() {
        for (final MouseListener listener : backBtn.getMouseListeners()) {
            backBtn.removeMouseListener(listener);
        }
    }

    /*===========================================
    * Default Helper Methods
    ===========================================*/

    /**
     * Applies uniform styling to a label for consistency across the UI.
     *
     * <p>
     * This method sets the alignment, font, foreground color, and optional
     * alignment settings for the specified label. It ensures that the label
     * is styled according to the application's design requirements.
     * </p>
     *
     * @param label the JLabel to style
     * @param textSize the font size to be applied to the label
     * @param snakeText if true, centers the label's text both horizontally
     * and vertically; otherwise, keeps default alignment.
     */
    default void labelStyling(final JLabel label, final int textSize) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font(AppConfig.MONOSPACE, Font.BOLD, textSize));
        label.setForeground(AppConfig.COLOR_WHITE);
    }

    /**
     * Styles a button with specific color and border for uniformity.
     *
     * @param btn the JLabel to style
     * @param borderAndHoverBackgroundClr the color used for the label's foreground and background
     */
    default void buttonStyler(final JButton btn, final Color borderAndHoverBackgroundClr) {
        btn.setFont(new Font(AppConfig.MONOSPACE, Font.BOLD, AppConfig.TEXT_SIZE_NORMAL));
        btn.setForeground(borderAndHoverBackgroundClr);
        btn.setBackground(borderAndHoverBackgroundClr); // Used for hovering effect.
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        final Border matteBorder = BorderFactory.createMatteBorder(
            AppConfig.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfig.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfig.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfig.LABEL_BTN_OUTER_BORDER_WIDTH,
            borderAndHoverBackgroundClr
        );

        btn.setBorder(BorderFactory.createCompoundBorder(matteBorder, AppConfig.LABEL_BTN_INNER_SPACE));
    }

    /**
     * Method to reset the GridBagConstraints
     * @param currentGridy the current position in the y-axis
     * @param currentGridx the current position in the x-axis
     */
    default GridBagConstraints resetGbc(final int currentGridy, final int currentGridx) {
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = currentGridy;  // Set current y position
        gbc.gridx = currentGridx;  // Set current x position
        gbc.gridwidth = 1;  // 1 column span
        gbc.gridheight = 1;  // 1 row span
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = AppConfig.RESET_INSETS;

        return gbc;
    }
}
