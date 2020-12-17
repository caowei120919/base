package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.NewTaskActivity;
import com.datacvg.dimp.adapter.ActionPlanAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ActionPlanBean;
import com.datacvg.dimp.bean.ActionPlanListBean;
import com.datacvg.dimp.event.CreateTaskEvent;
import com.datacvg.dimp.presenter.ActionPresenter;
import com.datacvg.dimp.view.ActionView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 行动方案
 */
public class ActionFragment extends BaseFragment<ActionView, ActionPresenter>
        implements ActionView, ActionPlanAdapter.ToActionClick {
    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.tv_all)
    TextView tvAll ;
    @BindView(R.id.tv_issued)
    TextView tvIssued ;
    @BindView(R.id.tv_received)
    TextView tvReceived ;
    @BindView(R.id.recycler_action)
    RecyclerView recyclerAction;

    private int pageNo = 0 ;
    private int pageSize = 20 ;
    private int tag = 0 ;

    private List<ActionPlanBean> actionPlanBeans = new ArrayList<>();
    private List<ActionPlanBean> allActionPlanBeans = new ArrayList<>();
    private List<ActionPlanBean> issuedActionPlanBeans = new ArrayList<>();
    private List<ActionPlanBean> receivedActionPlanBeans = new ArrayList<>();
    private ActionPlanAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_action;
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
        imgLeft.setVisibility(View.GONE);
        tvTitle.setText(resources.getString(R.string.action));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_add));
        tvAll.setSelected(true);
        tag = tvAll.getId() ;
        restoreTitleStyle();

        adapter = new ActionPlanAdapter(mContext,actionPlanBeans,this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        recyclerAction.addItemDecoration(new RecyclerView.ItemDecoration() {
        });
        recyclerAction.setLayoutManager(manager);
        recyclerAction.setAdapter(adapter);
        getActionList();
    }

    /**
     * 获取行动方案列表
     */
    private void getActionList() {
        Map params = new HashMap<String,String>();
        params.put("pageSize",pageSize);
        params.put("pageNo",pageNo);
        getPresenter().getActionList(params);
    }

    @OnClick({R.id.img_right,R.id.tv_all,R.id.tv_issued,R.id.tv_received})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_right :
                    Intent newTaskIntent = new Intent(mContext, NewTaskActivity.class);
                    newTaskIntent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,true);
                    mContext.startActivity(newTaskIntent);
                break;

            case R.id.tv_all :
                    tag = view.getId();
                    tvAll.setSelected(true);
                    tvIssued.setSelected(false);
                    tvReceived.setSelected(false);
                    actionPlanBeans.clear();
                    actionPlanBeans.addAll(allActionPlanBeans);
                    adapter.notifyDataSetChanged();
                    restoreTitleStyle();
                break;

            case R.id.tv_issued :
                    tag = view.getId();
                    tvAll.setSelected(false);
                    tvIssued.setSelected(true);
                    tvReceived.setSelected(false);
                    actionPlanBeans.clear();
                    actionPlanBeans.addAll(issuedActionPlanBeans);
                    adapter.notifyDataSetChanged();
                    restoreTitleStyle();
                break;

            case R.id.tv_received :
                    tag = view.getId();
                    tvAll.setSelected(false);
                    tvIssued.setSelected(false);
                    tvReceived.setSelected(true);
                    actionPlanBeans.clear();
                    actionPlanBeans.addAll(receivedActionPlanBeans);
                    adapter.notifyDataSetChanged();
                    restoreTitleStyle();
                 break;
        }
    }

    private void restoreTitleStyle() {
        tvAll.setTypeface(null, tvAll.isSelected() ? Typeface.BOLD : Typeface.NORMAL);
        tvIssued.setTypeface(null, tvIssued.isSelected() ? Typeface.BOLD : Typeface.NORMAL);
        tvReceived.setTypeface(null,tvReceived.isSelected() ? Typeface.BOLD : Typeface.NORMAL);
    }

    /**
     * 获取行动方案成功
     * @param beans
     */
    @Override
    public void getActionPlanListSuccess(ActionPlanListBean beans) {
        if(pageNo == 0){
            actionPlanBeans.clear();
            allActionPlanBeans.clear();
            issuedActionPlanBeans.clear();
            receivedActionPlanBeans.clear();
        }
        allActionPlanBeans.addAll(beans);
        for (ActionPlanBean bean:beans) {
            if(bean.getCreate_user_pkid()
                    .equals(PreferencesHelper.get(Constants.USER_PKID,""))){
                issuedActionPlanBeans.add(bean);
            }else{
                receivedActionPlanBeans.add(bean);
            }
        }
        switch (tag){
            case R.id.tv_all :
                actionPlanBeans.clear();
                actionPlanBeans.addAll(allActionPlanBeans);
                break;

            case R.id.tv_issued :
                actionPlanBeans.clear();
                actionPlanBeans.addAll(issuedActionPlanBeans);
                break;

            case R.id.tv_received :
                actionPlanBeans.clear();
                actionPlanBeans.addAll(receivedActionPlanBeans);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 查看行动方案详情
     * @param position
     */
    @Override
    public void goActionDetailClick(int position) {
        PLog.e("查看行动方案详情");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CreateTaskEvent event){
        pageNo = 0 ;
        getActionList();
    }
}
