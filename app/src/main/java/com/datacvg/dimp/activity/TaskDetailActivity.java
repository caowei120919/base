package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.datacvg.dimp.bean.ActionPlanBean;
import com.datacvg.dimp.bean.CreateTaskBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.presenter.TaskDetailPresenter;
import com.datacvg.dimp.view.TaskDetailView;
import com.datacvg.dimp.widget.FlowLayout;
import com.enlogy.statusview.StatusRelativeLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-17
 * @Description : 行动方案详情
 */
public class TaskDetailActivity extends BaseActivity<TaskDetailView, TaskDetailPresenter>
        implements TaskDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_state)
    ImageView imgState ;
    @BindView(R.id.tv_stateStatus)
    TextView tvStateStatus ;
    @BindView(R.id.img_level)
    ImageView imgLevel ;
    @BindView(R.id.lin_handle)
    LinearLayout linHandle ;
    @BindView(R.id.tv_createName)
    TextView tvCreateName ;
    @BindView(R.id.tv_createTime)
    TextView tvCreateTime ;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime ;
    @BindView(R.id.status_task)
    StatusRelativeLayout statusTask ;
    @BindView(R.id.tv_headUser)
    TextView tvHeadUser ;
    @BindView(R.id.flow_assistant)
    FlowLayout flowAssistant ;
    @BindView(R.id.img_addAssistant)
    ImageView imgAddAssistant ;
    @BindView(R.id.ed_taskDetails)
    EditText edTaskDetails ;

    TextView tvDate ;
    TextView tvActionTypeCommon ;
    TextView tvActionTypeSpecial ;
    TextView tvPriorityHigh ;
    TextView tvPriorityMiddle ;
    TextView tvPriorityLow ;

    private ActionPlanBean actionPlanBean ;
    private TaskInfoBean taskInfoBean ;
    private CreateTaskBean createTaskBean ;
    private boolean isEditStatus = false ;
    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    private String taskDate = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_detail;
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

    private void setStatusClick() {
        tvDate = statusTask.findViewById(R.id.tv_date) ;
        tvActionTypeCommon = statusTask.findViewById(R.id.tv_actionTypeCommon);
        tvActionTypeSpecial = statusTask.findViewById(R.id.tv_actionTypeSpecial);
        tvPriorityHigh = statusTask.findViewById(R.id.tv_priorityHigh);
        tvPriorityMiddle = statusTask.findViewById(R.id.tv_priorityMiddle);
        tvPriorityLow = statusTask.findViewById(R.id.tv_priorityLow);

        tvDate.setOnClickListener(view -> {
            if(pvCustomTime != null){
                pvCustomTime.show();
            }else{
                initCustomPickView();
            }
        });

        tvActionTypeCommon.setOnClickListener(view -> {
            tvActionTypeCommon.setSelected(true);
            tvActionTypeSpecial.setSelected(false);
//                actionPlanInfoDTO.setTask_type("N");
        });
        tvActionTypeSpecial.setOnClickListener(view -> {
            tvActionTypeCommon.setSelected(false);
            tvActionTypeSpecial.setSelected(true);
//                actionPlanInfoDTO.setTask_type("S");
        });
        tvPriorityHigh.setOnClickListener(view -> {
//                actionPlanInfoDTO.setTask_priority("1");
            tvPriorityHigh.setSelected(true);
            tvPriorityMiddle.setSelected(false);
            tvPriorityLow.setSelected(false);
            imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                ,R.mipmap.action_high));
        });
        tvPriorityMiddle.setOnClickListener(view -> {
//                actionPlanInfoDTO.setTask_priority("2");
            tvPriorityHigh.setSelected(false);
            tvPriorityMiddle.setSelected(true);
            tvPriorityLow.setSelected(false);
            imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                ,R.mipmap.action_mid));
        });
        tvPriorityLow.setOnClickListener(view -> {
//                actionPlanInfoDTO.setTask_priority("3");
            tvPriorityHigh.setSelected(false);
            tvPriorityMiddle.setSelected(false);
            tvPriorityLow.setSelected(true);
            imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                ,R.mipmap.action_low));
        });
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        actionPlanBean = (ActionPlanBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(actionPlanBean == null){
            finish();
            return;
        }
        tvTitle.setText(actionPlanBean.getTitle());
        edTaskDetails.setEnabled(false);
        createTaskBean = new CreateTaskBean() ;
        getTaskInfo();
    }

    /**
     * 查询行动方案详情
     */
    private void getTaskInfo() {
        getPresenter().getTaskInfo(actionPlanBean.getId(),actionPlanBean.getUser_type()
                , PreferencesHelper.get(Constants.USER_ID,"")
                , LanguageUtils.getLanguage(mContext));
    }

    @OnClick({R.id.img_left})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;
        }
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
//                actionPlanInfoDTO.setTask_deadline(taskDate);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
//                        LinearLayout linChooseType = v.findViewById(R.id.lin_chooseType);
//                        linChooseType.setVisibility(View.GONE);
                        TextView tvDataTitle = v.findViewById(R.id.tv_dataTitle);
                        tvDataTitle.setVisibility(View.VISIBLE);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(view ->  {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                        });
                        ivCancel.setOnClickListener(view ->  {
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
        pvCustomTime.show();
    }


    /**
     * 获取行动方案详情成功
     * @param resdata
     */
    @Override
    public void getTaskInfoSuccess(TaskInfoBean resdata) {
        if(resdata == null){
            return;
        }
        taskInfoBean = resdata ;
        if(resdata.getDetail() != null && resdata.getDetail().size() > 0){
            fillDetailDate(resdata.getDetail().get(0));
        }
        if(resdata.getBaseInfo() != null){
            switch (resdata.getBaseInfo().getTask_priority()){
                case 1 :
                    imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_high));
                    break;

                case 2 :
                    imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_mid));
                    break;

                default:
                    imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_low));
                    break;
            }
            tvCreateName.setText(resdata.getBaseInfo().getCreate_user_name());
            tvCreateTime.setText(resources.getString(R.string.create_date)
                    .replace("#1",resdata.getBaseInfo().getCreate_time()));
            tvEndTime.setText(resources.getString(R.string.expiration_date)
                    .replace("#1",resdata.getBaseInfo().getTask_deadline()));
            statusTask.showContent();
        }
        if(resdata.getHandle() != null && resdata.getHandle().size() > 0){
            drawHandleView(resdata.getHandle());
        }
        /**
         *  2 负责人
         *  3 协助人
         */
        if (resdata.getTaskUser() !=null){
            List<CreateTaskBean.TaskUser> taskUsers = new ArrayList<>() ;
            for (TaskInfoBean.TaskUserBean bean : resdata.getTaskUser()){
                if(bean.getType().equals("2")){
                    CreateTaskBean.TaskUser taskUser = new CreateTaskBean.TaskUser();
                    taskUser.setId(bean.getUser_pkid());
                    taskUser.setName(bean.getName());
                    taskUser.setChecked(true);
                    taskUser.setType(bean.getType());
                    tvHeadUser.setText(bean.getName());
                    taskUsers.add(taskUser);
                    tvHeadUser.setVisibility(View.VISIBLE);
                }
                if(bean.getType().equals("3")){
                    CreateTaskBean.TaskUser taskUser = new CreateTaskBean.TaskUser();
                    taskUser.setId(bean.getUser_pkid());
                    taskUser.setName(bean.getName());
                    taskUser.setType(bean.getType());
                    taskUser.setChecked(true);
                    tvHeadUser.setText(bean.getName());
                    taskUsers.add(taskUser);
                }
            }
            for (CreateTaskBean.TaskUser user : taskUsers){
                if(user.getType().equals("3")){
                    buildAssistantFlow(user,true);
                }
            }
        }
    }

    /**
     * 构建协助人视图
     */
    public void buildAssistantFlow(CreateTaskBean.TaskUser taskUser , boolean isAddView){
        if(isAddView){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
            TextView tv_group = view.findViewById(R.id.tv_user);
            tv_group.setText(taskUser.getName());
            view.setTag(taskUser.getId());
            flowAssistant.addView(view);
        }else{
            for (int i = 0 ; i < flowAssistant.getChildCount() ; i++){
                View view = flowAssistant.getChildAt(i);
                if(view.getTag().equals(taskUser.getId())){
                    flowAssistant.removeView(view);
                    break;
                }
            }
        }
    }

    /**
     * 动态绘制操作按钮
     * @param handle
     */
    private void drawHandleView(List<TaskInfoBean.HandleBean> handle) {
        for (int i = 0 ; i < handle.size() ; i ++){
            switch (handle.get(i).getId()){
                case "do_edit" :
                case "do_reject" :
                case "do_cancel" :
                case "do_commit" :
                case "do_delete" :
                case "do_delay" :
                case "do_confirm" :
                    TextView operateView = new TextView(mContext);
                    operateView.setText(handle.get(i).getDesc());
                    operateView.setGravity(Gravity.CENTER);
                    operateView.setTextColor(i == 0 ? resources.getColor(R.color.c_FFFFFF)
                            : resources.getColor(R.color.c_da3a16));
                    operateView.setTextSize(10);
                    operateView.setMinWidth((int) resources.getDimension(R.dimen.W144));
                    operateView.setHeight((int) resources.getDimension(R.dimen.H50));
                    operateView.setBackground( i == 0 ? resources.getDrawable(R.drawable.shape_round_8_da3a16)
                            : resources.getDrawable(R.drawable.shape_ffffff_da3a16_8));
                    if(i != 0){
                        LinearLayout.LayoutParams params
                                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        params.leftMargin = (int) resources.getDimension(R.dimen.W16);
                        operateView.setLayoutParams(params);
                    }
                    int finalI = i;
                    operateView.setOnClickListener(view -> {
                        taskOperate(operateView,handle.get(finalI).getId());
                    });
                    linHandle.addView(operateView);
                    break;

                default:
                        continue;
            }
        }
    }

    /**
     * 行动操作
     * @param id
     *             "do_edit",        //编辑
     *             "do_reject",    //拒绝
     *             "do_cancel",    //撤销
     *             "do_commit",    //完成
     *             "do_delete",    //删除
     *             "do_delay",        //申请延期
     *             "do_confirm",  //审核
     */
    private void taskOperate(View view ,String id) {
        switch (id){
            case "do_edit" :
                if (edTaskDetails.isEnabled()){
                    edTaskDetails.setEnabled(false);
                    PLog.e("重新保存");
                }else{
                    ((TextView)view).setText(resources.getString(R.string.save));
                    edTaskDetails.setEnabled(true);
                    imgAddAssistant.setVisibility(View.VISIBLE);
                    statusTask.showExtendContent();
                    setStatusClick();
                    tvDate.setText(resources.getString(R.string.expiration_date)
                            .replace("#1",taskInfoBean.getBaseInfo().getTask_deadline()));
                    tvActionTypeCommon.setSelected(taskInfoBean.getBaseInfo().getTask_type().equals("N"));
                    tvActionTypeSpecial.setSelected(taskInfoBean.getBaseInfo().getTask_type().equals("S"));
                    tvPriorityHigh.setSelected(taskInfoBean.getBaseInfo().getTask_priority() == 1);
                    tvPriorityMiddle.setSelected(taskInfoBean.getBaseInfo().getTask_priority() == 2);
                    tvPriorityLow.setSelected(taskInfoBean.getBaseInfo().getTask_priority() == 3);
                }
                isEditStatus = !isEditStatus ;
                break;

            case "do_reject" :

                break;

            case "do_cancel" :

                break;

            case "do_commit" :

                break;

            case "do_delete" :

                break;

            case "do_delay" :

                break;

            case "do_confirm" :

                break;
        }
    }

    /**
     * 填充详情有关数据
     * @param detailBean
     */
    private void fillDetailDate(TaskInfoBean.DetailBean detailBean) {
        switch (detailBean.getTask_state()){
            case "3" :
                imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_processing));
                break;

            case "6" :
            case "7" :
            case "8" :
                imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_complete));
                break;

            case "9" :
            case "11" :
                imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_undo));
                break;

            default:
                imgState.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                        ,R.mipmap.action_wait_receive));
                break;
        }
        tvStateStatus.setText(detailBean.getState_desc());

    }
}
