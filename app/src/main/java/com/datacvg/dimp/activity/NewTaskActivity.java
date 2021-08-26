package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ActionPlanIndexBean;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.CreateTaskBean;
import com.datacvg.dimp.bean.Contact;
import com.datacvg.dimp.bean.IndexTreeBean;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.event.CreateTaskEvent;
import com.datacvg.dimp.event.HeadOrAssistantEvent;
import com.datacvg.dimp.presenter.NewTaskPresenter;
import com.datacvg.dimp.view.NewTaskView;
import com.datacvg.dimp.widget.FlowLayout;
import com.google.gson.Gson;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
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
    @BindView(R.id.flow_assistant)
    FlowLayout flowAssistant ;
    @BindView(R.id.flow_index)
    FlowLayout flowIndex ;
    @BindView(R.id.rel_actionPlan)
    RelativeLayout relActionPlan ;
    @BindView(R.id.rel_dimensionName)
    RelativeLayout relDimensionName ;
    @BindView(R.id.rel_dimension)
    RelativeLayout relDimension ;
    @BindView(R.id.flow_dimension)
    FlowLayout flowDimension ;

    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    /**
     * 任务标题
     */
    private String taskTitle ;
    private boolean fromActionFragment = true ;
    private String actionType = "1";
    private IndexTreeNeedBean indexTreeNeedBean ;
    private List<ActionPlanIndexBean> actionPlanIndexBeans = new ArrayList<>();
    private List<ActionPlanIndexBean> taskIndexBeans = new ArrayList<>();
    private List<IndexTreeBean> indexTreeBeans = new ArrayList<>();
    private List<IndexTreeBean> showDimensionIndex = new ArrayList<>() ;
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
    private String taskDetail;
    private CreateTaskBean createTaskBean;
    private CreateTaskBean.ActionPlanInfoDTO actionPlanInfoDTO ;

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
        taskDate = TimeUtils.getCurDateStr(TimeUtils.FORMAT_YMD) ;
        fromActionFragment = getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_SCAN
                ,true);
        if(!fromActionFragment){
            indexTreeBeans = (List<IndexTreeBean>) getIntent()
                    .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
            indexTreeNeedBean = (IndexTreeNeedBean) getIntent()
                    .getSerializableExtra(Constants.EXTRA_DATA_FOR_ALBUM);
            actionType = indexTreeNeedBean.getType();
            relDimensionName.setVisibility(TextUtils.isEmpty(actionType) ? View.GONE : (actionType.equals("4") ? View.GONE : View.VISIBLE));
            relDimension.setVisibility(TextUtils.isEmpty(actionType) ? View.GONE : (actionType.equals("4") ? View.GONE : View.VISIBLE));
            if(indexTreeBeans == null || indexTreeBeans.size() <= 0){
                finish();
                return;
            }
            buildIndexFlow(indexTreeBeans);
        }
        imgAddIndex.setVisibility(fromActionFragment ? View.VISIBLE : View.GONE);
        relActionPlan.setVisibility(fromActionFragment ? View.GONE : View.VISIBLE);
        tvActionTypeCommon.setSelected(true);
        tvPriorityHigh.setSelected(true);
        createTaskBean = new CreateTaskBean();
        actionPlanInfoDTO = new CreateTaskBean.ActionPlanInfoDTO();
        actionPlanInfoDTO.setTask_priority("1");
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
                actionPlanInfoDTO.setTask_deadline(taskDate);
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
     * 标题
     */
    @OnTextChanged(value = R.id.ed_title,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTitleTextChange(Editable editable){
        taskTitle = editable.toString().trim();
    }

    @OnTextChanged(value = R.id.ed_taskDetails,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onDetailTextChange(Editable editable){
        taskDetail = editable.toString().trim() ;
    }

    @OnCheckedChanged(R.id.switch_task)
    public void onCheckChanged(boolean checked){
        PLog.e("测试 ====" + checked);
        createTaskBean.setPlanFlg(checked ? "T" : "F");
    }

    @OnClick({R.id.img_left,R.id.tv_date,R.id.tv_actionTypeCommon
            ,R.id.tv_actionTypeSpecial,R.id.tv_priorityHigh,R.id.tv_priorityMiddle
            ,R.id.tv_priorityLow,R.id.img_addHead,R.id.img_addAssistant,R.id.img_addIndex
            ,R.id.tv_right})
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
                actionPlanInfoDTO.setTask_type("N");
                break;

            case R.id.tv_actionTypeSpecial :
                tvActionTypeCommon.setSelected(false);
                tvActionTypeSpecial.setSelected(true);
                actionPlanInfoDTO.setTask_type("S");
                break;

            case R.id.tv_priorityHigh :
                actionPlanInfoDTO.setTask_priority("1");
                tvPriorityHigh.setSelected(true);
                tvPriorityMiddle.setSelected(false);
                tvPriorityLow.setSelected(false);
                break;

            case R.id.tv_priorityMiddle :
                actionPlanInfoDTO.setTask_priority("2");
                tvPriorityHigh.setSelected(false);
                tvPriorityMiddle.setSelected(true);
                tvPriorityLow.setSelected(false);
                break;

            case R.id.tv_priorityLow :
                actionPlanInfoDTO.setTask_priority("3");
                tvPriorityHigh.setSelected(false);
                tvPriorityMiddle.setSelected(false);
                tvPriorityLow.setSelected(true);
                break;

            case R.id.img_addHead :
                    PLog.e("选择联系人");
                break;

            case R.id.img_addAssistant :
                    PLog.e("选择协助人");
                break;

            case R.id.img_addIndex :
                    PLog.e("选择指标");
                break;

            case R.id.tv_right :
                    if(TextUtils.isEmpty(taskTitle)){
                        ToastUtils.showLongToast(resources
                                .getString(R.string.please_fill_in_the_title));
                        return;
                    }
                    if(headContact == null){
                        ToastUtils.showLongToast(resources.getString(R.string.please_select_the_person_in_charge));
                        return;
                    }
                    if(TextUtils.isEmpty(taskDetail)){
                        ToastUtils.showLongToast(resources.getString(R.string.please_fill_in_the_task_details));
                        return;
                    }
                    actionPlanInfoDTO.setTask_title(taskTitle);
                    createTaskBean.setTaskText(taskDetail);
                    List<CreateTaskBean.TaskUser> taskUsers = new ArrayList<>();
                    CreateTaskBean.TaskUser taskUser = new CreateTaskBean.TaskUser();
                    taskUser.setChecked(true);
                    taskUser.setId(headContact.getBean().getUser_id());
                    taskUser.setName(headContact.getBean().getName());
                    taskUser.setType("2");
                    taskUsers.add(taskUser);
                    for (Contact contact : assistantBeans){
                        CreateTaskBean.TaskUser taskAssistant = new CreateTaskBean.TaskUser();
                        taskAssistant.setType("3");
                        taskAssistant.setName(contact.getBean().getName());
                        taskAssistant.setId(contact.getBean().getUser_id());
                        taskAssistant.setChecked(true);
                        taskUsers.add(taskAssistant);
                    }
                    actionPlanInfoDTO.setTask_deadline(taskDate);
                    actionPlanInfoDTO.setTask_parent_id("0");
                    createTaskBean.setLang(LanguageUtils.isZh(mContext) ? "zh" : "en");
                    if(!fromActionFragment){
                        createTaskBean.setFuDimension(indexTreeNeedBean.getFuDimension());
                        createTaskBean.setpDimension(indexTreeNeedBean.getpDimension());
                        createTaskBean.setOrgDimension(indexTreeNeedBean.getOrgDimension());
                        createTaskBean.setAllDeimension(indexTreeNeedBean.getOrgName() + ","
                                + indexTreeNeedBean.getFuName()
                                + "," + indexTreeNeedBean.getpName());
                    }
                    createTaskBean.setIndexList(new Gson().toJson(indexTreeBeans));
                    createTaskBean.setUserMsg(new Gson().toJson(taskUsers));
                    createTaskBean.setIndex(new Gson().toJson(taskIndexBeans));
                    createTaskBean.setActionType(actionType);
                    createTaskBean.setActionPlanInfoDTO(actionPlanInfoDTO);
                    getPresenter().createTask(createTaskBean);
                break;
        }
    }

    @Override
    public void getIndexSuccess(ActionPlanIndexListBean resdata) {
        actionPlanIndexBeans.addAll(resdata);
    }

    @Override
    public void createTaskSuccess() {
        EventBus.getDefault().post(new CreateTaskEvent());
        ToastUtils.showLongToast(resources.getString(R.string.action_plan_created_successfully));
        finish();
    }

    @Override
    public void createTaskFailed(String message) {
        ToastUtils.showLongToast(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(ActionPlanIndexBean bean){
        PLog.e(bean.getName() + "---" + bean.isChecked());
        if(bean.isChecked()){
            taskIndexBeans.add(bean);
        }else{
            taskIndexBeans.remove(bean);
        }
        buildIndexFlow(bean);
        PLog.e("测试:指标选择" + taskIndexBeans.size() + "");
    }

    /**
     * 添加选择指标
     * @param bean
     */
    private void buildIndexFlow(ActionPlanIndexBean bean) {
       if(bean.isChecked()){
           View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
           TextView tv_group = view.findViewById(R.id.tv_user);
           tv_group.setText(bean.getName());
           view.setTag(bean.getIndex_id());
           flowIndex.addView(view);
       }else{
           for (int i = 0 ; i < flowIndex.getChildCount() ; i++){
               View view = flowIndex.getChildAt(i);
               if(view.getTag().equals(bean.getIndex_id())){
                   flowIndex.removeView(view);
                   break;
               }
           }
       }
    }

    /**
     * 指标树下发指标
     * @param
     */
    private void buildIndexFlow(List<IndexTreeBean> indexTreeBeans) {
        if (!actionType.equals("4")){
            for (IndexTreeBean bean : indexTreeBeans){
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
                TextView tv_group = view.findViewById(R.id.tv_user);
                tv_group.setText(bean.getName());
                view.setTag(bean.getIndex_id());
                flowDimension.addView(view);
                if(!checkIndex(bean)){
                    showDimensionIndex.add(bean);
                }
            }

            if(showDimensionIndex.size() > 0){
                for (IndexTreeBean bean : showDimensionIndex){
                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
                    TextView tv_group = view.findViewById(R.id.tv_user);
                    tv_group.setText(bean.getIndex_clname());
                    view.setTag(bean.getIndex_id());
                    flowIndex.addView(view);
                }
            }
        }else{
            for (IndexTreeBean bean : indexTreeBeans){
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
                TextView tv_group = view.findViewById(R.id.tv_user);
                tv_group.setText(bean.getName());
                view.setTag(bean.getIndex_id());
                flowIndex.addView(view);
            }
        }
    }

    /**
     * 去除不同维度下同一指标
     * @param bean
     * @return
     */
    private boolean checkIndex(IndexTreeBean bean) {
        if(showDimensionIndex.size() > 0){
            for (IndexTreeBean indexTreeBean : showDimensionIndex){
                if(indexTreeBean.getIndex_id().equals(bean.getIndex_id())){
                    return true ;
                }
            }
            return false ;
        }else{
            return false;
        }
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
                buildAssistantFlow(event.getContact(),true);
            }else{
                Contact removeContact = null;
                for (Contact contact : assistantBeans){
                    if(contact.getBean().getId().equals(event.getContact().getBean().getId())){
                        removeContact = contact ;
                        break;
                    }
                }
                if(removeContact != null){
                    buildAssistantFlow(event.getContact(),false);
                    assistantBeans.remove(removeContact);
                }
            }
        }
    }

    /**
     * 构建协助人视图
     */
    public void buildAssistantFlow(Contact contact ,boolean isAddView){
        if(isAddView){
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
            TextView tv_group = view.findViewById(R.id.tv_user);
            tv_group.setText(contact.getBean().getName());
            view.setTag(contact.getBean().getId());
            flowAssistant.addView(view);
        }else{
            for (int i = 0 ; i < flowAssistant.getChildCount(); i++){
                View view = flowAssistant.getChildAt(i);
                if(view.getTag().equals(contact.getBean().getId())){
                    flowAssistant.removeView(view);
                    break;
                }
            }
        }
    }
}
