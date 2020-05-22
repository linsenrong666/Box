package com.box.user_center;

import android.app.Application;

import androidx.annotation.Keep;

import com.box.lib.app.AbsAppLife;

@Keep
public class UserCenterModule extends AbsAppLife {

    @Override
    public void onCreate(Application application) {
        AccountManager.getInstance().init();
    }

    @Override
    public void onChildProcessCreate(Application app, String processName) {

    }

}
