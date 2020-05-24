package com.box.biz.presenters;

import android.text.TextUtils;

import com.box.biz.contracts.LoginContract;
import com.box.lib.mvp.PresenterEx;
import com.box.lib.utils.ToastUtils;
import com.box.user_center.AccountManager;

public class LoginPresenter extends PresenterEx<LoginContract.View> implements LoginContract.Presenter {

    public LoginPresenter(LoginContract.View IView) {
        super(IView);
        TAG = LoginPresenter.class.getSimpleName();
    }

    @Override
    public void login(String account, String password) {
//        if (TextUtils.isEmpty(account)) {
//            getView().onFailed("用户名/手机号不能为空");
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            getView().onFailed("密码不能为空");
//            return;
//        }
        AccountManager.getInstance().login("a");
        getView().onLoginSucceed();
    }
}
