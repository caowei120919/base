package com.datacvg.dimp.activity;

import android.os.Bundle;
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

    private List<String> assistIds = new ArrayList<>();
    private String headContactId ;
    private List<ContactOrDepartmentForActionBean> departmentInAtBeans = new ArrayList<>();
    private List<ContactOrDepartmentForActionBean> showBeans = new ArrayList<>() ;
    private ContactOrDepartmentAdapter adapter ;
    private boolean isHeadChoose ;

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
        isHeadChoose = getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_ALBUM,false) ;
        tvTitle.setText(isHeadChoose ? resources.getString(R.string.head) : resources.getString(R.string.assistant));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        this.setFinishOnTouchOutside(true);
        headContactId = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        assistIds = getIntent().getStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN);

        createDepartmentInAtBeans();

        adapter = new ContactOrDepartmentAdapter(mContext,showBeans,isHeadChoose);
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
                    showBeans.clear();
                    searchUserForList();
                    adapter.setDepartments(showBeans);
                    editUserName.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 根据关键字搜索人员信息
     */
    private void searchUserForList() {
        if(TextUtils.isEmpty(editUserName.getText().toString())){
            adapter.setJustContact(false);
            showBeans.addAll(departmentInAtBeans);
        }else{
            for (ContactOrDepartmentForActionBean bean : departmentInAtBeans){
                if(bean.getContactOrDepartmentBean().getName().contains(editUserName.getText().toString())){
                    if(isHeadChoose){
                        bean.setUnableChoose((assistIds == null || assistIds.isEmpty()) ? false : assistIds.contains(bean.getId()));
                    }else{
                        bean.setUnableChoose(TextUtils.isEmpty(headContactId) ? false : bean.getId().equals(headContactId));
                    }
                    showBeans.add(bean);
                }
            }
            adapter.setJustContact(true);
        }
    }

    /**
     * 拉取创建
     */
    private void createDepartmentInAtBeans() {
        for (ContactOrDepartmentBean contactOrDepartmentBean : DbContactOrDepartmentController.getInstance(mContext).queryAllResources()){
                ContactOrDepartmentForActionBean contactOrDepartmentForActionBean = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId()
                        ,contactOrDepartmentBean.getParentId(),contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
                if(isHeadChoose){
                    contactOrDepartmentForActionBean.setChecked(TextUtils.isEmpty(headContactId) ? false : headContactId.equals(contactOrDepartmentForActionBean.getId()));
                    contactOrDepartmentForActionBean.setUnableChoose((assistIds == null || assistIds.isEmpty())  ? false : assistIds.contains(contactOrDepartmentForActionBean.getId()));
                }else{
                    contactOrDepartmentForActionBean.setChecked((assistIds == null || assistIds.isEmpty())  ? false : assistIds.contains(contactOrDepartmentForActionBean.getId()));
                    contactOrDepartmentForActionBean.setUnableChoose(TextUtils.isEmpty(headContactId) ? false : contactOrDepartmentForActionBean.getId().equals(headContactId));
                    if(contactOrDepartmentForActionBean.isUnableChoose()){
                    }
                }
                departmentInAtBeans.add(contactOrDepartmentForActionBean);
            }
        showBeans.clear();
        showBeans.addAll(departmentInAtBeans);
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
