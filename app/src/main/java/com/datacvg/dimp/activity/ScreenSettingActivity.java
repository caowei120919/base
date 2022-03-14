package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.AddToScreenAttrBean;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.ScreenDetailBean;
import com.datacvg.dimp.presenter.ScreenSettingPresenter;
import com.datacvg.dimp.view.ScreenSettingView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description : 大屏报表图片设置
 * */
public class ScreenSettingActivity extends BaseActivity<ScreenSettingView, ScreenSettingPresenter>
        implements ScreenSettingView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_name)
    TextView tvName ;
    @BindView(R.id.edit_stayTime)
    EditText editStayTime ;
    @BindView(R.id.edit_previewTime)
    EditText editPreviewTime ;
    @BindView(R.id.tv_stayTimeUnit)
    TextView tvStayTimeUnit ;
    @BindView(R.id.tv_previewTimeUnit)
    TextView tvPreviewTimeUnit;
    @BindView(R.id.tv_basicPrompt)
    TextView tvBasicPrompt ;
    @BindView(R.id.tv_animationStyle)
    TextView tvAnimationStyle ;
    @BindView(R.id.cb_circulation)
    CheckBox cbCirculation ;


    private ScreenDetailBean.ListBean bean ;
    private PopupWindow popupWindow ;
    private PopupWindow stayTimePop ;
    private PopupWindow previewTimePop ;
    private PopupWindow animationPop ;
    private int stayTimeValue = 300 ;
    private int previewTimeValue = 20 ;
    private int animationTimeValue = 300 ;
    private AddToScreenAttrBean addToScreenAttrBean ;
    private ScreenBean screenBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen_setting;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        createStayAndPreviewPop();
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        bean = (ScreenDetailBean.ListBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        tvTitle.setText(resources.getString(R.string.set));
        tvName.setText(bean.getImg_name());
        addToScreenAttrBean = new AddToScreenAttrBean() ;
        addToScreenAttrBean.setStayUnit(Constants.SECOND);
        addToScreenAttrBean.setStayTime(stayTimeValue);
        addToScreenAttrBean.setLoadTimeUnit(Constants.SECOND);
        addToScreenAttrBean.setLoadTime(previewTimeValue);
        addToScreenAttrBean.setAnimationTime(animationTimeValue);
        addToScreenAttrBean.setAnimationEffect(Constants.SCREEN_FADE_IN_AND_OUT);
    }



    /**
     * 停留时间输入
     * @param editable
     */
    @OnTextChanged(value = R.id.edit_stayTime,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onStayTimeChange(Editable editable){
        if(TextUtils.isEmpty(editable.toString().trim())){
            stayTimeValue = 300;
        }else{
            stayTimeValue = Integer.valueOf(editable.toString().trim());
        }
        addToScreenAttrBean.setStayTime(stayTimeValue);
    }

    /**
     * 预加载时间
     * @param editable
     */
    @OnTextChanged(value = R.id.edit_previewTime,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onPreviewTimeChange(Editable editable){
        if(TextUtils.isEmpty(editable.toString().trim())){
            previewTimeValue = 20;
        }else{
            previewTimeValue = Integer.valueOf(editable.toString().trim());
        }
        addToScreenAttrBean.setLoadTime(previewTimeValue);
    }

    /**
     * 动效时间
     * @param editable
     */
    @OnTextChanged(value = R.id.edit_animationTime,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onAnimationTimeChange(Editable editable){
        if(TextUtils.isEmpty(editable.toString().trim())){
            animationTimeValue = 300;
        }else{
            animationTimeValue = Integer.valueOf(editable.toString().trim());
        }
        addToScreenAttrBean.setAnimationTime(animationTimeValue);
    }

    /**
     * 创建停留和预览单位弹窗
     */
    private void createStayAndPreviewPop() {
        View stayContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_time_unit, null);
        stayContentView.findViewById(R.id.tv_hour).setOnClickListener(v -> {
            tvStayTimeUnit.setText(resources.getString(R.string.hour));
            addToScreenAttrBean.setStayUnit(Constants.HOUR);
            if(stayTimePop != null && stayTimePop.isShowing()){
                stayTimePop.dismiss();
            }
        });
        stayContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvStayTimeUnit.setText(resources.getString(R.string.minute));
            addToScreenAttrBean.setStayUnit(Constants.MINUTE);
            if(stayTimePop != null && stayTimePop.isShowing()){
                stayTimePop.dismiss();
            }
        });
        stayContentView.findViewById(R.id.tv_second).setOnClickListener(v -> {
            tvStayTimeUnit.setText(resources.getString(R.string.second));
            addToScreenAttrBean.setStayUnit(Constants.SECOND);
            if(stayTimePop != null && stayTimePop.isShowing()){
                stayTimePop.dismiss();
            }
        });
        stayTimePop = new PopupWindow(stayContentView,
                (int) resources.getDimension(R.dimen.W200), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        stayTimePop.setTouchable(true);
        stayTimePop.setOutsideTouchable(false);
        stayTimePop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));

        View previewContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_time_unit, null);
        previewContentView.findViewById(R.id.tv_hour).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.hour));
            addToScreenAttrBean.setLoadTimeUnit(Constants.HOUR);
            if(previewTimePop != null && previewTimePop.isShowing()){
                previewTimePop.dismiss();
            }
        });
        previewContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.minute));
            addToScreenAttrBean.setLoadTimeUnit(Constants.MINUTE);
            if(previewTimePop != null && previewTimePop.isShowing()){
                previewTimePop.dismiss();
            }
        });
        previewContentView.findViewById(R.id.tv_second).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.second));
            addToScreenAttrBean.setLoadTimeUnit(Constants.SECOND);
            if(previewTimePop != null && previewTimePop.isShowing()){
                previewTimePop.dismiss();
            }
        });
        previewTimePop = new PopupWindow(previewContentView,
                (int) resources.getDimension(R.dimen.W200), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        previewTimePop.setTouchable(true);
        previewTimePop.setOutsideTouchable(false);
        previewTimePop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));

        View animationContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_screen_animation, null);
        animationContentView.findViewById(R.id.tv_hour).setOnClickListener(v -> {
            tvAnimationStyle.setText(resources.getString(R.string.the_level_of_translation));
            addToScreenAttrBean.setAnimationEffect(Constants.SCREEN_PAN_HORIZONTAL);
            if(animationPop != null && animationPop.isShowing()){
                animationPop.dismiss();
            }
        });
        animationContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvAnimationStyle.setText(resources.getString(R.string.fade_in_fade_out));
            addToScreenAttrBean.setAnimationEffect(Constants.SCREEN_FADE_IN_AND_OUT);
            if(animationPop != null && animationPop.isShowing()){
                animationPop.dismiss();
            }
        });
        animationPop = new PopupWindow(animationContentView,
                (int) resources.getDimension(R.dimen.W350), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        animationPop.setTouchable(true);
        animationPop.setOutsideTouchable(false);
        animationPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
    }

    @OnClick({R.id.img_left,R.id.btn_save,R.id.tv_stayTimeUnit,R.id.tv_previewTimeUnit,R.id.tv_animationStyle})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.btn_save :

                break;

            case R.id.tv_stayTimeUnit :
                if(stayTimePop != null){
                    stayTimePop.showAsDropDown(tvStayTimeUnit);
                }
                break;

            case R.id.tv_previewTimeUnit :
                if(previewTimePop != null){
                    previewTimePop.showAsDropDown(tvPreviewTimeUnit);
                }
                break;

            case R.id.tv_animationStyle :
                if(animationPop != null){
                    animationPop.showAsDropDown(tvAnimationStyle);
                }
                break;
        }
    }
}
