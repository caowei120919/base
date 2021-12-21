package com.datacvg.dimp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.MessageListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ActionPlanBean;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.MessageIdBean;
import com.datacvg.dimp.event.OnMessageReadEvent;
import com.datacvg.dimp.presenter.MessageListPresenter;
import com.datacvg.dimp.view.MessageListView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-10
 * @Description : 消息列表
 */
public class MessageListActivity extends BaseActivity<MessageListView, MessageListPresenter>
        implements MessageListView, MessageListAdapter.OnMessageClick {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_all)
    TextView tvAll ;
    @BindView(R.id.tv_unRead)
    TextView tvUnRead ;
    @BindView(R.id.tv_received)
    TextView tvReceived ;
    @BindView(R.id.recycler_message)
    RecyclerView recyclerMessage ;

    private String module_id = "" ;
    private int pageSize = Constants.MAX_PAGE_SIZE ;
    private int pageIndex = 1;
    /**
     * T for true and F for false
     */
    private String read_flag = "" ;
    private List<MessageBean.ResultBean> allResultBeans = new ArrayList<>();
    private List<MessageBean.ResultBean> displayBeans = new ArrayList<>();
    private MessageListAdapter adapter ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        module_id = getIntent().getStringExtra("module_id");
        tvAll.setSelected(true);
        restoreTitleStyle();
        switch (module_id){
            case Constants.MSG_ACTION :
                    tvTitle.setText(resources.getString(R.string.the_action_plan));
                break;

            case Constants.MSG_WARN :
                    tvTitle.setText(resources.getString(R.string.the_warning_message));
                break;

            case Constants.MSG_REPORTCOMMENT :
                    tvTitle.setText(resources.getString(R.string.report_comments));
                break;

            case Constants.MSG_DASHBOARD :
                    tvTitle.setText(resources.getString(R.string.management_kanban));
                break;
        }
        adapter = new MessageListAdapter(mContext,displayBeans,this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        recyclerMessage.setLayoutManager(manager);
        recyclerMessage.setAdapter(adapter);
        getMessage();
    }

    /**
     * 查询消息
     */
    private void getMessage() {
        getPresenter().getMessage(pageIndex + "",pageSize + "",module_id,read_flag);
    }

    /***
     * 消息获取成功
     * @param resdata
     */
    @Override
    public void getMessageSuccess(MessageBean resdata) {
       allResultBeans.clear();
       allResultBeans.addAll(resdata.getResult());
       displayBeans.addAll(allResultBeans);
       adapter.notifyDataSetChanged();
    }

    @Override
    public void getMessageReadSuccess() {
        adapter.notifyDataSetChanged();
        EventBus.getDefault().post(new OnMessageReadEvent());
    }

    @OnClick({R.id.img_left,R.id.tv_all,R.id.tv_unRead,R.id.tv_received})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_all :
                displayBeans.clear();
                displayBeans.addAll(allResultBeans);
                tvAll.setSelected(true);
                tvUnRead.setSelected(false);
                tvReceived.setSelected(false);
                restoreTitleStyle();
                adapter.notifyDataSetChanged();
                break;

            case R.id.tv_unRead :
                displayBeans.clear();
                for (MessageBean.ResultBean bean : allResultBeans){
                    if (!bean.isRead_flag()){
                        displayBeans.add(bean);
                    }
                }
                tvAll.setSelected(false);
                tvUnRead.setSelected(true);
                tvReceived.setSelected(false);
                restoreTitleStyle();
                adapter.notifyDataSetChanged();
                break;

            case R.id.tv_received :
                displayBeans.clear();
                for (MessageBean.ResultBean bean : allResultBeans){
                    if (bean.isRead_flag()){
                        displayBeans.add(bean);
                    }
                }
                tvAll.setSelected(false);
                tvUnRead.setSelected(false);
                tvReceived.setSelected(true);
                restoreTitleStyle();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void restoreTitleStyle() {
        tvAll.setTypeface(null, tvAll.isSelected() ? Typeface.BOLD : Typeface.NORMAL);
        tvUnRead.setTypeface(null, tvUnRead.isSelected() ? Typeface.BOLD : Typeface.NORMAL);
        tvReceived.setTypeface(null,tvReceived.isSelected() ? Typeface.BOLD : Typeface.NORMAL);
    }

    /**
     * 消息查看详情
     * @param bean
     */
    @Override
    public void toDetailClick(MessageBean.ResultBean bean) {
        if(bean.getModule_id().equals(Constants.MSG_ACTION)){
            ActionPlanBean actionPlanBean = new ActionPlanBean() ;
            String[] type = bean.getPrimary_id().split("_");
            actionPlanBean.setId(type[0]);
            actionPlanBean.setUser_type(Integer.valueOf(type[1]));
            Intent intent = new Intent(mContext, TaskDetailActivity.class);
            intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,actionPlanBean);
            mContext.startActivity(intent);
        }
    }

    /**
     * 消息标记已读
     * @param bean
     */
    @Override
    public void onReadClick(MessageBean.ResultBean bean) {
        bean.setRead_flag(true);
        List<MessageIdBean> messageIdBeans = new ArrayList<>();
        messageIdBeans.add(new MessageIdBean(bean.getId()));
        adapter.notifyDataSetChanged();
        getPresenter().doReadMessage(Constants.DO_READ,new Gson().toJson(messageIdBeans),"1",bean.getModule_id());
    }
}
