package com.box.flash_module.presenters

import android.os.Handler
import android.os.Message
import androidx.lifecycle.LifecycleOwner
import com.box.flash_module.contracts.FlashContract
import com.box.lib.mvp.PresenterEx
import com.box.user_center.AccountManager

class FlashPresenter(view: FlashContract.View) : PresenterEx<FlashContract.View>(view), FlashContract.Presenter {

    companion object {
        const val NEXT_ACT_MAIN = 0
        const val NEXT_ACT_LOGIN = 1

        const val FLASH_TIME: Long = 2 * 1000L
        const val MSG_START_NEXT = 1
    }

    private val mAccountManager: AccountManager = AccountManager.getInstance()

    private val mHandler: Handler = Handler {
        if (it.what == MSG_START_NEXT) {
            val nextAct = it.arg1
            getView().startActivity(nextAct)
        }
        false
    }

    override fun onCreate(owner: LifecycleOwner) {
        TAG = FlashPresenter::class.simpleName;
        super.onCreate(owner)
    }

    override fun startNext() {
        val nextAct = if (mAccountManager.isLoggedIn) {
            NEXT_ACT_MAIN
        } else {
            NEXT_ACT_LOGIN
        }
        val msg: Message = Message.obtain();
        msg.what = MSG_START_NEXT;
        msg.arg1 = nextAct;
        mHandler.sendMessageDelayed(msg, FLASH_TIME)
    }
}