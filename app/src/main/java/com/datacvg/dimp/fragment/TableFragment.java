package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
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

    private List<TableBean> tableBeans = new ArrayList<>();
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
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        imgLeft.setVisibility(View.GONE);
        tvTitle.setText(resources.getString(R.string.the_theme_report));

        getTableList();
        adapter = new TableAdapter(mContext,tableBeans,this);
        recyclerTable.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerTable.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                //不是第一个的格子都设一个左边和底部的间距
                outRect.left = (int) resources.getDimension(R.dimen.W25);
                if (parent.getChildLayoutPosition(view) % 2 ==0) {
                    outRect.left = 0;
                }
            }
        });
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
                    this.tableBeans.add(childBean);
                }
            }
        }

        adapter.notifyDataSetChanged();
        for (TableBean bean : this.tableBeans){
            getPresenter().getImageRes(bean.getRes_id());
        }
        PLog.e("tableBeans size is " + this.tableBeans.size());
    }

    @Override
    public void getImageResSuccess(String res_id, String resdata) {
        for (TableBean bean : tableBeans){
            if(bean.getRes_id().equals(res_id)){
                bean.setImageRes(Base64Utils.decode(resdata.split("base64,")[1]));
                adapter.notifyDataSetChanged();
                return;
            }
        }

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
                    ReportBean reportBean = new ReportBean();
                    reportBean.setReport_type(Constants.REPORT_MINE );
                    reportBean.setModel_clname(tableBean.getRes_clname());
                    reportBean.setModel_flname(tableBean.getRes_flname());
                    reportBean.setModel_id(tableBean.getRes_id());
                    reportBean.setPkid(tableBean.getRes_pkid());
                    reportBean.setParent_id(tableBean.getRes_parentid());
                    Intent modelIntent = new Intent(mContext, ReportDetailActivity.class);
                    modelIntent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,reportBean);
                    mContext.startActivity(modelIntent);
                break;

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
}
