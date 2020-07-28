package com.datacvg.sempmobile.baseandroid.manager;

import android.app.Activity;
import android.os.Build;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * FileName: BaseAppManager
 * Author: 曹伟
 * Date: 2019/9/16 14:57
 * Description:
 */

public class BaseAppManager {
    private Stack<WeakReference<Activity>> activitys = new Stack<>();
    private static BaseAppManager manager ;

    public BaseAppManager() {

    }

    public static boolean isActivityFinished(Activity activity) {
        if (activity == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isFinishing() || activity.isDestroyed();
        } else {
            return activity.isFinishing();
        }
    }

    /**
     * 创建manager的单一实例，确保应用中只有一个manager
     */
    public static BaseAppManager getManager(){
        if(manager == null){
            synchronized (BaseAppManager.class){
                if(manager == null){
                    manager = new BaseAppManager();
                }
            }
        }
        return manager ;
    }

    /**
     * 将新建的activity添加到activity堆栈中，便于管理
     * @param activity
     */
    public void addActivity(WeakReference<Activity> activity){
        activitys.add(activity);
    }

    /**
     * activity在onDestroy的将该activity从堆栈中移除
     * @param softActivity
     */
    public void removeActivity(WeakReference<Activity> softActivity) {
        activitys.remove(softActivity);
    }

    /**
     * 获取当前activity,即最后一个压入到堆栈中的
     * @return
     */
    public Activity currentActivity(){
        Activity activity = activitys.lastElement().get();
        return activity ;
    }

    /**
     * 结束当前activity,即最后一个压入堆栈中的
     */
    public void finishCurrent(){
        WeakReference<Activity> activityWeakReference = activitys.lastElement();
        activitys.remove(activityWeakReference);
        Activity ac = activityWeakReference.get();
        if(ac != null){
            ac.finish();
        }
    }

    /**
     * 结束堆栈中所有的activity，用于退出操作
     */
    public void finishAllActivity(){
        for(int i = 0; i < activitys.size() ; i++){
            WeakReference<Activity> activityWeakReference = activitys.get(i);
            if(activityWeakReference != null){
                Activity activity = activityWeakReference.get() ;
                if(activity != null){
                    activity.finish();
                }
            }
        }
        activitys.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp(){
        try {
            finishAllActivity();
            //杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }catch (Exception e){

        }
    }
}
