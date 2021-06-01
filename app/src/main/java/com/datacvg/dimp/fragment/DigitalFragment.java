package com.datacvg.dimp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.BoardPagerAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.bean.PageItemListBean;
import com.datacvg.dimp.event.HideNavigationEvent;
import com.datacvg.dimp.presenter.DigitalPresenter;
import com.datacvg.dimp.view.DigitalView;
import com.datacvg.dimp.widget.TitleNavigator;
import com.enlogy.statusview.StatusRelativeLayout;
import com.google.gson.Gson;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 数字神经
 */
public class DigitalFragment extends BaseFragment<DigitalView, DigitalPresenter>
        implements DigitalView, TitleNavigator.OnTabSelectedListener ,BoardPagerAdapter.OnExtraPageChangeListener{
    @BindView(R.id.status_title)
    StatusRelativeLayout statusTitle ;
    @BindView(R.id.tv_manage)
    TextView tvManage ;
    @BindView(R.id.tv_name)
    TextView tvName ;
    @BindView(R.id.tv_time)
    TextView tvTime ;
    @BindView(R.id.magic_title)
    MagicIndicator magicTitle ;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.status_board)
    StatusRelativeLayout statusBoard ;
    @BindView(R.id.vp_board)
    ViewPager vpBoard ;

    private TitleNavigator titleNavigator ;
    private FragmentContainerHelper mTitleFragmentContainerHelper ;
    private List<PageItemBean> pageItemBeans = new ArrayList<>() ;
    private BoardPagerAdapter adapter ;
    private List<Fragment> fragments = new ArrayList<>() ;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_digital;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        statusTitle.setOnItemClickListener(R.id.tv_complete,view -> {
            PLog.e("完成");
            statusTitle.showContent();
            EventBus.getDefault().post(new HideNavigationEvent(true));
        });
        statusTitle.setOnItemClickListener(R.id.img_addIndex,view -> {
            PLog.e("添加指标");
        });
        initTitleMagicTitle();
        initPageAdapter();
    }

    /**
     * 初始化pager指示器
     */
    private void initPagerMagic() {
        CircleNavigator circleNavigator = new CircleNavigator(mContext);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleCount(fragments.size());
        circleNavigator.setCircleSpacing(20);
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, vpBoard);
    }

    /**
     * 初始化分页适配器
     */
    private void initPageAdapter() {
        adapter = new BoardPagerAdapter(getChildFragmentManager(),vpBoard,fragments);
        adapter.setOnExtraPageChangeListener(this);
    }

    /**
     * 初始化标题指示器
     */
    private void initTitleMagicTitle() {
        List<String> titles = Arrays.asList(resources.getStringArray(R.array.digital_title));
        titleNavigator = new TitleNavigator(mContext,titles);
        titleNavigator.setOnTabSelectedListener(this);
        magicTitle.setNavigator(titleNavigator);
        mTitleFragmentContainerHelper = new FragmentContainerHelper() ;
        mTitleFragmentContainerHelper.attachMagicIndicator(magicTitle);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        getDigitalPage();
    }

    /**
     * 获取数字神经多页数据
     */
    private void getDigitalPage() {
        getPresenter().getDigitalPage();
    }

    @OnClick({R.id.tv_manage})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_manage :
                EventBus.getDefault().post(new HideNavigationEvent(false));
                statusTitle.showExtendContent();
                break;
        }
    }

    /**
     * 标题切换
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        mTitleFragmentContainerHelper.handlePageSelected(position);
        tvManage.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        switch (position){
            case 0 :
                statusBoard.showContent();
                break;

            case 1 :
                statusBoard.showExtendContent();
                break;
        }
    }

    @Override
    public void getDigitalPageSuccess(PageItemListBean pageItemBeans) {
        this.pageItemBeans.clear();
        Collections.sort(pageItemBeans);
        this.pageItemBeans.addAll(pageItemBeans);
        fragments.clear();
        for (PageItemBean bean : pageItemBeans) {
            String[] dimensionArr = new Gson().fromJson(bean.getDimensions(),String[].class);
            switch (dimensionArr.length){
                case 1 :
                    bean.setmOrgDimension(dimensionArr[0]);
                    break;

                case 2 :
                    bean.setmOrgDimension(dimensionArr[0]);
                    bean.setmFuDimension(dimensionArr[1]);
                    break;

                case 3 :
                    bean.setmOrgDimension(dimensionArr[0]);
                    bean.setmFuDimension(dimensionArr[1]);
                    bean.setmPDimension(dimensionArr[2]);
                    break;

                default:
                    break;
            }
            fragments.add(BoardPagerFragment.newInstance(bean));
        }
        adapter.notifyDataSetChanged();
        initPagerMagic();
    }

    @Override
    public void onExtraPageScrolled(int i, float v, int i2) {
        if(pageItemBeans.get(i).getPad_name().contains("{default}")){
            tvName.setText(resources.getString(R.string.the_current_page)
                    + String.format(resources.getString(R.string.the_page_is)
                    ,i + ""));
        }else{
            tvName.setText(resources.getString(R.string.the_current_page)
                    +pageItemBeans.get(i).getPad_name());
        }
        setTimeValue(i);
    }

    private void setTimeValue(int position) {
        PageItemBean bean = pageItemBeans.get(position);
        switch (bean.getTime_type()){
            case "month" :
                tvTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH,""));
                break;
            case "year" :
                tvTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_YEAR,""));
                break;
            default:
                tvTime.setText(PreferencesHelper.get(Constants.USER_DEFAULT_DAY,""));
                break;
        }
        bean.setTimeVal(tvTime.getText().toString()
                .replaceAll("/",""));
    }

    @Override
    public void onExtraPageSelected(int i) {
        if(pageItemBeans.get(i).getPad_name().contains("{default}")){
            tvName.setText(resources.getString(R.string.the_current_page)
                    + String.format(resources.getString(R.string.the_page_is)
                    ,i + ""));
        }else{
            tvName.setText(resources.getString(R.string.the_current_page)
                    +pageItemBeans.get(i).getPad_name());
        }
        setTimeValue(i);
    }

    @Override
    public void onExtraPageScrollStateChanged(int i) {

    }
}
