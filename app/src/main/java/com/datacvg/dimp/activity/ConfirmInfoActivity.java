package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.presenter.ConfirmInfoPresenter;
import com.datacvg.dimp.view.ConfirmInfoView;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-18
 * @Description : 大屏投放确认信息
 */
public class ConfirmInfoActivity extends BaseActivity<ConfirmInfoView, ConfirmInfoPresenter>
        implements ConfirmInfoView {
    @BindView(R.id.circle_head)
    CircleImageView circleHead ;
    @BindView(R.id.tv_name)
    TextView tvName ;
    @BindView(R.id.tv_companyName)
    TextView tvCompanyName ;
    @BindView(R.id.tv_prompt)
    TextView tvPrompt ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;

    private String title ;
    private String hour = "1";
    private ScreenBean bean ;
    private String targetIp = "" ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_info;
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
        tvTitle.setText(resources.getString(R.string.confirm_the_information));
        bean = Constants.screenBean ;
        title = getIntent().getStringExtra("title");
        targetIp = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        tvName.setText(PreferencesHelper.get(Constants.USER_NAME,""));
        tvCompanyName.setText(PreferencesHelper.get(Constants.USER_ORG_NAME,""));
        GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + Constants.HEAD_IMG_URL
                + PreferencesHelper.get(Constants.USER_PKID,"")
                , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
        Glide.with(mContext).load(imgUrl).into(circleHead);
        String prompt = resources.getString(R.string.whether_to_confirm_authorization);
        tvPrompt.setText(Html.fromHtml(prompt
                .replace("#1","\r\r<font color='#da3a16'>"
                + tvName.getText() + "</font><br />")
                .replace("#2","\r\r<font color='#da3a16'>"
                        + title + "</font>\r\r")));
    }

    /**
     * 时长输入框监听
     */
    @OnTextChanged(value = R.id.ed_hour,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onHourTextChange(Editable editable){
        hour = editable.toString().trim();
    }

    @OnClick({R.id.img_left,R.id.btn_login})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.btn_login :
                    if (TextUtils.isEmpty(hour)){
                        ToastUtils.showLongToast(resources
                                .getString(R.string.please_enter_duration));
                        return;
                    }
                    getPresenter().confirmOnTheScreen("app",Constants.token,hour
                            ,new Gson().toJson(bean),Constants.SCREEN_BOOT
                            ,Constants.DEFAULT_POSITION,Constants.COMMON_CODE,targetIp);
                break;
        }
    }

    /**
     * 投屏成功
     */
    @Override
    public void forScreenSuccess() {
        Intent intent = new Intent(mContext,ScreenResultActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,true);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,targetIp);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,hour);
        mContext.startActivity(intent);
        finish();
    }

    /**
     * 投屏失败
     */
    @Override
    public void forScreenFailed() {
        Intent intent = new Intent(mContext,ScreenResultActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,false);
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,targetIp);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,hour);
        mContext.startActivity(intent);
        finish();
    }
}
