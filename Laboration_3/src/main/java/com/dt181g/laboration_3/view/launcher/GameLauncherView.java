package com.dt181g.laboration_3.view.launcher;

import com.dt181g.laboration_3.support.AppConfigLab3;
import com.dt181g.laboration_3.view.common.IView;
import com.dt181g.laboration_3.view.common.ImageManager;
import com.dt181g.laboration_3.view.games.common.IGameView;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseWheelListener;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import java.awt.event.MouseAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * The view component of the Game Launcher.
 * <p>
 * This class represents the user interface for the game launcher, providing
 * a scrollable list of game icons and a panel to display the selected game.
 * It interacts with the controller by accepting listeners for user actions.
 * </p>
 *
 * <p>
 * It extends {@link JFrame} and organizes components using {@link JPanel},
 * {@link JScrollPane}, and various layout managers for an intuitive interface.
 * </p>
 *
 * <p>
 * Uses {@link AppConfigLab3} for styling and layout configurations.
 * </p>
 *
 * @author Joel Lansgren
 */
public class GameLauncherView extends JFrame implements IView {
    // Launcher
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    // Game selector panel
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel gameSelectorPanel = new JPanel();
    private final JLabel gameLabel = new JLabel("GAMES:");
    private final JPanel gamesListPanel = new JPanel();
    private final JScrollPane scrollPane = new JScrollPane(gamesListPanel);
    private final List<JButton> gameIcons = new ArrayList<JButton>();
    private final JLabel quitBtn = new JLabel("Exit");
    private final JLabel aboutBtn = new JLabel("About");

    // Game panel
    private final CardLayout gamePanelCL = new CardLayout();
    private final JPanel gamePanel = new JPanel();
    private final JPanel startScreen = new JPanel();
    private final JPanel aboutPanel = new JPanel();
    private final JLabel instructionsHeading = new JLabel("Instructions:");
    private final JLabel instructionsSelectGame = new JLabel("Click on a game from the left side panel to start it");
    private final JLabel instructionsExit = new JLabel("Use the \"Exit\" button to close the launcher");
    private final JLabel instructionsAbout = new JLabel("...Or the \"About\" button to open this screen");
    private final JLabel instructionsEnjoy = new JLabel("Enjoy!");
    private final JLabel instructionsSmiley = new JLabel("(^_^)");
    private final JLabel instructionsBackBtn = new JLabel("Back");

