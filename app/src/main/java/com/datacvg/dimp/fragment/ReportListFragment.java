package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportPopAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.presenter.ReportListPresenter;
import com.datacvg.dimp.view.ReportListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class ReportListFragment extends BaseFragment<ReportListView, ReportListPresenter>
        implements ReportListView {
    @BindView(R.id.tv_emptyView)
    TextView tvEmptyView ;
    @BindView(R.id.recycler_report)
    RecyclerView recyclerReport ;

    private String indexId ;
    private List<TableBean> tableBeans = new ArrayList<>();
    private ReportPopAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_list;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        indexId = getArguments().getString(Constants.EXTRA_DATA_FOR_BEAN);
        PLog.e("indexId ======= > " + indexId);
        initAdapter();
    }

    /**
     * 初始化适配器相关
     */
    private void initAdapter() {
        adapter = new ReportPopAdapter(mContext,tableBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerReport.setLayoutManager(manager);
        recyclerReport.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getReportByIndexId(indexId);
    }

    public static ReportListFragment newInstance(String indexId) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_DATA_FOR_BEAN,indexId);
        ReportListFragment fragment = new ReportListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 获取关联报表成功
     * @param resdata
     */
    @Override
    public void getReportsSuccess(TableListBean resdata) {
        tableBeans.clear();
        tableBeans.addAll(resdata);
        if(resdata.isEmpty()){
            tvEmptyView.setVisibility(View.VISIBLE);
        }else{
            tvEmptyView.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }
}
