package com.box.biz.gui.fragments;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.box.biz.R;
import com.box.biz.contracts.HomeContract;
import com.box.biz.presenters.HomePresenter;
import com.box.lib.mvp.FragmentEx;
import com.box.lib.router.url.MainModule;

@Route(path = MainModule.Fragment.HOME)
public class HomeFragment extends FragmentEx<HomePresenter> implements HomeContract.View {

    @Override
    protected int getLayoutId() {
        return R.layout.biz_fragment_home;
    }

    @Override
    protected void initView() {

    }

}
