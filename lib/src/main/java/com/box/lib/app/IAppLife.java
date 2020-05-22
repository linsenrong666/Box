package com.box.lib.app;

import android.app.Application;

public interface IAppLife {

    /**
     * app创建
     *
     * @param application
     */
    void onCreate(Application application);

    /**
     * 子进程创建的回调
     *
     * @param app         application
     * @param processName 进程名称
     */
    void onChildProcessCreate(Application app, String processName);

    /**
     * @param application
     */
    void onTerminate(Application application);

}
