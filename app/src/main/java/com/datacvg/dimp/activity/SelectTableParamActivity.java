package com.datacvg.dimp.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableParamInfoBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.event.RefreshTableEvent;
import com.datacvg.dimp.presenter.SelectTableParamPresenter;
import com.datacvg.dimp.view.SelectTableParamView;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.rel_typeTime)
    RelativeLayout relTypeTime ;
    @BindView(R.id.tv_timeTitleOfOne)
    TextView tvTimeTitleOfOne ;
    @BindView(R.id.tv_timeOfOne)
    TextView tvTimeOfOne ;
    @BindView(R.id.tv_timeTitleOfTwo)
    TextView tvTimeTitleOfTwo ;
    @BindView(R.id.tv_timeOfTwo)
    TextView tvTimeOfTwo ;

    private TimePickerView pvCustomTime ;
    private TableBean tableBean ;

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
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.report_parameters));
        tvRight.setText(resources.getString(R.string.confirm));
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

    @OnClick({R.id.img_left,R.id.tv_right,R.id.tv_timeOfOne,R.id.tv_timeOfTwo})
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

            case R.id.tv_timeOfOne :
                    if(pvCustomTime != null){
                        pvCustomTime.getDialogContainerLayout().setTag(tvTimeOfOne);
                        pvCustomTime.show();
                    }
                break;

            case R.id.tv_timeOfTwo :
                    if(pvCustomTime != null){
                        pvCustomTime.getDialogContainerLayout().setTag(tvTimeOfTwo);
                        pvCustomTime.show();
                    }
                break;
        }
    }

    /**
     * 封装选择参数
     */
    private void getParamArr() throws UnsupportedEncodingException {
        String paramArr = "" ;
        if(relTypeTime.getVisibility() == View.VISIBLE){
            if(tvTimeOfTwo.getVisibility() == View.VISIBLE){
                paramArr = "beginTime=" + tvTimeOfOne.getText().toString() + "::"
                        + "endTime=" + tvTimeOfTwo.getText().toString();
            }else{
                paramArr = "reportTime=" + tvTimeOfOne.getText().toString();
            }
        }
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
        if(resdata != null && resdata.size() > 0){
            for (TableParamInfoBean tableParamInfoBean : resdata){
                /**
                 * 时间维度
                 */
                if(tableParamInfoBean.getParam_type().equals(Constants.REPORT_PARAMS_TIME)){
                    dealWithTimeType(tableParamInfoBean);
                }
                /**
                 * 地区维度
                 */
                if(tableParamInfoBean.getParam_type().equals(Constants.REPORT_PARAMS_DIMENSION)){
                    dealWithDimensionType();
                }
            }
        }
    }

    /**
     * 维度参数
     */
    private void dealWithDimensionType() {

    }

    /**
     * 处理时间参数
     *      年月日季周
     */
    @SuppressLint("NewApi")
    private void dealWithTimeType(TableParamInfoBean tableParamInfoBean) {
        relTypeTime.setVisibility(View.VISIBLE);
        initCustomPickView(tableParamInfoBean);
        switch (tableParamInfoBean.getTime_type()){
            /**
             * 时间点
             */
            case "POINTER" :
                    if(tableParamInfoBean.getTime_unit().equals("TIME_DAY")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.day) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_MONTH")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.month) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_YEAR")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.year) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_QUARTER")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.quarter) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_WEEK")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.week) + ")");
                    }
                tvTimeOfOne.setText(tableParamInfoBean.getEnd_time());
                tvTimeTitleOfTwo.setVisibility(View.GONE);
                tvTimeOfTwo.setVisibility(View.GONE);
                break;

            default:
                tvTimeTitleOfTwo.setVisibility(View.VISIBLE);
                tvTimeOfTwo.setVisibility(View.VISIBLE);
                    if(tableParamInfoBean.getTime_unit().equals("TIME_DAY")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.day) + ")");
                        tvTimeTitleOfTwo.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.day) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_MONTH")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.month) + ")");
                        tvTimeTitleOfTwo.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.month) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_YEAR")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.year) + ")");
                        tvTimeTitleOfTwo.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.year) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_QUARTER")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.quarter) + ")");
                        tvTimeTitleOfTwo.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.quarter) + ")");
                    }else if(tableParamInfoBean.getTime_unit().equals("TIME_WEEK")){
                        tvTimeTitleOfOne.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.week) + ")");
                        tvTimeTitleOfTwo.setText(resources.getString(R.string.to_choose_time)
                                + "(" + resources.getString(R.string.week) + ")");
                    }
                tvTimeOfOne.setText(tableParamInfoBean.getStart_time());
                tvTimeOfTwo.setText(tableParamInfoBean.getEnd_time());
                break;
        }
    }

    /**
     * 初始化时间选择器
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initCustomPickView(TableParamInfoBean tableParamInfoBean) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date());
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        if (tableParamInfoBean.getStart_time().equals(tableParamInfoBean.getEnd_time())){
            startDate.set(2000,0,0);
            endDate.setTime(new Date());
        }else{
            if(tableParamInfoBean.getTime_unit().equals("TIME_WEEK")
                    || tableParamInfoBean.getTime_unit().equals("TIME_QUARTER")){
                if(TextUtils.isEmpty(tableParamInfoBean.getTime_format())){
                    startDate.setTime(TimeUtils.getWeekFirstDay(tableParamInfoBean.getStart_time()));
                    endDate.setTime(TimeUtils.getWeekLastDay(tableParamInfoBean.getEnd_time()));
                }else{
                    startDate.setTime(TimeUtils.getQuarterFirstDay(tableParamInfoBean.getStart_time()));
                    endDate.setTime(TimeUtils.getQuarterLastDay(tableParamInfoBean.getEnd_time()));
                }
            }else{
                startDate.setTime(TimeUtils.str2Date(tableParamInfoBean.getStart_time(),tableParamInfoBean.getTime_format()));
                endDate.setTime(TimeUtils.str2Date(tableParamInfoBean.getEnd_time(),tableParamInfoBean.getTime_format()));
            }
        }
        boolean[] dateType ;
        if(tableParamInfoBean.getTime_unit().equals("TIME_YEAR")){
            dateType = new boolean[]{true,false,false,false,false,false};
        }else if(tableParamInfoBean.getTime_unit().equals("TIME_MONTH")){
            dateType = new boolean[]{true,true,false,false,false,false};
        }else{
            dateType = new boolean[]{true,true,true,false,false,false};
        }
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeSelect(Date date, View v) {
                if (tableParamInfoBean!= null){
                    switch (tableParamInfoBean.getTime_unit()){
                        case "TIME_DAY" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.date2Str(date,TimeUtils.FORMAT_YMD_EN));
                            break;

                        case "TIME_MONTH" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.date2Str(date,TimeUtils.FORMAT_YM));
                            break;

                        case "TIME_YEAR" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.date2Str(date, TextUtils.isEmpty(tableParamInfoBean.getTime_format())
                                            ? TimeUtils.FORMAT_YMD_EN : tableParamInfoBean.getTime_format()));
                            break;

                        case "TIME_WEEK" :
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.getNewStrDateInWeekForStr(TimeUtils.date2Str(date)
                                            ,TimeUtils.FORMAT_YMD_CN));
                            break;

                        default:
                            ((TextView)pvCustomTime.getDialogContainerLayout().getTag())
                                    .setText(TimeUtils.getNewStrDateInQuarterForStrQ(TimeUtils.date2Str(date)
                                            ,TimeUtils.FORMAT_YMD_CN));
                            break;
                    }
                }
            }
        })
                .setType(dateType)
                .setLayoutRes(R.layout.pickerview_report_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
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
}
