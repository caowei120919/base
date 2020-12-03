package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.WebSocketLinkBean;
import com.datacvg.dimp.event.ForScreenSuccessEvent;
import com.datacvg.dimp.presenter.ScreenResultPresenter;
import com.datacvg.dimp.view.ScreenResultView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-19
 * @Description : 投屏结果页面
 */
public class ScreenResultActivity extends BaseActivity<ScreenResultView, ScreenResultPresenter>
        implements ScreenResultView {
    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_result)
    ImageView imgResult ;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt ;
    @BindView(R.id.btn_cancel)
    Button btnCancel ;

    private boolean isSuccess = false ;
    private String hour = "1" ;
    private String targetIp = "" ;
    private ScreenBean bean ;
    private String scPlayStatus = "" ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen_result;
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
        isSuccess = getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_SCAN,false);
        hour = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_BEAN);
        targetIp = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_ALBUM);
        bean = Constants.screenBean ;
        imgLeft.setVisibility(View.GONE);
        tvTitle.setText(resources.getString(R.string.confirm_the_information));
        imgResult.setImageBitmap(BitmapFactory.decodeResource(resources,isSuccess
                ? R.mipmap.icon_screen_success : R.mipmap.icon_screen_failed));
        tvPrompt.setText(isSuccess ? resources.getString(R.string.for_screen_success)
                :resources.getString(R.string.screen_projection_failed_please_check_the_device));
        btnCancel.setText(isSuccess ? resources.getString(R.string.recall)
                : resources.getString(R.string.back_on));
    }

    @OnClick({R.id.btn_login,R.id.btn_cancel})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_login :
                    if(isSuccess){
                        WebSocketLinkBean linkBean = new WebSocketLinkBean(bean.getScreen_id(),hour);
                        linkBean.setTargetIp(targetIp);
                        Constants.linkBeans.add(linkBean);
                        EventBus.getDefault().post(new ForScreenSuccessEvent());
                    }
                    finish();
                break;

            case R.id.btn_cancel :
                    if(isSuccess){
                        scPlayStatus = Constants.SCREEN_CLOSE ;
                    }else{
                        scPlayStatus = Constants.SCREEN_BOOT ;
                    }
                getPresenter().confirmOnTheScreen("app",Constants.token,hour
                        ,new Gson().toJson(bean),scPlayStatus
                        ,Constants.DEFAULT_POSITION,Constants.COMMON_CODE,targetIp);
                break;
        }
    }

    @Override
    public void forScreenFailed() {
        if (isSuccess){
            finish();
        }else{
            ToastUtils.showLongToast(resources.getString(R.string.cast_screen_failure));
        }
    }

    @Override
    public void forScreenSuccess() {
        if (isSuccess){
            finish();
            return;
        }
        ToastUtils.showLongToast(resources.getString(R.string.for_screen_success));
        isSuccess = true ;
        imgResult.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_screen_success));
        tvPrompt.setText(resources.getString(R.string.for_screen_success));
        btnCancel.setText(resources.getString(R.string.recall));
    }
}
