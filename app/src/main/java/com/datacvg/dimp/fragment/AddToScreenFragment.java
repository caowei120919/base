package com.datacvg.dimp.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ChooseScreenAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.AddToScreenAttrBean;
import com.datacvg.dimp.bean.AddToScreenRequestBean;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.ScreenListBean;
import com.datacvg.dimp.event.AddToScreenEvent;
import com.datacvg.dimp.event.AddToScreenSuccessEvent;
import com.datacvg.dimp.presenter.AddToScreenPresenter;
import com.datacvg.dimp.view.AddToScreenView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description : 大屏
 */
public class AddToScreenFragment extends BaseFragment<AddToScreenView, AddToScreenPresenter>
        implements AddToScreenView, ChooseScreenAdapter.OnScreenChooseClick {
    @BindView(R.id.rel_chooseScreen)
    RelativeLayout relChooseScreen ;
    @BindView(R.id.tv_selectName)
    TextView tvSelectName ;
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

    private List<ScreenBean> screenBeans = new ArrayList<>() ;
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
        return R.layout.fragment_add_to_screen;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        createStayAndPreviewPop();
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
        getPresenter().getScreenList(Constants.DESC);
    }

    @OnClick({R.id.tv_selectName,R.id.tv_stayTimeUnit,R.id.tv_previewTimeUnit,R.id.tv_animationStyle})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_selectName :
                if(screenBeans.size() == 0){
                    return;
                }
                if(popupWindow == null){
                    createPopupWindow();
                }else{
                    popupWindow.showAsDropDown(tvSelectName);
                }
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

    /**
     * 创建弹窗视图
     */
    private void createPopupWindow() {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_choose_screen, null);
        RecyclerView recyclerScreen = contentView.findViewById(R.id.recycler_screen);
        ChooseScreenAdapter adapter = new ChooseScreenAdapter(mContext,this,screenBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerScreen.setLayoutManager(manager);
        recyclerScreen.setAdapter(adapter);
        popupWindow = new PopupWindow(contentView,
                tvSelectName.getMeasuredWidth(),
                (int) resources.getDimension(R.dimen.H400), true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_ffffff));
        popupWindow.showAsDropDown(tvSelectName);
    }

    @Override
    public void getScreenSuccess(ScreenListBean data) {
        screenBeans.clear();
        screenBeans.addAll(data);
        if(!screenBeans.isEmpty()){
            this.screenBean = screenBeans.get(0);
            tvSelectName.setText(screenBeans.get(0).getScreen_name());
        }
    }

    /**
     * 添加到大屏成功
     */
    @Override
    public void addToScreenSuccess() {
        ToastUtils.showLongToast(resources.getString(R.string.description_added_to_the_large_screen_successfully));
        EventBus.getDefault().post(new AddToScreenSuccessEvent());
    }

    /**
     * 大屏选择回调
     * @param position
     */
    @Override
    public void onChooseClick(int position) {
        tvSelectName.setText(screenBeans.get(position).getScreen_name());
        this.screenBean = screenBeans.get(position);
        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddToScreenEvent event){
        if(isFragmentVisible()){
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
            requestBean.setScreenId(screenBean.getScreen_id());
            requestBean.setScreenName(screenBean.getScreen_name());
            requestBean.setBasicAttr(new Gson().toJson(addToScreenAttrBean));

            getPresenter().addToScreenRequest(requestBean);
        }
    }
}