    /**
     * Sets up the GameLauncherView components.
     * <p>
     * Initializes the game selection panel, the scroll pane that holds it, and the game display panel.
     * Also sets the layout for the JFrame and configures general window properties.
     * </p>
     */
    public void initializeView() {
        SwingUtilities.invokeLater(() -> {
            /*===================================
             * GamePanel (left side)
             ==================================*/
            this.gameSelectorPanel.setLayout(new GridBagLayout());
            this.gameSelectorPanel.setPreferredSize(AppConfigLab3.GAME_SELECTOR_PANEL_DIMENSIONS);
            this.gameSelectorPanel.setBackground(AppConfigLab3.COLOR_DARK_GREY);

            // Top of GameSelectorPanel
            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbc.insets = AppConfigLab3.INSET_TOP_30_BOTTOM_20;
            labelStyling(this.gameLabel, AppConfigLab3.TEXT_SIZE_NORMAL, false);
            this.gameSelectorPanel.add(this.gameLabel, this.gbc);

            // ScrollPane (handles size of gamesPanel)
            this.gamesListPanel.setLayout(new BoxLayout(this.gamesListPanel, BoxLayout.Y_AXIS));
            this.gamesListPanel.setBackground(AppConfigLab3.COLOR_DARK_GREY);
            this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            this.scrollPane.setBorder(AppConfigLab3.REMOVE_BORDER);

            this.gbc.gridy++;
            this.gbc.weighty = 1;
            this.gbc.weightx = 1;
            this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;
            this.gbc.fill = GridBagConstraints.BOTH;
            this.gameSelectorPanel.add(this.scrollPane, this.gbc);

            // The button at the bottom of the gameSelectorPanel
            labelBtn(this.quitBtn, AppConfigLab3.COLOR_WHITE);
            this.gbc.gridy++;
            this.gbc.weighty = 0;
            this.gbc.fill = GridBagConstraints.NONE;
            this.gbc.insets = AppConfigLab3.INSET_BOTTOM_30;
            this.gameSelectorPanel.add(this.quitBtn, this.gbc);

            /*===================================
             * GamePanel (right side)
             ==================================*/
            this.gamePanel.setLayout(this.gamePanelCL);

            // StartScreen
            this.startScreen.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
            this.startScreen.setLayout(new GridBagLayout());

            resetGbc(this.gbc);
            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbc.weighty = 1;
            this.gbc.weightx = 1;
            this.startScreen.add(new JLabel(), this.gbc); // Empty label as spacer

            labelBtn(this.aboutBtn, AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);
            this.gbc.gridy++;
            this.gbc.weighty = 0;
            this.gbc.anchor = GridBagConstraints.SOUTHWEST;
            this.gbc.insets = AppConfigLab3.INSET_LEFT_BOTTOM_CORNER_30;
            this.startScreen.add(this.aboutBtn, this.gbc);

            this.gamePanel.add(this.startScreen, "Start Screen");

            // AboutScreen
            this.aboutPanel.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
            this.aboutPanel.setLayout(new GridBagLayout());
            labelStyling(this.instructionsHeading, AppConfigLab3.TEXT_HEADING_1, false);
            labelStyling(this.instructionsSelectGame, AppConfigLab3.TEXT_SIZE_NORMAL, false);
            labelStyling(this.instructionsExit, AppConfigLab3.TEXT_SIZE_NORMAL, false);
            labelStyling(this.instructionsAbout, AppConfigLab3.TEXT_SIZE_NORMAL, false);
            labelStyling(this.instructionsEnjoy, AppConfigLab3.TEXT_SIZE_NORMAL, false);
            labelStyling(this.instructionsSmiley, AppConfigLab3.TEXT_HEADING_2, false);
            labelBtn(instructionsBackBtn, AppConfigLab3.COLOR_SNAKE_GAME_ACCENT);

            resetGbc(this.gbc);
            this.gbc.gridx = 0;
            this.gbc.gridy = 0;
            this.gbc.insets = AppConfigLab3.INSET_BOTTOM_20;
            this.aboutPanel.add(this.instructionsHeading, this.gbc);
            this.gbc.gridy++;
            this.aboutPanel.add(this.instructionsSelectGame, this.gbc);
            this.gbc.gridy++;
            this.aboutPanel.add(this.instructionsExit, this.gbc);
            this.gbc.gridy++;
            this.aboutPanel.add(this.instructionsAbout, this.gbc);
            this.gbc.gridy++;
            this.aboutPanel.add(this.instructionsEnjoy, this.gbc);
            this.gbc.gridy++;
            this.gbc.insets = AppConfigLab3.INSET_BOTTOM_30;
            this.aboutPanel.add(this.instructionsSmiley, this.gbc);
            this.gbc.gridy++;
            this.aboutPanel.add(this.instructionsBackBtn, this.gbc);

            this.gamePanel.add(this.aboutPanel, "About Screen");

            /*===================================
             * JFrame
             ==================================*/

            this.setSize(screenSize);
            this.setUndecorated(true);
            // this.setSize(AppConfigLab3.GAME_LAUNCHER_DIMENSIONS);
            this.setLayout(new BorderLayout());
            this.add(this.gameSelectorPanel, BorderLayout.WEST);
            this.add(this.gamePanel, BorderLayout.CENTER);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    /**
     * Makes the game launcher view visible on the Event Dispatch Thread (EDT).
     */
    public void showLauncher() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }

    /**
     * Adds game icons to the selection panel.
     * <p>
     * Takes a list of image paths and corresponding game titles, and creates
     * a clickable icon for each game. Each icon is set up with the appropriate
     * image and action command, and is stored for later use in event listeners.
     * </p>
     *
     * @param pathToIcon a list of file paths for the game icons
     * @param titles a list of game titles corresponding to each icon
     */
    public void addGameIcons(final List<String> iconPaths, final List<String> titles) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < iconPaths.size(); i++) {
                // Set up the icon.
                final JButton gameIcon = new JButton();
                gameIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
                gameIcon.setBorder(AppConfigLab3.REMOVE_BORDER);
                gameIcon.setContentAreaFilled(false);
                gameIcon.setBorderPainted(false);
                gameIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Fetch  and sets the icon image.
                gameIcon.setIcon(ImageManager.INSTANCE.loadIcon(iconPaths.get(i)));
                // Set the action command of the icon to the game title,
                // to let the action listener know which game was clicked.
                gameIcon.setActionCommand(titles.get(i));

                // Add icon to the panel, plus a distance to create some air.
                gamesListPanel.add(gameIcon);
                if (i < iconPaths.size() - 1) { gamesListPanel.add(Box.createRigidArea(AppConfigLab3.HIGHT_20)); }

                // Add the icon to the icon list so action listeners can be attached at a later stage.
                gameIcons.add(gameIcon);
            }
            // Adds flexible space below the icons,
            // allowing the panel to respect the preferred size of the buttons.
            gamesListPanel.add(Box.createVerticalGlue());
        });
    }

    /**
     * Displays the game panel by showing the start screen.
     */
    public void displayStartScreen() {
        this.gamePanelCL.show(this.gamePanel, "Start Screen");
    }

    /**
     * Displays the about panel by showing the start screen.
     */
    public void displayAboutPanel() {
        this.gamePanelCL.show(this.gamePanel, "About Screen");
    }

    /**
     * Method that controls the scroll speed of the scroll pane.
     * @param notches The direction of the mouse wheel (1 or -1).
     */
    public void handleScroll(final int notches) {
        final JScrollBar vertical = this.scrollPane.getVerticalScrollBar();
        // Sets the new position either up (-1) or down (1) depending on scroll direction.
        vertical.setValue(vertical.getValue() + (notches * AppConfigLab3.SCROLL_SPEED_MULTIPLIER));
    }

    /**
     * Loads the selected game's view into the game display panel.
     *
     * <p>
     * This method is called from the {@link ActionListener} attached to the game icons.
     * It adds the new game's view to the card layout panel. It then shows the view.
     * </p>
     *
     * @param gameView the {@link IGameView} to be displayed in the panel
     */
    public void displayGame(final IGameView gameView) {
        // Adds the panel and show the game view.
        this.gamePanel.add((JPanel) gameView, "Game");
        this.gamePanelCL.show(gamePanel, "Game");
    }

    /*====================
     * Listeners
     ===================*/

    /**
     * Adds a mouse wheel listener to the scroll pane.
     * @param scrollPaneListener the {@link MouseWheelListener} that listens for mouse wheel events on the scroll pane.
     */
    public void addScrollPaneListener(final MouseWheelListener scrollPaneListener) {
        SwingUtilities.invokeLater(() -> {
            this.scrollPane.addMouseWheelListener(scrollPaneListener);
        });
    }

    /**
     * Adds an action listener to a specific game icon button.
     * @param gameIcon the {@link JButton} representing the game icon to which the listener is added.
     * @param gameIconListener the {@link ActionListener} that listens for action events on the game icon button.
     */
    public void addGameIconListeners(final ActionListener gameIconListener) {
        SwingUtilities.invokeLater(() -> {
            // For game icons so they start the game when clicked
            for (final JButton gameIcon : this.gameIcons) {
                gameIcon.addActionListener(gameIconListener);
            }
        });
    }

    /**
     * Adds a mouse listener to the about button.
     * @param aboutBtnListener the {@link MouseAdapter} that listens for events on the about button.
     */
    public void addAboutBtnListener(final MouseAdapter aboutBtnListener) {
        SwingUtilities.invokeLater(() -> {
            this.aboutBtn.addMouseListener(aboutBtnListener);
        });
    }

    /**
     * Adds a mouse listener to the back button.
     * @param backBtnListener the {@link MouseAdapter} that listens for events on the back button.
     */
    public void addBackBtnListener(final MouseAdapter backBtnListener) {
        SwingUtilities.invokeLater(() -> {
            this.instructionsBackBtn.addMouseListener(backBtnListener);
        });
    }

    /**
     * Adds a mouse listener to the quit button.
     * @param quitBtnListener the {@link MouseAdapter} that listens for events on the quit button.
     */
    public void addQuitBtnListener(final MouseAdapter quitBtnListener) {
        SwingUtilities.invokeLater(() -> {
            this.quitBtn.addMouseListener(quitBtnListener);
        });
    }

    /*=====================
     * Getters
     ====================*/

    /**
     * Returns the game panel.
     * Eventually it ends up in the started games main view.
     * There it is used to display the launcher again when the user quits the game.
     * @return the {@link JPanel} representing the game panel.
     */
    public JPanel getGamePanel() {
        return this.gamePanel;
    }

    /**
     * Returns the quit button label.
     * @return the JLabel representing the quit button.
     */
    public JLabel getQuitBtn() {
        return this.quitBtn;
    }

    /**
     * Returns the about button.
     * @return the JLabel representing the about button.
     */
    public JLabel getAboutBtn() {
        return this.aboutBtn;
    }

    /**
     * Returns the back button.
     * @return the JLabel representing the back button.
     */
    public JLabel getInstructionsBackBtn() {
        return this.instructionsBackBtn;
    }
}
