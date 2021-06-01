package com.datacvg.dimp.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.datacvg.dimp.fragment.DigitalFragment;

import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-28
 * @Description :
 */
public class BoardPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private List<Fragment> fragments;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private int currentPageIndex = 0;

    private OnExtraPageChangeListener onExtraPageChangeListener;

    public BoardPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager
            , List<Fragment> fragments) {
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
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);
        if(!fragment.isAdded()){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commit();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            fragmentManager.executePendingTransactions();
        }

        if(fragment.getView().getParent() == null){
            container.addView(fragment.getView());
        }

        return fragment.getView();
    }

    /**
     * 当前page索引（切换之前）
     * @return
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
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
        int position = i ;
        if(i >= fragments.size()){
            position = 0 ;
        }
        fragments.get(position).onPause();
        if(fragments.get(position).isAdded()){
            fragments.get(position).onResume();
        }
        currentPageIndex = position;
        if(null != onExtraPageChangeListener){
            onExtraPageChangeListener.onExtraPageSelected(position);
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
        void onExtraPageScrolled(int i, float v, int i2);
        void onExtraPageSelected(int i);
        void onExtraPageScrollStateChanged(int i);
    }
}
