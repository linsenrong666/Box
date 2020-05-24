package com.box.user_center;

import android.text.TextUtils;

import com.box.lib.utils.mmkv.MMKVManager;
import com.box.user_center.model.AccountPojo;
import com.tencent.mmkv.MMKV;

public class AccountManager implements AccountConstants {

    private static volatile AccountManager mInstance;
    private AccountPojo mCurrentUser = null;
    private MMKV mAccountMMKV;

    public static AccountManager getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        synchronized (AccountManager.class) {
            if (mInstance == null) {
                mInstance = new AccountManager();
            }
        }
        return mInstance;
    }

    public void init() {
        mAccountMMKV = MMKVManager.getInstance().mmkv(MMKV_ACCOUNT);
        saveAccount(mAccountMMKV.getString(KEY_ACCOUNT, null));
    }

    public boolean isLoggedIn() {
        return getAccount() != null;
    }

    public void login(String account) {
        saveAccount(account);
    }

    public void logout() {
        mAccountMMKV.encode(KEY_ACCOUNT, "");
        mAccountMMKV.encode(KEY_SESSION_ID, "");
    }

    public void saveAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            return;
        }
        mAccountMMKV.encode(KEY_ACCOUNT, account);
        mAccountMMKV.encode(KEY_SESSION_ID, account);
    }

    public AccountPojo getAccount() {
        String account = mAccountMMKV.decodeString(KEY_ACCOUNT);
        if (TextUtils.isEmpty(account)) {
            return null;
        }
        AccountPojo pojo = new AccountPojo();
        pojo.setAccount(account);
        return pojo;
    }
}
