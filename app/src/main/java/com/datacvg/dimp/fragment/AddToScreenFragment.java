package com.datacvg.dimp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ChooseScreenAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.ScreenListBean;
import com.datacvg.dimp.presenter.AddToScreenPresenter;
import com.datacvg.dimp.view.AddToScreenView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    private List<ScreenBean> screenBeans = new ArrayList<>() ;
    private PopupWindow popupWindow ;
    private PopupWindow stayTimePop ;
    private PopupWindow previewTimePop ;
    private PopupWindow animationPop ;

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
     * 创建停留和预览单位弹窗
     */
    private void createStayAndPreviewPop() {
        View stayContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_time_unit, null);
        stayContentView.findViewById(R.id.tv_hour).setOnClickListener(v -> {
            tvStayTimeUnit.setText(resources.getString(R.string.hour));
        });
        stayContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvStayTimeUnit.setText(resources.getString(R.string.minute));
        });
        stayContentView.findViewById(R.id.tv_second).setOnClickListener(v -> {
            tvStayTimeUnit.setText(resources.getString(R.string.second));
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
        });
        previewContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.minute));
        });
        previewContentView.findViewById(R.id.tv_second).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.second));
        });
        previewTimePop = new PopupWindow(previewContentView,
                (int) resources.getDimension(R.dimen.W200), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        previewTimePop.setTouchable(true);
        previewTimePop.setOutsideTouchable(false);
        previewTimePop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));

        View animationContentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_time_unit, null);
        animationContentView.findViewById(R.id.tv_hour).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.hour));
        });
        animationContentView.findViewById(R.id.tv_minute).setOnClickListener(v -> {
            tvPreviewTimeUnit.setText(resources.getString(R.string.minute));
        });
        animationPop = new PopupWindow(previewContentView,
                (int) resources.getDimension(R.dimen.W350), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        animationPop.setTouchable(true);
        animationPop.setOutsideTouchable(false);
        animationPop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
    }

    @Override
    protected void setupData() {
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
            tvSelectName.setText(screenBeans.get(0).getScreen_name());
        }
    }

    /**
     * 大屏选择回调
     * @param position
     */
    @Override
    public void onChooseClick(int position) {
        tvSelectName.setText(screenBeans.get(position).getScreen_name());
        if(popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }
}
