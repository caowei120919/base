package com.datacvg.dimp.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.bean.ChatTypeRequestBean;
import com.datacvg.dimp.event.ChangeTimeValEvent;
import com.datacvg.dimp.presenter.IndexDetailPresenter;
import com.datacvg.dimp.view.IndexDetailView;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description :
 */
public class IndexDetailActivity extends BaseActivity<IndexDetailView, IndexDetailPresenter>
        implements IndexDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_indexName)
    TextView tvIndexName ;
    @BindView(R.id.tv_target)
    TextView tvTarget ;
    @BindView(R.id.rel_target)
    RelativeLayout relTarget ;
    @BindView(R.id.rel_challenge)
    RelativeLayout relChallenge ;
    @BindView(R.id.tv_challenge)
    TextView tvChallenge ;
    @BindView(R.id.rel_guaranteed)
    RelativeLayout relGuaranteed ;
    @BindView(R.id.tv_guaranteed)
    TextView tvGuaranteed ;
    @BindView(R.id.tv_describe)
    TextView tvDescribe ;


    private ChatTypeRequestBean chatTypeRequestBean ;
    private String mTimeValue ;
    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_detail;
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
        chatTypeRequestBean = (ChatTypeRequestBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(chatTypeRequestBean == null){
            finish();
            return;
        }
        initCustomPickView();
        mTimeValue = PreferencesHelper.get(Constants.USER_DEFAULT_TIME,"") ;
        tvTitle.setText(TimeUtils.getNewStrDateForStr(mTimeValue,TimeUtils.FORMAT_YM_CN));
        Drawable drawable = resources.getDrawable(R.mipmap.icon_drop);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tvTitle.setCompoundDrawables(null,null,drawable,null);
        tvTitle.setCompoundDrawablePadding(40);
        getChart(mTimeValue);
    }

    private void getChart(String timeVal) {
        chatTypeRequestBean.setTimeValue(timeVal);
        chatTypeRequestBean.setLang(LanguageUtils.isZh(mContext) ? Constants.LANGUAGE_CHINESE
                : Constants.LANGUAGE_ENGLISH);
        Map map = new Gson().fromJson(new Gson().toJson(chatTypeRequestBean),Map.class);
        getPresenter().getChart(map);
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
                mTimeValue = TimeUtils.date2Str(date,TimeUtils.FORMAT_YM);
                EventBus.getDefault().post(new ChangeTimeValEvent(mTimeValue));
                getChart(mTimeValue);
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

    @OnClick({R.id.img_left})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left:
                finish();
                break;
        }
    }

    /**
     * 获取图表信息成功
     * @param resdata
     */
    @Override
    public void getChartSuccess(ChartListBean resdata) {
        PLog.e("获取图表信息成功");
        if(resdata != null && resdata.size() >0){
            tvIndexName.setText(TextUtils.isEmpty(resdata.get(0).getChart_top_title())
                    ? resdata.get(0).getIndex_clname() : resdata.get(0).getChart_top_title());
            if(TextUtils.isEmpty(resdata.get(0).getThresholdTarget())){
                relTarget.setVisibility(View.GONE);
            }else{
                relTarget.setVisibility(View.VISIBLE);
                tvTarget.setText(resources.getString(R.string.target)
                        + ":" + resdata.get(0).getThresholdTarget() + "("
                        + (TextUtils.isEmpty(resdata.get(0).getThresholdUnit())
                        ? resdata.get(0).getChart_unit()
                        :  resdata.get(0).getThresholdUnit()) + ")");
            }
            if(TextUtils.isEmpty(resdata.get(0).getThresholdChallenge())){
                relChallenge.setVisibility(View.GONE);
            }else{
                relChallenge.setVisibility(View.VISIBLE);
                tvChallenge.setText(resources.getString(R.string.challenge)
                        + ":" + resdata.get(0).getThresholdChallenge() + "("
                        + (TextUtils.isEmpty(resdata.get(0).getThresholdUnit())
                        ? resdata.get(0).getChart_unit()
                        :  resdata.get(0).getThresholdUnit()) + ")");
            }
            if(TextUtils.isEmpty(resdata.get(0).getThresholdMinimum())){
                relGuaranteed.setVisibility(View.GONE);
            }else{
                relGuaranteed.setVisibility(View.VISIBLE);
                tvGuaranteed.setText(resources.getString(R.string.guaranteed)
                        + ":" + resdata.get(0).getThresholdMinimum() + "("
                        + (TextUtils.isEmpty(resdata.get(0).getThresholdUnit())
                        ? resdata.get(0).getChart_unit()
                        :  resdata.get(0).getThresholdUnit()) + ")");
            }
            if(TextUtils.isEmpty(resdata.get(0).getIndex_description())){
                tvDescribe.setVisibility(View.GONE);
            }else{
                tvDescribe.setVisibility(View.VISIBLE);
                tvDescribe.setText(resources.getString(R.string.annotation)
                        + ":" + resdata.get(0).getIndex_description());
            }
        }
    }
}
