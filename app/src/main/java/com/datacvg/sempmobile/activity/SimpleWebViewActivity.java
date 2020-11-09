package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 报告，报表统一展示的页面
 */
public class SimpleWebViewActivity extends SimpleBaseActivity{
    @BindView(R.id.webView)
    WebView webView ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple;
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }
}
