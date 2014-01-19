/**
 * Name: ScheduleManager.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * The type Schedule manager.
 */
public class ScheduleManager {

    private int schedNumThreads;
    private int repeatingThreads;
    private int loginNumThreads;
    private ScheduledExecutorService scheduler;
    private ExecutorService executor = null;

    /**
     * Instantiates a new Schedule manager.
     */
    public ScheduleManager() {

        schedNumThreads = 6;
        scheduler = Executors.newScheduledThreadPool(schedNumThreads);
    }

    /**
     * Gets sched num threads.
     * 
     * @return the sched num threads
     */
    public int getSchedNumThreads() {
        return schedNumThreads;
    }

    /**
     * Sets sched num threads.
     * 
     * @param schedNumThreads the sched num threads
     */
    public void setSchedNumThreads(int schedNumThreads) {
        this.schedNumThreads = schedNumThreads;
    }

    /**
     * Gets login num threads.
     * 
     * @return the login num threads
     */
    public int getLoginNumThreads() {
        return loginNumThreads;
    }

    /**
     * Sets login num threads.
     * 
     * @param loginNumThreads the login num threads
     */
    public void setLoginNumThreads(int loginNumThreads) {
        this.loginNumThreads = loginNumThreads;
    }

    /**
     * Gets repeating threads.
     * 
     * @return the repeating threads
     */
    public int getRepeatingThreads() {
        return repeatingThreads;
    }

    /**
     * Sets repeating threads.
     * 
     * @param repeatingThreads the repeating threads
     */
    public void setRepeatingThreads(int repeatingThreads) {
        this.repeatingThreads = repeatingThreads;
    }

    /**
     * Get Scheduler for repeating tasks
     * {@link java.util.concurrent.ScheduledExecutorService}
     * <p>
     * details can be found here
     * 
     * @return the scheduled executor service
     */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
