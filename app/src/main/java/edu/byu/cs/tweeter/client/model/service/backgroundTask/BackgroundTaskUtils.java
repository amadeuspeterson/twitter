package edu.byu.cs.tweeter.client.model.service.backgroundTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * BackgroundTaskUtils contains utility methods needed by background tasks.
 */
public class BackgroundTaskUtils {

    public static void runTask(Runnable task) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }

    public static void runTasks(Runnable[] tasks) {
        ExecutorService executor = Executors.newFixedThreadPool(tasks.length);
        for(Runnable task: tasks) {
            executor.execute(task);
        }
    }
}
