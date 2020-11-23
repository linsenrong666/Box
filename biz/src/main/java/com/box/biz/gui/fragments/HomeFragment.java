package com.box.biz.gui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.box.biz.R;
import com.box.biz.contracts.HomeContract;
import com.box.biz.presenters.HomePresenter;
import com.box.lib.album.Album;
import com.box.lib.mvp.FragmentEx;
import com.box.lib.router.Params;
import com.box.lib.router.Router;
import com.box.lib.router.url.CommonModule;
import com.box.lib.router.url.LoginModule;
import com.box.lib.router.url.MainModule;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

@Route(path = MainModule.Fragment.HOME)
public class HomeFragment extends FragmentEx<HomePresenter> implements HomeContract.View {

    private TextView mLogoutBtn;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.biz_fragment_home;
    }

    @Override
    protected HomePresenter bindPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {
        mLogoutBtn = findViewById(R.id.user_info_logout);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPresenter != null) {
                    mPresenter.logout();
                }
            }
        });
        findViewById(R.id.to_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.getInstance().chooseOne(getActivity());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Album.getInstance().chooseResult(requestCode, resultCode, data, new Album.OnChooseResultListener() {
            @Override
            public void onChooseResult(List<Uri> list) {
                Params params = new Params();
                params.put(CommonModule.Activity.VideoParams.VIDEO_PATH, list.get(0));
                Router.startActivity(CommonModule.Activity.VIDEO_PAGE, params);
            }
        });
    }

    @Override
    protected void loadData() {
        mLogoutBtn.setText("登出");
    }

    @Override
    public void afterLogout() {
        if (getActivity() != null) {
            getActivity().finish();
        }
        Router.startActivity(LoginModule.Activity.LOGIN);
    }
}
