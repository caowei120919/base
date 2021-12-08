package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ContactOrDepartmentAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.baseandroid.greendao.controller.DbContactOrDepartmentController;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;
import com.datacvg.dimp.presenter.ChooseContactFromActionPresenter;
import com.datacvg.dimp.view.ChooseContactFromActionView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-25
 * @Description :
 */
public class ChooseContactFromActionActivity extends BaseActivity<ChooseContactFromActionView, ChooseContactFromActionPresenter>
        implements ChooseContactFromActionView {

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.recycler_contact)
    RecyclerView recyclerContact ;
    @BindView(R.id.edit_userName)
    EditText editUserName ;

    private List<String> assistIds = new ArrayList<>() ;
    private String headContactId ;
    private List<ContactOrDepartmentForActionBean> departmentInAtBeans = new ArrayList<>();
    private ContactOrDepartmentAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_choose_from_action;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        tvTitle.setText(getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_ALBUM,false)
                ? resources.getString(R.string.head) : resources.getString(R.string.assistant));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        this.setFinishOnTouchOutside(true);
        headContactId = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        assistIds = getIntent().getStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN);

        createDepartmentInAtBeans();

        adapter = new ContactOrDepartmentAdapter(mContext,departmentInAtBeans,assistIds,headContactId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerContact.setLayoutManager(layoutManager);
        recyclerContact.setHasFixedSize(true);
        recyclerContact.setNestedScrollingEnabled(false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerContact.setAdapter(adapter);
        recyclerContact.setItemAnimator(new DefaultItemAnimator());

        editUserName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    DisplayUtils.hideSoftInput(mContext);
                    departmentInAtBeans.clear();
                    if(TextUtils.isEmpty(editUserName.getText().toString())){
                        adapter.setJustContact(false);
                    }else{
                        adapter.setJustContact(true);
                    }
                    createDepartmentInAtBeans();
                    adapter.setDepartments(departmentInAtBeans);
                    editUserName.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 拉取创建
     */
    private void createDepartmentInAtBeans() {
        for (ContactOrDepartmentBean contactOrDepartmentBean : DbContactOrDepartmentController.getInstance(mContext).queryAllResources()){
            if(TextUtils.isEmpty(editUserName.getText().toString())){
                ContactOrDepartmentForActionBean contactOrDepartmentForActionBean = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId()
                        ,contactOrDepartmentBean.getParentId(),contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
                departmentInAtBeans.add(contactOrDepartmentForActionBean);
            }else{
                if(contactOrDepartmentBean.getName().contains(editUserName.getText().toString()) && contactOrDepartmentBean.getIsContact()){
                    ContactOrDepartmentForActionBean contactOrDepartmentForActionBean = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId()
                            ,contactOrDepartmentBean.getParentId(),contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
                    departmentInAtBeans.add(contactOrDepartmentForActionBean);
                }
            }
        }
        PLog.e(departmentInAtBeans.size() + "");
    }

    @OnClick({R.id.rel_outside,R.id.rel_view})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rel_outside :
                    finish();
                break;

            case R.id.rel_view :

                break;
        }
    }
}
