package com.datacvg.dimp.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ChooseScreenSizeAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.AddToScreenAttrBean;
import com.datacvg.dimp.bean.AddToScreenRequestBean;
import com.datacvg.dimp.bean.ScreenFormatBean;
import com.datacvg.dimp.event.AddToScreenEvent;
import com.datacvg.dimp.event.AddToScreenSuccessEvent;
import com.datacvg.dimp.presenter.NewScreenPresenter;
import com.datacvg.dimp.view.NewScreenView;
import com.enlogy.statusview.StatusRelativeLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description : 新屏
 */
public class NewScreenFragment extends BaseFragment<NewScreenView, NewScreenPresenter>
        implements NewScreenView, ChooseScreenSizeAdapter.OnScreenSizeChooseClick {
    @BindView(R.id.rel_screenSizeOfNine)
    RelativeLayout relScreenSizeOfNine ;
    @BindView(R.id.rel_screenSizeOfTen)
    RelativeLayout relScreenSizeOfTen ;
    @BindView(R.id.rel_screenSizeOfOne)
    RelativeLayout relScreenSizeOfOne ;
    @BindView(R.id.rel_screenSizeOfCustom)
    RelativeLayout relScreenSizeOfCustom ;
    @BindView(R.id.lin_screenTypeOfNineOrTen)
    LinearLayout linScreenTypeOfNineOrTen ;
    @BindView(R.id.lin_screenTypeOfCustom)
    LinearLayout linScreenTypeOfCustom ;
    @BindView(R.id.lin_screenTypeOfOne)
    LinearLayout linScreenTypeOfOne ;
    @BindView(R.id.tv_screenSizeValueOfNineOrTen)
    TextView tvScreenSizeValueOfNineOrTen ;
    @BindView(R.id.tv_screenSizeValueOfOne)
    TextView tvScreenSizeValueOfOne ;
    @BindView(R.id.tv_landscape)
    TextView tvLandscape ;
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

    private PopupWindow stayTimePop ;
    private PopupWindow previewTimePop ;
    private PopupWindow animationPop ;
    private PopupWindow screenOfOnePop ;
    private PopupWindow screenOfNineOrTenPop ;
    private PopupWindow screenOrientationPop ;
    private int stayTimeValue = 300 ;
    private int previewTimeValue = 20 ;
    private int animationTimeValue = 300 ;
    private int screenWide = 2000 ;
    private int screenHeight = 2000 ;
    private String screenName = "" ;
    private AddToScreenAttrBean addToScreenAttrBean ;
    private ScreenFormatBean screenFormatBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_screen;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        relScreenSizeOfNine.setSelected(true);
        relScreenSizeOfTen.setSelected(false);
        relScreenSizeOfOne.setSelected(false);
        relScreenSizeOfCustom.setSelected(false);
        createStayAndPreviewPop();
    }

    @Override
    protected void setupData() {
        addToScreenAttrBean = new AddToScreenAttrBean() ;
        addToScreenAttrBean.setStayUnit(Constants.SECOND);
        addToScreenAttrBean.setStayTime(stayTimeValue);
        addToScreenAttrBean.setLoadTimeUnit(Constants.SECOND);
        addToScreenAttrBean.setLoadTime(previewTimeValue);
        addToScreenAttrBean.setAnimationTime(animationTimeValue);
        addToScreenAttrBean.setAnimationEffect(Constants.SCREEN_FADE_IN_AND_OUT);

        screenFormatBean = new ScreenFormatBean();
        screenFormatBean.setType("16:9");
        screenFormatBean.setSize("32");
        screenFormatBean.setDirection(Constants.SCREEN_HORIZONTAL);
    }

    @OnTextChanged(value = R.id.edit_screenName,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onScreenNameChange(Editable editable){
        screenName = editable.toString().trim();
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

    @OnTextChanged(value = R.id.edit_screenWide,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onScreenWideChange(Editable editable){
        if(TextUtils.isEmpty(editable.toString().trim())){
            screenWide = 2000;
        }else{
            screenWide = Integer.valueOf(editable.toString().trim());
        }
        screenFormatBean.setWidth(screenWide + "");
    }

    @OnTextChanged(value = R.id.edit_screenHigh,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onScreenHighChange(Editable editable){
        if(TextUtils.isEmpty(editable.toString().trim())){
            screenHeight = 2000;
        }else{
            screenHeight = Integer.valueOf(editable.toString().trim());
        }
        screenFormatBean.setHeight(screenHeight + "");
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
            tvAnimationStyle.setText(resources.getString(R.string.fade_in_fade_out));
            addToScreenAttrBean.setAnimationEffect(Constants.SCREEN_FADE_IN_AND_OUT);
            if(animationPop != null && animationPop.isShowing()){
                animationPop.dismiss();
            }
        });
        animationContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvAnimationStyle.setText(resources.getString(R.string.the_level_of_translation));
            addToScreenAttrBean.setAnimationEffect(Constants.SCREEN_PAN_HORIZONTAL);
            if(animationPop != null && animationPop.isShowing()){
                animationPop.dismiss();
            }
        });
        animationPop = new PopupWindow(animationContentView,
                (int) resources.getDimension(R.dimen.W350), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        animationPop.setTouchable(true);
        animationPop.setOutsideTouchable(false);
        animationPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));

        View orientationContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_screen_orientation, null);
        orientationContentView.findViewById(R.id.tv_hour).setOnClickListener(v -> {
            if(screenOrientationPop != null && screenOrientationPop.isShowing()){
                screenFormatBean.setDirection(Constants.SCREEN_VERTICAL);
                tvLandscape.setText(resources.getString(R.string.landscape));
                screenOrientationPop.dismiss();
            }
        });
        orientationContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            if(screenOrientationPop != null && screenOrientationPop.isShowing()){
                screenFormatBean.setDirection(Constants.SCREEN_HORIZONTAL);
                tvLandscape.setText(resources.getString(R.string.vertical_screen));
                screenOrientationPop.dismiss();
            }
        });
        screenOrientationPop = new PopupWindow(orientationContentView,
                (int) resources.getDimension(R.dimen.W170), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        screenOrientationPop.setTouchable(true);
        screenOrientationPop.setOutsideTouchable(false);
        screenOrientationPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));

        View screenNineOrTenContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_choose_screen, null);
        RecyclerView recyclerScreenNineOrTen = screenNineOrTenContentView.findViewById(R.id.recycler_screen);
        ChooseScreenSizeAdapter chooseScreenNineOrTenAdapter
                = new ChooseScreenSizeAdapter(mContext,this,Arrays.asList(resources.getStringArray(R.array.screen_size)));
        recyclerScreenNineOrTen.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerScreenNineOrTen.setAdapter(chooseScreenNineOrTenAdapter);
        screenOfNineOrTenPop = new PopupWindow(screenNineOrTenContentView,
                (int) resources.getDimension(R.dimen.W170), (int) resources.getDimension(R.dimen.H400), true);
        screenOfNineOrTenPop.setTouchable(true);
        screenOfNineOrTenPop.setOutsideTouchable(false);
        screenOfNineOrTenPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
    }

    @OnClick({R.id.rel_screenSizeOfNine,R.id.rel_screenSizeOfTen,
            R.id.rel_screenSizeOfOne,R.id.rel_screenSizeOfCustom,
            R.id.tv_stayTimeUnit,R.id.tv_previewTimeUnit,
            R.id.tv_animationStyle,R.id.tv_screenSizeValueOfOne,
            R.id.tv_landscape,R.id.tv_screenSizeValueOfNineOrTen})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rel_screenSizeOfNine :
                screenFormatBean.setType("16:9");
                relScreenSizeOfNine.setSelected(true);
                relScreenSizeOfTen.setSelected(false);
                relScreenSizeOfOne.setSelected(false);
                relScreenSizeOfCustom.setSelected(false);
                linScreenTypeOfNineOrTen.setVisibility(View.VISIBLE);
                linScreenTypeOfOne.setVisibility(View.GONE);
                linScreenTypeOfCustom.setVisibility(View.GONE);
                break;

            case R.id.rel_screenSizeOfTen :
                screenFormatBean.setType("16:10");
                relScreenSizeOfNine.setSelected(false);
                relScreenSizeOfTen.setSelected(true);
                relScreenSizeOfOne.setSelected(false);
                relScreenSizeOfCustom.setSelected(false);
                linScreenTypeOfNineOrTen.setVisibility(View.VISIBLE);
                linScreenTypeOfOne.setVisibility(View.GONE);
                linScreenTypeOfCustom.setVisibility(View.GONE);
                break;

            case R.id.rel_screenSizeOfOne :
                screenFormatBean.setType("1:1");
                relScreenSizeOfNine.setSelected(false);
                relScreenSizeOfTen.setSelected(false);
                relScreenSizeOfOne.setSelected(true);
                relScreenSizeOfCustom.setSelected(false);
                linScreenTypeOfNineOrTen.setVisibility(View.GONE);
                linScreenTypeOfOne.setVisibility(View.VISIBLE);
                linScreenTypeOfCustom.setVisibility(View.GONE);
                if(screenOfOnePop == null){
                    View screenOneContentView = LayoutInflater.from(mContext)
                            .inflate(R.layout.item_pup_choose_screen, null);
                    RecyclerView recyclerScreenOne = screenOneContentView.findViewById(R.id.recycler_screen);
                    ChooseScreenSizeAdapter chooseScreenOneAdapter
                            = new ChooseScreenSizeAdapter(mContext,this,Arrays.asList(resources.getStringArray(R.array.screen_size)));
                    recyclerScreenOne.setLayoutManager(new LinearLayoutManager(mContext));
                    recyclerScreenOne.setAdapter(chooseScreenOneAdapter);
                    screenOfOnePop = new PopupWindow(screenOneContentView,
                            (int) resources.getDimension(R.dimen.W550), (int) resources.getDimension(R.dimen.H400), true);
                    screenOfOnePop.setTouchable(true);
                    screenOfOnePop.setOutsideTouchable(false);
                    screenOfOnePop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
                }
                break;

            case R.id.rel_screenSizeOfCustom :
                screenFormatBean.setType("custom");
                relScreenSizeOfNine.setSelected(false);
                relScreenSizeOfTen.setSelected(false);
                relScreenSizeOfOne.setSelected(false);
                relScreenSizeOfCustom.setSelected(true);
                linScreenTypeOfNineOrTen.setVisibility(View.GONE);
                linScreenTypeOfOne.setVisibility(View.GONE);
                linScreenTypeOfCustom.setVisibility(View.VISIBLE);
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

            case R.id.tv_landscape :
                if(screenOrientationPop != null){
                    screenOrientationPop.showAsDropDown(tvLandscape);
                }
                break;

            case R.id.tv_animationStyle :
                if(animationPop != null){
                    animationPop.showAsDropDown(tvAnimationStyle);
                }
                break;

            case R.id.tv_screenSizeValueOfOne :
                if(screenOfOnePop != null){
                    screenOfOnePop.showAsDropDown(tvScreenSizeValueOfOne);
                }
                break;

            case R.id.tv_screenSizeValueOfNineOrTen :
                if(screenOfNineOrTenPop != null){
                    screenOfNineOrTenPop.showAsDropDown(tvScreenSizeValueOfNineOrTen);
                }
                break;
        }
    }

    @Override
    public void onSizeChooseClick(int position) {
        if(screenOfOnePop != null && screenOfOnePop.isShowing()){
            screenOfOnePop.dismiss();
            tvScreenSizeValueOfOne.setText(Arrays.asList(resources
                    .getStringArray(R.array.screen_size)).get(position));
        }
        if(screenOfNineOrTenPop != null && screenOfNineOrTenPop.isShowing()){
            screenOfNineOrTenPop.dismiss();
            tvScreenSizeValueOfNineOrTen.setText(Arrays.asList(resources
                    .getStringArray(R.array.screen_size)).get(position));
        }
        screenFormatBean.setSize(Arrays.asList(resources
                .getStringArray(R.array.screen_size)).get(position));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddToScreenEvent event){
        if(isFragmentVisible()){
            if(TextUtils.isEmpty(screenName)){
                ToastUtils.showLongToast(resources.getString(R.string.please_enter_a_large_screen_name));
                return;
            }
            AddToScreenRequestBean requestBean = new AddToScreenRequestBean();
            switch (event.getReportBean().getReport_type()){
                case Constants.REPORT_MINE :
                    requestBean.setReportType(Constants.REPORT_MINE_TYPE);
                    requestBean.setReportId(event.getReportBean().getModel_id());
                    break;

                case Constants.REPORT_SHARE :
                    requestBean.setReportType(Constants.REPORT_SHARE_TYPE);
                    requestBean.setReportId(event.getReportBean().getShare_id());
                    break;
            }
            requestBean.setScreenName(screenName);
            requestBean.setScreenFormat(new Gson().toJson(screenFormatBean));
            requestBean.setType("new");
            requestBean.setBasicAttr(new Gson().toJson(addToScreenAttrBean));

            getPresenter().addToScreenRequest(requestBean);
        }
    }

    @Override
    public void addToScreenSuccess() {
        ToastUtils.showLongToast(resources.getString(R.string.description_added_to_the_large_screen_successfully));
        EventBus.getDefault().post(new AddToScreenSuccessEvent());
    }
}
