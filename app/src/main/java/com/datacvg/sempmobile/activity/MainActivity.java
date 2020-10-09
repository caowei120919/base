package com.datacvg.sempmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.sempmobile.baseandroid.greendao.controller.DbModuleInfoController;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.ModuleBean;
import com.datacvg.sempmobile.bean.ModuleListBean;
import com.datacvg.sempmobile.event.LoginOutEvent;
import com.datacvg.sempmobile.fragment.ActionFragment;
import com.datacvg.sempmobile.fragment.DigitalFragment;
import com.datacvg.sempmobile.fragment.PersonalFragment;
import com.datacvg.sempmobile.fragment.ReportFragment;
import com.datacvg.sempmobile.fragment.ScreenFragment;
import com.datacvg.sempmobile.fragment.TableFragment;
import com.datacvg.sempmobile.presenter.MainPresenter;
import com.datacvg.sempmobile.view.MainView;
import com.next.easynavigation.view.EasyNavigationBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :主页，
 */
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
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
    private List<Fragment> fragments = new ArrayList<>();
    private Map<String,Fragment> fragmentMap = new HashMap<>();

    /**
     *记录返回键点击时间
     */
    private long firstTime = 0;
    private List<ModuleInfo> moduleBeans = new ArrayList<>();
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
        buildFragment();
        getPresenter().getPermissionModule();
    }

    /**
     * fragment初始化
     */
    private void buildFragment() {
        if(null == personalFragment){
            personalFragment = new PersonalFragment();
        }
        fragmentMap.put(personalFragment.getClass().getSimpleName(),personalFragment);

        if (null == screenFragment){
            screenFragment = new ScreenFragment();
        }
        fragmentMap.put(screenFragment.getClass().getSimpleName(),screenFragment);

        if(null == digitalFragment){
            digitalFragment = new DigitalFragment();
        }
        fragmentMap.put(digitalFragment.getClass().getSimpleName(),digitalFragment);

        if (null == reportFragment){
            reportFragment = new ReportFragment();
        }
        fragmentMap.put(reportFragment.getClass().getSimpleName(),reportFragment);

        if(null == tableFragment){
            tableFragment = new TableFragment();
        }
        fragmentMap.put(tableFragment.getClass().getSimpleName(),tableFragment);

        if (null == actionFragment){
            actionFragment = new ActionFragment();
        }
        fragmentMap.put(actionFragment.getClass().getSimpleName(),actionFragment);
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
            if (secondTime - firstTime > 2000) {
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
        DbModuleInfoController controller = DbModuleInfoController.getInstance(mContext);
        for (ModuleBean bean : resdata){
            ModuleInfo moduleInfo = controller.getModule(bean.getRes_pkid());
            moduleInfo.setModule_permission(true);
            DbModuleInfoController.getInstance(mContext).updateModuleInfo(moduleInfo);
        }
        buildTab();
    }

    /**
     * 创建底部tab标签
     */
    private void buildTab() {
        moduleBeans = DbModuleInfoController.getInstance(mContext).getSelectedModuleList();
        for (ModuleInfo info : moduleBeans){
            fragments.add(fragmentMap.get(info.getModule_fragment_name()));
        }
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
                .normalIconItems(normalIcons)
                .selectIconItems(selectIcons)
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)
                .navigationBackground(resources.getColor(R.color.c_FFFFFF))
                .setOnTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabSelectEvent(View view, int position) {
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
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginOutEvent event){
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
        finish();
    }
}
