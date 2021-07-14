package com.datacvg.dimp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-28
 * @Description :
 */
public class BoardPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private int currentPageIndex = 0;

    private OnExtraPageChangeListener onExtraPageChangeListener;

    public int getCurrentPageIndex(){
        return currentPageIndex ;
    }

    public BoardPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager
            , List<Fragment> fragments) {
        super(fragmentManager);
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.viewPager.setAdapter(this);
        this.viewPager.setOnPageChangeListener(this);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    /**
     * 设置页面切换额外功能监听器
     * @param onExtraPageChangeListener
     */
    public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        if(null != onExtraPageChangeListener){
            onExtraPageChangeListener.onExtraPageScrolled(i, v, i2);
        }
    }

    @Override
    public void onPageSelected(int i) {
        fragments.get(i).onPause();
        if(fragments.get(i).isAdded()){
            fragments.get(i).onResume();
        }
        currentPageIndex = i;
        if(null != onExtraPageChangeListener){
            onExtraPageChangeListener.onExtraPageSelected(i);
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if(null != onExtraPageChangeListener){
            onExtraPageChangeListener.onExtraPageScrollStateChanged(i);
        }
    }



    /**
     * page切换额外功能接口
     */
    public interface OnExtraPageChangeListener{
        void onExtraPageSelected(int i);
        void onExtraPageScrolled(int i, float v, int i2);
        void onExtraPageScrollStateChanged(int i);
    }
}
