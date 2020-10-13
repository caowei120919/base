package com.datacvg.sempmobile.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.TablePresenter;
import com.datacvg.sempmobile.view.TableView;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 主题报表
 */
public class TableFragment extends BaseFragment<TableView, TablePresenter> implements TableView {

    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;

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
    }
}
