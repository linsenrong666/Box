package com.box.user_center;

import android.accounts.Account;

import com.box.lib.utils.mmkv.MMKVManager;
import com.box.user_center.model.AccountPojo;

public class AccountManager {

    private static volatile AccountManager mInstance;
    private AccountPojo mCurrentUser = null;

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

    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public AccountPojo getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(AccountPojo currentUser) {
        mCurrentUser = currentUser;
    }
}
