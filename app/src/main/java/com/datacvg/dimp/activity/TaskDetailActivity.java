package com.datacvg.dimp.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ActionRecordAdapter;
import com.datacvg.dimp.adapter.PlanOnActionAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.baseandroid.greendao.controller.DbContactOrDepartmentController;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ActionPlanBean;
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;
import com.datacvg.dimp.bean.CreateTaskBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.event.AddDepartmentCompleteEvent;
import com.datacvg.dimp.event.ChooseUserForActionEvent;
import com.datacvg.dimp.event.CreateTaskEvent;
import com.datacvg.dimp.presenter.TaskDetailPresenter;
import com.datacvg.dimp.view.TaskDetailView;
import com.datacvg.dimp.widget.FlowLayout;
import com.enlogy.statusview.StatusRelativeLayout;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.flow_index)
    FlowLayout flowIndex ;
    @BindView(R.id.tv_theSnapshotComparison)
    TextView tvTheSnapshotComparison ;
    @BindView(R.id.lin_actionPlan)
    LinearLayout linActionPlan ;
    @BindView(R.id.tv_actionStatus)
    TextView tvActionStatus ;
    @BindView(R.id.tv_actionPlan)
    TextView tvActionPlan ;
    @BindView(R.id.recycler_record)
    RecyclerView recyclerRecord;
    @BindView(R.id.recycler_plan)
    RecyclerView recyclerPlan ;
    @BindView(R.id.tv_planUserName)
    TextView tvPlanUserName ;
    @BindView(R.id.tv_notPlan)
    TextView tvNotPlan ;
    @BindView(R.id.img_planUserImage)
    ImageView imgPlanUserImage ;
    @BindView(R.id.rel_addPlan)
    RelativeLayout relAddPlan ;

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
    private String delayData = "";
    private ActionRecordAdapter actionRecordAdapter ;
    private PlanOnActionAdapter planOnActionAdapter ;
    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    private TimePickerView delayTimePicker ;
    private String taskDate = "";
    private CreateTaskBean.ActionPlanInfoDTO actionPlanInfoDTO ;

    /**
     * 负责人
     */
    private ContactOrDepartmentForActionBean headContact ;
    private String headContactId = "" ;
    /**
     * 协助人
     */
    private List<ContactOrDepartmentForActionBean> assistantBeans = new ArrayList<>() ;
    private List<String> assistantBeanIds = new ArrayList<>() ;

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
        tvActionStatus.setSelected(true);
    }

    private void setStatusClick() {
        tvDate = statusTask.findViewById(R.id.tv_date);
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
        });
        tvActionTypeSpecial.setOnClickListener(view -> {
            tvActionTypeCommon.setSelected(false);
            tvActionTypeSpecial.setSelected(true);
        });
        tvPriorityHigh.setOnClickListener(view -> {
            tvPriorityHigh.setSelected(true);
            tvPriorityMiddle.setSelected(false);
            tvPriorityLow.setSelected(false);
            actionPlanInfoDTO.setTask_priority("1");
            imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                ,R.mipmap.action_high));
        });
        tvPriorityMiddle.setOnClickListener(view -> {
            tvPriorityHigh.setSelected(false);
            tvPriorityMiddle.setSelected(true);
            tvPriorityLow.setSelected(false);
            actionPlanInfoDTO.setTask_priority("2");
            imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                ,R.mipmap.action_mid));
        });
        tvPriorityLow.setOnClickListener(view -> {
            tvPriorityHigh.setSelected(false);
            tvPriorityMiddle.setSelected(false);
            tvPriorityLow.setSelected(true);
            actionPlanInfoDTO.setTask_priority("3");
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
        tvTitle.setText(TextUtils.isEmpty(actionPlanBean.getTitle())
                ? "" : actionPlanBean.getTitle());
        edTaskDetails.setEnabled(false);
        createTaskBean = new CreateTaskBean() ;
        actionPlanInfoDTO = new CreateTaskBean.ActionPlanInfoDTO();
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

    @OnClick({R.id.img_left,R.id.tv_theSnapshotComparison,R.id.tv_actionStatus,R.id.tv_actionPlan,R.id.img_addPlan,R.id.img_addAssistant})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            /**
             * 快照对比
             */
            case R.id.tv_theSnapshotComparison :
                Intent intent = new Intent(mContext,SnapShotActivity.class) ;
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,taskInfoBean);
                mContext.startActivity(intent);
                break;

            /**
             * 行动状态记录
             */
            case R.id.tv_actionStatus :
                tvActionStatus.setSelected(true);
                tvActionPlan.setSelected(false);
                recyclerPlan.setVisibility(View.GONE);
                tvPlanUserName.setVisibility(View.GONE);
                tvNotPlan.setVisibility(View.GONE);
                imgPlanUserImage.setVisibility(View.GONE);
                recyclerRecord.setVisibility(View.VISIBLE);
                relAddPlan.setVisibility(View.GONE);
                break;

            /**
             * 行动计划
             */
            case R.id.tv_actionPlan :
                tvActionStatus.setSelected(false);
                tvActionPlan.setSelected(true);
                recyclerRecord.setVisibility(View.GONE);
                recyclerPlan.setVisibility(View.VISIBLE);
                tvPlanUserName.setVisibility(View.VISIBLE);
                imgPlanUserImage.setVisibility(View.VISIBLE);
                if(taskInfoBean.getPlan() !=null && taskInfoBean.getPlan().getPlan_detail_list().isEmpty()){
                    for (TaskInfoBean.TaskUserBean taskUserBean : taskInfoBean.getTaskUser()){
                        if(taskUserBean.getType().equals("2")){
                            if(taskUserBean.getUser_pkid().equals(PreferencesHelper.get(Constants.USER_ID,""))){
                                relAddPlan.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    tvNotPlan.setVisibility(relAddPlan.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    imgPlanUserImage.setVisibility(relAddPlan.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                    tvPlanUserName.setVisibility(relAddPlan.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                }else{
                    relAddPlan.setVisibility(View.GONE);
                    tvNotPlan.setVisibility(View.GONE);
                }
            break;

            case R.id.img_addPlan :
                PLog.e("添加行动方案");
                break;

            case R.id.img_addAssistant :
                PLog.e("选择协助人");
                ArrayList<String> assistIds = new ArrayList<>() ;
                Intent assistantIntent = new Intent(mContext,ChooseContactFromActionActivity.class) ;
                assistantIntent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,false);
                if(!TextUtils.isEmpty(headContactId)){
                    ContactOrDepartmentBean head = DbContactOrDepartmentController.getInstance(mContext).queryContact(headContactId);
                    if(head != null){
                        assistantIntent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,head.getResId()) ;
                    }
                }
                if(!assistantBeanIds.isEmpty()){
                    for (String assistantBeanId : assistantBeanIds){
                        assistIds.add(DbContactOrDepartmentController.getInstance(mContext).queryContact(assistantBeanId).getResId());
                    }
                    assistantIntent.putStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN, assistIds);
                }
                mContext.startActivity(assistantIntent);
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
                actionPlanInfoDTO.setTask_deadline(taskDate);
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
                .isCyclic(false)
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
        if(tvTitle.getText().toString().isEmpty()){
            tvTitle.setText(taskInfoBean.getBaseInfo().getTask_title());
        }
        tvTheSnapshotComparison.setVisibility((resdata.getPlan() != null
                && resdata.getPlan().getPlan_flg().equals("T")) ? View.VISIBLE : View.GONE);
        tvActionPlan.setVisibility((resdata.getPlan() != null
                && resdata.getPlan().getPlan_flg().equals("T")) ? View.VISIBLE : View.GONE);
        if(resdata.getDetail() != null && resdata.getDetail().size() > 0){
            fillDetailDate(resdata.getBaseInfo());
        }
        if(resdata.getBaseInfo() != null){
            switch (resdata.getBaseInfo().getTask_priority()){
                case 1 :
                    imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_high));
                    actionPlanInfoDTO.setTask_priority("1");
                    break;

                case 2 :
                    imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_mid));
                    actionPlanInfoDTO.setTask_priority("2");
                    break;

                default:
                    imgLevel.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources()
                            ,R.mipmap.action_low));
                    actionPlanInfoDTO.setTask_priority("3");
                    break;
            }
            tvCreateName.setText(resdata.getBaseInfo().getCreate_user_name());
            tvCreateTime.setText(resources.getString(R.string.create_date)
                    .replace("#1",resdata.getBaseInfo().getCreate_time()));
            delayData = resdata.getBaseInfo().getTask_deadline() ;
            tvEndTime.setText(resources.getString(R.string.expiration_date)
                    .replace("#1",resdata.getBaseInfo().getTask_deadline()));

            edTaskDetails.setText(resdata.getBaseInfo().getTask_text());
            statusTask.showContent();
        }
        if(!taskInfoBean.getDetail().isEmpty()){
            actionRecordAdapter = new ActionRecordAdapter(mContext,taskInfoBean.getDetail());
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            recyclerRecord.setLayoutManager(manager);
            recyclerRecord.setAdapter(actionRecordAdapter);
        }
        if(taskInfoBean.getPlan() != null){
            tvPlanUserName.setText(taskInfoBean.getPlan().getLast_updater());
            planOnActionAdapter = new PlanOnActionAdapter(mContext,taskInfoBean.getPlan().getPlan_detail_list());
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            recyclerPlan.setLayoutManager(manager);
            recyclerPlan.setAdapter(planOnActionAdapter);
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
                    headContactId = taskUser.getId() ;
                }
                if(bean.getType().equals("3")){
                    CreateTaskBean.TaskUser taskUser = new CreateTaskBean.TaskUser();
                    taskUser.setId(bean.getUser_pkid());
                    taskUser.setName(bean.getName());
                    taskUser.setType(bean.getType());
                    taskUser.setChecked(true);
                    taskUsers.add(taskUser);
                    assistantBeanIds.add(taskUser.getId());
                    buildAssistantFlow(taskUser,true);
                }
            }
        }

        /**
         * 相关指标
         */
        if (!resdata.getIndexList().isEmpty()){
            for (TaskInfoBean.IndexListBean bean : resdata.getIndexList()){
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null) ;
                TextView tv_group = view.findViewById(R.id.tv_user);
                tv_group.setText(bean.getIndex_name());
                view.setTag(bean.getIndex_id());
                flowIndex.addView(view);
            }
        }

        if(!resdata.getDetail().isEmpty()){
            actionRecordAdapter = new ActionRecordAdapter(mContext,resdata.getDetail());
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            recyclerRecord.setLayoutManager(manager);
            recyclerRecord.setAdapter(actionRecordAdapter);
        }
    }

    /**
     * 操作成功说明
     */
    @Override
    public void operateTaskSuccess() {
        ToastUtils.showLongToast(resources.getString(R.string.operation_is_successful));
        EventBus.getDefault().post(new CreateTaskEvent());
        finish();
    }

    /**
     * 构建协助人视图
     */
    public void buildAssistantFlow(CreateTaskBean.TaskUser contact,boolean addOrRemove){
        if(addOrRemove){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
            TextView tv_group = view.findViewById(R.id.tv_user);
            ImageView img_delete =view.findViewById(R.id.img_delete);
            img_delete.setVisibility(isEditStatus ? View.VISIBLE : View.GONE);
            img_delete.setOnClickListener(v -> {
                flowAssistant.removeView(view);
                assistantBeans.remove(contact);
            });
            tv_group.setText(contact.getName());
            view.setTag(contact.getId());
            flowAssistant.addView(view);
        }else{
            for (int i = 0 ; i < flowAssistant.getChildCount(); i++){
                View view = flowAssistant.getChildAt(i);
                if(view.getTag().equals(contact.getName())){
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
                case "do_delete" :
                case "do_delay" :
                case "do_accept" :
                case "do_commit" :
                    if(taskInfoBean != null && taskInfoBean.getPlan().getPlan_status().equals("1")
                            && taskInfoBean.getPlan().getUser_type().equals("2")){
                        continue;
                    }
                case "do_confirm" :
                    TextView operateView = new TextView(mContext);
                    operateView.setText(handle.get(i).getDesc());
                    operateView.setGravity(Gravity.CENTER);
                    operateView.setTextColor(i == 0 ? resources.getColor(R.color.c_FFFFFF)
                            : resources.getColor(R.color.c_da3a16));
                    operateView.setTextSize(14);
                    operateView.setMinWidth((int) resources.getDimension(R.dimen.W144));
                    operateView.setHeight((int) resources.getDimension(R.dimen.H50));
                    operateView.setBackground( i == 0 ? resources.getDrawable(R.drawable.shape_round_8_da3a16)
                            : resources.getDrawable(R.drawable.shape_ffffff_da3a16_8));
                    if(i != 0){
                        LinearLayout.LayoutParams params
                                = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                                , ViewGroup.LayoutParams.MATCH_PARENT);
                        params.leftMargin = (int) resources.getDimension(R.dimen.W16);
                        operateView.setLayoutParams(params);
                    }
                    int finalI = i;
                    operateView.setOnClickListener(view -> {
                        taskOperate(operateView,handle.get(finalI).getId());
                    });
                    linHandle.addView(operateView);
                    break;
                case "do_flow" :
                case "do_subtask" :
                default:
                        continue;
            }
        }
    }

    /**
     * 行动操作
     * @param id
     *             "do_edit",     //编辑
     *             "do_reject",   //拒绝
     *             "do_cancel",   //撤销
     *             "do_commit",   //完成
     *             "do_delete",   //删除
     *             "do_delay",    //申请延期
     *             "do_confirm",  //审核
     *             "do_accept"    //同意
     */
    private void taskOperate(View view ,String id) {
        switch (id){
            case "do_edit" :
                if (edTaskDetails.isEnabled()){
                    edTaskDetails.setEnabled(false);
                }else{
                    ((TextView)view).setText(resources.getString(R.string.save));
                    linHandle.removeAllViews();
                    if(flowAssistant.getChildCount() > 0){
                        for (int i=0 ; i < flowAssistant.getChildCount();i ++){
                            View containView = flowAssistant.getChildAt(i);
                            ImageView imgDelete = containView.findViewById(R.id.img_delete) ;
                            imgDelete.setVisibility(View.VISIBLE);
                            imgDelete.setOnClickListener(v -> {
                                PLog.e("删除协助人");
                                flowAssistant.removeView(containView);
                                assistantBeanIds.remove(containView.getTag());
                            });
                        }
                    }
                    drawEditView();
                    edTaskDetails.setEnabled(true);
                    imgAddAssistant.setVisibility(View.VISIBLE);
                    statusTask.showExtendContent();
                    setStatusClick();
                    delayData = taskInfoBean.getBaseInfo().getTask_deadline() ;
                    tvDate.setText(resources.getString(R.string.expiration_date)
                            .replace("#1",delayData));
                    tvActionTypeCommon.setSelected(taskInfoBean.getBaseInfo().getTask_type().equals("N"));
                    tvActionTypeSpecial.setSelected(taskInfoBean.getBaseInfo().getTask_type().equals("S"));
                    tvPriorityHigh.setSelected(taskInfoBean.getBaseInfo().getTask_priority() == 1);
                    tvPriorityMiddle.setSelected(taskInfoBean.getBaseInfo().getTask_priority() == 2);
                    tvPriorityLow.setSelected(taskInfoBean.getBaseInfo().getTask_priority() == 3);
                }
                isEditStatus = !isEditStatus ;
                break;

            case "do_reject" :
                showRejectDialog(id);
                break;

            case "do_cancel" :
                showCancelDialog(id);
                break;

            case "do_commit" :
                showCommitDialog(id);
                break;

            case "do_delete" :
                showDeleteDialog(id);
                break;

            case "do_delay" :
                showDelayDialog(id);
                break;

            /**
             * 审核
             */
            case "do_confirm" :
                showConfirmDialog(id);
                break;

            case "do_accept" :
                getPresenter().operateTask(id,actionPlanBean.getId(),"","0","");
                break;
        }
    }

    /**
     * 拒绝
     * @param id
     */
    private void showRejectDialog(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_task_reject_confirm
                ,null,false);
        EditText editDescribe = containView.findViewById(R.id.edit_describe) ;
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        containView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        containView.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(TextUtils.isEmpty(editDescribe.getText().toString())){
                ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_instructions));
            }else{
                alertDialog.dismiss();
                getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"0","");
            }
        });
    }

    /**
     * 删除
     * @param id
     */
    private void showDeleteDialog(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_task_delete_confirm
                ,null,false);
        EditText editDescribe = containView.findViewById(R.id.edit_describe) ;
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        containView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        containView.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(TextUtils.isEmpty(editDescribe.getText().toString())){
                ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_instructions));
            }else{
                alertDialog.dismiss();
                getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"0","");
            }
        });
    }

    /**
     * 完成
     */
    private void showCommitDialog(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_task_commit_confirm
                ,null,false);
        EditText editDescribe = containView.findViewById(R.id.edit_describe) ;
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        containView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        containView.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(TextUtils.isEmpty(editDescribe.getText().toString())){
                ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_instructions));
            }else{
                alertDialog.dismiss();
                getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"0","");
            }
        });
    }

    /**
     * 撤销
     * @param id
     */
    private void showCancelDialog(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_task_cancel_confirm
                ,null,false);
        EditText editDescribe = containView.findViewById(R.id.edit_describe) ;
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        containView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        containView.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(TextUtils.isEmpty(editDescribe.getText().toString())){
                ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_instructions));
            }else{
                alertDialog.dismiss();
                getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"0","");
            }
        });
    }

    /**
     * 申请延期
     */
    private void showDelayDialog(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_task_delay_confirm
                ,null,false);
        TextView tvDelayTime = containView.findViewById(R.id.tv_delayTime) ;
        tvDelayTime.setText(delayData);
        tvDelayTime.setOnClickListener(v -> {
            showDelayDataPop(tvDelayTime);
        });
        EditText editDescribe = containView.findViewById(R.id.edit_describe) ;
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        containView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        containView.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(TextUtils.isEmpty(editDescribe.getText().toString())){
                ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_instructions));
            }else{
                getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"0",tvDelayTime.getText().toString());
                alertDialog.dismiss();
            }
        });
    }

    /**
     *
     * @param tvDelayTime
     */
    private void showDelayDataPop(TextView tvDelayTime) {
       String[] timeArr = tvDelayTime.getText().toString().split("-");
       Calendar selectedDate = Calendar.getInstance();
       Calendar startDate = Calendar.getInstance();
       Calendar endDate = Calendar.getInstance();
       endDate.set(2100,1,1);
       startDate.set(Integer.valueOf(timeArr[0]), Integer.valueOf(timeArr[1]) - 1,Integer.valueOf(timeArr[2]));
       delayTimePicker = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String dateStr = TimeUtils.date2Str(date,TimeUtils.FORMAT_YMD);
                tvDelayTime.setText(dateStr);
                PLog.e(dateStr);
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
                        tvSubmit.setOnClickListener(view ->  {
                            delayTimePicker.returnData();
                            delayTimePicker.dismiss();
                        });
                        ivCancel.setOnClickListener(view ->  {
                            delayTimePicker.dismiss();
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
                .isDialog(true)
                .build();
        delayTimePicker.show();
    }

    private void drawEditView() {
        for (int i = 0 ; i <= 1 ; i++){
            TextView operateView = new TextView(mContext);
            operateView.setText(i == 0 ? resources.getString(R.string.save) : resources.getString(R.string.cancel));
            operateView.setGravity(Gravity.CENTER);
            operateView.setTextColor(i == 0 ? resources.getColor(R.color.c_FFFFFF)
                    : resources.getColor(R.color.c_da3a16));
            operateView.setTextSize(14);
            operateView.setMinWidth((int) resources.getDimension(R.dimen.W144));
            operateView.setHeight((int) resources.getDimension(R.dimen.H50));
            operateView.setBackground( i == 0 ? resources.getDrawable(R.drawable.shape_round_8_da3a16)
                    : resources.getDrawable(R.drawable.shape_ffffff_da3a16_8));
            if(i != 0){
                LinearLayout.LayoutParams params
                        = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.MATCH_PARENT);
                params.leftMargin = (int) resources.getDimension(R.dimen.W16);
                operateView.setLayoutParams(params);
            }
            int finalI = i;
            operateView.setOnClickListener(view -> {
                if(finalI == 0){
                    saveTaskDetail();
                }else{
                    isEditStatus = false ;
                    edTaskDetails.setEnabled(false);
                    if(flowAssistant.getChildCount() > 0){
                        for (int j=0 ; j < flowAssistant.getChildCount();j ++){
                            View containView = flowAssistant.getChildAt(j);
                            ImageView imgDelete = containView.findViewById(R.id.img_delete) ;
                            imgDelete.setVisibility(View.GONE);
                        }
                    }
                    imgAddAssistant.setVisibility(View.GONE);
                    statusTask.showContent();
                    linHandle.removeAllViews();
                    if(taskInfoBean.getHandle() != null && taskInfoBean.getHandle().size() > 0){
                        drawHandleView(taskInfoBean.getHandle());
                    }
                }
            });
            linHandle.addView(operateView);
        }
    }

    /**
     * 保存重新编辑详情
     */
    private void saveTaskDetail() {
//        List<CreateTaskBean.TaskUser> taskUsers = new ArrayList<>();
//        CreateTaskBean.TaskUser taskUser = new CreateTaskBean.TaskUser();
//        taskUser.setChecked(true);
//        taskUser.setId(headContact.getContactOrDepartmentBean().getUserId());
//        taskUser.setName(headContact.getContactOrDepartmentBean().getName());
//        taskUser.setType("2");
//        taskUsers.add(taskUser);
//        for (ContactOrDepartmentForActionBean contact : assistantBeans){
//            CreateTaskBean.TaskUser taskAssistant = new CreateTaskBean.TaskUser();
//            taskAssistant.setType("3");
//            taskAssistant.setName(contact.getContactOrDepartmentBean().getName());
//            taskAssistant.setId(contact.getContactOrDepartmentBean().getUserId());
//            taskAssistant.setChecked(true);
//            taskUsers.add(taskAssistant);
//        }
//        actionPlanInfoDTO.setTask_deadline(taskDate);
//        actionPlanInfoDTO.setTask_parent_id("0");
//        createTaskBean.setLang(LanguageUtils.isZh(mContext) ? "zh" : "en");
//        createTaskBean.setIndexList(new Gson().toJson(indexTreeBeans));
//        createTaskBean.setUserMsg(new Gson().toJson(taskUsers));
//        createTaskBean.setIndex(new Gson().toJson(taskIndexBeans));
//        createTaskBean.setActionType("");
//        createTaskBean.setActionPlanInfoDTO(actionPlanInfoDTO);
    }

    /**
     * 审核弹窗
     */
    private void showConfirmDialog(String id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_task_detail_confirm
                ,null,false);
        RadioGroup radioSelect = containView.findViewById(R.id.radio_select) ;
        RadioButton radioYes = containView.findViewById(R.id.radio_yes) ;
        EditText editDescribe = containView.findViewById(R.id.edit_describe) ;
        radioYes.setSelected(true);
        RadioButton radioDeny = containView.findViewById(R.id.radio_deny) ;
        RadioButton radioNegate = containView.findViewById(R.id.radio_negate) ;
        radioSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_yes :
                        radioDeny.setSelected(false);
                        radioNegate.setSelected(false);
                        break;

                    case R.id.radio_deny :
                        radioYes.setSelected(false);
                        radioNegate.setSelected(false);
                        break;

                    case R.id.radio_negate :
                        radioYes.setSelected(false);
                        radioDeny.setSelected(false);
                        break;
                }
            }
        });
        dialog.setView(containView);
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        containView.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        containView.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(TextUtils.isEmpty(editDescribe.getText().toString())){
                ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_instructions));
            }else{
                switch (radioSelect.getCheckedRadioButtonId()){
                    case R.id.radio_yes :
                        getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"0","");
                        break;

                    case R.id.radio_deny :
                        getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"1","");
                        break;

                    case R.id.radio_negate :
                        getPresenter().operateTask(id,actionPlanBean.getId(),editDescribe.getText().toString(),"2","");
                        break;
                }
                alertDialog.dismiss();
            }
        });
    }

    /**
     * 填充详情有关数据
     * @param detailBean
     */
    private void fillDetailDate(TaskInfoBean.BaseInfoBean detailBean) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChooseUserForActionEvent event){
        if(event.getContactOrDepartmentForActionBean().isChecked()){
            assistantBeanIds.add(event.getContactOrDepartmentForActionBean().getContactOrDepartmentBean().getUserId());
        }else{
            assistantBeanIds.remove(event.getContactOrDepartmentForActionBean().getContactOrDepartmentBean().getUserId());
        }
        ContactOrDepartmentBean actionBean = DbContactOrDepartmentController.getInstance(mContext)
                .queryContact(event.getContactOrDepartmentForActionBean().getContactOrDepartmentBean().getUserId());
        CreateTaskBean.TaskUser taskUser = new CreateTaskBean.TaskUser() ;
        taskUser.setId(actionBean.getResId());
        taskUser.setName(actionBean.getName());
        taskUser.setType("3");
        buildAssistantFlow(taskUser,event.getContactOrDepartmentForActionBean().isChecked());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddDepartmentCompleteEvent event){

    }
}
