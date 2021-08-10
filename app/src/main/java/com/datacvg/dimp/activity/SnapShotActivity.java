package com.datacvg.dimp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.BoardPagerAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.KpiPermissionDataBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.fragment.SnapFragment;
import com.datacvg.dimp.presenter.SnapShotPresenter;
import com.datacvg.dimp.view.SnapShotView;
import com.datacvg.dimp.widget.CircleNavigator;
import com.datacvg.dimp.widget.ControlScrollViewPager;
import com.enlogy.statusview.StatusRelativeLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-03
 * @Description : 快照对比
 */
public class SnapShotActivity extends BaseActivity<SnapShotView, SnapShotPresenter>
        implements SnapShotView, BoardPagerAdapter.OnExtraPageChangeListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.vp_snap)
    ControlScrollViewPager vpSnap;

    private TaskInfoBean taskInfoBean ;
    private List<Fragment> pageFragments = new ArrayList<>();
    private CircleNavigator circleNavigator;
    private BoardPagerAdapter boardPagerAdapter ;
    private KpiPermissionDataBean kpiPermissionDataBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_snap_shot;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
        taskInfoBean = (TaskInfoBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if (taskInfoBean == null){
            finish();
            return;
        }
        tvTitle.setText(resources.getString(R.string.the_snapshot_comparison));
    }

    /**
     * 初始化
     */
    private void initFragment() {
        for (TaskInfoBean.FastPhotoOldBean bean : taskInfoBean.getFastphotoold()){
            pageFragments.add(SnapFragment.newInstance(bean,kpiPermissionDataBean));
        }
    }

    private void initMagicIndicator() {
        PLog.e(pageFragments.size() + "");
        circleNavigator = new CircleNavigator(mContext);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleCount(pageFragments.size());
        circleNavigator.setCircleSpacing(20);
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, vpSnap);
    }

    private void initPageAdapter() {
        boardPagerAdapter = new BoardPagerAdapter(getSupportFragmentManager(),vpSnap,pageFragments);
        boardPagerAdapter.setOnExtraPageChangeListener(this);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        getPresenter().getPermissionName();
    }

    @OnClick({R.id.img_left})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                PLog.e("退出");
                finish();
                break;
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

    /**
     * 自定义阈值名称获取成功
     * @param data
     */
    @Override
    public void getKpiPermissionSuccess(KpiPermissionDataBean data) {
        kpiPermissionDataBean = data ;
        initFragment();
        initPageAdapter();
        initMagicIndicator();
    }

    /**
     * 自定义阈值名称获取失败
     */
    @Override
    public void getKpiPermissionFailed() {
        initFragment();
        initPageAdapter();
        initMagicIndicator();
    }
}
