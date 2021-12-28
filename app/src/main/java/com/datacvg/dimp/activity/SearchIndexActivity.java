package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.SearchIndexAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.SearchIndexBean;
import com.datacvg.dimp.event.SearchIndexBeanClickEvent;
import com.datacvg.dimp.event.SearchIndexBeanSuccessEvent;
import com.datacvg.dimp.presenter.SearchIndexPresenter;
import com.datacvg.dimp.view.SearchIndexView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-13
 * @Description : 指标搜索
 */
public class SearchIndexActivity extends BaseActivity<SearchIndexView, SearchIndexPresenter>
        implements SearchIndexView {
    @BindView(R.id.ed_search)
    EditText edSearch ;
    @BindView(R.id.recycle_index)
    RecyclerView recycleIndex ;
    @BindView(R.id.cb_selectAll)
    CheckBox cbSelectAll ;

    private SearchIndexBean searchIndexBean ;
    private SearchIndexAdapter adapter ;
    private List<IndexChartBean> indexChartBeans = new ArrayList<>() ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_index;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        initAdapter();
    }

    private void initAdapter() {
        adapter = new SearchIndexAdapter(mContext,indexChartBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recycleIndex.setLayoutManager(manager);
        recycleIndex.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        searchIndexBean = (SearchIndexBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(searchIndexBean == null || searchIndexBean.getIndexChartBeans().isEmpty()){
            finish();
            return;
        }
        indexChartBeans.addAll(searchIndexBean.getIndexChartBeans());
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.tv_cancel,R.id.edit_delete,R.id.btn_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel :
                    finish();
                break;

            case R.id.edit_delete :
                edSearch.setText("");
                indexChartBeans.clear();
                indexChartBeans.addAll(searchIndexBean.getIndexChartBeans());
                adapter.notifyDataSetChanged();
                checkAllIndex();
                break;

            case R.id.btn_clear :
                EventBus.getDefault().post(new SearchIndexBeanSuccessEvent(searchIndexBean));
                finish();
                break;
        }
    }

    @OnTextChanged(value = R.id.ed_search,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChange(Editable editable){
        if (TextUtils.isEmpty(editable.toString())){
            indexChartBeans.clear();
            indexChartBeans.addAll(searchIndexBean.getIndexChartBeans());
        }
    }

    @OnEditorAction(R.id.ed_search)
    public  boolean onEditorAction(KeyEvent key) {
        DisplayUtils.hideSoftInput(mContext);
        edSearch.setCursorVisible(false);
        searchForKeyWord(edSearch.getText().toString());
        checkAllIndex();
        return true;
    }

    @OnCheckedChanged(R.id.cb_selectAll)
    public void onCheckChanged(boolean isSSelected){
        changeAllIndex(isSSelected);
    }

    private void changeAllIndex(boolean isSSelected) {
        for (IndexChartBean chartBean : indexChartBeans){
            chartBean.setSelected(isSSelected);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 关键字搜索
     * @param keyWord
     */
    private void searchForKeyWord(String keyWord) {
        indexChartBeans.clear();
        for (IndexChartBean chartBean : searchIndexBean.getIndexChartBeans()){
            if(chartBean.getIndex_clname().contains(keyWord)){
                indexChartBeans.add(chartBean);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchIndexBeanClickEvent event){
        checkAllIndex();
    }

    /**
     * 校验所有指标信息
     */
    private void checkAllIndex() {
        for (IndexChartBean chartBean : indexChartBeans){
            if(!chartBean.getSelected()){
                cbSelectAll.setChecked(false);
                return;
            }
        }
        cbSelectAll.setChecked(true);
    }
}
