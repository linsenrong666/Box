package com.box.flash_module.gui.activities

import android.Manifest
import android.os.Bundle
import android.widget.TextView
import com.box.flash_module.R
import com.box.flash_module.contracts.FlashContract
import com.box.flash_module.presenters.FlashPresenter
import com.box.lib.mvp.ActivityEx
import com.box.lib.router.Router
import com.box.lib.router.url.LoginModule
import com.box.lib.router.url.MainModule
import com.box.lib.utils.Permissions
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class FlashActivity : ActivityEx<FlashPresenter>(), FlashContract.View {


    override fun getLayoutId(): Int {
        return R.layout.flash_activity_main;
    }

    override fun bindPresenter(): FlashPresenter {
        return FlashPresenter(this)
    }

    override fun showTitleView(): Boolean {
        return false
    }

    override fun initView() {
        findViewById<TextView>(R.id.flash_main_tv).setText(R.string.app_name);

    }


    override fun initData(savedInstanceState: Bundle?) {
        requestPermissions();
    }

    @AfterPermissionGranted(Permissions.REQUEST_STORAGE)
    private fun requestPermissions() {
        if (EasyPermissions.hasPermissions(this, *Permissions.PERMISSIONS_STORAGE)) {
            mPresenter.startNext()
        } else {
            EasyPermissions.requestPermissions(this,
                    "应用需要存储权限",
                    Permissions.REQUEST_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    override fun startActivity(activity: Int) {
        when (activity) {
            FlashPresenter.NEXT_ACT_MAIN -> {
                Router.startActivity(MainModule.Activity.MAIN)
            }
            FlashPresenter.NEXT_ACT_LOGIN -> {
                Router.startActivity(LoginModule.Activity.LOGIN)
            }
        }
        finish()
    }
}