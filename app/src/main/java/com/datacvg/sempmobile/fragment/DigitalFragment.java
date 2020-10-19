package com.datacvg.sempmobile.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.activity.MyIndexActivity;
import com.datacvg.sempmobile.adapter.DimensionIndexAdapter;
import com.datacvg.sempmobile.adapter.DimensionPopAdapter;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.TimeUtils;
import com.datacvg.sempmobile.baseandroid.widget.CustomPopWindow;
import com.datacvg.sempmobile.bean.ChatTypeRequestBean;
import com.datacvg.sempmobile.bean.DimensionBean;
import com.datacvg.sempmobile.bean.DimensionListBean;
import com.datacvg.sempmobile.bean.DimensionPositionBean;
import com.datacvg.sempmobile.bean.DimensionPositionListBean;
import com.datacvg.sempmobile.bean.OtherDimensionBean;
import com.datacvg.sempmobile.presenter.DigitalPresenter;
import com.datacvg.sempmobile.view.DigitalView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 数字神经
 */
public class DigitalFragment extends BaseFragment<DigitalView, DigitalPresenter> implements DigitalView {

    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    /**
     * 维度，最少有组织维度，最多三个维度
     */
    @BindView(R.id.tv_orgDimension)
    TextView tvOrgDimension ;
    @BindView(R.id.tv_areaDimension)
    TextView tvAreaDimension ;
    @BindView(R.id.tv_proDimension)
    TextView tvProDimension ;

    private DimensionPopAdapter popAdapter ;
    private DimensionIndexAdapter dimensionIndexAdapter ;

