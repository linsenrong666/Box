package com.box.lib.mvp;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.box.lib.log.JLog;


/**
 * Description
 *
 * @author Linsr 2018/12/9 下午5:06
 */
public abstract class PresenterEx<V extends IView> implements IPresenter {

    protected String TAG = PresenterEx.class.getSimpleName();
    private V mView;
    private Context mContext;
    private LifecycleOwner mLifecycleOwner;

    public PresenterEx(V IView) {
        mView = IView;
        if (mView != null) {
            mContext = mView.getSelf();
        }
    }

    protected Context getContext() {
        return mContext;
    }

    protected V getView() {
        return mView;
    }

    protected LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    @Override
    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public void onLifecycleChanged(@NonNull LifecycleOwner owner,
                                   @NonNull Lifecycle.Event event) {
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        JLog.v(TAG, "onCreate.called this:" + getClass().getSimpleName());
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        JLog.v(TAG, "onStart.called this:" + getClass().getSimpleName());
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        JLog.v(TAG, "onResume.called this:" + getClass().getSimpleName());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        JLog.v(TAG, "onPause.called this:" + getClass().getSimpleName());
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        JLog.v(TAG, "onStop.called this:" + getClass().getSimpleName());
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        JLog.v(TAG, "onDestroy.called this:" + getClass().getSimpleName());
        mView = null;
        mContext = null;
    }
}
