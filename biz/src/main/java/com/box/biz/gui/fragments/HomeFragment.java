package com.box.biz.gui.fragments;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.box.biz.R;
import com.box.biz.contracts.HomeContract;
import com.box.biz.presenters.HomePresenter;
import com.box.lib.mvp.FragmentEx;
import com.box.lib.router.Router;
import com.box.lib.router.url.LoginModule;
import com.box.lib.router.url.MainModule;

import androidx.recyclerview.widget.RecyclerView;

@Route(path = MainModule.Fragment.HOME)
public class HomeFragment extends FragmentEx<HomePresenter> implements HomeContract.View {

    private TextView mLogoutBtn;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.biz_fragment_home;
    }

    @Override
    protected HomePresenter bindPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {
        mLogoutBtn = findViewById(R.id.user_info_logout);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter != null) {
                    mPresenter.logout();
                }
            }
        });
    }

    @Override
    protected void loadData() {
        mLogoutBtn.setText("登出");
    }

    @Override
    public void afterLogout() {
        if (getActivity() != null) {
            getActivity().finish();
        }
        Router.startActivity(LoginModule.Activity.LOGIN);
    }
}
