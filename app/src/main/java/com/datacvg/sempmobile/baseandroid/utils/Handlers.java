package com.datacvg.sempmobile.baseandroid.utils;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * FileName: ThreadUtils
 * Author: 曹伟
 * Date: 2019/9/16 15:33
 * Description:
 */

public final class Handlers {
    public static final String DEFAULT_TAG = "Default";

    private static Handlers instance;

    public static synchronized Handlers sharedInstance() {
        if (instance == null) {
            instance = new Handlers();
        }

        return instance;
    }

    private static Handler sharedHandler;

    /**
     * get shared handler for main looper
     *
     * @param context
     * @return
     */
    public static final Handler sharedHandler(Context context) {
        /**
         * duplicate handlers !!! i don't care
         */

        if (sharedHandler == null) {
            sharedHandler = new Handler(context.getMainLooper());
        }

        return sharedHandler;
    }

    /**
     * get new handler for main looper
     *
     * @param context
     * @return
     */
    public static final Handler newHandler(Context context) {
        return new Handler(context.getMainLooper());
    }

    private Handlers() {

    }

    /**
     * get new handler for a background default looper
     *
     * @return
     */
    public static final Handler newHandler() {
        return newHandler(DEFAULT_TAG);
    }

    /**
     * get new handler for a background stand alone looper identified by tag
     *
     * @param tag
     * @return
     */
    public static final Handler newHandler(String tag) {
        return new Handler(getHandlerThread(tag).getLooper());
    }

    private static final HashMap<String, HandlerThread> threads = new HashMap<String, HandlerThread>();

    private static final HandlerThread getHandlerThread(String tag) {
        HandlerThread handlerThread = null;

        synchronized (threads) {
            handlerThread = threads.get(tag);

            if (handlerThread == null) {
                handlerThread = new HandlerThread(nameOfTag(tag));

                handlerThread.start();

                threads.put(tag, handlerThread);
            }
        }

        return handlerThread;
    }

    private final static String nameOfTag(String tag) {
        return "HT-" + (TextUtils.isEmpty(tag) ? DEFAULT_TAG : tag);
    }
}
