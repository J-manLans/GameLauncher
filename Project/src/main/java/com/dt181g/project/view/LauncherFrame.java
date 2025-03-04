package com.dt181g.project.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.dt181g.project.support.AppConfigLauncher;

/**
 * The main component of the Game Launcher that holds the LauncherSideView and LauncherMainView.
 *
 * <p>This class represents the user interface for the game launcher, providing
 * a scrollable list of game icons and a panel with a card layout to display
 * various other panels like a start, about and active game screen.</p>
 *
 * <p>It holds a {@link JFrame} and organizes components using {@link JPanel}.</p>
 *
 * @author Joel Lansgren
 */
public class LauncherFrame {
    private final JFrame frame = new JFrame();

    public LauncherFrame(final JPanel launcherSideView, final JPanel launcherMainView) {
        SwingUtilities.invokeLater(() -> {
            // NOTE: For submission these two should be uncommented and the setSize below commented out.
            // frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            // frame.setUndecorated(true);
            frame.setSize(AppConfigLauncher.GAME_LAUNCHER_DIMENSIONS);
            frame.setLayout(new BorderLayout());
            frame.add(launcherSideView, BorderLayout.WEST);
            frame.add(launcherMainView, BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    /** Shows the launcher frame */
    public void showFrame() {
        frame.setVisible(true);
    }
}
