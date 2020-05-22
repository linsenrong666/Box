package com.box.biz.presenters;

import com.box.biz.contracts.MainContract;
import com.box.lib.mvp.PresenterEx;

public class MainPresenter extends PresenterEx<MainContract.View> implements MainContract.Presenter {
    public MainPresenter(MainContract.View IView) {
        super(IView);
    }
}
