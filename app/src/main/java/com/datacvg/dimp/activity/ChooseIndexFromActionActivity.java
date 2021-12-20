package com.datacvg.dimp.activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.TaskIndexAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ActionPlanIndexBean;
import com.datacvg.dimp.bean.ActionPlanIndexForActionBean;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.event.AddOrRemoveIndexForTaskEvent;
import com.datacvg.dimp.presenter.ChooseIndexFromActionPresenter;
import com.datacvg.dimp.view.ChooseIndexFromActionView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-17
 * @Description :
 */
public class ChooseIndexFromActionActivity extends BaseActivity<ChooseIndexFromActionView, ChooseIndexFromActionPresenter>
        implements ChooseIndexFromActionView {
    @BindView(R.id.recycler_index)
    RecyclerView recyclerIndex ;
    @BindView(R.id.edit_indexName)
    EditText editIndexName ;

    private List<ActionPlanIndexForActionBean> allActionPlanIndexBeans = new ArrayList<>();
    private List<ActionPlanIndexForActionBean> showActionPlanIndexBeans = new ArrayList<>();
    private ArrayList<String> selectedIndexIds = new ArrayList<>() ;
    private TaskIndexAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_choose_from_action;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        selectedIndexIds = getIntent().getStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN);

        adapter = new TaskIndexAdapter(mContext, showActionPlanIndexBeans);
        adapter.setSelectDepartments(selectedIndexIds);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerIndex.setLayoutManager(manager);
        recyclerIndex.setAdapter(adapter);

        editIndexName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    DisplayUtils.hideSoftInput(mContext);
                    showActionPlanIndexBeans.clear();
                    searchIndexForList();
                    adapter.setDepartments(showActionPlanIndexBeans);
                    editIndexName.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 搜索指标
     */
    private void searchIndexForList() {
        if(TextUtils.isEmpty(editIndexName.getText().toString())){
            showActionPlanIndexBeans.addAll(allActionPlanIndexBeans);
            adapter.setSearch(false);
        }else{
            for (ActionPlanIndexForActionBean actionPlanIndexForActionBean : allActionPlanIndexBeans){
                if(actionPlanIndexForActionBean.getBean().getName().equals(editIndexName.getText().toString())){
                    showActionPlanIndexBeans.add(actionPlanIndexForActionBean);
                }
            }
            adapter.setSearch(true);
        }
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        getPresenter().getActionPlanIndex();
    }

    @OnClick({R.id.rel_outside})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rel_outside :
                finish();
                break;
        }
    }

    @Override
    public void getIndexSuccess(ActionPlanIndexListBean resdata) {
        List<ActionPlanIndexBean> defaultUserBeanList = resetResource(resdata);
        for (ActionPlanIndexBean bean : defaultUserBeanList){
            ActionPlanIndexForActionBean actionPlanIndexForActionBean = new ActionPlanIndexForActionBean(bean.getId(),bean.getPid(),bean.getLevel());
            actionPlanIndexForActionBean.setBean(bean);
            allActionPlanIndexBeans.add(actionPlanIndexForActionBean);
        }
        showActionPlanIndexBeans.clear();
        showActionPlanIndexBeans.addAll(allActionPlanIndexBeans);
        adapter.setDepartments(showActionPlanIndexBeans);
    }


    /**
     * 对数据层级进行重新处理
     * @param resdata
     * @return
     */
    private List<ActionPlanIndexBean> resetResource(List<ActionPlanIndexBean> resdata) {
        List<ActionPlanIndexBean> allResources = new ArrayList<>();
        List<ActionPlanIndexBean> rootUsers = findRootUserForList(resdata);
        for (ActionPlanIndexBean defaultUserBean : rootUsers){
            defaultUserBean.setLevel(0);
        }
        while (allResources.size() < resdata.size()){
            if (rootUsers.isEmpty()){
                return null ;
            }else{
                List<ActionPlanIndexBean> childUserBeans = new ArrayList<>() ;
                for (ActionPlanIndexBean defaultUserBean : rootUsers){
                    for (ActionPlanIndexBean userBean : resdata){
                        if(!userBean.getPid().equals(userBean.getId())
                                && userBean.getPid().equals(defaultUserBean.getId())){
                            userBean.setLevel(defaultUserBean.getLevel() + 1);
                            childUserBeans.add(userBean);
                        }
                    }
                }
                allResources.addAll(rootUsers);
                rootUsers.clear();
                rootUsers.addAll(childUserBeans);
            }
        }
        return allResources;
    }

    /**
     * 查询根节点
     * @param resdata
     */
    private List<ActionPlanIndexBean> findRootUserForList(List<ActionPlanIndexBean> resdata) {
        List<ActionPlanIndexBean> defaultUserBeans = new ArrayList<>() ;
        for (ActionPlanIndexBean rootUser : resdata){
            if(rootUser.getId().equals(rootUser.getRootid())){
                defaultUserBeans.add(rootUser);
            }
        }
        PLog.e(new Gson().toJson(defaultUserBeans));
        return defaultUserBeans ;
    }
}
