package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.DepartmentAdapter;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.DepartmentInAtBean;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;
import com.datacvg.dimp.presenter.DepartmentPresenter;
import com.datacvg.dimp.view.DepartmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private List<DepartmentBean> departmentRootBeans = new ArrayList<>() ;
    private List<DepartmentBean> departmentBeans = new ArrayList<>();
    private List<DepartmentBean> departmentChildBeans = new ArrayList<>();
    private List<DepartmentInAtBean> departmentInAtBeans = new ArrayList<>();
    private DepartmentAdapter adapter ;

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

        createDepartmentInAtBeans();
        /**
         * 添加根节点
         */
        adapter = new DepartmentAdapter(mContext,departmentInAtBeans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
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

    /**
     * 拉取创建
     */
    private void createDepartmentInAtBeans() {
        /**
         * 存取的部门总数量
         */
        departmentBeans = DbDepartmentController.getInstance(mContext)
                .queryDepartmentList();
        departmentRootBeans = DbDepartmentController.getInstance(mContext)
                .queryDepartmentListForParent("0") ;
        int count = departmentBeans.size();
        /**
         * 是否有parent为0的根节点
         */
        boolean hasRootDepartment = departmentRootBeans.size() > 0;
        int leave = 0 ;
        /**
         * 循环遍历部门，创建树型结构的列表
         */
        while (departmentInAtBeans.size() < count && hasRootDepartment){
            if(leave != 0){
                departmentRootBeans.clear();
                departmentRootBeans.addAll(departmentChildBeans)  ;
                departmentChildBeans.clear();
            }
            for (int i = 0 ; i < departmentRootBeans.size() ; i++){
                DepartmentInAtBean departmentInAtBean
                        = new DepartmentInAtBean(departmentRootBeans.get(i).getDepartment_id().intValue()
                        ,DbDepartmentController.getInstance(mContext).getParentDepartmentIdForResId(departmentRootBeans.get(i).getD_res_parentid()),leave
                        ,DbDepartmentController.getInstance(mContext).queryDepartmentListForParent(departmentRootBeans.get(i).getD_res_id()).size() > 0
                        ,departmentRootBeans.get(i));
                departmentInAtBeans.add(departmentInAtBean);
                departmentChildBeans.addAll(DbDepartmentController.getInstance(mContext).queryDepartmentListForParent(departmentRootBeans.get(i).getD_res_id()));
            }
            leave ++ ;
        }
    }
}
