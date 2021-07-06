package com.datacvg.dimp.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.BoardPagerAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.bean.PageItemListBean;
import com.datacvg.dimp.presenter.BoardPresenter;
import com.datacvg.dimp.view.BoardView;
import com.datacvg.dimp.widget.CircleNavigator;
import com.datacvg.dimp.widget.ControlScrollViewPager;
import com.google.gson.Gson;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-02
 * @Description :
 */
public class BoardFragment extends BaseFragment<BoardView, BoardPresenter> implements BoardView, BoardPagerAdapter.OnExtraPageChangeListener {

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.vp_board)
    ControlScrollViewPager vpBoard ;

    private List<PageItemBean> pageItemBeans = new ArrayList<>() ;
    private List<Fragment> pageFragments = new ArrayList<>() ;
    private CircleNavigator circleNavigator;
    private BoardPagerAdapter boardPagerAdapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_board;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupData() {
        getPresenter().getBoardPage();
    }

    @Override
    protected void setupView(View rootView) {
        initPageAdapter();
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        circleNavigator = new CircleNavigator(mContext);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleCount(pageFragments.size());
        circleNavigator.setCircleSpacing(20);
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, vpBoard);
    }

    private void initPageAdapter() {
        boardPagerAdapter = new BoardPagerAdapter(getChildFragmentManager(),vpBoard,pageFragments);
        boardPagerAdapter.setOnExtraPageChangeListener(this);
    }

    @Override
    public void getPageSuccess(List<PageItemBean> pageItemBeans) {
        this.pageItemBeans.clear();
        pageFragments.clear();
        if(pageItemBeans.isEmpty()){
            boardPagerAdapter.notifyDataSetChanged();
        }else{
            this.pageItemBeans.addAll(pageItemBeans);
            for (PageItemBean pageItemBean : pageItemBeans){
                String[] dimensionArr = new Gson().fromJson(pageItemBean.getDimensions(),String[].class);
                switch (dimensionArr.length){
                    case 1 :
                        pageItemBean.setmOrgDimension(dimensionArr[0]);
                        break;

                    case 2 :
                        pageItemBean.setmOrgDimension(dimensionArr[0]);
                        pageItemBean.setmFuDimension(dimensionArr[1]);
                        break;

                    case 3 :
                        pageItemBean.setmOrgDimension(dimensionArr[0]);
                        pageItemBean.setmFuDimension(dimensionArr[1]);
                        pageItemBean.setmPDimension(dimensionArr[2]);
                        break;
                }
                pageFragments.add(BoardPagerFragment.newInstance(pageItemBean));
            }
            boardPagerAdapter.notifyDataSetChanged();
            circleNavigator.setCircleCount(pageFragments.size());
            magicIndicator.getNavigator().notifyDataSetChanged();
            vpBoard.setCurrentItem(0);
        }
    }

    @Override
    public void onExtraPageSelected(int i) {

    }

    @Override
    public void onExtraPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onExtraPageScrollStateChanged(int i) {

    }
}
