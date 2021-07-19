package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.presenter.SelectFilterPresenter;
import com.datacvg.dimp.view.SelectFilterView;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-19
 * @Description : 维度时间选择
 */
public class SelectFilterActivity extends BaseActivity<SelectFilterView, SelectFilterPresenter>
        implements SelectFilterView {
    @BindView(R.id.tv_parameterTime)
    TextView tvParameterTime ;
    @BindView(R.id.img_time)
    ImageView imgTime ;
    @BindView(R.id.line_org)
    LinearLayout lineOrg ;
    @BindView(R.id.tv_parameterOrg)
    TextView tvParameterOrg ;
    @BindView(R.id.lin_area)
    LinearLayout linArea ;
    @BindView(R.id.tv_parameterArea)
    TextView tvParameterArea ;
    @BindView(R.id.lin_pro)
    LinearLayout linPro ;
    @BindView(R.id.tv_parameterPro)
    TextView tvParameterPro ;
    @BindView(R.id.timepicker)
    LinearLayout timePicker ;

    private PageItemBean pageItemBean ;
    private TimePickerView pvCustomTime ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_filter;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        pageItemBean = (PageItemBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        StringBuilder builder = new StringBuilder(pageItemBean.getTimeVal());
        switch (pageItemBean.getTime_type()){
            case "year":
                initCustomPickView(true,false,false);
                break;
            case "month":
                initCustomPickView(true,true,false);
                builder.insert(4,"-");
                break;
            default:
                initCustomPickView(true,true,true);
                builder.insert(6,"-").insert(4,"-");
                break;
        }
        tvParameterTime.setText(builder);
        lineOrg.setVisibility(TextUtils.isEmpty(pageItemBean.getmOrgName()) ? View.GONE : View.VISIBLE);
        tvParameterOrg.setText(TextUtils.isEmpty(pageItemBean.getmOrgName())?"":pageItemBean.getmOrgName());
        linPro.setVisibility(TextUtils.isEmpty(pageItemBean.getMpName()) ? View.GONE : View.VISIBLE);
        tvParameterPro.setText(TextUtils.isEmpty(pageItemBean.getMpName())?"":pageItemBean.getMpName());
        linArea.setVisibility(TextUtils.isEmpty(pageItemBean.getmFuName()) ? View.GONE : View.VISIBLE);
        tvParameterArea.setText(TextUtils.isEmpty(pageItemBean.getmFuName())?"":pageItemBean.getmFuName());
    }

    /**
     *
     * @param hasYear
     * @param hasMonth
     * @param hasDay
     */
    private void initCustomPickView(boolean hasYear, boolean hasMonth, boolean hasDay) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date(TimeUtils.parse(pageItemBean.getTimeVal()).getTime()));
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2000,1,1);
        endDate.set(selectedDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH)
                , endDate.get(Calendar.DATE));
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
            }
        })
                .setType(new boolean[]{hasYear, hasMonth, hasDay, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time_of_select, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        switch (pageItemBean.getTime_type()){
                            case "year" :
                                tvParameterTime.setText(calendar.get(Calendar.YEAR));
                                pageItemBean.setTimeVal(calendar.get(Calendar.YEAR) + "");
                                break;

                            case "month" :
                                tvParameterTime.setText(calendar.get(Calendar.YEAR) + "-"
                                        + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1)));
                                pageItemBean.setTimeVal(Calendar.YEAR + ""+ ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1)));
                                break;

                            default:
                                tvParameterTime.setText(calendar.get(Calendar.YEAR) + "-"
                                        + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1))
                                        + "-" + (calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : "0" + calendar.get(Calendar.DATE)));
                                pageItemBean.setTimeVal(calendar.get(Calendar.YEAR) + ""
                                        + ((calendar.get(Calendar.MONTH) + 1) > 9 ? (calendar.get(Calendar.MONTH) + 1) : "0" + (calendar.get(Calendar.MONTH) + 1))
                                        + "" + (calendar.get(Calendar.DATE) > 9 ? calendar.get(Calendar.DATE) : "0" + calendar.get(Calendar.DATE)));
                                break;
                        }
                    }
                })
                .setContentTextSize(18)
                .setTitleText("")
                .setOutSideCancelable(false)
                .isCyclic(true)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .setDecorView(timePicker)
                .build();
        pvCustomTime.show();
    }

    @OnClick({R.id.tv_filtrate_cancel,R.id.tv_complete,R.id.lin_time,R.id.line_org})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_filtrate_cancel :
                    finish();
                break;

            case R.id.tv_complete :
                PLog.e("确定");
                break;

            case R.id.lin_time :
                timePicker.setVisibility(timePicker.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                imgTime.setSelected(timePicker.getVisibility() == View.VISIBLE);
                break;
            case R.id.line_org :

                break;
        }
    }

    @Override
    public void getDimensionSuccess(List<DimensionBean> selectDimension) {

    }

    @Override
    public void getOtherDimensionSuccess(List<DimensionBean> selectOtherDimension, String tag) {

    }
}
