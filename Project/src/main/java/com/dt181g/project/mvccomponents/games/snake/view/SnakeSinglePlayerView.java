package com.dt181g.project.mvccomponents.games.snake.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.dt181g.project.mvccomponents.IBaseView;
import com.dt181g.project.mvccomponents.games.listeners.SnakeMovementListener;
import com.dt181g.project.support.AppConfigProject;
import com.dt181g.project.support.DebugLogger;

/**
 * Represents the single-player view of the Snake game, displaying the game grid,
 * game over information, and user interaction controls.
 *
 * <p>
 * This class extends {@link JPanel} and implements {@link IBaseView}.
 * It provides a grid layout for the snake game, manages game assets,
 * and handles user input for controlling the snake's movement.
 * </p>
 *
 * @author Joel Lansgren
 */
public class SnakeSinglePlayerView extends JPanel implements IBaseView {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private JPanel snakeGrid;
    private JPanel[][] snakeGridCells;

    // The paintComponent method is overridden to create a transparent overlay effect,
    // for some reason the setBackground method doesn't apply the alpha channel as one would wish.
    private final JPanel gameOverPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(AppConfigProject.COLOR_TRANSPARENT);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    };

    private final JLabel gameOver = new JLabel("GAME OVER");
    private final JLabel snakeLengthLabel = new JLabel();
    private final JLabel snakeSpeedLabel = new JLabel();
    private int[][] modelGameGrid;
    private final JLabel snakeBackBtn = new JLabel("Back");

    /**
     * Initializes the single-player view for the Snake game.
     * Sets up the layout and labels for displaying the game status.
     */
    public SnakeSinglePlayerView() {
        this.setLayout(new GridBagLayout());
        this.setBackground(AppConfigProject.COLOR_DARKER_GREY);
        labelBtn(this.snakeBackBtn, AppConfigProject.COLOR_WHITE);
        labelStyling(this.gameOver, AppConfigProject.TEXT_HEADING_2);
        labelStyling(this.snakeLengthLabel, AppConfigProject.TEXT_SIZE_NORMAL);
        labelStyling(this.snakeSpeedLabel, AppConfigProject.TEXT_SIZE_NORMAL);
    }

    /**
     * Initializes the view with the specified game assets.
     *
     * @param gameAssets A list of game assets, including the initial position of the snake.
     */
    public void initializeView(List<Object> gameAssets) {
        this.setGameAssets(gameAssets);
        // This one is only truly utilized the first time the game starts up.
        // Every other time it just sets the focus to the panel so the keyListener
        // will work properly
        this.startGame();
        // This is to get the true state of the game at every startup
        this.updateGameGrid();
    }

    /**
     * Sets the initial game assets for the snake's position in the grid.
     *
     * @param gameAssets a list containing a 2D int array representing the snake's position.
     */
    private void setGameAssets(List<Object> gameAssets) {
        for (Object asset : gameAssets) {
            if (asset instanceof int[][]) {
                this.modelGameGrid = (int[][]) asset;
            }
        }
    }

    /**
     * Initializes and starts the game by setting up the game grid and adding it to the view.
     * <p>
     * This method checks if the snake grid has already been created. If not, it initializes
     * the grid with a GridLayout based on the size of the game model. It also sets
     * up the game over panel and adds the necessary components to the layered pane for display.
     * </p>
     *
     * <p>
     * The method concludes by requesting focus on the snake grid to enable key listeners for
     * controlling the snake's movement.
     * </p>
     */
    private void startGame() {
        if (this.snakeGrid == null) {
            this.snakeGrid = new JPanel(new GridLayout(this.modelGameGrid.length, this.modelGameGrid.length));
            this.snakeGrid.setBounds(0, 0, AppConfigProject.SNAKE_GRID_SIZE.width, AppConfigProject.SNAKE_GRID_SIZE.height);
            this.snakeGridCells = new JPanel[this.modelGameGrid.length][this.modelGameGrid.length];

            this.layeredPane.setPreferredSize(AppConfigProject.SNAKE_GRID_SIZE);
            this.layeredPane.add(this.snakeGrid, JLayeredPane.DEFAULT_LAYER);
            this.setupGameOverPanel();

            // Display components
            this.gbc.gridy = 0;
            this.gbc.insets = AppConfigProject.INSET_BOTTOM_20;
            this.add(this.layeredPane, gbc);

            this.gbc.gridy++;
            this.gbc.insets = AppConfigProject.RESET_INSETS;
            this.add(this.snakeBackBtn, gbc);

            // Sets up the initial snake grid
            this.initializeGrid();
        }
        // For enabling the keyListener
        this.snakeGrid.requestFocusInWindow();
    }

    /**
     * Configures the game over panel, setting its layout, bounds, and adding components.
     * <p>
     * This method initializes the game over panel to be displayed when the game ends. It sets
     * the layout to a vertical box layout and adds the game over message, the snake's length,
     * and the snake's speed labels. The panel is set to be transparent to allow for an overlay
     * effect over the game grid.
     * </p>
     */
    private void setupGameOverPanel() {
        this.gameOverPanel.setLayout(new BoxLayout(this.gameOverPanel, BoxLayout.Y_AXIS));
        this.gameOverPanel.setBounds(0, 0, AppConfigProject.SNAKE_GRID_SIZE.width, AppConfigProject.SNAKE_GRID_SIZE.height);
        this.gameOverPanel.setOpaque(false);

        this.gameOverPanel.add(Box.createVerticalGlue());
        this.gameOverPanel.add(this.gameOver);
        this.gameOverPanel.add(this.snakeLengthLabel);
        this.gameOverPanel.add(this.snakeSpeedLabel);
        this.gameOverPanel.add(Box.createVerticalGlue());
    }

    /**
     * Displays the game over panel with the snake's length and speed.
     *
     * @param snakeLength The length of the snake at the time of game over.
     * @param snakeSpeed The speed of the snake at the time of game over.
     */
    public void showGameOver(int snakeLength, int snakeSpeed) {
        if (!this.layeredPane.isAncestorOf(gameOverPanel)) {
            this.layeredPane.add(this.gameOverPanel, JLayeredPane.POPUP_LAYER);
        }
        this.snakeLengthLabel.setText("Your snake was " + snakeLength + " body parts long.");
        this.snakeSpeedLabel.setText("It traveled with a speed of " + snakeSpeed + " cells per second.");
        this.gameOverPanel.setVisible(true);
    }

    /**
     * Hides the game over panel when the game restarts.
     */
    public void hideGameOver() {
        this.gameOverPanel.setVisible(false);
    }

    /**
     * Initializes the grid with the current state of the snake.
     */
    private void initializeGrid() {
        for (int i = 0; i < this.modelGameGrid.length; i++) {
            for (int j = 0; j < this.modelGameGrid.length; j++) {
                // Create cells to put in the grid
                JPanel cell = new JPanel(new BorderLayout());
                cell.setBorder(BorderFactory.createLineBorder(AppConfigProject.COLOR_DARK_GREY));

                if (this.modelGameGrid[i][j] == 1) {  // Displays the snake in the grid
                    cell.setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
                } else {  // Background
                    cell.setBackground(AppConfigProject.COLOR_DARKER_GREY);
                }

                // Add the cells to the grid and then add the cells to a 2D array
                // used for the consecutive updates of the grid.
                this.snakeGrid.add(cell);
                this.snakeGridCells[i][j] = cell;
            }
        }
    }

    /**
     * Updates the game grid with the current state of the snake and booster items.
     */
    public void updateGameGrid() {
        for (int i = 0; i < this.modelGameGrid.length; i++) {
            for (int j = 0; j < this.modelGameGrid.length; j++) {
                this.snakeGridCells[i][j].removeAll();

                if (this.modelGameGrid[i][j] != 0) {  // Displays the snake and items in the grid
                    switch (this.modelGameGrid[i][j]) {
                        case AppConfigProject.COLOR_SNAKE_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_ACCENT);
                        } case AppConfigProject.COLOR_CHERRY_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_CHERRY);
                        } case AppConfigProject.COLOR_SPEED_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_GAME_SPEED);
                        } case AppConfigProject.COLOR_SNAKE_HEAD_INT -> {
                            this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_SNAKE_HEAD);
                        } default -> DebugLogger.INSTANCE.logInfo("Unimplemented snake booster in " + getClass().getName());
                    }

                } else {  // Background
                    this.snakeGridCells[i][j].setBackground(AppConfigProject.COLOR_DARKER_GREY);
                }
            }
        }
    }

    /*=========================
     * Listeners
     ========================*/

    /**
     * Adds a mouse listener to the back button in the controls menu.
     *
     * @param controlsBackBtnListener The mouse listener to be added to the back button,
     * allowing for interaction when the button is clicked.
     */
    public void addSnakeBackBtnListener(final MouseAdapter controlsBackBtnListener) {
        this.snakeBackBtn.addMouseListener(controlsBackBtnListener);
    }

    /**
     * Adds a key listener for snake movement control.
     *
     * @param snakeKeyListener The key listener to be added for handling snake movement.
     */
    public void addSnakeKeyListener(SnakeMovementListener snakeKeyListener) {
        this.snakeGrid.addKeyListener(snakeKeyListener);
    }

    /**
     * Removes all listeners from the grid and back button.
     */
    public void removeListeners() {
        if (this.snakeGrid != null) {
            for (KeyListener listener : this.snakeGrid.getKeyListeners()) {
                this.snakeGrid.removeKeyListener(listener);
            }
        }
        removeAllListenersFromButton(this.snakeBackBtn);
    }

    /*=========================
     * Getters
     ========================*/

    /**
     * Returns the back button.
     *
     * @return The JLabel representing the back button.
     */
    public JLabel getSnakeBackBtn() {
        return this.snakeBackBtn;
    }

    /**
     * Returns the panel representing the snake grid.
     *
     * @return The JPanel representing the snake grid.
     */
    public JPanel getSnakeGrid() {
        return this.snakeGrid;
    }
}
