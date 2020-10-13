package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.TimeUtils;
import com.datacvg.sempmobile.presenter.NewTaskPresenter;
import com.datacvg.sempmobile.view.NewTaskView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-09
 * @Description :新建任务
 */
public class NewTaskActivity extends BaseActivity<NewTaskView, NewTaskPresenter>
        implements NewTaskView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_right)
    TextView tvRight ;
    @BindView(R.id.tv_date)
    TextView tvDate ;

    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    /**
     * 任务标题
     */
    private String taskTitle ;
    /**
     * 任务截止日期
     */
    private String taskDate ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_task;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,resources.getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.a_new_task));
        tvRight.setText(resources.getString(R.string.create));
        tvDate.setText(tvDate.getText().toString().replace("#1"
                , TimeUtils.getCurDateStr(TimeUtils.FORMAT_YMD)));
        initCustomPickView();
    }

    /**
     * 初始化时间选择器
     */
    private void initCustomPickView() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100,1,1);
        startDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH)
                , selectedDate.get(Calendar.DATE));
        pvCustomTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvDate.setText(resources.getString(R.string.expiration_date).replace("#1"
                        , TimeUtils.date2Str(date,TimeUtils.FORMAT_YMD)));
                taskDate = TimeUtils.date2Str(date,TimeUtils.FORMAT_YMD);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        LinearLayout linChooseType = v.findViewById(R.id.lin_chooseType);
                        linChooseType.setVisibility(View.GONE);
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
                .isCyclic(true)
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .isCenterLabel(false)
                .isDialog(true)
                .build();
    }

    /**
     * 密码输入监听
     */
    @OnTextChanged(value = R.id.ed_title,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTitleTextChange(Editable editable){
        taskTitle = editable.toString().trim();
    }

    @OnClick({R.id.img_left,R.id.tv_date})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_date :
                    pvCustomTime.show();
                break;
        }
    }
}
