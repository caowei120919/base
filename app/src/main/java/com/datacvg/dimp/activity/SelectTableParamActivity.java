package com.datacvg.dimp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableParamInfoBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.event.DimensionParamsSelectEvent;
import com.datacvg.dimp.event.RefreshTableEvent;
import com.datacvg.dimp.presenter.SelectTableParamPresenter;
import com.datacvg.dimp.view.SelectTableParamView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-27
 * @Description : 报表参数选择
 */
public class SelectTableParamActivity extends BaseActivity<SelectTableParamView, SelectTableParamPresenter>
        implements SelectTableParamView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_right)
    TextView tvRight ;
    @BindView(R.id.lin_timeParam)
    LinearLayout linTimeParam ;
    @BindView(R.id.lin_dimensionParam)
    LinearLayout linDimensionParam ;
    @BindView(R.id.lin_custom)
    LinearLayout linCustom ;

    private TimePickerView pvCustomTime ;
    private TableBean tableBean ;
    private View selectView ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_table_param;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(this,mContext.getResources()
                .getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.report_parameters));
        tvRight.setText(resources.getString(R.string.confirm));
        initDataPicker();
    }

    private void initDataPicker() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tableBean = (TableBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(tableBean == null){
            finish();
            return;
        }
        getResParamInfo();
    }

    /**
     * 获取报表参数信息
     */
    private void getResParamInfo() {
        getPresenter().getResParamInfo(tableBean.getRes_id());
    }

    @OnClick({R.id.img_left,R.id.tv_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                 finish();
                break;

            case R.id.tv_right :
                try {
                    getParamArr();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
                break;
        }
    }

    /**
     * 封装选择参数
     */
    private void getParamArr() throws UnsupportedEncodingException {
        String paramArr = "" ;
        if(!TextUtils.isEmpty(paramArr)){
            paramArr = paramArr + "::"
                    + "userpkid=" + PreferencesHelper.get(Constants.USER_PKID,"");
        }
        paramArr = URLEncoder.encode(paramArr,"UTF-8");
        PLog.e(paramArr);
        EventBus.getDefault().post(new RefreshTableEvent(paramArr));
    }

    @Override
    public void getParamInfoSuccess(TableParamInfoListBean resdata) {
        if(resdata != null && resdata.getReportInitParam().size() > 0){
            for (TableParamInfoBean tableParamInfoBean : resdata.getReportInitParam()){
                if(tableParamInfoBean.getControlType().equals(Constants.REPORT_PARAMS_TIME)){
                    dealWithTimeType(tableParamInfoBean);
                }
                if(tableParamInfoBean.getControlType().equals(Constants.REPORT_PARAMS_DIMENSION)){
                    dealWithDimensionType(tableParamInfoBean);
                }
                if(tableParamInfoBean.getControlType().equals(Constants.REPORT_PARAMS_CUSTOM)){
                    dealWithCustomType(tableParamInfoBean);
                }
            }
        }
    }

    /**
     * 自定义参数
     */
    private void dealWithCustomType(TableParamInfoBean tableParamInfoBean) {
        linCustom.setVisibility(View.VISIBLE);
        if(tableParamInfoBean.getDataSource().isEmpty()){
            View customView = LayoutInflater.from(mContext).inflate(R.layout.item_param
                    ,linCustom,false);
            linCustom.addView(customView);
            TextView tvCustomName = customView.findViewById(R.id.tv_paramName);
            tvCustomName.setText(tableParamInfoBean.getControlName());
            customView.setOnClickListener(view -> {
                ToastUtils.showLongToast(resources.getString(R.string.no_optional_parameter));
            });
        }else{
            View customView = LayoutInflater.from(mContext).inflate(R.layout.item_param_custom
                    ,linCustom,false);
            linCustom.addView(customView);
            TextView tvCustomName = customView.findViewById(R.id.tv_customName);
            LinearLayout linCustomType = customView.findViewById(R.id.lin_customType);
            tvCustomName.setText(tableParamInfoBean.getControlName());
            for (TableParamInfoBean.DataSourceBean dataSourceBean : tableParamInfoBean.getDataSource()){
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_param
                        ,linCustomType,false);
                TextView tvParamName = view.findViewById(R.id.tv_paramName);
                TextView tvParamValue = view.findViewById(R.id.tv_paramValue);
                tvParamName.setText(dataSourceBean.getSetName());
                linCustomType.addView(view);
                view.setOnClickListener(view1 -> {
                    PLog.e(dataSourceBean.getBizKey());
                });
            }
        }
    }

    /**
     * 维度参数
     */
    private void dealWithDimensionType(TableParamInfoBean tableParamInfoBean) {
        linDimensionParam.setVisibility(View.VISIBLE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_param
                ,linDimensionParam,false);
        linDimensionParam.addView(view);
        TextView tvParamName = view.findViewById(R.id.tv_paramName);
        TextView tvParamValue = view.findViewById(R.id.tv_paramValue);
        tvParamName.setText(tableParamInfoBean.getControlName());
        if(!tableParamInfoBean.getDataSource().isEmpty()){
            tvParamValue.setText(LanguageUtils.isZh(mContext)
                    ? tableParamInfoBean.getDataSource().get(0).getD_res_clname()
                    : tableParamInfoBean.getDataSource().get(0).getD_res_flname());
        }else{
            tvParamValue.setText("");
        }
        view.setOnClickListener(view1 -> {
            PLog.e("维度参数");
            selectView = tvParamValue ;
            if (tableParamInfoBean.getDataSource().isEmpty()){
                ToastUtils.showLongToast(resources.getString(R.string.no_optional_parameter));
            }else{
                Intent intent = new Intent(mContext,SelectDimensionActivity.class);
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,tableParamInfoBean);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 处理时间参数
     *      年月日季周
     */
    @SuppressLint("NewApi")
    private void dealWithTimeType(TableParamInfoBean tableParamInfoBean) {
        linTimeParam.setVisibility(View.VISIBLE);
        if (tableParamInfoBean.getDataSource() != null && tableParamInfoBean.getDataSource().size() > 0){
            TableParamInfoBean.DataSourceBean dataSourceBean = tableParamInfoBean.getDataSource().get(0) ;
            initCustomPickView(dataSourceBean);
            switch (dataSourceBean.getTimeType()) {
                /**
                 * 时间点
                 */
                case "POINTER":
                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_param
                            ,linTimeParam,false);
                    linTimeParam.addView(view);
                    TextView tvParamName = view.findViewById(R.id.tv_paramName);
                    TextView tvParamValue = view.findViewById(R.id.tv_paramValue);
                    Date date = new Date();
                    if (dataSourceBean.getTimeUnit().equals("TIME_DAY")) {
                        if (!TextUtils.isEmpty(dataSourceBean.getEndTime())){
                            date = TimeUtils.parse(dataSourceBean.getEndTime()
                                    ,dataSourceBean.getTimeFormat());
                        }
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        tvParamValue.setText(TimeUtils.date2Str(TimeUtils.addDay(date,-change)
                                ,dataSourceBean.getTimeFormat()));
                        tvParamName.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.day) + "):");
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_MONTH")) {
                        tvParamName.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.month) + "):");
                        if (!TextUtils.isEmpty(dataSourceBean.getEndTime())){
                            date = TimeUtils.parse(dataSourceBean.getEndTime()
                                    ,dataSourceBean.getTimeFormat());
                        }
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        tvParamValue.setText(TimeUtils.date2Str(TimeUtils.addMonth(date,-change)
                                ,dataSourceBean.getTimeFormat()));
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_YEAR")) {
                        tvParamName.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.year) + "):");
                        if (!TextUtils.isEmpty(dataSourceBean.getEndTime())){
                            date = TimeUtils.parse(dataSourceBean.getEndTime()
                                    ,dataSourceBean.getTimeFormat());
                        }
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        tvParamValue.setText(TimeUtils.date2Str(TimeUtils.addYear(date,-change)
                                ,dataSourceBean.getTimeFormat()));
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_QUARTER")) {
                        tvParamName.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.quarter) + "):");
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        if (!TextUtils.isEmpty(dataSourceBean.getEndTime())){
                            int year = Integer.valueOf(dataSourceBean.getEndTime().substring(0,4));
                            int month = Integer.valueOf(dataSourceBean.getEndTime().substring(5,6)) * 3;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year,month,1);
                            date = calendar.getTime();
                        }
                        tvParamValue.setText(TimeUtils.getNewStrDateInQuarterForStrQ(TimeUtils.addQuarter(date,-change)));
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_WEEK")) {
                        tvParamName.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.week) + "):");
                        if (!TextUtils.isEmpty(dataSourceBean.getEndTime())){
                            date = TimeUtils.parse(dataSourceBean.getEndTime()
                                    ,dataSourceBean.getTimeFormat());
                        }
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(TimeUtils.addWeek(date,-change));
                        int week = calendar.get(Calendar.WEEK_OF_YEAR) ;
                        tvParamValue.setText(calendar.get(Calendar.YEAR) + "" + (week >= 10 ? week : "0" + week));
                    }

                    view.setOnClickListener(view1 -> {
                        PLog.e("选择参数");
                        pvCustomTime.show();
                    });
                    break;

                default:
                    View viewStart = LayoutInflater.from(mContext).inflate(R.layout.item_param
                            ,linTimeParam,false);
                    linTimeParam.addView(viewStart);
                    TextView tvStartParamName = viewStart.findViewById(R.id.tv_paramName);
                    TextView tvStartParamValue = viewStart.findViewById(R.id.tv_paramValue);

                    View viewEnd = LayoutInflater.from(mContext).inflate(R.layout.item_param
                            ,linTimeParam,false);
                    linTimeParam.addView(viewEnd);
                    TextView tvEndParamName = viewEnd.findViewById(R.id.tv_paramName);
                    TextView tvEndParamValue = viewEnd.findViewById(R.id.tv_paramValue);
                    Date dateRange = new Date();
                    if (dataSourceBean.getTimeUnit().equals("TIME_DAY")) {
                        dateRange = TimeUtils.parse(dataSourceBean.getEndTime()
                                ,dataSourceBean.getTimeFormat());
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        tvStartParamName.setText(resources.getString(R.string.the_start_time)
                                + "(" + resources.getString(R.string.day) + "):");
                        tvStartParamValue.setText(dataSourceBean.getStartTime());

                        tvEndParamValue.setText(TimeUtils.date2Str(TimeUtils.addDay(dateRange,-change)
                                ,dataSourceBean.getTimeFormat()));
                        tvEndParamName.setText(resources.getString(R.string.the_end_of_time)
                                + "(" + resources.getString(R.string.day) + "):");
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_MONTH")) {
                        tvEndParamName.setText(resources.getString(R.string.the_end_of_time)
                                + "(" + resources.getString(R.string.month) + "):");

                        tvStartParamName.setText(resources.getString(R.string.the_start_time)
                                + "(" + resources.getString(R.string.month) + "):");
                        tvStartParamValue.setText(dataSourceBean.getStartTime());

                        dateRange = TimeUtils.parse(dataSourceBean.getEndTime()
                                ,dataSourceBean.getTimeFormat());
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        tvEndParamValue.setText(TimeUtils.date2Str(TimeUtils.addMonth(dateRange,-change)
                                ,dataSourceBean.getTimeFormat()));
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_YEAR")) {
                        tvEndParamName.setText(resources.getString(R.string.the_end_of_time)
                                + "(" + resources.getString(R.string.year) + "):");
                        tvStartParamName.setText(resources.getString(R.string.the_start_time)
                                + "(" + resources.getString(R.string.year) + "):");
                        tvStartParamValue.setText(dataSourceBean.getStartTime());
                        dateRange = TimeUtils.parse(dataSourceBean.getEndTime()
                                ,dataSourceBean.getTimeFormat());
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        tvEndParamValue.setText(TimeUtils.date2Str(TimeUtils.addYear(dateRange,-change)
                                ,dataSourceBean.getTimeFormat()));
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_QUARTER")) {
                        tvEndParamName.setText(resources.getString(R.string.the_end_of_time)
                                + "(" + resources.getString(R.string.quarter) + "):");
                        tvStartParamName.setText(resources.getString(R.string.the_start_time)
                                + "(" + resources.getString(R.string.quarter) + "):");
                        tvStartParamValue.setText(dataSourceBean.getStartTime());
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        int year = Integer.valueOf(dataSourceBean.getEndTime().substring(0,4));
                        int month = Integer.valueOf(dataSourceBean.getEndTime().substring(5,6)) * 3;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year,month,1);
                        dateRange = calendar.getTime();
                        tvEndParamValue.setText(TimeUtils.getNewStrDateInQuarterForStrQ(TimeUtils.addQuarter(dateRange,-change)));
                    } else if (dataSourceBean.getTimeUnit().equals("TIME_WEEK")) {
                        tvEndParamName.setText(resources.getString(R.string.the_end_of_time)
                                + "(" + resources.getString(R.string.week) + "):");
                        tvStartParamName.setText(resources.getString(R.string.the_start_time)
                                + "(" + resources.getString(R.string.week) + "):");
                        tvStartParamValue.setText(dataSourceBean.getStartTime());
                        dateRange = TimeUtils.parse(dataSourceBean.getEndTime()
                                ,dataSourceBean.getTimeFormat());
                        int change = Integer.valueOf(dataSourceBean.getRelativeEnd());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(TimeUtils.addWeek(dateRange,-change));
                        int week = calendar.get(Calendar.WEEK_OF_YEAR) ;
                        tvEndParamValue.setText(calendar.get(Calendar.YEAR) + "" + (week >= 10 ? week : "0" + week));
                    }
                    break;
            }
        }else{
            ToastUtils.showLongToast(resources.getString(R.string.time_parameter_query_error));
        }
    }

    private void initCustomPickView(TableParamInfoBean.DataSourceBean dataSourceBean) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100,1,1);
        startDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH)
                , selectedDate.get(Calendar.DATE));
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView tvDataTitle = v.findViewById(R.id.tv_dataTitle);
                        tvDataTitle.setVisibility(View.VISIBLE);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("")
                .setOutSideCancelable(false)
                .isCyclic(false)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .isDialog(false)
                .build();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DimensionParamsSelectEvent event){
        String params = "" ;
        for (TableParamInfoBean.DataSourceBean bean : event.getDataSourceBeans()){
            params = TextUtils.isEmpty(params) ? params + bean.getD_res_clname() : params
                    + "," + bean.getD_res_clname();
        }
        ((TextView)selectView).setText(params);
    }
}
