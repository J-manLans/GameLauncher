package com.jman.gamelauncher.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.jman.gamelauncher.controller.CardinalDirectionListener;
import com.jman.gamelauncher.support.AppConfig;
import com.jman.gamelauncher.support.AppConfigSnake;

/**
 * Represents the single-player view for the Snake game.
 * This view class manages the graphical display of the game grid and the snake,
 * updating the snake's position as it moves and controlling the layout
 * and style of the grid cells.
 *
 * <p>This class primarily interacts with {@link SnakeView} for shared view components
 * and relies on a 2D array to manage the snake's appearance in the grid.</p>
 *
 * @author Joel Lansgren
 */
public class SnakeSinglePlayerView implements IView{
    private final JPanel mainPanel = new JPanel();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final int gridSize = AppConfigSnake.CELL_COUNT;
    private final JLayeredPane layeredPane = new JLayeredPane();
    private final JPanel gameGrid = new JPanel(new GridLayout(gridSize, gridSize));
    private final JPanel gameOverPanel = new JPanel() {
        /**
         * Override to set a transparent background on the GameOverPanel
         * in the layeredPane, so the snakeGrid will be visible under it.
         */
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            g.setColor(AppConfig.COLOR_TRANSPARENT);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    };
    private final JLabel gameOverLabel = new JLabel("GAME OVER");
    private final JLabel gameOverSnakeLengthLabel = new JLabel();
    private final JLabel gameOverSnakeSpeedLabel = new JLabel();
    private final JButton restartBtn = new JButton("RESTART");
    private final int backBtnPosition;

    /**
     * Constructs a new single-player view for the Snake game.
     * Sets up the layout and some basic styling.
     */
    public SnakeSinglePlayerView() {
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(AppConfig.COLOR_DARKER_GREY);

        // Setting up the snakeGrid.
        gameGrid.setBounds(0, 0, AppConfigSnake.GRID_SIZE.width, AppConfigSnake.GRID_SIZE.height);

        // Setting up the gameOverPanel.
        labelStyling(gameOverLabel, AppConfig.TEXT_HEADING_2);
        labelStyling(gameOverSnakeLengthLabel, AppConfig.TEXT_SIZE_NORMAL);
        labelStyling(gameOverSnakeSpeedLabel, AppConfig.TEXT_SIZE_NORMAL);
        buttonStyler(restartBtn, AppConfig.COLOR_WHITE);

        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setBounds(0, 0, AppConfigSnake.GRID_SIZE.width, AppConfigSnake.GRID_SIZE.height);
        gameOverPanel.setOpaque(false);

        gameOverPanel.add(Box.createVerticalGlue());
        gameOverPanel.add(gameOverLabel);
        gameOverPanel.add(gameOverSnakeLengthLabel);
        gameOverPanel.add(gameOverSnakeSpeedLabel);
        gameOverPanel.add(Box.createRigidArea(AppConfig.HIGHT_20));
        gameOverPanel.add(restartBtn);
        gameOverPanel.add(Box.createVerticalGlue());

        // Set up the layeredPane and add the panels to it.
        layeredPane.setPreferredSize(AppConfigSnake.GRID_SIZE);
        layeredPane.add(gameGrid, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gameOverPanel, JLayeredPane.POPUP_LAYER);

        // Adds the layeredPane in the mainPanel.
        gbc.gridy = 0;
        gbc.insets = AppConfig.INSET_BOTTOM_20;
        mainPanel.add(layeredPane, gbc);

        gbc.insets = AppConfig.RESET_INSETS;
        backBtnPosition = gbc.gridy + 1;
    }

    /*=============================
    * Game Methods
    =============================*/

    /**
     * Initializes the snakeGrid cells upon game initialization.
     * <p>Can only happen one time or else multiple grids will be overlaid on top of each-other.</p>
     */
    public void initializeGridCell() {
        for (int i = 0; i < gridSize * gridSize; i++) {
            // Creates the cell to put in the grid, gives it a border and background
            final JPanel cell = new JPanel(new BorderLayout());
            cell.setBorder(BorderFactory.createLineBorder(AppConfig.COLOR_DARK_GREY));

            // Add the cell to the grid
            gameGrid.add(cell);
        }
    }

    /**
     * Just before the game starts we we request focus on the snake grid to enable
     * key listeners for controlling the snake's movement.
     */
    public void requestFocusInGameGrid() {
        gameGrid.requestFocusInWindow();
    }

    /**
     * Updates the game grid with the current state of the snake.
     */
    public void updateGameGrid(final List<Point> snake, final Map<Point, Color> boosterData) {
        clearGameGrid();

        final Point head = snake.get(snake.size() - 1);

        for (final Point p : snake) {
            gameGrid.getComponent((p.y * gridSize) + p.x).setBackground(
                !p.equals(head) ? AppConfigSnake.COLOR_ACCENT : AppConfigSnake.COLOR_SNAKE_HEAD
            );
        }

        for (final Point p : boosterData.keySet()) {
            gameGrid.getComponent((p.y * gridSize) + p.x).setBackground(boosterData.get(p));
        }
    }

    /**
     * Clears the snakeGrid by coloring its cells to the background color.
     * This method is executed before each update.
     */
    private void clearGameGrid() {
        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                gameGrid.getComponent((y * gridSize) + x).setBackground(AppConfig.COLOR_DARKER_GREY);
            }
        }
    }

    /**
     * Displays the game over panel with the snake's length and speed.
     *
     * @param snakeLength The length of the snake at the time of game over.
     * @param snakeSpeed The speed of the snake at the time of game over.
     */
    public void showGameOver(final int snakeLength, final int snakeSpeed) {
        gameOverSnakeLengthLabel.setText("Your snake was " + snakeLength + " body parts long.");
        gameOverSnakeSpeedLabel.setText("It traveled with a speed of " + snakeSpeed + " cells per second.");
        gameOverPanel.setVisible(true);
    }

    /**
     * Hides the game over panel when the game restarts.
     */
    public void hideGameOver() {
        gameOverPanel.setVisible(false);
    }

    /*=============================
    * Setters
    =============================*/

    /**
     * Re-adds the shared back button to the menu.
     * Must be called each time the menu is displayed to ensure
     * the button appears in the correct position.
     */
    public void setBackBtn() {
        gbc.gridy = backBtnPosition;
        mainPanel.add(backBtn, gbc);
    }

    /*=============================
    * Getters
    =============================*/

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /*=========================
    * Listeners
    =========================*/

    /**
     * Adds a key listener for snake movement control.
     * @param snakeKeyListener The key listener to be added for handling snake movement.
     */
    public void addSnakeKeyListener(final CardinalDirectionListener snakeKeyListener) {
        gameGrid.addKeyListener(snakeKeyListener);
    }

    /**
     * Adds a {@link ButtonListener} to the restartBtn for restarting the game.
     * @param closeGameListener a callback that will restart the game.
     */
    public void addRestartBtnListener(final Runnable restartGameListener) {
        restartBtn.addMouseListener(new ButtonListener(restartGameListener, restartBtn.getForeground()));
    }

    /**
     * Removes the listeners from the grid and restart button.
     */
    public void removeListeners() {
        for (final KeyListener listener : gameGrid.getKeyListeners()) {
            gameGrid.removeKeyListener(listener);
        }

        for (final MouseListener listener : restartBtn.getMouseListeners()) {
            restartBtn.removeMouseListener(listener);
        }
    }
}
