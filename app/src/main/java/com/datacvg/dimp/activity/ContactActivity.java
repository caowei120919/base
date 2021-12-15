package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ContactAdapter;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.baseandroid.greendao.controller.DbContactOrDepartmentController;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;
import com.datacvg.dimp.event.AddDepartmentToContactEvent;
import com.datacvg.dimp.event.ContactEvent;
import com.datacvg.dimp.presenter.ContactPresenter;
import com.datacvg.dimp.view.ContactView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-02
 * @Description : 联系人选择页面
 */
public class ContactActivity extends BaseActivity<ContactView, ContactPresenter>
        implements ContactView{
    @BindView(R.id.tv_right)
    TextView tvRight ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.recycle_contacts)
    RecyclerView recycleContacts ;
    @BindView(R.id.tv_chooseDepartment)
    TextView tvChooseDepartment ;
    @BindView(R.id.tv_selectNum)
    TextView tvSelectNum ;

    private LinearLayoutManager layoutManager;
    private ContactAdapter adapter;
    private List<ContactOrDepartmentForActionBean> contactBeans = new ArrayList<>() ;
    private List<ContactOrDepartmentForActionBean> selectContactBeans = new ArrayList<>();
    /**
     * 已选择联系人,已选择部门
     */
    private List<String> selectUserIds = new ArrayList<>() ;
    private List<String> selectDepartmentIds = new ArrayList<>() ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvSelectNum.setText(String.format(resources.getString(R.string.selected_people)
                ,selectContactBeans.size()+""));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.the_contact));

        List<ContactOrDepartmentBean> beans
                = DbContactOrDepartmentController.getInstance(mContext).queryContactList();
        for (ContactOrDepartmentBean contactOrDepartmentBean : beans){
            ContactOrDepartmentForActionBean contactOrDepartmentForActionBean
                    = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId(),contactOrDepartmentBean.getParentId(),contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
            contactBeans.add(contactOrDepartmentForActionBean);
        }
        if(contactBeans.isEmpty()){
            finish();
            return;
        }
        layoutManager = new LinearLayoutManager(this);
        adapter = new ContactAdapter(this, contactBeans);
        recycleContacts.setLayoutManager(layoutManager);
        recycleContacts.setAdapter(adapter);
    }

    @OnClick({R.id.img_left,R.id.rel_chooseDepartment,R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();
                break;

            case R.id.rel_chooseDepartment :
                startActivity(new Intent(mContext,DepartmentActivity.class));
                break;

            case R.id.tv_confirm :
//                EventBus.getDefault().post(new SelectChooseContactEvent(selectContactBeans));
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ContactEvent event){
        selectUserIds.clear();
        selectUserIds.addAll(event.getSelectIds());
        tvSelectNum.setText(String.format(resources.getString(R.string.selected_people)
                ,selectUserIds.size()+""));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddDepartmentToContactEvent event){
        selectDepartmentIds.clear();
        selectDepartmentIds.addAll(event.getSelectDepartmentIds());
        if (selectDepartmentIds.isEmpty()){
            tvChooseDepartment.setText("");
        }else{
            tvChooseDepartment.setText(String.format(resources.getString(R.string.selected_department)
                    ,selectDepartmentIds.size()+""));
        }
    }
}
