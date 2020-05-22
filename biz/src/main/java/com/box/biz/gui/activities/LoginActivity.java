package com.box.biz.gui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.box.biz.R;
import com.box.biz.contracts.LoginContract;
import com.box.biz.presenters.LoginPresenter;
import com.box.lib.mvp.ActivityEx;
import com.box.lib.router.Router;
import com.box.lib.router.url.LoginModule;
import com.box.lib.router.url.MainModule;
import com.box.lib.utils.ToastUtils;

@Route(path = LoginModule.Activity.LOGIN)
public class LoginActivity extends ActivityEx<LoginPresenter> implements LoginContract.View {

    private EditText mAccountEditText;
    private EditText mPasswordEditText;

    @Override
    protected int getLayoutId() {
        return R.layout.biz_activity_login;
    }

    @Override
    protected void initView() {
        initTitleView(R.string.login);
        mAccountEditText = findViewById(R.id.login_account_et);
        mPasswordEditText = findViewById(R.id.login_password_et);

        TextView registerTextView = findViewById(R.id.login_register_tv);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.startActivity(LoginModule.Activity.REGISTER);
            }
        });

        TextView mForgotTextView = findViewById(R.id.login_forgot_password_tv);
        mForgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.startActivity(LoginModule.Activity.FIND_PASSWORD);
            }
        });

        Button mConfirmButton = findViewById(R.id.login_confirm_btn);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mAccountEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                mPresenter.login(userName, password);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected LoginPresenter bindPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onLoginSucceed() {
        Router.startActivity(MainModule.Activity.MAIN);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        ToastUtils.show(msg);
    }
}
