package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.TableParamInfoBean;
import com.datacvg.dimp.event.DimensionParamsSelectEvent;
import com.datacvg.dimp.presenter.SelectDimensionPresenter;
import com.datacvg.dimp.view.SelectDimensionView;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

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
    /**
     *是否为单选
     */
    private Boolean isSingleChoice = false ;

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
        isSingleChoice = tableParamInfoBean.getSelectType().equals("0")
                && tableParamInfoBean.getSelectionType().equals("0");
        cbSelectAll.setEnabled(!isSingleChoice);
        cbSelectAll.setVisibility(isSingleChoice ? View.GONE : View.VISIBLE);
        tvTitle.setText(tableParamInfoBean.getControlName());
        dataSourceBeansOfAll.addAll(tableParamInfoBean.getDataSource());
        allAdapter.notifyDataSetChanged();
    }

    @OnTextChanged(value = R.id.ed_search,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChange(Editable editable){
        if(editable.toString().isEmpty()){
            dataSourceBeansOfAll.addAll(tableParamInfoBean.getDataSource());
            dataSourceBeansOfAll.removeAll(dataSourceBeansOfMine);
            allAdapter.notifyDataSetChanged();
        }
    }

    @OnEditorAction(R.id.ed_search)
    public  boolean onEditorAction(KeyEvent key) {
        DisplayUtils.hideSoftInput(mContext);
        searchForKeyWord(edSearch.getText().toString());
        return true;
    }

    /**
     * 根据关键字查找
     * @param keyword
     */
    private void searchForKeyWord(String keyword) {
        List<TableParamInfoBean.DataSourceBean> dataSourceBeans = new ArrayList<>();
        dataSourceBeans.addAll(dataSourceBeansOfAll);
        dataSourceBeansOfAll.clear();
        if(!TextUtils.isEmpty(keyword)){
            for (TableParamInfoBean.DataSourceBean bean : dataSourceBeans){
                if(bean.getD_res_clname().contains(keyword)){
                    dataSourceBeansOfAll.add(bean);
                }
            }
        }else{
            dataSourceBeansOfAll.addAll(tableParamInfoBean.getDataSource());
            dataSourceBeansOfAll.removeAll(dataSourceBeansOfMine);
        }
        allAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.img_left,R.id.edit_delete,R.id.btn_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                EventBus.getDefault().post(new DimensionParamsSelectEvent(dataSourceBeansOfMine));
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
        if(isSingleChoice){
            dataSourceBeansOfAll.remove(dataSourceBean);
            dataSourceBeansOfAll.addAll(dataSourceBeansOfMine);
            allAdapter.notifyDataSetChanged();
            dataSourceBeansOfMine.clear();
            dataSourceBeansOfMine.add(dataSourceBean);
            mineAdapter.notifyDataSetChanged();
        }else{
            dataSourceBeansOfAll.remove(dataSourceBean);
            allAdapter.notifyDataSetChanged();
            dataSourceBeansOfMine.add(dataSourceBean);
            mineAdapter.notifyDataSetChanged();
        }
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
        if(edSearch.getText().toString().isEmpty()){
            dataSourceBeansOfAll.add(dataSourceBean);
            allAdapter.notifyDataSetChanged();
        }
        if(dataSourceBeansOfAll.isEmpty()){
            cbSelectAll.setChecked(true);
            cbSelectAll.setEnabled(false);
        }else{
            cbSelectAll.setChecked(false);
            cbSelectAll.setEnabled(true);
        }
    }
}
