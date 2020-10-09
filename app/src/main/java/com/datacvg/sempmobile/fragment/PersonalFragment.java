package com.datacvg.sempmobile.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.activity.SettingActivity;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.event.LoginOutEvent;
import com.datacvg.sempmobile.presenter.PersonPresenter;
import com.datacvg.sempmobile.view.PersonView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 个人中心
 */
public class PersonalFragment extends BaseFragment<PersonView, PersonPresenter> implements PersonView{

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.circle_head)
    CircleImageView circleHead ;
    @BindView(R.id.tv_name)
    TextView tvName ;
    @BindView(R.id.tv_companyName)
    TextView tvCompanyName ;
    @BindView(R.id.tv_jobName)
    TextView tvJobName ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.personal_center));
        imgLeft.setVisibility(View.GONE);
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        tvName.setText(PreferencesHelper.get(Constants.USER_NAME,""));
        tvCompanyName.setText(PreferencesHelper.get(Constants.USER_ORG_NAME,""));
        tvName.setText(PreferencesHelper.get(Constants.USER_NAME,""));
//        String url =
        getPresenter().getJob(PreferencesHelper.get(Constants.USER_PKID,""));
    }

    @OnClick({R.id.rel_setting,R.id.rel_logout})
    public void OnClick(View view){
        switch (view.getId()){
            /**
             * 设置
             */
            case R.id.rel_setting :
                startActivity(new Intent(mContext, SettingActivity.class));
                break;

            /**
             * 退出登录
             */
            case R.id.rel_logout :
                    getPresenter().loginOut();
                break;
        }
    }

    /**
     * 成功退出登录
     */
    @Override
    public void loginOutSuccess() {
        Constants.BASE_FIS_URL = "" ;
        Constants.BASE_MOBILE_URL = "" ;
        Constants.BASE_UPLOAD_URL = "" ;

        Constants.token = "" ;
        RetrofitUrlManager.getInstance()
                .setGlobalDomain(Constants.BASE_URL);
        RetrofitUrlManager.getInstance().setRun(false);

        EventBus.getDefault().post(new LoginOutEvent());
    }
}
