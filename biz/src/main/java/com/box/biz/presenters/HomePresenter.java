package com.box.biz.presenters;

import com.box.biz.contracts.HomeContract;
import com.box.lib.mvp.PresenterEx;

public class HomePresenter extends PresenterEx<HomeContract.View> implements HomeContract.Presenter {
    public HomePresenter(HomeContract.View IView) {
        super(IView);
    }
}
