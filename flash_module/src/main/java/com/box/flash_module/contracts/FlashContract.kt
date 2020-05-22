package com.box.flash_module.contracts

import com.box.lib.mvp.IPresenter
import com.box.lib.mvp.IView

interface FlashContract {

    interface View : IView {
        fun startActivity(activity: Int)
    }

    interface Presenter : IPresenter {
        fun startNext()
    }
}