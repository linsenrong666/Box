package com.box.biz.presenters;

import com.box.biz.contracts.HomeContract;
import com.box.lib.mvp.PresenterEx;
import com.box.user_center.AccountManager;

public class HomePresenter extends PresenterEx<HomeContract.View> implements HomeContract.Presenter {
    public HomePresenter(HomeContract.View IView) {
        super(IView);
    }

    @Override
    public void logout() {
        AccountManager.getInstance().logout();
        getView().afterLogout();
    }
}
