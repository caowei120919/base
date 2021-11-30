package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ChooseContactAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.ChooseContactForActionBean;
import com.datacvg.dimp.bean.Contact;
import com.datacvg.dimp.bean.DepartmentInAtBean;
import com.datacvg.dimp.greendao.bean.ContactBean;
import com.datacvg.dimp.greendao.bean.ContactBeanDao;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbContactController;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;
import com.datacvg.dimp.presenter.ChooseContactFromActionPresenter;
import com.datacvg.dimp.view.ChooseContactFromActionView;
import com.google.gson.Gson;

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

    private List<String> assistIds = new ArrayList<String>() ;
    private String headContactId ;
    private List<DepartmentInAtBean> departmentInAtBeans = new ArrayList<>() ;
    private List<ChooseContactForActionBean> chooseContactForActionBeans = new ArrayList<>() ;
    private List<ChooseContactForActionBean> sortBeans = new ArrayList<>() ;
    private ChooseContactAdapter adapter ;

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
        tvTitle.setText(getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_ALBUM));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        this.setFinishOnTouchOutside(true);
        headContactId = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        assistIds = getIntent().getStringArrayListExtra(Constants.EXTRA_DATA_FOR_BEAN);

        createDepartmentInAtBeans();

        adapter = new ChooseContactAdapter(mContext,sortBeans,chooseContactForActionBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerContact.setLayoutManager(layoutManager);
        recyclerContact.setHasFixedSize(true);
        recyclerContact.setNestedScrollingEnabled(false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerContact.setAdapter(adapter);
        recyclerContact.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 拉取创建
     */
    private void createDepartmentInAtBeans() {
        List<DepartmentBean> rootDepartmentBeans = DbDepartmentController.getInstance(mContext)
                .queryDepartmentListForParent("0");
        int count = DbDepartmentController.getInstance(mContext)
                .queryDepartmentList().size();
        for (DepartmentBean departmentBean : rootDepartmentBeans){

        }
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
