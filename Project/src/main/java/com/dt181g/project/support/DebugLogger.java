package com.dt181g.project.support;

import java.util.function.Function;
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
        performLog(String.format("%s%s%s", AppConfigDebug.ANSI_YELLOW, message, AppConfigDebug.ANSI_RESET));

    }


    /**
     * Logs warning information with a red color formatting.
     *
     * @param message the message to be logged
     */
    public void logWarning(final String message) {
        performLog(String.format("%s%s%s", AppConfigDebug.ANSI_RED, message, AppConfigDebug.ANSI_RESET));
    }

    /**
     * Logs a concise representation of the provided exception, including the class
     * and method names of the caller.
     *
     * @param e the exception that occurred, which is logged with its details.
     */
    public void logException(Exception e) {
        performLog(String.format("%s%s exception in %s.%s()%s",
        AppConfigDebug.ANSI_RED,
            e.toString(),
            getCallerName(StackWalker.StackFrame::getClassName),
            getCallerName(StackWalker.StackFrame::getMethodName),
            AppConfigDebug.ANSI_RESET)
        );
    }

    /**
     * Retrieves the name of the caller (class or method) from the stack trace.
     *
     * @param getName a function that takes a StackWalker.StackFrame as input
     * and returns a String. This function is used to extract
     * the desired name (either class name or method name) from the
     * StackFrame. For example, use:
     * <ul>
     * <li> StackWalker.StackFrame::getClassName to get the class name</li>
     * <li> StackWalker.StackFrame::getMethodName to get the method name</li>
     * </ul>
     * Note: StackWalker.StackFrame::getFileName also returns a string,
     * but it may not be applicable for this method.
     * @return the name of the original caller as a string
     */
    private String getCallerName(Function<StackWalker.StackFrame, String> getName) {
        return StackWalker.getInstance()
        // The skip(2) method retrieves the calling method directly outside this class as a StackFrame
        .walk(frames -> frames.skip(2).findFirst())
        // Applies the provided function to extract the desired value from the StackFrame
        .map(getName)
        // Fallback string, essentially never used since it is only used when there is no caller 2 methods above.
        // However, it’s required because findFirst() returns an Optional that needs a fallback value.
        .orElse("");
    }
}
