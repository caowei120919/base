package com.datacvg.sempmobile.activity;

import android.os.Bundle;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.presenter.TableFolderPresenter;
import com.datacvg.sempmobile.view.TableFolderView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 报表文件夹页面
 */
public class TableFolderActivity extends BaseActivity<TableFolderView, TableFolderPresenter>
        implements TableFolderView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_table_folder;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
