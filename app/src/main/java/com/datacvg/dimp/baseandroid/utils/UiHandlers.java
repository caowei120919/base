package com.datacvg.dimp.baseandroid.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by kevin on 1/20/14.
 */
public class UiHandlers {
    private final static long MAIN_THREAD_ID = 1;

    /**
     * Adds the runnable into the message queue. The runnable will be run after
     * all posted runnables get run.
     *
     * @param runnable the {@link Runnable} to be run.
     */
    public static void post(Runnable runnable) {
        getHandler().post(runnable);
    }

    /**
     * Adds the UiRunnable into the message queue. The runnable will be run after
     * all posted runnables get run on active Activity.
     *
     * @param runnable the {@link UiRunnable} to be run.
     */
    public static void post(final UiRunnable runnable) {
        getHandler().post(new Runnable() {
            @Override
            public void run() {
                runnable.execute();
            }
        });
    }

    /**
     * Runs the runnable with delayed milliseconds in UIThread.
     *
     * @param runnable    the task to run.
     * @param delayMillis the delayed milliseconds.
     */
    public static void postDelayed(Runnable runnable, long delayMillis) {
        getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * Runs the runnable with delayed milliseconds in UIThread.
     *
     * @param runnable    the task to run.
     * @param delayMillis the delayed milliseconds.
     */
    public static void postDelayed(final UiRunnable runnable, long delayMillis) {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runnable.execute();
            }
        }, delayMillis);
    }

    /**
     * Removes the posted {@link Runnable}.
     *
     * @param runnable the {@link Runnable} to be removed.
     */
    public static void removeCallback(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    private static Handler getHandler() {
        return HandlerHolder.HANDLER;
    }

    public static boolean isMainThread() {
        return Thread.currentThread().getId() == MAIN_THREAD_ID;
    }

    private static class HandlerHolder {
        public static final Handler HANDLER = new Handler(Looper.getMainLooper());
    }
}
