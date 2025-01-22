package com.dt181g.laboration_1;

//Adding some other changes in main


/**
 * The {@code Lab1} class is the entry point of the application. It simulates multiple clients that utilize
 * worker threads from a thread pool managed by the {@code ThreadManager}. Each client runs its own thread,
 * waits for the worker thread to complete a task, and then returns the thread back to the pool.
 *
 * This class cannot be instantiated as it is designed to contain only static methods.
 * @author Joel Lansgren
 */
public final class Lab1 {
    private Lab1() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    //somecomment

    /**
     * The main method is the entry point for the application.
     *
     * @param args command-line arguments (not used in this application).
     */
    public static void main(final String... args) {
        new Storefront();
    }
}
