package com.box.lib.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import androidx.annotation.NonNull;

import java.util.LinkedList;

/**
 * Description
 *
 * @author Linsr 2019/1/20 下午1:50
 */
public class AppLife implements Application.ActivityLifecycleCallbacks {

    private final LinkedList<Activity> mActivities = new LinkedList<>();

    private AppLife() {
    }

    private volatile static AppLife mInstance;

    public static AppLife getInstance() {
        if (mInstance == null) {
            synchronized (AppLife.class) {
                if (mInstance == null) {
                    mInstance = new AppLife();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        mActivities.addFirst(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    /**
     * 清除 除了自己外其他activity
     *
     * @param oneself 不被移除的activity
     */
    public void removeOtherActivity(Activity oneself) {
        try {
            for (Activity activity : mActivities) {
                if (activity != null && !activity.getLocalClassName().equals(oneself.getLocalClassName())) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 退出应用时调用
     */
    public void exit() {
        for (Activity activity : mActivities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

}
