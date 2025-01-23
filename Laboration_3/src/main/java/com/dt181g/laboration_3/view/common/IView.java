package com.dt181g.laboration_3.view.common;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.dt181g.laboration_3.support.AppConfigLab3;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Cursor;

/**
 * Base interface for view components in the MVC architecture of the application.
 * <p>
 * This interface defines common functionalities for views, such as styling labels
 * to look like buttons and providing access to a quit button. Any view component
 * in the application that requires these functionalities should implement this
 * interface.
 * </p>
 *
 * @author Joel Lansgren
 */
public interface IView {
     /**
     * Retrieves the quit button for the game, allowing the launcher controller
     * to access it for displaying the start screen of the view.
     *
     * @return the JLabel representing the game's quit button
     */
    JLabel getQuitBtn();

    /**
     * Adds a mouse listener to the quit button.
     *
     * @param listener the MouseAdapter to be added to the quit button, enabling
     * interaction when the button is clicked.
     */
    void addQuitBtnListener(final MouseAdapter listener);

    /**
     * Styles a label to look like a button with specific color and border.
     *
     * @param labelToBtn the JLabel to style
     * @param borderAndHoverBackgroundClr the color used for the label's foreground and background
     */
    default void labelBtn(final JLabel labelToBtn, final Color borderAndHoverBackgroundClr) {
        labelToBtn.setFont(new Font("Monospace", Font.BOLD, AppConfigLab3.TEXT_SIZE_NORMAL));
        labelToBtn.setForeground(borderAndHoverBackgroundClr);
        labelToBtn.setBackground(borderAndHoverBackgroundClr); // Used for hovering effect.
        labelToBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final Border matteBorder = BorderFactory.createMatteBorder(
            AppConfigLab3.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfigLab3.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfigLab3.LABEL_BTN_OUTER_BORDER_WIDTH,
            AppConfigLab3.LABEL_BTN_OUTER_BORDER_WIDTH,
            borderAndHoverBackgroundClr
        );

        labelToBtn.setBorder(BorderFactory.createCompoundBorder(matteBorder, AppConfigLab3.LABEL_BTN_INNER_SPACE));
    }

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
    default void labelStyling(final JLabel label, final int textSize, final boolean snakeText) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Monospace", Font.BOLD, textSize));
        label.setForeground(AppConfigLab3.COLOR_WHITE);
        if (snakeText) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
        }
    }

    /**
     * Method to reset the GridBagConstraints
     */
    default void resetGbc(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = AppConfigLab3.RESET_INSETS;
        gbc.ipadx = 0;
        gbc.ipady = 0;
    }
}
