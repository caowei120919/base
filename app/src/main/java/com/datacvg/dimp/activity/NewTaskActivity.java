package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.baseandroid.widget.dialog.ChooseContactWindowDialog;
import com.datacvg.dimp.baseandroid.widget.dialog.ChooseIndexWindowDialog;
import com.datacvg.dimp.bean.ActionPlanIndexBean;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.Contact;
import com.datacvg.dimp.bean.DefaultUserBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.event.HeadOrAssistantEvent;
import com.datacvg.dimp.presenter.NewTaskPresenter;
import com.datacvg.dimp.view.NewTaskView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @BindView(R.id.tv_actionTypeCommon)
    TextView tvActionTypeCommon ;
    @BindView(R.id.tv_actionTypeSpecial)
    TextView tvActionTypeSpecial ;
    @BindView(R.id.tv_priorityHigh)
    TextView tvPriorityHigh ;
    @BindView(R.id.tv_priorityMiddle)
    TextView tvPriorityMiddle ;
    @BindView(R.id.tv_priorityLow)
    TextView tvPriorityLow ;
    @BindView(R.id.tv_headUser)
    TextView tvHeadUser ;
    @BindView(R.id.img_addIndex)
    ImageView imgAddIndex ;

    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    /**
     * 任务标题
     */
    private String taskTitle ;
    private ChooseContactWindowDialog contactWindowDialog;
    private ChooseIndexWindowDialog indexWindowDialog ;
    private boolean fromActionFragment = true ;
    private List<ActionPlanIndexBean> actionPlanIndexBeans = new ArrayList<>();
    /**
     * 负责人
     */
    private Contact headContact ;

    /**
     * 协助人
     */
    private List<Contact> assistantBeans = new ArrayList<>() ;

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
        fromActionFragment = getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_SCAN
                ,true);
        tvActionTypeCommon.setSelected(true);
        tvPriorityHigh.setSelected(true);
        initCustomPickView();
        getPresenter().getActionPlanIndex();
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
                taskDate = TimeUtils.date2Str(date,TimeUtils.FORMAT_YMD);
                tvDate.setText(resources.getString(R.string.expiration_date).replace("#1", taskDate));
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

    @OnClick({R.id.img_left,R.id.tv_date,R.id.tv_actionTypeCommon
            ,R.id.tv_actionTypeSpecial,R.id.tv_priorityHigh,R.id.tv_priorityMiddle
            ,R.id.tv_priorityLow,R.id.img_addHead,R.id.img_addAssistant,R.id.img_addIndex})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_date :
                    pvCustomTime.show();
                break;

            case R.id.tv_actionTypeCommon :
                tvActionTypeCommon.setSelected(true);
                tvActionTypeSpecial.setSelected(false);
                break;

            case R.id.tv_actionTypeSpecial :
                tvActionTypeCommon.setSelected(false);
                tvActionTypeSpecial.setSelected(true);
                break;

            case R.id.tv_priorityHigh :
                tvPriorityHigh.setSelected(true);
                tvPriorityMiddle.setSelected(false);
                tvPriorityLow.setSelected(false);
                break;

            case R.id.tv_priorityMiddle :
                tvPriorityHigh.setSelected(false);
                tvPriorityMiddle.setSelected(true);
                tvPriorityLow.setSelected(false);
                break;

            case R.id.tv_priorityLow :
                tvPriorityHigh.setSelected(false);
                tvPriorityMiddle.setSelected(false);
                tvPriorityLow.setSelected(true);
                break;

            case R.id.img_addHead :
                initSelectParamsView(Constants.CHOOSE_TYPE_HEAD);
                break;

            case R.id.img_addAssistant :
                initSelectParamsView(Constants.CHOOSE_TYPE_ASSISTANT);
                break;

            case R.id.img_addIndex :
                    createIndexWindowDialog();
                break;
        }
    }


    private void createIndexWindowDialog() {
        if(indexWindowDialog != null){
            indexWindowDialog.showDialog();
        }else{
            indexWindowDialog = new ChooseIndexWindowDialog(mContext,actionPlanIndexBeans)
                    .backgroundLight(0.2)
                    .fromTopToMiddle()
                    .showDialog()
                    .setCancelAble(true)
                    .setWidthAndHeight((int)resources.getDimension(R.dimen.W500)
                            ,(int)resources.getDimension(R.dimen.H800))
                    .setCanceledOnTouchOutside(true);
        }
    }

    /**
     *  初始化选择器
     * @param chooseTypeHead
     */
    private void initSelectParamsView(int chooseTypeHead){
        if(contactWindowDialog != null){
            contactWindowDialog.setChooseType(chooseTypeHead);
            contactWindowDialog.showDialog();
        }else{
            contactWindowDialog = new ChooseContactWindowDialog(mContext,chooseTypeHead)
                    .backgroundLight(0.2)
                    .fromTopToMiddle()
                    .showDialog()
                    .setCancelAble(true)
                    .setWidthAndHeight((int)resources.getDimension(R.dimen.W500),(int)resources.getDimension(R.dimen.H800))
                    .setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void getIndexSuccess(ActionPlanIndexListBean resdata) {
        actionPlanIndexBeans.addAll(resdata);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ActionPlanIndexBean bean){
        PLog.e(bean.getName() + "---" + bean.isChecked());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(HeadOrAssistantEvent event){
        /**
         * 选择负责人
         */
        if(event.getChooseType() == Constants.CHOOSE_TYPE_HEAD){
            if (event.isChecked()){
                headContact = event.getContact();
                tvHeadUser.setVisibility(View.VISIBLE);
                tvHeadUser.setText(event.getContact().getBean().getName());
            }else{
                tvHeadUser.setVisibility(View.GONE);
                headContact = null ;
            }
        }else{
            if(event.isChecked()){
                assistantBeans.add(event.getContact());
            }else{
                Contact removeContact = null;
                for (Contact contact : assistantBeans){
                    if(contact.getBean().getId().equals(event.getContact().getBean().getId())){
                        removeContact = contact ;
                        break;
                    }
                }
                if(removeContact != null){
                    assistantBeans.remove(removeContact);
                }
            }
        }
    }
}
