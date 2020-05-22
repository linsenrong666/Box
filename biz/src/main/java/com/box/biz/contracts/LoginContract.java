package com.box.biz.contracts;

import com.box.lib.mvp.IPresenter;
import com.box.lib.mvp.IView;

public interface LoginContract {

    interface View extends IView {

        void onLoginSucceed();

        void onFailed(String msg);
    }

    interface Presenter extends IPresenter {
        void login(String account, String password);
    }
}
