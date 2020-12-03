package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ContactAdapter;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbContactController;
import com.datacvg.dimp.presenter.ContactPresenter;
import com.datacvg.dimp.view.ContactView;
import com.datacvg.dimp.widget.DividerItemDecoration;
import com.datacvg.dimp.widget.LetterView;

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

    private List<DepartmentBean> departmentBeans = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ContactAdapter adapter;
    private List<ContactBean> contactBeans = new ArrayList<>();

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
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvRight.setText(resources.getString(R.string.confirm));
        tvTitle.setText(resources.getString(R.string.the_contact));

        contactBeans = DbContactController.getInstance(mContext).queryContactList();
        layoutManager = new LinearLayoutManager(this);
        adapter = new ContactAdapter(this, contactBeans);
        recycleContacts.setLayoutManager(layoutManager);
        recycleContacts.addItemDecoration(new DividerItemDecoration(this
                , DividerItemDecoration.VERTICAL_LIST));
        recycleContacts.setAdapter(adapter);
    }

    @OnClick({R.id.img_left,R.id.rel_chooseDepartment})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();
                break;

            case R.id.rel_chooseDepartment :
                startActivity(new Intent(mContext,DepartmentActivity.class));
                break;
        }
    }
}
