package com.datacvg.sempmobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.sempmobile.baseandroid.greendao.controller.DbModuleInfoController;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.ModuleBean;
import com.datacvg.sempmobile.bean.ModuleListBean;
import com.datacvg.sempmobile.presenter.MainPresenter;
import com.datacvg.sempmobile.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-08-31
 * @Description :主页，
 */
public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {
    @BindView(R.id.radio_module)
    RadioGroup tabModule;

    @BindView(R.id.content)
    FrameLayout content ;

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
        for (int i = 0 ; i < moduleBeans.size() ; i++){
            RadioButton radioButton = new RadioButton(mContext);
        }
    }
}
