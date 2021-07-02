package com.datacvg.dimp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.dimp.baseandroid.greendao.controller.DbModuleInfoController;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.PreferencesUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.DefaultUserBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.ModuleBean;
import com.datacvg.dimp.bean.ModuleListBean;
import com.datacvg.dimp.event.ChangeUnReadMessageEvent;
import com.datacvg.dimp.event.DeletePageEvent;
import com.datacvg.dimp.event.HideNavigationEvent;
import com.datacvg.dimp.event.LoginOutEvent;
import com.datacvg.dimp.event.RebuildTableEvent;
import com.datacvg.dimp.event.TabShowOrHideEvent;
import com.datacvg.dimp.event.ToAddPageEvent;
import com.datacvg.dimp.fragment.ActionFragment;
import com.datacvg.dimp.fragment.DigitalFragment;
import com.datacvg.dimp.fragment.PersonalFragment;
import com.datacvg.dimp.fragment.ReportFragment;
import com.datacvg.dimp.fragment.ScreenFragment;
import com.datacvg.dimp.fragment.TableFragment;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbContactController;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;
import com.datacvg.dimp.presenter.MainPresenter;
import com.datacvg.dimp.view.MainView;
import com.next.easynavigation.view.EasyNavigationBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :主页，
 */
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    private final int INTERVAL_TIME = 2000 ;
    private int UNREAD_MESSAGE = 0 ;

    @BindView(R.id.easy_tab)
    EasyNavigationBar tabModule;

    private PersonalFragment personalFragment ;
    private ScreenFragment screenFragment ;
    private DigitalFragment digitalFragment ;
    private ReportFragment reportFragment ;
    private TableFragment tableFragment ;
    private ActionFragment actionFragment ;

    String[] titles ;
    int[] normalIcons ;
    int[] selectIcons ;

    /**
     *记录返回键点击时间
     */
    private FragmentManager fragmentManager ;
    private FragmentTransaction fragmentTransaction ;
    private long firstTime = 0;
    private List<ModuleInfo> moduleBeans = new ArrayList<>();
    private boolean isNeedRebuild = false ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        getPresenter().getPermissionModule();
    }

    /**
     * 返回键返回虚拟键点击事件处理
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > INTERVAL_TIME) {
                ToastUtils.showLongToast(mContext.getResources()
                        .getString(R.string.click_again_exit_app));
                firstTime = secondTime;
            } else {
                //执行返回桌面
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 模块获取成功
     * @param resdata
     */
    @Override
    public void getModuleSuccess(ModuleListBean resdata) {
        for (ModuleBean moduleBean : resdata){
            ModuleInfo moduleInfo = DbModuleInfoController.getInstance(mContext)
                    .getModule(moduleBean.getRes_pkid());
            if(moduleInfo != null){
                moduleInfo.setModule_permission(true);
                DbModuleInfoController.getInstance(mContext).updateModuleInfo(moduleInfo);
            }
        }
        buildTab();
    }

    /**
     * 创建底部tab标签
     */
    private void buildTab() {
        moduleBeans = DbModuleInfoController.getInstance(mContext).getSelectedModuleList();
        titles = new String[moduleBeans.size()];
        normalIcons = new int[moduleBeans.size()];
        selectIcons = new int[moduleBeans.size()];
        for (int i = 0 ; i < moduleBeans.size() ; i++){
            titles[i] = moduleBeans.get(i).getModule_name();
            normalIcons[i] = moduleBeans.get(i).getModule_normal_res();
            selectIcons[i] = moduleBeans.get(i).getModule_selected_res();
        }
        tabModule.defaultSetting()
                .titleItems(titles)
                .normalTextColor(resources.getColor(R.color.c_999999))
                .selectTextColor(resources.getColor(R.color.c_da3a16))
                .tabTextSize(10)
                .iconSize(20)
                .tabTextTop(2)
                .hintPointLeft(-3)
                .hintPointTop(-3)
                .hintPointSize(9)
                .msgPointLeft(-15)
                .msgPointTop(-5)
                .msgPointTextSize(8)
                .msgPointSize(12)
                .setMsgPointColor(Color.RED)
                .normalIconItems(normalIcons)
                .selectIconItems(selectIcons)
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)
                .navigationBackground(resources.getColor(R.color.c_FFFFFF))
                .setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabSelectEvent(View view, int position) {
                        PLog.e(position + "===>" + view.getTag());
                        showFragment((String)view.getTag());
                        return false;
                    }

                    @Override
                    public boolean onTabReSelectEvent(View view, int position) {
                        return false;
                    }
                })
                .smoothScroll(false)
                .canScroll(false)
                .mode(EasyNavigationBar.NavigationMode.MODE_NORMAL)
                .hasPadding(true)
                .build();
        showFragment(titles[0]);
    }

    private void showFragment(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (tag){
            case "数字神经" :
                if(null != digitalFragment){
                    fragmentTransaction.show(digitalFragment);
                }else{
                    digitalFragment = new DigitalFragment();
                    fragmentTransaction.add(R.id.content,digitalFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case "管理画布" :
                if(null != reportFragment){
                    fragmentTransaction.show(reportFragment);
                }else{
                    reportFragment = new ReportFragment();
                    fragmentTransaction.add(R.id.content,reportFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case "主题报表" :
                if(null != tableFragment){
                    fragmentTransaction.show(tableFragment);
                }else{
                    tableFragment = new TableFragment();
                    fragmentTransaction.add(R.id.content,tableFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case "行动方案" :
                if(null != actionFragment){
                    fragmentTransaction.show(actionFragment);
                }else{
                    actionFragment = new ActionFragment();
                    fragmentTransaction.add(R.id.content,actionFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case "数字大屏" :
                if(null != screenFragment){
                    fragmentTransaction.show(screenFragment);
                }else{
                    screenFragment = new ScreenFragment();
                    fragmentTransaction.add(R.id.content,screenFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case "个人中心" :
                if(null != personalFragment){
                    fragmentTransaction.show(personalFragment);
                }else{
                    personalFragment = new PersonalFragment();
                    fragmentTransaction.add(R.id.content,personalFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(null != personalFragment){
            fragmentTransaction.hide(personalFragment);
        }
        if(null != screenFragment){
            fragmentTransaction.hide(screenFragment);
        }
        if(null != digitalFragment){
            fragmentTransaction.hide(digitalFragment);
        }
        if(null != reportFragment){
            fragmentTransaction.hide(reportFragment);
        }
        if(null != tableFragment){
            fragmentTransaction.hide(tableFragment);
        }
        if(null != actionFragment){
            fragmentTransaction.hide(actionFragment);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RebuildTableEvent event){
       PLog.e("模块切换");
       moduleBeans = DbModuleInfoController.getInstance(mContext).getSelectedModuleList();
       titles = new String[moduleBeans.size()];
       normalIcons = new int[moduleBeans.size()];
       selectIcons = new int[moduleBeans.size()];
       for (int i = 0 ; i < moduleBeans.size() ; i++){
           titles[i] = moduleBeans.get(i).getModule_name();
           normalIcons[i] = moduleBeans.get(i).getModule_normal_res();
           selectIcons[i] = moduleBeans.get(i).getModule_selected_res();
       }
       tabModule.titleItems(titles);
       tabModule.normalIconItems(normalIcons);
       tabModule.selectIconItems(selectIcons);
       tabModule.setOnTabLoadListener(new EasyNavigationBar.OnTabLoadListener() {
           @Override
           public void onTabLoadCompleteEvent() {
               tabModule.selectTab(titles.length -1);
           }
       }).build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginOutEvent event){
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChangeUnReadMessageEvent event){
        tabModule.setMsgPointCount(titles.length - 1,event.getTotal());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HideNavigationEvent event){
        tabModule.setVisibility(event.getHide() ? View.VISIBLE : View.GONE);
    }
}
