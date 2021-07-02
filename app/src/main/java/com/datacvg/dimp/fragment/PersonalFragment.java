package com.datacvg.dimp.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.ContactActivity;
import com.datacvg.dimp.activity.MessageCentreActivity;
import com.datacvg.dimp.activity.ScanActivity;
import com.datacvg.dimp.activity.SettingActivity;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.UserJobsBean;
import com.datacvg.dimp.bean.UserJobsListBean;
import com.datacvg.dimp.event.ChangeUnReadMessageEvent;
import com.datacvg.dimp.event.LoginOutEvent;
import com.datacvg.dimp.presenter.PersonPresenter;
import com.datacvg.dimp.view.PersonView;
import com.datacvg.dimp.widget.CircleNumberView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tv_unReadMessageCount)
    CircleNumberView tvUnReadMessageCount ;

    private List<UserJobsBean> userJobsBeans = new ArrayList<>();
    private IntentIntegrator mIntentIntegrator ;
    private int pageSize = Constants.MAX_PAGE_SIZE ;
    private int pageIndex = 1;
    private String module_id = "" ;
    /**
     * T for true and F for false
     */
    private String read_flag = "F" ;
    private List<String> permissions = new ArrayList<>();
    private int unReadMessage = 0 ;

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
    protected void setupData() {
        tvTitle.setText(resources.getString(R.string.personal_center));
        imgLeft.setVisibility(View.GONE);
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        tvName.setText(PreferencesHelper.get(Constants.USER_NAME,""));
        tvCompanyName.setText(PreferencesHelper.get(Constants.USER_ORG_NAME,""));
        tvName.setText(PreferencesHelper.get(Constants.USER_NAME,""));
        GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + Constants.HEAD_IMG_URL
                + PreferencesHelper.get(Constants.USER_PKID,"")
                , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
        Glide.with(mContext).load(imgUrl).into(circleHead);
        getPresenter().getJob(PreferencesHelper.get(Constants.USER_PKID,""));
        getPresenter().getMessage(pageIndex+ "",pageSize + "",module_id,read_flag);
    }

    @OnClick({R.id.rel_setting,R.id.rel_logout,R.id.img_right,R.id.rel_message,R.id.rel_feedback})
    public void OnClick(View view){
        switch (view.getId()){
            /**
             * 设置
             */
            case R.id.rel_setting :
                startActivity(new Intent(mContext, SettingActivity.class));
//                startActivity(new Intent(mContext, ContactActivity.class));
                break;

            /**
             * 退出登录
             */
            case R.id.rel_logout :
                    getPresenter().loginOut();
                break;

            case R.id.img_right :
                new RxPermissions(getActivity())
                        .request(Manifest.permission.CAMERA
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                        .subscribe(new RxObserver<Boolean>(){
                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {      //授权通过拍摄照片
                                    mIntentIntegrator = new IntentIntegrator(getActivity());
                                    mIntentIntegrator.setCaptureActivity(ScanActivity.class);
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_SCAN
                                            ,Constants.SCAN_FOR_LOGIN);
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_ALBUM,false);
                                    mIntentIntegrator.initiateScan();
                                }
                            }
                        });
                break;

            /**
             * 消息中心
             */
            case R.id.rel_message :
                    mContext.startActivity(new Intent(mContext, MessageCentreActivity.class));
                break;

            case R.id.rel_feedback :
                    mContext.startActivity(new Intent(mContext, ContactActivity.class));
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

    /**
     * 用户职位获取成功
     * @param resdata
     */
    @Override
    public void getUseJobsSuccess(UserJobsListBean resdata) {
        userJobsBeans = resdata ;
        for (UserJobsBean bean: resdata) {
            if(TextUtils.isEmpty(bean.getUser_pkid())){
                continue;
            }else{
                if (bean.getUser_pkid().equals(PreferencesHelper.get(Constants.USER_PKID,""))){
                    tvJobName.setText(bean.getPost_clname());
                }
            }
        }
    }

    @Override
    public void getMessageSuccess(MessageBean resdata) {
        if (resdata == null){
            return;
        }
        if(resdata.getPermissions() == null || resdata.getPermissions().size() == 0){
            return;
        }
        permissions.addAll(resdata.getPermissions());
        for (MessageBean.ResultBean bean : resdata.getResult()){
            if(permissions.contains(bean.getModule_id())){
                unReadMessage ++ ;
            }
        }
        if(unReadMessage > 0){
            tvUnReadMessageCount.setNumber(unReadMessage+ "");
            EventBus.getDefault().post(new ChangeUnReadMessageEvent(unReadMessage));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
