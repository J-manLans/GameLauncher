package com.dt181g.laboration_3.supportChange;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A singleton logger utility for debugging and logging messages in the application.
 *
 * <p>
 * The {@code DebugLogger} is designed to provide a centralized logging mechanism,
 * allowing different parts of the application to log messages with various severity levels.
 * It formats log messages with specific colors for better visibility in the console.
 * </p>
 *
 * @author Joel Lansgren
 */
public enum DebugLogger {
    /**
    * Singleton instance of the DebugLogger enum.
    */
    INSTANCE;
    private final Logger logger = Logger.getLogger("Logger");

    /*================================
        ↓ CONSTRUCTOR
    ================================*/

    /**
     * Initializes the enum {@code DebugLogger} instance by attaching a
     * {@code ConsoleHandler} set to INFO level with a simplified
     * {@code SimpleFormatter} that logs messages directly.     *
     */
    DebugLogger() {
        // Initializes the console handler
        final ConsoleHandler consoleHandler = new ConsoleHandler();

        // Configures the handler
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(final LogRecord logRecord) {
                return String.format("%s%n", logRecord.getMessage());
            }
        });

        // Configures the logger
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false); // Disable the default console handler to avoid duplicate logs
    }

    /*================================
        ↓ Logger Methods
    ================================*/

    /**
     * Logs the provided message with INFO level.
     *
     * @param message the message to be logged
     */
    private void performLog(final String message) {
        logger.info(message);
    }

    // ===== Statistical logs =====

    /**
     * Logs information with a yellow color formatting.
     *
     * @param message the message to be logged
     */
    public void logInfo(final String message) {
        performLog(String.format("%s%s%s", AppConfigLab3.ANSI_YELLOW, message, AppConfigLab3.ANSI_RESET));

    }


    /**
     * Logs warning information with a red color formatting.
     *
     * @param message the message to be logged
     */
    public void logWarning(final String message) {
        performLog(String.format("%s%s%s", AppConfigLab3.ANSI_RED, message, AppConfigLab3.ANSI_RESET));
    }
}
