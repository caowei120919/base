package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ContactAdapter;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.event.SelectChooseContactEvent;
import com.datacvg.dimp.event.ContactEvent;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbContactController;
import com.datacvg.dimp.presenter.ContactPresenter;
import com.datacvg.dimp.view.ContactView;
import com.datacvg.dimp.widget.LetterView;

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

    private List<DepartmentBean> departmentBeans = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private LetterView letterView;
    private ContactAdapter adapter;
    private List<ContactBean> contactBeans = new ArrayList<>();
    private List<ContactBean> selectContactBeans = new ArrayList<>();

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

        contactBeans = DbContactController.getInstance(mContext).queryContactList();
        if(contactBeans.isEmpty()){
            finish();
            return;
        }
        for (ContactBean contactBean : contactBeans){
            contactBean.setChecked(false);
            DbContactController.getInstance(mContext).updateContact(contactBean);
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
                EventBus.getDefault().post(new SelectChooseContactEvent(selectContactBeans));
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ContactEvent event){
        PLog.e(event.getContactBean().hashCode() + "");
        if(event.getContactBean().getChecked()){
            selectContactBeans.add(event.getContactBean());
        }else{
            selectContactBeans.remove(event.getContactBean());
        }
        tvSelectNum.setText(String.format(resources.getString(R.string.selected_people)
                ,selectContactBeans.size()+""));
    }
}
