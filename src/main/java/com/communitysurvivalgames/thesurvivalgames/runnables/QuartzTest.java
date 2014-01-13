/**
 * Name: QuartzTest.java Edited: 13 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.runnables;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

    public QuartzTest() {
        try {
            // Grad the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // start it
            scheduler.startDelayed(3);
            System.out.println("3 seconds till start");

            if (scheduler.isStarted()) {
                System.out.println("Now shutting doing");
                scheduler.shutdown();
            }

            if (scheduler.isShutdown()) {
                System.out.println("Shutdown successfully");
            } else {
                System.out.println("Error: Shutting down");
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
