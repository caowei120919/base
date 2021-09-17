package com.datacvg.dimp.fragment;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.presenter.ReportPresenter;
import com.datacvg.dimp.view.ReportView;
import com.datacvg.dimp.widget.TitleNavigator;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 管理画布
 */
public class ReportFragment extends BaseFragment<ReportView, ReportPresenter> implements ReportView
        , TitleNavigator.OnTabSelectedListener {
    @BindView(R.id.magic_title)
    MagicIndicator magicTitle ;
    @BindView(R.id.img_changeType)
    ImageView imgChangeType ;

    /**
     * 0 : 九宫格样式
     * 1 : 列表样式
     */
    private static Integer showType = 0 ;
    private static Integer selectPosition = 0 ;
    private TitleNavigator titleNavigator ;
    private FragmentContainerHelper mTitleFragmentContainerHelper ;
    private FragmentTransaction fragmentTransaction;

    private ReportOfMineGridFragment reportOfMineFragment ;
    private ReportListOfMineFragment reportOfMineListFragment ;
    private ReportOfSharedGridFragment reportOfSharedFragment ;
    private ReportListOfSharedFragment reportOfSharedListFragment ;
    private ReportOfTemplateGridFragment reportOfTemplateFragment ;
    private ReportListOfTemplateFragment reportOfTemplateListFragment ;
    private ReportOfTrashGridFragment reportOfTrashFragment ;
    private ReportListOfTrashFragment reportOfTrashListFragment ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        initTitleMagicTitle();
    }

    /**
     * 初始化标题指示器
     */
    private void initTitleMagicTitle() {
        List<String> titles = Arrays.asList(resources.getStringArray(R.array.report_title));
        titleNavigator = new TitleNavigator(mContext,titles,true);
        titleNavigator.setOnTabSelectedListener(this);
        magicTitle.setNavigator(titleNavigator);
        mTitleFragmentContainerHelper = new FragmentContainerHelper() ;
        mTitleFragmentContainerHelper.attachMagicIndicator(magicTitle);
        showFragment(0);
    }

    @Override
    protected void setupData() {

    }


    @OnClick({R.id.img_changeType})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_changeType :
                if (showType == 0){
                    showType = 1 ;
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_list));
                }else{
                    showType = 0 ;
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_grid));
                }
                showFragment(selectPosition);
                break;
        }
    }

    /**
     * 标题点击指示器点击监听
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        selectPosition = position ;
        showFragment(position);
        mTitleFragmentContainerHelper.handlePageSelected(position);
    }

    /**
     * 根据位置展示对应fragment
     * @param position
     */
    private void showFragment(int position) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (position){
            case 0 :
                switch (showType){
                    case 0 :
                        if(null != reportOfMineFragment){
                            fragmentTransaction.show(reportOfMineFragment);
                        }else{
                            reportOfMineFragment = new ReportOfMineGridFragment();
                            fragmentTransaction.add(R.id.content,reportOfMineFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case 1 :
                        if(null != reportOfMineListFragment){
                            fragmentTransaction.show(reportOfMineListFragment);
                        }else{
                            reportOfMineListFragment = new ReportListOfMineFragment();
                            fragmentTransaction.add(R.id.content,reportOfMineListFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                }
                break;

            case 1 :
                switch (showType){
                    case 0 :
                        if(null != reportOfSharedFragment){
                            fragmentTransaction.show(reportOfSharedFragment);
                        }else{
                            reportOfSharedFragment = new ReportOfSharedGridFragment();
                            fragmentTransaction.add(R.id.content,reportOfSharedFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case 1 :
                        if(null != reportOfSharedListFragment){
                            fragmentTransaction.show(reportOfSharedListFragment);
                        }else{
                            reportOfSharedListFragment = new ReportListOfSharedFragment();
                            fragmentTransaction.add(R.id.content,reportOfSharedListFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                }
                break;

            case 2 :
                switch (showType){
                    case 0 :
                        if(null != reportOfTemplateFragment){
                            fragmentTransaction.show(reportOfTemplateFragment);
                        }else{
                            reportOfTemplateFragment = new ReportOfTemplateGridFragment();
                            fragmentTransaction.add(R.id.content,reportOfTemplateFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case 1 :
                        if(null != reportOfTemplateListFragment){
                            fragmentTransaction.show(reportOfTemplateListFragment);
                        }else{
                            reportOfTemplateListFragment = new ReportListOfTemplateFragment();
                            fragmentTransaction.add(R.id.content,reportOfTemplateListFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                }
                break;

            case 3 :
                switch (showType){
                    case 0 :
                        if(null != reportOfTrashFragment){
                            fragmentTransaction.show(reportOfTrashFragment);
                        }else{
                            reportOfTrashFragment = new ReportOfTrashGridFragment();
                            fragmentTransaction.add(R.id.content,reportOfTrashFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case 1 :
                        if(null != reportOfTrashListFragment){
                            fragmentTransaction.show(reportOfTrashListFragment);
                        }else{
                            reportOfTrashListFragment = new ReportListOfTrashFragment();
                            fragmentTransaction.add(R.id.content,reportOfTrashListFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;
                }
                break;
        }
    }

    /**
     * 隐藏fragment
     * @param fragmentTransaction
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(null != reportOfMineFragment){
            fragmentTransaction.hide(reportOfMineFragment);
        }
        if(null != reportOfMineListFragment){
            fragmentTransaction.hide(reportOfMineListFragment);
        }
        if(null != reportOfSharedFragment){
            fragmentTransaction.hide(reportOfSharedFragment);
        }
        if(null != reportOfSharedListFragment){
            fragmentTransaction.hide(reportOfSharedListFragment);
        }
        if(null != reportOfTemplateFragment){
            fragmentTransaction.hide(reportOfTemplateFragment);
        }
        if(null != reportOfTemplateListFragment){
            fragmentTransaction.hide(reportOfTemplateListFragment);
        }
        if(null != reportOfTrashFragment){
            fragmentTransaction.hide(reportOfTrashFragment);
        }
        if(null != reportOfTrashListFragment){
            fragmentTransaction.hide(reportOfTrashListFragment);
        }
    }
}
