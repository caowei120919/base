package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.SearchListReportBean;
import com.datacvg.dimp.presenter.SearchReportPresenter;
import com.datacvg.dimp.view.SearchReportView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-20
 * @Description :报告搜索
 */
public class SearchReportActivity extends BaseActivity<SearchReportView, SearchReportPresenter>
        implements SearchReportView {
    @BindView(R.id.edit_searchText)
    EditText editSearchText ;
    @BindView(R.id.recycler_searchReport)
    RecyclerView recyclerSearchReport ;

    private String searchText ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        editSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    AndroidUtils.hideKeyboard(editSearchText);
                    getPresenter().searchReportForName(searchText);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    @OnTextChanged(value = R.id.edit_searchText,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onSearchTextChange(Editable editable){
        searchText = editable.toString().trim();
    }

    @OnClick({R.id.tv_cancel,R.id.rel_clear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_cancel :
                    finish();
                break;

            case R.id.rel_clear :
                searchText = "" ;
                editSearchText.setText("");
                break;
        }
    }

    /**
     * 获取搜索报告列表成功
     * @param data
     */
    @Override
    public void getSearchReportListSuccess(SearchListReportBean data) {

    }
}
