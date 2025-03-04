package com.dt181g.project.support;

import java.awt.AWTEvent;
import java.awt.EventQueue;

/**
 * TimedEventQueue is a custom implementation of the EventQueue class that measures
 * the time taken to dispatch events. It logs the duration of any event that takes
 * longer than 50 milliseconds to process.
 *
 * <p>
 * This class can be useful for profiling the performance of GUI event handling in
 * Java applications, allowing developers to identify and address performance bottlenecks.
 * </p>
 *
 * <p>
 * It overrides the {@link #dispatchEvent(AWTEvent)} method to calculate the time
 * taken for each event and logs the duration if it exceeds the specified threshold.
 * </p>
 *
 * @author Joel Lansgren
 */
public class TimedEventQueue extends EventQueue {
    @Override
    protected void dispatchEvent(final AWTEvent event) {
       final long startTime = System.nanoTime();
       super.dispatchEvent(event);
       final long duration = System.nanoTime() - startTime;
       if (duration > 50000000) {  // longer than 50ms
         System.out.println((duration * .000001) + "ms: " + event);
       }
    }
}
