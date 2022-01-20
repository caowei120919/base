package com.datacvg.dimp.activity;

import android.content.Intent;
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
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;
import com.datacvg.dimp.bean.CreateTaskBean;
import com.datacvg.dimp.bean.CreateTaskIndex;
import com.datacvg.dimp.bean.IndexTreeBean;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.event.AddOrRemoveIndexForTaskEvent;
import com.datacvg.dimp.event.ChooseUserForActionEvent;
import com.datacvg.dimp.event.CreateTaskEvent;
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
    @BindView(R.id.rel_headUser)
    RelativeLayout relHeadUser ;

    /**
     * 时间选择器
     */
    private TimePickerView pvCustomTime ;
    /**
     * 任务标题
     */
    private String taskTitle ;
    private boolean fromActionFragment = true ;
    private String actionType = "";
    private IndexTreeNeedBean indexTreeNeedBean ;
    private List<ActionPlanIndexBean> taskIndexBeans = new ArrayList<>() ;
    private List<IndexTreeBean> indexTreeBeans = new ArrayList<>() ;
    private List<IndexTreeBean> showDimensionIndex = new ArrayList<>() ;
    /**
     * 负责人
     */
    private ContactOrDepartmentForActionBean headContact ;
    private ArrayList<String> selectIndexIds = new ArrayList<>() ;

    /**
     * 协助人
     */
    private List<ContactOrDepartmentForActionBean> assistantBeans = new ArrayList<>() ;

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
        for(int i = editable.length(); i > 0; i--){
            if(editable.subSequence(i-1, i).toString().equals("\n")){
                editable.replace(i-1, i, "");
            }
        }
        taskDetail = editable.toString().trim() ;
    }

    @OnCheckedChanged(R.id.switch_task)
    public void onCheckChanged(boolean checked){
        createTaskBean.setPlanFlg(checked ? "T" : "F");
    }

    @OnClick({R.id.img_left,R.id.tv_date,R.id.tv_actionTypeCommon
            ,R.id.tv_actionTypeSpecial,R.id.tv_priorityHigh,R.id.tv_priorityMiddle
            ,R.id.tv_priorityLow,R.id.img_addHead,R.id.img_addAssistant,R.id.img_addIndex
            ,R.id.tv_right,R.id.img_delete})
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
                    chooseContacts();
                break;

            case R.id.img_addAssistant :
                ArrayList<String> assistIds = new ArrayList<>() ;
                Intent intent = new Intent(mContext,ChooseContactFromActionActivity.class) ;
                intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,false);
                if(headContact != null){
                    intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,headContact.getId()) ;
                }
                if(!assistantBeans.isEmpty()){
                    for (ContactOrDepartmentForActionBean contact : assistantBeans){
                        assistIds.add(contact.getId());
                    }
                    intent.putStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN, assistIds);
                }
                mContext.startActivity(intent);
                break;

            case R.id.img_addIndex :
                    PLog.e("选择指标");
                    chooseIndex();
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
                    taskUser.setId(headContact.getContactOrDepartmentBean().getUserId());
                    taskUser.setName(headContact.getContactOrDepartmentBean().getName());
                    taskUser.setType("2");
                    taskUsers.add(taskUser);
                    for (ContactOrDepartmentForActionBean contact : assistantBeans){
                        CreateTaskBean.TaskUser taskAssistant = new CreateTaskBean.TaskUser();
                        taskAssistant.setType("3");
                        taskAssistant.setName(contact.getContactOrDepartmentBean().getName());
                        taskAssistant.setId(contact.getContactOrDepartmentBean().getUserId());
                        taskAssistant.setChecked(true);
                        taskUsers.add(taskAssistant);
                    }
                    actionPlanInfoDTO.setTask_deadline(taskDate);
                    actionPlanInfoDTO.setTask_parent_id("0");
                    createTaskBean.setLang(LanguageUtils.isZh(mContext) ? "zh" : "en");
                    if(!fromActionFragment){
                        createTaskBean.setStartTime(indexTreeNeedBean.getTimeVal());
                        createTaskBean.setFuDimension(indexTreeNeedBean.getFuDimension());
                        createTaskBean.setpDimension(indexTreeNeedBean.getpDimension());
                        createTaskBean.setOrgDimension(indexTreeNeedBean.getOrgDimension());
                        createTaskBean.setAllDeimension(indexTreeNeedBean.getOrgName() + ","
                                + indexTreeNeedBean.getFuName()
                                + "," + indexTreeNeedBean.getpName());
                    }else{
                        createTaskBean.setStartTime("");
                        createTaskBean.setFuDimension("");
                        createTaskBean.setpDimension("");
                        createTaskBean.setOrgDimension("");
                        createTaskBean.setAllDeimension("");
                    }
                    createTaskBean.setIndexList(new Gson().toJson(indexTreeBeans));
                    createTaskBean.setUserMsg(new Gson().toJson(taskUsers));
                    createTaskBean.setIndex(new Gson().toJson(taskIndexBeans));
                    createTaskBean.setActionType(actionType);
                    createTaskBean.setActionPlanInfoDTO(actionPlanInfoDTO);
                    getPresenter().createTask(createTaskBean);
                    PLog.e(new Gson().toJson(createTaskBean));
                break;

            case R.id.img_delete :
                if(relHeadUser.getVisibility() == View.VISIBLE){
                    relHeadUser.setVisibility(View.GONE);
                }
                headContact = null ;
                relHeadUser.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 选择指标
     */
    private void chooseIndex() {
        ArrayList<String> assistIds = new ArrayList<>() ;
        Intent intent = new Intent(mContext,ChooseIndexFromActionActivity.class) ;
        if(!selectIndexIds.isEmpty()){
            intent.putStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN, selectIndexIds);
        }
        mContext.startActivity(intent);
    }

    /**
     * 弹出选择联系人弹窗
     */
    private void chooseContacts() {
        ArrayList<String> assistIds = new ArrayList<>() ;
        Intent intent = new Intent(mContext,ChooseContactFromActionActivity.class) ;
        intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM,true);
        if(headContact != null){
            intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,headContact.getId()) ;
        }
        if(!assistantBeans.isEmpty()){
            for (ContactOrDepartmentForActionBean contact : assistantBeans){
                assistIds.add(contact.getId());
            }
            intent.putStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN, assistIds);
        }
        mContext.startActivity(intent);
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
        if(bean.isChecked()){
            taskIndexBeans.add(bean);
        }else{
            taskIndexBeans.remove(bean);
        }
        buildIndexFlow(bean);
    }

    /**
     * 添加选择指标
     * @param bean
     */
    private void buildIndexFlow(ActionPlanIndexBean bean) {
        String indexId = bean.getId() ;
       if((selectIndexIds == null || selectIndexIds.isEmpty()) || !selectIndexIds.contains(indexId)){
           /**
            * relation_i_id : 1874210758208575801734
            * rootid : 83967606548302484211
            * name : 运维收入
            * checked : false
            * pid : 83967606548302484211
            * id : 1874210758208575801734
            * index_id : 15341079964696855071
            */
           ActionPlanIndexBean indexTreeBean = new ActionPlanIndexBean() ;
           indexTreeBean.setName(bean.getName());
           indexTreeBean.setChecked(true);
           indexTreeBean.setId(bean.getId());
           indexTreeBean.setIndex_id(bean.getIndex_id());
           indexTreeBean.setPid(bean.getPid());
           indexTreeBean.setRootid(bean.getRootid());
           indexTreeBean.setRelation_i_id(bean.getRelation_i_id());
           taskIndexBeans.add(indexTreeBean);
           View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
           TextView tv_group = view.findViewById(R.id.tv_user);
           ImageView img_delete = view.findViewById(R.id.img_delete);
           img_delete.setVisibility(View.VISIBLE);
           img_delete.setOnClickListener(v -> {
               flowIndex.removeView(view);
               taskIndexBeans.remove(bean);
               selectIndexIds.remove(indexId);
           });
           tv_group.setText(bean.getName());
           view.setTag(bean.getIndex_id());
           selectIndexIds.add(indexId);
           flowIndex.addView(view);
       }else{
           selectIndexIds.remove(indexId);
           for (ActionPlanIndexBean indexTreeBean : taskIndexBeans){
               if(indexTreeBean.getIndex_id().equals(indexId)){
                   taskIndexBeans.remove(indexTreeBean);
                   return;
               }
           }
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
    public void onEvent(ChooseUserForActionEvent event){
        if(event.isHeadChoose()){
            if(event.getContactOrDepartmentForActionBean().isChecked()){
                headContact = event.getContactOrDepartmentForActionBean() ;
                relHeadUser.setVisibility(View.VISIBLE);
                tvHeadUser.setText(event.getContactOrDepartmentForActionBean()
                        .getContactOrDepartmentBean().getName());
            }else{
                relHeadUser.setVisibility(View.GONE);
                headContact = null ;
            }
        }else{
            buildAssistantFlow(event.getContactOrDepartmentForActionBean());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddOrRemoveIndexForTaskEvent event){
        buildIndexFlow(event.getActionPlanIndexBean());
    }

    /**
     * 构建协助人视图
     */
    public void buildAssistantFlow(ContactOrDepartmentForActionBean contact){
        if(contact.isChecked()){
            assistantBeans.add(contact);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_selected_user,null);
            TextView tv_group = view.findViewById(R.id.tv_user);
            ImageView img_delete =view.findViewById(R.id.img_delete);
            img_delete.setVisibility(View.VISIBLE);
            img_delete.setOnClickListener(v -> {
                flowAssistant.removeView(view);
                assistantBeans.remove(contact);
            });
            tv_group.setText(contact.getContactOrDepartmentBean().getName());
            view.setTag(contact.getId());
            flowAssistant.addView(view);
        }else{
            assistantBeans.remove(contact);
            for (int i = 0 ; i < flowAssistant.getChildCount(); i++){
                View view = flowAssistant.getChildAt(i);
                if(view.getTag().equals(contact.getId())){
                    flowAssistant.removeView(view);
                    break;
                }
            }
        }
    }


}
