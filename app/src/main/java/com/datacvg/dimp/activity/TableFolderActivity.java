package com.datacvg.dimp.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.TableAdapter;
import com.datacvg.dimp.adapter.TableGridAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.Base64Utils;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.presenter.TableFolderPresenter;
import com.datacvg.dimp.view.TableFolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 报表文件夹页面
 */
public class TableFolderActivity extends BaseActivity<TableFolderView, TableFolderPresenter>
        implements TableFolderView ,TableGridAdapter.OnItemClickListener{
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.ed_search)
    EditText edSearch ;
    @BindView(R.id.grid_tableOfFolder)
    GridView recyclerTable ;

    private List<TableBean> tableBeans = new ArrayList<>();
    private List<TableBean> allTableBeans = new ArrayList<>() ;
    private List<TableBean> showTables = new ArrayList<>() ;
    private TableGridAdapter adapter ;
    private TableBean rootBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_table_folder;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        rootBean = (TableBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(rootBean == null){
            finish();
            return;
        }
        tvTitle.setText(LanguageUtils.isZh(mContext)
                ? rootBean.getRes_clname() : rootBean.getRes_flname());
        getTableList();
        adapter = new TableGridAdapter(mContext,tableBeans,this);
        recyclerTable.setAdapter(adapter);
    }

    /**
     * 查询报表列表
     */
    private void getTableList() {
        getPresenter().getTableList(Constants.TABLE_TYPE);
    }

    /**
     * 获取报表列表成功
     * @param tableBeans
     */
    @Override
    public void getTableSuccess(TableListBean tableBeans) {
        allTableBeans.clear();
        showTables.clear();
        for (TableBean childBean : tableBeans){
            if(childBean.getRes_parentid().equals(rootBean.getRes_id())){
                this.showTables.add(childBean);
                this.allTableBeans.add(childBean);
            }
        }
        this.tableBeans.clear();
        this.tableBeans.addAll(showTables);
        adapter.notifyDataSetChanged();
        PLog.e("tableBeans size is " + this.tableBeans.size());
    }

    /**
     * 单个点击监听
     * @param tableBean
     * CUSTOMJUMP  MODEL   CUSTOMRPT  powerbi   powerbi_install   TABLEAU   CX   BO_DASHBOARD
     */
    @Override
    public void onItemClick(TableBean tableBean) {
        switch (tableBean.getRes_showtype()){
            case "FOLDER" :
                Intent intent = new Intent(mContext, TableFolderActivity.class);
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,tableBean);
                mContext.startActivity(intent);
                break;
            case "MODEL" :
            case "CUSTOMJUMP" :
            case "CUSTOMRPT" :
            case "powerbi" :
            case "powerbi_install" :
            case "TABLEAU" :
            case "BO_DASHBOARD" :
                Intent tableIntent = new Intent(mContext, TableDetailActivity.class);
                tableIntent.putExtra(Constants
                        .EXTRA_DATA_FOR_BEAN,tableBean);
                mContext.startActivity(tableIntent);
                break;

            case "CX" :
                PLog.e("jump to CX");
                break;

            default:
                ToastUtils.showLongToast(resources
                        .getString(R.string.the_current_version_is_not_supported));
                break;
        }
    }

    @OnEditorAction(R.id.ed_search)
    public  boolean onEditorAction(KeyEvent key) {
        DisplayUtils.hideSoftInput(mContext);
        if(TextUtils.isEmpty(edSearch.getText())){
            ToastUtils.showLongToast(resources.getString(R.string.the_search_content_cannot_be_empty));
            return false ;
        }
        searchForTab(edSearch.getText().toString());
        edSearch.setCursorVisible(false);
        return true;
    }

    @OnTextChanged(value = R.id.ed_search,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onCodeTextChange(Editable editable){
        if(TextUtils.isEmpty(editable)){
            tableBeans.clear();
            tableBeans.addAll(showTables);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.img_left,R.id.edit_delete})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.edit_delete :
                edSearch.setText("");
                edSearch.setCursorVisible(false);
                break;
        }
    }

    /**
     * 根据关键字搜索
     * @param keyStr
     */
    private void searchForTab(String keyStr) {
        tableBeans.clear();
        for (TableBean tableBean : allTableBeans){
            if(tableBean.getRes_clname().contains(keyStr)
                    && !tableBean.getRes_showtype().equals("FOLDER")){
                tableBeans.add(tableBean);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
