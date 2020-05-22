package com.box.lib.utils.mmkv;

import android.app.Application;
import android.text.TextUtils;

import com.box.lib.log.JLog;
import com.tencent.mmkv.MMKV;

public class MMKVManager {

    private static final String TAG = MMKVManager.class.getSimpleName();

    private MMKVManager() {
    }

    private static volatile MMKVManager mInstance;

    public static MMKVManager getInstance() {
        if (mInstance == null) {
            synchronized (MMKVManager.class) {
                if (mInstance == null) {
                    mInstance = new MMKVManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Application application) {
        String rootDir = MMKV.initialize(application);
        JLog.i(TAG, "mmkv root: " + rootDir);
    }

    public MMKV mmkv() {
        return mmkv(null);
    }

    public MMKV mmkv(String name) {
        if (TextUtils.isEmpty(name)) {
            return MMKV.defaultMMKV();
        }
        return MMKV.mmkvWithID(name);
    }

}
