package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.DepartmentAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.baseandroid.greendao.controller.DbContactOrDepartmentController;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;
import com.datacvg.dimp.event.AddDepartmentEvent;
import com.datacvg.dimp.event.AddDepartmentToContactEvent;
import com.datacvg.dimp.event.SearchContactOrDepartmentEvent;
import com.datacvg.dimp.presenter.DepartmentPresenter;
import com.datacvg.dimp.view.DepartmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-02
 * @Description : 选择部门
 */
public class DepartmentActivity extends BaseActivity<DepartmentView, DepartmentPresenter>
        implements DepartmentView {
    @BindView(R.id.img_left)
    ImageView ivLeft ;

    @BindView(R.id.tv_right)
    TextView tvRight ;

    @BindView(R.id.tv_title)
    TextView tvCenter ;

    @BindView(R.id.recycle_department)
    RecyclerView recycleDepartment ;

    /**
     * 根节点数据
     */
    private List<ContactOrDepartmentBean> departmentBeans = new ArrayList<>();
    private List<ContactOrDepartmentForActionBean> departmentInAtBeans = new ArrayList<>();
    private DepartmentAdapter adapter ;
    private ArrayList<String> selectDepartmentIds = new ArrayList<>() ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_department;
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

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvCenter.setText(resources.getString(R.string.department));
        tvRight.setText(resources.getString(R.string.confirm));

        departmentBeans = DbContactOrDepartmentController.getInstance(mContext).queryDepartmentList();
        for (ContactOrDepartmentBean contactOrDepartmentBean : departmentBeans){
            ContactOrDepartmentForActionBean contactOrDepartmentForActionBean
                    = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId(),contactOrDepartmentBean.getParentId(),contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
            departmentInAtBeans.add(contactOrDepartmentForActionBean);
        }
        /**
         * 添加根节点
         */
        adapter = new DepartmentAdapter(mContext,departmentInAtBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recycleDepartment.setLayoutManager(layoutManager);
        recycleDepartment.setHasFixedSize(true);
        recycleDepartment.setNestedScrollingEnabled(false);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置Adapter
        recycleDepartment.setAdapter(adapter);
        //设置增加或删除条目的动画
        recycleDepartment.setItemAnimator( new DefaultItemAnimator());
    }


    @OnClick({R.id.img_left,R.id.tv_right,R.id.rel_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_right :
                EventBus.getDefault().post(new AddDepartmentToContactEvent(selectDepartmentIds));
                finish();
                break;

            case R.id.rel_search:
                Intent intent = new Intent(mContext,SearchContactOrDepartmentActivity.class);
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,false);
                intent.putStringArrayListExtra(Constants.EXTRA_DATA_FOR_SCAN,selectDepartmentIds);
                startActivity(intent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddDepartmentEvent event){
        selectDepartmentIds.clear();
        selectDepartmentIds.addAll(event.getSelectDepartmentIds());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchContactOrDepartmentEvent event){
        if(!event.isContact()){
            selectDepartmentIds.clear();
            if(event.getSelectId() != null && !event.getSelectId().isEmpty()){
                selectDepartmentIds.addAll(event.getSelectId());
                adapter.setSelectDepartments(selectDepartmentIds);
            }
        }
    }
}