    private List<DimensionBean> orgDimensionBeans = new ArrayList<>();
    private List<DimensionBean> areaDimensionBeans = new ArrayList<>();
    private List<DimensionBean> proDimensionBeans = new ArrayList<>();
    private List<DimensionBean> popDimensionBeans = new ArrayList<>();
    private List<DimensionPositionBean> dimensionPositionBeans = new ArrayList<>() ;
    private CustomPopWindow popWindow ;
    private String mFuValue ;
    private String mOrgValue ;
    private String mPValue ;
    private String mOrgDimension ;
    private String mFuDimension ;
    private String mPDimension ;
    private String mLang ;
    private String mTimeValue ;

    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_digital;
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
        imgLeft.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_share));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_filter));
        tvTitle.setText(TimeUtils.getNewStrDateForStr(PreferencesHelper
                .get(Constants.USER_DEFAULT_TIME,""),TimeUtils.FORMAT_YM_CN));
        Drawable drawable = resources.getDrawable(R.mipmap.icon_drop);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null,null,drawable,null);
        tvTitle.setCompoundDrawablePadding(40);

        initCustomPickView();
        getDimension();
        getOtherDimension();
        getIndexPosition();
    }

    /**
     * 获取维度数据
     */
    private void getDimension() {
        getPresenter().getDimension();
    }

    /**
     * 获取其他维度
     */
    private void getOtherDimension() {
        getPresenter().getOtherDimension();
    }

    /**
     *  获取指标列表
     */
    private void getIndexPosition() {
        getPresenter().getIndexPosition();
    }

    /**
     * 初始化时间选择器
     */
    private void initCustomPickView() {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date(TimeUtils.parse(PreferencesHelper.get(Constants.USER_DEFAULT_TIME
                ,selectedDate.getTime().toString())).getTime()));
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(1900,1,1);
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH)
                , selectedDate.get(Calendar.DATE));
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvTitle.setText(TimeUtils.date2Str(date,TimeUtils.FORMAT_YM_CN));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        TextView tvYear = v.findViewById(R.id.tv_year);
                        WheelView wheelMonth = v.findViewById(R.id.month);
                        WheelView wheelDay = v.findViewById(R.id.day);
                        TextView tvMoth = v.findViewById(R.id.tv_month);
                        TextView tvDay = v.findViewById(R.id.tv_day);
                        tvDay.setSelected(true);
                        tvYear.setOnClickListener(view->{
                            tvYear.setSelected(true);
                            tvMoth.setSelected(false);
                            tvDay.setSelected(false);
                            wheelMonth.setVisibility(View.GONE);
                            wheelDay.setVisibility(View.GONE);
                        });

                        tvMoth.setOnClickListener(view ->{
                                tvYear.setSelected(false);
                                tvMoth.setSelected(true);
                                tvDay.setSelected(false);
                                wheelMonth.setVisibility(View.VISIBLE);
                                wheelDay.setVisibility(View.GONE);
                        });

                        tvDay.setOnClickListener(view -> {
                                tvYear.setSelected(false);
                                tvMoth.setSelected(false);
                                tvDay.setSelected(true);
                                wheelMonth.setVisibility(View.VISIBLE);
                                wheelDay.setVisibility(View.VISIBLE);
                        });

                        tvSubmit.setOnClickListener(view -> {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                        });

                        ivCancel.setOnClickListener(view -> {
                                pvCustomTime.dismiss();
                        });
                    }
                })
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("")
                .setOutSideCancelable(false)
                .isCyclic(true)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .isDialog(true)
                .build();
    }

    @OnClick({R.id.img_left,R.id.tv_title,R.id.img_right,R.id.tv_orgDimension
            ,R.id.tv_areaDimension,R.id.tv_proDimension})
    public void OnClick(View view){
        switch (view.getId()){
            /**
             * 分享
             */
            case R.id.img_left :

                break;

            /**
             * 时间选择
             */
            case R.id.tv_title :
                    pvCustomTime.show();
                break;

            /**
             * 指标选择
             */
            case R.id.img_right :
                    mContext.startActivity(new Intent(mContext, MyIndexActivity.class));
                break;

            /**
             * 组织维度选择
             */
            case R.id.tv_orgDimension :
                popDimensionBeans.clear();
                popDimensionBeans.addAll(orgDimensionBeans);
                showPopView(view);
                break;

            case R.id.tv_areaDimension :
                popDimensionBeans.clear();
                popDimensionBeans.addAll(areaDimensionBeans);
                showPopView(view);
                break;

            case R.id.tv_proDimension :
                popDimensionBeans.clear();
                popDimensionBeans.addAll(proDimensionBeans);
                showPopView(view);
                break;
        }
    }

    /**
     * 弹出维度选择框
     */
    private void showPopView(View parent) {
        if(popWindow == null){
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.pop_dimension,null,false);
            RecyclerView recyclerDimension = view.findViewById(R.id.recycler_dimension);
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(RecyclerView.VERTICAL);
            manager.setAutoMeasureEnabled(true);
            recyclerDimension.setLayoutManager(manager);
            popAdapter = new DimensionPopAdapter(mContext,popDimensionBeans);
            recyclerDimension.setAdapter(popAdapter);
            popWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                    .setView(view).enableBackgroundDark(true).create();
            popWindow.showAtLocation(parent, Gravity.CENTER,0,0);
        }else{
            popWindow.showAtLocation(parent, Gravity.CENTER,0,0);
            popAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取维度成功
     * @param dimensions
     */
    @Override
    public void getDimensionSuccess(DimensionListBean dimensions) {
        orgDimensionBeans.clear();
        if(dimensions != null && dimensions.size() > 0){
            tvOrgDimension.setVisibility(View.VISIBLE);
            tvOrgDimension.setText(dimensions.get(0).getText());
            mOrgValue = dimensions.get(0).getValue();
            mOrgDimension = dimensions.get(0).getFlname();
            orgDimensionBeans.addAll(dimensions);
        }else{
            tvOrgDimension.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void getOtherDimensionSuccess(OtherDimensionBean dimensions) {
        areaDimensionBeans.clear();
        proDimensionBeans.clear();
        if(dimensions != null){
            if(dimensions.get_$0() != null && dimensions.get_$0().size() > 0){
                tvAreaDimension.setVisibility(View.VISIBLE);
                tvAreaDimension.setText(dimensions.get_$0().get(0).getText());
                areaDimensionBeans.addAll(dimensions.get_$0());
            }else{
                tvAreaDimension.setVisibility(View.INVISIBLE);
            }

            if(dimensions.get_$1() != null && dimensions.get_$1().size() > 0){
                tvProDimension.setVisibility(View.VISIBLE);
                tvProDimension.setText(dimensions.get_$1().get(0).getText());
                proDimensionBeans.addAll(dimensions.get_$1());
            }else{
                tvProDimension.setVisibility(View.INVISIBLE);
            }
        }else{
            tvAreaDimension.setVisibility(View.INVISIBLE);
            tvProDimension.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取指标列表成功
     * @param dimensionPositionBeans
     */

    @Override
    public void getDimensionPositionSuccess(DimensionPositionListBean dimensionPositionBeans) {
        PLog.e(dimensionPositionBeans.size() + "");
        ChatTypeRequestBean chatTypeRequestBean = new ChatTypeRequestBean();
        chatTypeRequestBean.setFuValue("region");
        chatTypeRequestBean.setOrgValue("DATACVG");
        chatTypeRequestBean.setPValue("GOODS");
        chatTypeRequestBean.setOrgDimension("14860367656855969470");
        chatTypeRequestBean.setFuDimension("118306192070461277956");
        chatTypeRequestBean.setPDimension("118341583624371459776");
        chatTypeRequestBean.setLang("zh");
        chatTypeRequestBean.setTimeValue("202004");
        List<ChatTypeRequestBean.ChartTypeBean> beans = new ArrayList<>();
        for (int position = 0 ; position < dimensionPositionBeans.size() ; position++){
            ChatTypeRequestBean.ChartTypeBean bean = new ChatTypeRequestBean.ChartTypeBean();
            bean.setAnalysisDim(dimensionPositionBeans.get(position).getAnalysis_dimension());
            bean.setDataType(dimensionPositionBeans.get(position).getChart_type());
            bean.setIndexId(dimensionPositionBeans.get(position).getIndex_id());
            beans.add(bean);
        }
        chatTypeRequestBean.setChartType(beans);
        Map map = new HashMap();
        map = new Gson().fromJson(new Gson().toJson(chatTypeRequestBean),Map.class);
        getPresenter().getCharts(map);
    }
}
