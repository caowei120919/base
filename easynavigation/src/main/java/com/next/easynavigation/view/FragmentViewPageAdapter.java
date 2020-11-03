package com.next.easynavigation.view;

import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.next.easynavigation.adapter.ViewPagerAdapter;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-29
 * @Description :
 */
public class FragmentViewPageAdapter extends ViewPagerAdapter<Fragment> {
    private List<Fragment> mFragments;

    public FragmentViewPageAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public Fragment getItemData(int position) {
        return mFragments.size() > position ? mFragments.get(position) : null;
    }

    @Override
    public int getDataPosition(Fragment s) {
        return mFragments.indexOf(s);
    }

    @Override
    public boolean equals(Fragment oldD, Fragment newD) {
        if(newD == null){
            return false ;
        }
        return TextUtils.equals(oldD.getClass().getSimpleName(), newD.getClass().getSimpleName());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
