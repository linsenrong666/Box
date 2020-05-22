package com.box.biz.gui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.box.biz.R;
import com.box.biz.contracts.MainContract;
import com.box.biz.presenters.MainPresenter;
import com.box.lib.log.JLog;
import com.box.lib.mvp.ActivityEx;
import com.box.lib.router.Router;
import com.box.lib.router.url.MainModule;
import com.box.uikit.adapters.FragmentPagerAdapterEx;
import com.box.uikit.widgets.NoScrollViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

@Route(path = MainModule.Activity.MAIN)
public class MainActivity extends ActivityEx<MainPresenter> implements MainContract.View, ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {


    private BottomNavigationView mBottomNavigationView;
    private NoScrollViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    protected int getLayoutId() {
        return R.layout.biz_activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.main_view_pager);
        mBottomNavigationView = findViewById(R.id.main_nav_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
        initFragment();

        //默认选择home页
        toHomePage();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(Router.findFragment(MainModule.Fragment.HOME));

        FragmentPagerAdapterEx mPagerAdapterEx = new FragmentPagerAdapterEx(getSupportFragmentManager());
        mPagerAdapterEx.addData(mFragmentList);

        mViewPager.setAdapter(mPagerAdapterEx);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.item_home) {
            mTitleView.setVisibility(View.GONE);
            mViewPager.setCurrentItem(0);
            return true;
        }
//        else if (i == R.id.item_category) {
//            mTitleView.setVisibility(View.GONE);
//            mViewPager.setCurrentItem(1);
//            return true;
//        } else if (i == R.id.item_cart) {
//            mTitleView.setVisibility(View.VISIBLE);
//            mTitleView.setTitleText(getString(R.string.main_cart));
//            mViewPager.setCurrentItem(2);
//            return true;
//        } else if (i == R.id.item_me) {
//            mTitleView.setVisibility(View.VISIBLE);
//            mTitleView.setTitleText(getString(R.string.main_me_center));
//            mViewPager.setCurrentItem(3);
//            return true;
//        }
        else {
            return false;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void toHomePage() {
        if (mBottomNavigationView != null) {
            mBottomNavigationView.setSelectedItemId(R.id.item_home);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int position = mViewPager.getCurrentItem();
        if (mFragmentList != null && mFragmentList.size() > position) {
            mFragmentList.get(position).onActivityResult(requestCode, resultCode, data);
        } else {
            JLog.e(TAG, "error onActivityResult. mFragmentList:"
                    + mFragmentList + ",cur position:" + position);
        }
    }

}
