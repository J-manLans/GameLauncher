package com.dt181g.laboration_3.view;

import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dt181g.laboration_3.support.AppConfigLab3;

/**
 * A mock implementation of the {@link GameView} interface for the Tic Tac Toe game.
 *
 * <p>
 * This class serves as a placeholder for testing purposes, simulating the game view
 * when the actual game is not available. It displays a message indicating the unavailability
 * of the game and is used to verify that buttons and UI components are added correctly
 * to the {@link GameLauncherView}.
 * </p>
 *
 * <p>
 * The view is styled using configurations defined in the {@link AppConfigLab3} class.
 * </p>
 *
 * <p>
 * Might be implemented in the future, who knows.
 * </p>
 * @author Joel Lansgren
 */
public class FakeTicTacToeView extends JPanel implements GameView {
    private final JLabel title;

    public FakeTicTacToeView(final String title) {
        this.title = new JLabel(title);
    }

    /**
     * May be properly implemented in the future.
     */
    @Override
    public void initializeStartMenu() {
        this.removeAll();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel gameLabel = new JLabel("Sorry! The game isn't available at the moment.");
        AppConfigLab3.labelStyling(gameLabel);

        this.setBackground(AppConfigLab3.COLOR_DARKER_GREY);
        this.add(Box.createVerticalGlue());
        this.add(gameLabel);
        this.add(Box.createVerticalGlue());
    }

    /**
     * May be implemented in the future.
     */
    @Override
    public JPanel getPanel() {
       return this;
    }

    /**
     * May be implemented in the future.
     */
    @Override
    public String getTitle() {
       return title.getText();
    }

    /**
     * May be implemented in the future.
     */
    @Override
    public void startGame(final List<Object> gameAssets) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }
}
