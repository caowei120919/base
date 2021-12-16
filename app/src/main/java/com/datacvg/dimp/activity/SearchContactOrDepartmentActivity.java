package com.datacvg.dimp.activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SearchContactOrDepartmentAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.greendao.bean.ContactOrDepartmentBean;
import com.datacvg.dimp.baseandroid.greendao.controller.DbContactOrDepartmentController;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ContactOrDepartmentForActionBean;
import com.datacvg.dimp.event.SearchContactOrDepartmentEvent;
import com.datacvg.dimp.presenter.SearchContactOrDepartmentPresenter;
import com.datacvg.dimp.view.SearchContactOrDepartmentView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-12-15
 * @Description :
 */
public class SearchContactOrDepartmentActivity extends BaseActivity<SearchContactOrDepartmentView, SearchContactOrDepartmentPresenter>
        implements SearchContactOrDepartmentView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.recycle_search)
    RecyclerView recycleSearch ;
    @BindView(R.id.ed_search)
    EditText edSearch ;

    /**
     * 是否为搜索联系人,默认为联系人
     */
    private boolean isSearchContact = true ;
    private ArrayList<String> selectIds = new ArrayList<>() ;
    private List<ContactOrDepartmentForActionBean> allContactOrDepartmentBeans = new ArrayList<>() ;
    private List<ContactOrDepartmentForActionBean> showContactOrDepartmentBeans = new ArrayList<>() ;
    private SearchContactOrDepartmentAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_contact_or_department;
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
        isSearchContact = getIntent().getBooleanExtra(Constants.EXTRA_DATA_FOR_BEAN,true);
        selectIds = getIntent().getStringArrayListExtra(Constants.EXTRA_DATA_FOR_SCAN);
        tvTitle.setText(isSearchContact ? resources.getString(R.string.the_contact)
                : resources.getString(R.string.department));
        if (isSearchContact){
            for (ContactOrDepartmentBean contactOrDepartmentBean : DbContactOrDepartmentController.getInstance(mContext).queryContactList()){
                ContactOrDepartmentForActionBean contactOrDepartmentForActionBean = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId(),contactOrDepartmentBean.getParentId()
                        ,contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
                contactOrDepartmentForActionBean.setChecked((selectIds == null || selectIds.isEmpty()) ? false : selectIds.contains(contactOrDepartmentBean.getResId()));
                allContactOrDepartmentBeans.add(contactOrDepartmentForActionBean);
            }
        }else{
            for (ContactOrDepartmentBean contactOrDepartmentBean : DbContactOrDepartmentController.getInstance(mContext).queryDepartmentList()){
                ContactOrDepartmentForActionBean contactOrDepartmentForActionBean = new ContactOrDepartmentForActionBean(contactOrDepartmentBean.getResId(),contactOrDepartmentBean.getParentId()
                        ,contactOrDepartmentBean.getLevel(),contactOrDepartmentBean.getIsExpend(),contactOrDepartmentBean);
                contactOrDepartmentForActionBean.setChecked((selectIds == null || selectIds.isEmpty()) ? false : selectIds.contains(contactOrDepartmentBean.getResId()));
                allContactOrDepartmentBeans.add(contactOrDepartmentForActionBean);
            }
        }
        showContactOrDepartmentBeans.addAll(allContactOrDepartmentBeans);
        adapter = new SearchContactOrDepartmentAdapter(mContext, showContactOrDepartmentBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recycleSearch.setLayoutManager(manager);
        recycleSearch.setAdapter(adapter);
    }

    @OnTextChanged(value = R.id.ed_search,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChange(Editable editable){
        showContactOrDepartmentBeans.clear();
        if(TextUtils.isEmpty(editable)){
            showContactOrDepartmentBeans.addAll(allContactOrDepartmentBeans);
        }else{
            for (ContactOrDepartmentForActionBean contactOrDepartmentForActionBean : allContactOrDepartmentBeans){
                if(contactOrDepartmentForActionBean.getContactOrDepartmentBean().getName().contains(editable.toString())){
                    showContactOrDepartmentBeans.add(contactOrDepartmentForActionBean);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.img_left,R.id.edit_delete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                if (selectIds != null && !selectIds.isEmpty()){
                    selectIds.clear();
                    for (ContactOrDepartmentForActionBean contactOrDepartmentForActionBean : allContactOrDepartmentBeans){
                        if(contactOrDepartmentForActionBean.isChecked()){
                            selectIds.add(contactOrDepartmentForActionBean.id);
                        }
                    }
                    SearchContactOrDepartmentEvent event = new SearchContactOrDepartmentEvent(selectIds);
                    event.setContact(isSearchContact);
                    EventBus.getDefault().post(event);
                }
                finish();
                break;

            case R.id.edit_delete :
                    edSearch.setText("");
                break;
        }
    }
}
