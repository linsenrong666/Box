package com.box.user_center.model;

import com.box.lib.model.pojo.BasePojo;

public class AccountPojo extends BasePojo {

    private String account;
    private String sessionId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
