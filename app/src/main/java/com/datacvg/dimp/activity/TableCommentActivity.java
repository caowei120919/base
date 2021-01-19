package com.datacvg.dimp.activity;

import android.os.Bundle;

import com.datacvg.dimp.R;
import com.datacvg.dimp.presenter.TableCommentPresenter;
import com.datacvg.dimp.view.TableCommentView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-01-05
 * @Description : 报表评论
 */
public class TableCommentActivity extends BaseActivity<TableCommentView, TableCommentPresenter>
        implements TableCommentView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_table_comment;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
