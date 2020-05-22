package com.box.uikit.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author Linsr 2018/7/11 下午4:21
 */
public class FragmentPagerAdapterEx extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> mTitles;

    public FragmentPagerAdapterEx(FragmentManager fm, List<Fragment> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mList = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    public FragmentPagerAdapterEx(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mList = new ArrayList<>();
    }

    public void setTitles(List<String> titles) {
        mTitles = titles;
    }

    public void addData(List<Fragment> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles.get(position);
        }
        return super.getPageTitle(position);
    }
}
