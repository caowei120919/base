package com.datacvg.sempmobile.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.activity.MyIndexActivity;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.TimeUtils;
import com.datacvg.sempmobile.presenter.DigitalPresenter;
import com.datacvg.sempmobile.view.DigitalView;
import java.util.Calendar;
import java.util.Date;
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
                        WheelView wheelYear = v.findViewById(R.id.year);
                        WheelView wheelMonth = v.findViewById(R.id.month);
                        WheelView wheelDay = v.findViewById(R.id.day);
                        TextView tvMoth = v.findViewById(R.id.tv_month);
                        TextView tvDay = v.findViewById(R.id.tv_day);
                        tvDay.setSelected(true);
                        tvYear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvYear.setSelected(true);
                                tvMoth.setSelected(false);
                                tvDay.setSelected(false);
                                wheelMonth.setVisibility(View.GONE);
                                wheelDay.setVisibility(View.GONE);
                            }
                        });

                        tvMoth.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvYear.setSelected(false);
                                tvMoth.setSelected(true);
                                tvDay.setSelected(false);
                                wheelMonth.setVisibility(View.VISIBLE);
                                wheelDay.setVisibility(View.GONE);
                            }
                        });

                        tvDay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvYear.setSelected(false);
                                tvMoth.setSelected(false);
                                tvDay.setSelected(true);
                                wheelMonth.setVisibility(View.VISIBLE);
                                wheelDay.setVisibility(View.VISIBLE);
                            }
                        });

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

    @OnClick({R.id.img_left,R.id.tv_title,R.id.img_right})
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

        }
    }
}
