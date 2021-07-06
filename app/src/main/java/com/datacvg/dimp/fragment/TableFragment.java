package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.ReportDetailActivity;
import com.datacvg.dimp.activity.TableDetailActivity;
import com.datacvg.dimp.activity.TableFolderActivity;
import com.datacvg.dimp.adapter.TableAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.Base64Utils;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.presenter.TablePresenter;
import com.datacvg.dimp.view.TableView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 主题报表
 */
public class TableFragment extends BaseFragment<TableView, TablePresenter> implements TableView, TableAdapter.OnItemClickListener {

    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.recycler_table)
    RecyclerView recyclerTable ;
    @BindView(R.id.ed_search)
    EditText edSearch ;

    private List<TableBean> tableBeans = new ArrayList<>();
    private List<TableBean> allTableBeans = new ArrayList<>() ;
    private List<TableBean> showTables = new ArrayList<>() ;
    private TableAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_table;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        imgLeft.setVisibility(View.GONE);
        tvTitle.setText(resources.getString(R.string.the_theme_report));
    }

    @Override
    protected void setupData() {
        getTableList();
        adapter = new TableAdapter(mContext,tableBeans,this);
        recyclerTable.setLayoutManager(new GridLayoutManager(mContext,2));
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
        List<TableBean> rootBeans = new ArrayList<>();
        allTableBeans.clear();
        allTableBeans.addAll(tableBeans);
        showTables.clear();
        /**
         * 查询根节点对象
         */
        for (TableBean bean : tableBeans){
            if(bean.getRes_parentid().equals("0")){
                rootBeans.add(bean);
            }
        }

        for (TableBean bean : rootBeans){
            for (TableBean childBean : tableBeans){
                if(childBean.getRes_parentid().equals(bean.getRes_id())){
                    showTables.add(childBean);
                }
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
                    Intent folderIntent = new Intent(mContext, TableFolderActivity.class);
                    folderIntent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,tableBean);
                    mContext.startActivity(folderIntent);
                break;

            case "MODEL" :
                    PLog.e("jump to model");
            case "CUSTOMJUMP" :
                PLog.e("jump to customJump");
            case "CUSTOMRPT" :
                PLog.e("jump to customRpt");
            case "powerbi" :
                PLog.e("jump to powerbi");
            case "powerbi_install" :
                PLog.e("jump to powerbi_install");
            case "TABLEAU" :
                PLog.e("jump to TABLEAU");
            case "BO_DASHBOARD" :
                PLog.e("jump to BO_DASHBOARD");
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
        DisplayUtils.hideSoftInput(getActivity());
        if(TextUtils.isEmpty(edSearch.getText())){
            ToastUtils.showLongToast(resources.getString(R.string.the_search_content_cannot_be_empty));
            return false ;
        }
        searchForTab(edSearch.getText().toString());
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
