package com.dt181g.project.view;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.support.AppConfig;
import com.dt181g.project.support.AppConfigLauncher;

/**
 * This is the main container of the launcher view.
 * It has a {@link CardLayout} where it displays the various views.
 * @author Joel Lansgren
 */
public class LauncherMainView implements IView {
    private final JPanel mainPanel = new JPanel();
    private final CardLayout mainPanelCL = new CardLayout();

    /** Constructs up the LauncherMainView on the EDT */
    public LauncherMainView() {
        SwingUtilities.invokeLater(() -> {
            mainPanel.setLayout(mainPanelCL);
            setColorOnBackBtn();
        });
    }

    /**
     * Sets the different views for the launcher.
     * @param startView the JPanel for the start menu
     * @param aboutView the JPanel for the about menu
     * @param comingSoonGameView the JPanel for the coming soon view
     */
    public void setViews(
        final JPanel startView,
        final JPanel aboutView,
        final JPanel comingSoonGameView
    ) {
        mainPanel.add(startView, AppConfigLauncher.START);
        mainPanel.add(aboutView, AppConfigLauncher.ABOUT);
        mainPanel.add(comingSoonGameView, AppConfigLauncher.COMING_SOON);
    }

    /*=====================
    * Show methods
    =====================*/

    /**
     * Displays the start screen.
     */
    public void ShowStartView() {
        mainPanelCL.show(mainPanel, AppConfigLauncher.START);
    }

    /**
     * Displays the about screen.
     */
    public void ShowAboutPanel() {
        mainPanelCL.show(mainPanel, AppConfigLauncher.ABOUT);
    }

    /**
     * Displays the coming soon screen.
     */
    public void ShowComingSoonGamePanel() {
        mainPanelCL.show(mainPanel, AppConfigLauncher.COMING_SOON);
    }

    /**
     * Loads the selected game's view into the game display panel.
     * <p>
     * This method is called from the {@link ActionListener} attached to the game buttons.
     * It adds the game's view to the card layout panel with the constraint fetched from the config file. Since
     * the same constraint is used every time, the new panel will replace any existing panel with
     * the same name in the CardLayout. The view is then displayed.
     * </p>
     * @param gamePanel The panel representing the selected game's view to be displayed.
     */
    public void ShowSelectedGame(final JPanel gamePanel) {
        mainPanel.add(gamePanel, AppConfigLauncher.SELECTED_GAME);
        mainPanelCL.show(mainPanel, AppConfigLauncher.SELECTED_GAME);
    }

    /*=====================
    * Setters
    =====================*/

    /**
     * Resets the color on the backBtn when closing a game.
     */
    public void setColorOnBackBtn() {
        setColorOnBackBtn(AppConfig.COLOR_ACCENT);
    }

    /*=====================
    * Getters
    =====================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
