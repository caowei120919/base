package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.SearchReportActivity;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.event.ClearAllReportEvent;
import com.datacvg.dimp.event.SortForNameEvent;
import com.datacvg.dimp.event.SortForSystemEvent;
import com.datacvg.dimp.presenter.ReportPresenter;
import com.datacvg.dimp.view.ReportView;
import com.datacvg.dimp.widget.TitleNavigator;
import com.enlogy.statusview.StatusRelativeLayout;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import org.greenrobot.eventbus.EventBus;
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
    @BindView(R.id.img_sort)
    ImageView imgSort ;
    @BindView(R.id.img_search)
    ImageView imgSearch ;
    @BindView(R.id.status_report)
    StatusRelativeLayout statusReport ;

    /**
     * 0 : 九宫格样式
     * 1 : 列表样式
     */
    private static Integer showType = 0 ;
    private static Integer selectPosition = 0 ;
    private TitleNavigator titleNavigator ;
    private FragmentContainerHelper mTitleFragmentContainerHelper ;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager ;
    private PopupWindow sortPop ;
    private String reportType = Constants.REPORT_MINE;
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
     * 创建排序选择弹窗
     */
    private void createSortPopWindow() {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_sort, null);
        RelativeLayout relBySystemDefault = contentView.findViewById(R.id.rel_bySystemDefault);
        RelativeLayout relAccordingToTheName = contentView.findViewById(R.id.rel_accordingToTheName);
        relBySystemDefault.setOnClickListener(v -> {
            PLog.e("按系统排序");
            EventBus.getDefault().post(new SortForSystemEvent());
            if(sortPop != null && sortPop.isShowing()){
                sortPop.dismiss();
            }
        });
        relAccordingToTheName.setOnClickListener(v -> {
            PLog.e("按名称排序");
            EventBus.getDefault().post(new SortForNameEvent());
            if(sortPop != null && sortPop.isShowing()){
                sortPop.dismiss();
            }
        });
        sortPop = new PopupWindow(contentView,
                (int) resources.getDimension(R.dimen.W260), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        sortPop.setTouchable(true);
        sortPop.setOutsideTouchable(false);
        sortPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
        sortPop.showAsDropDown(imgSearch,-150,20);
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


    @OnClick({R.id.img_changeType,R.id.img_sort,R.id.img_search})
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

            case R.id.img_sort :
                    if (sortPop == null){
                        createSortPopWindow();
                    }else{
                        sortPop.showAsDropDown(imgSort,-150,20);
                    }
                break;

            case R.id.img_search :
                Intent intent = new Intent(mContext, SearchReportActivity.class) ;
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportType) ;
                mContext.startActivity(intent);
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
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (position){
            case 0 :
                reportType = Constants.REPORT_MINE ;
                statusReport.showContent();
                if (showType == 0){
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_grid));
                }else{
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_list));
                }
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
                reportType = Constants.REPORT_SHARE ;
                statusReport.showContent();
                if (showType == 0){
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_grid));
                }else{
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_list));
                }
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
                reportType = Constants.REPORT_TEMPLATE ;
                statusReport.showContent();
                if (showType == 0){
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_grid));
                }else{
                    imgChangeType.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_list));
                }
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
                statusReport.showExtendContent();
                statusReport.findViewById(R.id.img_trashChangeType).setOnClickListener(v -> {
                    if(showType == 0){
                        showType = 1;
                        ((ImageView)statusReport.findViewById(R.id.img_trashChangeType)).setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_list));
                    } else{
                        showType = 0;
                        ((ImageView)statusReport.findViewById(R.id.img_trashChangeType)).setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_grid));
                    }
                    showFragment(position);
                });
                statusReport.findViewById(R.id.tv_clear).setOnClickListener(v -> {
                    EventBus.getDefault().post(new ClearAllReportEvent());
                });
                switch (showType){
                    case 0 :
                        ((ImageView)statusReport.findViewById(R.id.img_trashChangeType)).setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_grid));
                        if(null != reportOfTrashFragment){
                            fragmentTransaction.show(reportOfTrashFragment);
                        }else{
                            reportOfTrashFragment = new ReportOfTrashGridFragment();
                            fragmentTransaction.add(R.id.content,reportOfTrashFragment);
                        }
                        fragmentTransaction.commitAllowingStateLoss();
                        break;

                    case 1 :
                        ((ImageView)statusReport.findViewById(R.id.img_trashChangeType)).setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_list));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : fragmentManager.getFragments()){
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
