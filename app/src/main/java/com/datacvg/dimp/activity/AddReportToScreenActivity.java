package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.fragment.AddToScreenFragment;
import com.datacvg.dimp.fragment.NewScreenFragment;
import com.datacvg.dimp.presenter.AddReportToScreenPresenter;
import com.datacvg.dimp.view.AddReportToScreenView;
import com.datacvg.dimp.widget.TitleNavigator;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description :报告添加到大屏
 */
public class AddReportToScreenActivity extends BaseActivity<AddReportToScreenView, AddReportToScreenPresenter>
        implements AddReportToScreenView, TitleNavigator.OnTabSelectedListener {
    @BindView(R.id.magic_title)
    MagicIndicator magicTitle ;

    private TitleNavigator titleNavigator ;
    private FragmentContainerHelper mTitleFragmentContainerHelper ;
    private FragmentTransaction fragmentTransaction;
    private AddToScreenFragment addToScreenFragment ;
    private NewScreenFragment newScreenFragment ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_report_to_screen;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        initTitleMagicTitle();
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    /**
     * 初始化标题指示器
     */
    private void initTitleMagicTitle() {
        List<String> titles = Arrays.asList(resources.getStringArray(R.array.screen_title));
        titleNavigator = new TitleNavigator(mContext,titles,false);
        titleNavigator.setOnTabSelectedListener(this);
        magicTitle.setNavigator(titleNavigator);
        mTitleFragmentContainerHelper = new FragmentContainerHelper() ;
        mTitleFragmentContainerHelper.attachMagicIndicator(magicTitle);
//        showFragment(0);
    }

    @OnClick({R.id.img_left,R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_confirm :
                PLog.e("确定");
                break;
        }
    }

    @Override
    public void onTabSelected(int position) {

    }
}
