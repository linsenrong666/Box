package com.box.lib.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.box.lib.R;
import com.box.lib.log.JLog;
import com.box.lib.utils.contents.AbstractOnContentUpdateListener;
import com.box.lib.utils.contents.ContentsManager;
import com.box.lib.utils.contents.OnContentUpdateListener;
import com.box.uikit.dialogs.DialogFactory;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Activity基类
 *
 * @author Linsr 2018/6/16 上午11:17
 */
public abstract class BaseActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks {

    protected String TAG;
    protected ContentsManager mContentsManager;
    private List<AbstractOnContentUpdateListener> mOnContentUpdateListeners = new ArrayList<>();
    protected boolean mIsActive = true;

    private RelativeLayout mMiddleLayout;
    private FrameLayout mTopLayout;
    protected FrameLayout mBottomLayout;
    protected FrameLayout mContentLayout;
    protected FrameLayout mNoDataLayout;

    protected DialogFactory mDialogFactory;

    /**
     * 加载layout id
     *
     * @return id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化 view
     */
    protected abstract void initView();

    /**
     * 初始化 data
     */
    protected abstract void initData(Bundle savedInstanceState);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置无title样式
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        init(getIntent());
        findView();
        setContentLayout();
        setNoDataLayout();
        initTopLayout(mTopLayout);
        onCreateEx(savedInstanceState);
    }

    /**
     * 初始化顶部布局
     */
    protected void initTopLayout(FrameLayout topLayout) {
    }

    protected void onCreateEx(@Nullable Bundle savedInstanceState) {
        initView();
        initData(savedInstanceState);
    }

    private void setContentLayout() {
        View mContentView = LayoutInflater.from(this).inflate(getLayoutId(), getRootContent(), false);
        mContentLayout.addView(mContentView);
    }

    /**
     * 初始化，执行顺序很靠前，建议再此进行接收intent参数，初始化变量等操作
     */
    protected void init(Intent intent) {
        TAG = getClass().getSimpleName();
        mContentsManager = ContentsManager.getInstance();
        mDialogFactory = DialogFactory.getInstance();
    }

    /**
     * 设置空数据托底页面，子类可以修改定制
     */
    protected void setNoDataLayout() {
    }

    private void findView() {
        setContentView(R.layout.box_activity_base);
        mTopLayout = (FrameLayout) findViewById(R.id.base_top_layout);
        mMiddleLayout = (RelativeLayout) findViewById(R.id.base_middle_layout);
        mBottomLayout = (FrameLayout) findViewById(R.id.base_bottom_layout);
        mContentLayout = (FrameLayout) findViewById(R.id.base_content_layout);
        mNoDataLayout = (FrameLayout) findViewById(R.id.base_no_data_layout);
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

    @Override
    protected void onResume() {
        super.onResume();
        JLog.v(TAG, "Activity onResume.called , this: " + getClass().getName());
        JLog.v(TAG, "mOnContentUpdateListeners.size: " + mOnContentUpdateListeners.size());
        mIsActive = true;
        for (AbstractOnContentUpdateListener listener : mOnContentUpdateListeners) {
            if (listener.isUpdateHappened()) {
                listener.onContentUpdated(listener.getCachedObjects());
                listener.clearCache();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsActive = false;
        JLog.v(TAG, "Activity onPause.called , this: " + getClass().getName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> list) {
        JLog.i(TAG, "===成功获取权限，requestCode:" + requestCode + "，list:" + list.toString());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> list) {
        JLog.i(TAG, "===获取权限失败，requestCode:" + requestCode + "，list:" + list.toString());
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
            sb.append("\n");
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("需要权限")
                    .setRationale("此功能需要" + sb + "权限,否则无法正常使用,是否打开设置")
                    .setPositiveButton("是")
                    .setNegativeButton("否")
                    .build().show();
        }
    }

    /**
     * 获取根视图
     */
    protected ViewGroup getRootContent() {
        return ((ViewGroup) findViewById(android.R.id.content));
    }

    /**
     * 后退
     */
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (OnContentUpdateListener listener : mOnContentUpdateListeners) {
            mContentsManager.unregisterOnContentUpdateListener(listener);
        }
        mOnContentUpdateListeners.clear();
    }

    public static void startSelf(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public int setSystemBarColor() {
        return R.color.white;
    }
}
