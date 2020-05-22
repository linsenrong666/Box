package com.box.lib.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.box.lib.log.JLog;
import com.box.lib.utils.mmkv.MMKVManager;

import java.util.List;

public class BoxApp extends Application {

    protected String TAG;

    private static BoxApp sApp;

    public static BoxApp getInstance() {
        return sApp;
    }

    private AppDelegate mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        sApp = this;
        mAppDelegate = new AppDelegate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setup();
        mAppDelegate.onCreate(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mAppDelegate.onTerminate(this);
    }

    private void setup() {
        //init log
        JLog.init(isDebug());
        //init router
        initRouter();
        //init mmkv
        MMKVManager.getInstance().init(this);
        //注册生命周期监听
        registerActivityLifecycleCallbacks(AppLife.getInstance());
    }

    /**
     * Application 是否在主进程
     *
     * @return 是否在主进程
     */
    protected boolean isInMainProcesses(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();

        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            JLog.i(TAG, "my.pid -> " + myPid + ",mainProcessName -> " + mainProcessName);
            JLog.i(TAG, "info.pid -> " + info.pid + ",info.processName -> " + info.processName);
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDebug() {
        return true;
    }

    private void initRouter() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

}
