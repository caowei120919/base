package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.DataSourceOfAllAdapter;
import com.datacvg.dimp.adapter.DataSourceOfMineAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.TableParamInfoBean;
import com.datacvg.dimp.presenter.SelectDimensionPresenter;
import com.datacvg.dimp.view.SelectDimensionView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-12
 * @Description : 维度参数
 */
public class SelectDimensionActivity extends BaseActivity<SelectDimensionView, SelectDimensionPresenter>
        implements SelectDimensionView, DataSourceOfAllAdapter.OnItemClick, DataSourceOfMineAdapter.OnItemClick {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.ed_search)
    EditText edSearch ;
    @BindView(R.id.edit_delete)
    ImageView editDelete ;
    @BindView(R.id.recycle_myChoice)
    RecyclerView recycleMyChoice ;
    @BindView(R.id.recycle_all)
    RecyclerView recycleAll ;
    @BindView(R.id.cb_selectAll)
    CheckBox cbSelectAll ;

    private TableParamInfoBean tableParamInfoBean ;
    private List<TableParamInfoBean.DataSourceBean> dataSourceBeansOfMine = new ArrayList<>();
    private List<TableParamInfoBean.DataSourceBean> dataSourceBeansOfAll = new ArrayList<>();
    private DataSourceOfMineAdapter mineAdapter ;
    private DataSourceOfAllAdapter allAdapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_dimension;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        initMineAdapter();
        initAllAdapter();
    }

    private void initAllAdapter() {
        allAdapter = new DataSourceOfAllAdapter(mContext,dataSourceBeansOfAll,this);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        recycleAll.setLayoutManager(manager);
        recycleAll.setAdapter(allAdapter);
    }

    private void initMineAdapter() {
        mineAdapter = new DataSourceOfMineAdapter(mContext,dataSourceBeansOfMine,this);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        recycleMyChoice.setLayoutManager(manager);
        recycleMyChoice.setAdapter(mineAdapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tableParamInfoBean = (TableParamInfoBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(tableParamInfoBean == null){
            finish();
            return;
        }
        tvTitle.setText(tableParamInfoBean.getControlName());
        dataSourceBeansOfAll.addAll(tableParamInfoBean.getDataSource());
        allAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.img_left,R.id.edit_delete,R.id.btn_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.edit_delete :
                edSearch.setText("");
                break;

            case R.id.btn_clear :
                dataSourceBeansOfAll.addAll(dataSourceBeansOfMine);
                dataSourceBeansOfMine.clear();
                allAdapter.notifyDataSetChanged();
                mineAdapter.notifyDataSetChanged();
                cbSelectAll.setChecked(false);
                cbSelectAll.setEnabled(true);
                break;
        }
    }

    @OnCheckedChanged(R.id.cb_selectAll)
    public void onCheckChanged(boolean checked){
        if(checked){
            dataSourceBeansOfMine.addAll(dataSourceBeansOfAll);
            mineAdapter.notifyDataSetChanged();
            dataSourceBeansOfAll.clear();
            allAdapter.notifyDataSetChanged();
            cbSelectAll.setEnabled(false);
        }
    }

    @Override
    public void onAllClick(TableParamInfoBean.DataSourceBean dataSourceBean) {
        dataSourceBeansOfAll.remove(dataSourceBean);
        allAdapter.notifyDataSetChanged();
        dataSourceBeansOfMine.add(dataSourceBean);
        mineAdapter.notifyDataSetChanged();
        if(dataSourceBeansOfAll.isEmpty()){
            cbSelectAll.setChecked(true);
            cbSelectAll.setEnabled(false);
        }else{
            cbSelectAll.setChecked(false);
            cbSelectAll.setEnabled(true);
        }
    }

    @Override
    public void onMineClick(TableParamInfoBean.DataSourceBean dataSourceBean) {
        dataSourceBeansOfMine.remove(dataSourceBean);
        mineAdapter.notifyDataSetChanged();
        dataSourceBeansOfAll.add(dataSourceBean);
        allAdapter.notifyDataSetChanged();
        if(dataSourceBeansOfAll.isEmpty()){
            cbSelectAll.setChecked(true);
            cbSelectAll.setEnabled(false);
        }else{
            cbSelectAll.setChecked(false);
            cbSelectAll.setEnabled(true);
        }
    }
}
