package com.box.biz.contracts;

import com.box.lib.mvp.IPresenter;
import com.box.lib.mvp.IView;

public interface HomeContract {

    interface View extends IView {

        void afterLogout();
    }

    interface Presenter extends IPresenter {
        void logout();
    }
}
