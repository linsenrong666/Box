package com.box.lib.gui;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.box.lib.R;
import com.box.lib.log.JLog;
import com.box.lib.utils.contents.AbstractOnContentUpdateListener;
import com.box.lib.utils.contents.ContentsManager;
import com.box.lib.utils.contents.OnContentUpdateListener;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment 基类
 *
 * @author Linsr 2018/6/16 上午11:18
 */
public abstract class BaseFragment extends Fragment implements SimpleImmersionOwner {

    protected Activity mActivity;
    protected Context mContext;
    protected String TAG;
    protected ContentsManager mContentsManager;
    private List<AbstractOnContentUpdateListener> mOnContentUpdateListeners = new ArrayList<>();
    /**
     * 当前页面是否展示
     */
    protected boolean mIsVisible;
    private boolean mLazyLoaded;
    private boolean mIsViewCreated;

    protected ImmersionBar mImmersionBar;
    protected FrameLayout mContentLayout;
    protected FrameLayout mNoDataLayout;
    private View mContentView;

    private int mThemeId;

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attach();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            attach();
        }
    }

    protected void attach() {
        TAG = getClass().getSimpleName();
        JLog.v(TAG, "Fragment onAttach.called , this: " + getClass().getName());
        mActivity = getActivity();
        mContext = getContext();
        mContentsManager = ContentsManager.getInstance();

        if (getArguments() != null) {
            initArguments(getArguments());
        }

        if (immersionBarEnabled()) {
            mImmersionBar = ImmersionBar.with(this);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void initArguments(Bundle arguments);

    protected abstract void initView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog.v(TAG, "Fragment onCreate.called, this: " + getClass().getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        JLog.v(TAG, "Fragment onStart.called , this: " + getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        JLog.v(TAG, "Fragment onResume.called , this: " + getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        JLog.v(TAG, "Fragment onPause.called , this: " + getClass().getName());
    }

    @Override
    public void onStop() {
        super.onStop();
        JLog.v(TAG, "Fragment onStop.called , this: " + getClass().getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JLog.v(TAG, "Fragment onDestroyView.called , this: " + getClass().getName());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        JLog.v(TAG, "Fragment onCreateView.called , this: " + getClass().getName());
        View view;
        if (getThemeId() > 0) {
            Context contextThemeWrapper = new ContextThemeWrapper(mActivity, getThemeId());
            LayoutInflater cloneInContext = inflater.cloneInContext(contextThemeWrapper);
            view = cloneInContext.inflate(R.layout.box_fragment_base, container, false);
        } else {
            view = inflater.inflate(R.layout.box_fragment_base, container, false);
        }

        mContentLayout = (FrameLayout) view.findViewById(R.id.base_content_layout);
        mNoDataLayout = (FrameLayout) view.findViewById(R.id.base_no_data_layout);

        if (mContentView != null) {
            mContentLayout.removeView(mContentView);
            mContentView = null;
        }
        if (getLayoutId() != 0) {
            mContentView = inflater.inflate(getLayoutId(), mContentLayout, false);
            mContentLayout.addView(mContentView);
        } else {
            JLog.e(TAG, "error : getLayoutId() is null ");
        }
        return view;
    }

    protected void setThemeId(int themeId) {
        mThemeId = themeId;
    }

    private int getThemeId() {
        return mThemeId;
    }

    abstract protected void setBlankLayout();

    protected <T extends View> T findViewById(int resId) {
        if (mContentView == null) {
            throw new RuntimeException("content view cannot be null!");
        }
        return mContentView.findViewById(resId);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JLog.v(TAG, "Fragment onViewCreated.called , this: " + getClass().getName());
        mIsViewCreated = true;
        initView();
        setBlankLayout();
        lazyLoad();
    }

    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    @Override
    public void initImmersionBar() {
        if (needImmersionNavigationBar()) {
            mImmersionBar.navigationBarColor(setNavigationBarColor());
            mImmersionBar.navigationBarDarkIcon(isDarkIcon());
        }
    }

    protected boolean needImmersionNavigationBar() {
        return true;
    }

    protected boolean isDarkIcon() {
        return true;
    }

    protected int setNavigationBarColor() {
        return R.color.white;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (mIsVisible) {
            onVisible();
            lazyLoad();
        } else {
            onInvisible();
        }
    }

    private void lazyLoad() {
        if (!mLazyLoaded && mIsViewCreated && mIsVisible) {
            loadData();
            mLazyLoaded = true;
        }
    }

    /**
     * 注册事件监听回调
     *
     * @param listener 监听
     */
    protected void registerOnContentUpdateListener(AbstractOnContentUpdateListener listener) {
        if (mContentsManager.registerOnContentUpdateListener(listener)) {
            mOnContentUpdateListeners.add(listener);
        }
    }

    /**
     * 懒加载数据，一般网络请求放在此处，
     * 该方法只会调用一次
     */
    protected void loadData() {
    }

    /**
     * 页面可见
     */
    protected void onVisible() {
        JLog.v(TAG, "Fragment onVisible " + getClass().getName());
        JLog.v(TAG, "mOnContentUpdateListeners.size: " + mOnContentUpdateListeners.size());
        for (AbstractOnContentUpdateListener listener : mOnContentUpdateListeners) {
            if (listener.isUpdateHappened()) {
                listener.onContentUpdated(listener.getCachedObjects());
                listener.clearCache();
            }
        }
    }

    /**
     * 页面隐藏
     */
    protected void onInvisible() {
        JLog.v(TAG, "Fragment onInvisible " + getClass().getName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        JLog.v(TAG, "Fragment onDetach.called , this: " + getClass().getName());
        for (OnContentUpdateListener listener : mOnContentUpdateListeners) {
            mContentsManager.unregisterOnContentUpdateListener(listener);
        }
        mOnContentUpdateListeners.clear();
        mActivity = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
