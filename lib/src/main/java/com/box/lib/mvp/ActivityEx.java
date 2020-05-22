package com.box.lib.mvp;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.lifecycle.Lifecycle;

import com.box.lib.R;
import com.box.lib.gui.BaseActivity;
import com.box.lib.log.JLog;
import com.box.uikit.widgets.TitleView;

/**
 * 和业务相关的activity基类
 *
 * @author Linsr 2018/6/16 下午2:42
 */
public abstract class ActivityEx<P extends IPresenter> extends BaseActivity implements IView {

    private final Object mLockObject = new Object();
    private Dialog mTransparentDialog;
    protected TitleView mTitleView;
    protected P mPresenter;

    @Override
    protected void init(Intent intent) {
        super.init(intent);
        mPresenter = bindPresenter();
        initLifecycleObserver(getLifecycle());
    }

    protected P bindPresenter() {
        return null;
    }

    @Override
    public Context getSelf() {
        return this;
    }

    @CallSuper
    @MainThread
    protected void initLifecycleObserver(Lifecycle lifecycle) {
        if (mPresenter != null) {
            mPresenter.setLifecycleOwner(this);
            lifecycle.addObserver(mPresenter);
        } else {
            JLog.w(TAG, "ERROR: Presenter is null !!!");
        }
    }

    @Override
    protected void initTopLayout(FrameLayout topLayout) {
        if (showTitleView()) {
            topLayout.setVisibility(View.VISIBLE);
            mTitleView = new TitleView(this);
            topLayout.addView(mTitleView);
        }
    }

    protected void initTitleView(int titleTextResId) {
        this.initTitleView(titleTextResId, R.mipmap.ic_back,
                0, 0,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back();
                    }
                }, null);
    }

    protected void initTitleViewWithRightText(int titleTextResId,
                                              int rightTextResId,
                                              View.OnClickListener rightClickListener) {
        this.initTitleView(titleTextResId, R.mipmap.ic_back, 0, rightTextResId,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back();
                    }
                }, rightClickListener);
    }

    protected void initTitleView(int titleTextResId,
                                 int leftImage,
                                 int rightImage,
                                 int rightTextResId,
                                 View.OnClickListener leftClickListener,
                                 View.OnClickListener rightClickListener) {
        if (mTitleView == null) {
            JLog.e(TAG, "error: title view has not be initialized.");
            return;
        }
        mTitleView.setTitleText(getString(titleTextResId));
        if (leftImage != 0) {
            mTitleView.setLeftImage(leftImage);
        }
        if (rightImage != 0) {
            mTitleView.setRightImage(rightImage);
        }
        if (rightTextResId != 0) {
            mTitleView.setRightText(getString(rightTextResId));
        }
        if (leftClickListener != null) {
            mTitleView.setOnLeftClickListener(leftClickListener);
        }
        if (rightClickListener != null) {
            mTitleView.setOnRightClickListener(rightClickListener);
        }
    }

    public TitleView getTitleView() {
        return mTitleView;
    }

    /**
     * @return 是否显示标题栏
     */
    protected boolean showTitleView() {
        return true;
    }

    /**
     * 设置空数据托底页面，子类可以修改定制
     */
    @Override
    protected void setNoDataLayout() {
        View mNoDataView = LayoutInflater.from(this).inflate(R.layout.box_layout_no_data,
                getRootContent(), false);
        mNoDataLayout.addView(mNoDataView);
    }

    @Override
    public void showBlankLayout() {
        mNoDataLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideBlankLayout() {
        mNoDataLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        synchronized (mLockObject) {
            hideLoading();
            mTransparentDialog = mDialogFactory.createTransparentProgressDialog(this);
            mDialogFactory.showDialog(mTransparentDialog);
        }
    }

    @Override
    public void hideLoading() {
        synchronized (mLockObject) {
            if (mTransparentDialog != null) {
                mDialogFactory.dismissDialog(mTransparentDialog);
                mTransparentDialog = null;
            }
        }
    }

    @Override
    public void showError(String text) {
        mNoDataLayout.setVisibility(View.VISIBLE);
        mContentLayout.setVisibility(View.GONE);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy(this);
            mPresenter = null;
        }
    }


}
