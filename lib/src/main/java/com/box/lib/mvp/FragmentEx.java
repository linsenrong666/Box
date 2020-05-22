package com.box.lib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.lifecycle.Lifecycle;

import com.box.lib.R;
import com.box.lib.gui.BaseFragment;
import com.box.lib.log.JLog;

/**
 * Description
 *
 * @author Linsr 2018/6/16 下午2:42
 */
public abstract class FragmentEx<P extends IPresenter> extends BaseFragment implements IView {

    protected P mPresenter;

    @Override
    protected void attach() {
        super.attach();
        mPresenter = bindPresenter();
        initLifecycleObserver(getLifecycle());
    }

    protected P bindPresenter() {
        return null;
    }

    @Override
    protected void initArguments(Bundle arguments) {

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
    protected void setBlankLayout() {
        View mNoDataView = LayoutInflater.from(mActivity).inflate(R.layout.box_layout_no_data,
                (ViewGroup) mNoDataLayout.getParent(), false);
        mNoDataLayout.addView(mNoDataView);
    }

    @Override
    public void showBlankLayout() {
        mContentLayout.setVisibility(View.GONE);
        mNoDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBlankLayout() {
        mContentLayout.setVisibility(View.VISIBLE);
        mNoDataLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        if (mActivity instanceof ActivityEx) {
            ((ActivityEx) mActivity).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mActivity instanceof ActivityEx) {
            ((ActivityEx) mActivity).hideLoading();
        }
    }

    @Override
    public void showError(String text) {
        mContentLayout.setVisibility(View.VISIBLE);
        mNoDataLayout.setVisibility(View.GONE);
    }

    @Override
    public Context getSelf() {
        return getContext();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy(this);
            mPresenter = null;
        }
    }
}
