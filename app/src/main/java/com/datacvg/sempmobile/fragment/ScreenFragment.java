package com.datacvg.sempmobile.fragment;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.adapter.ScreenAdapter;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.bean.ScreenBean;
import com.datacvg.sempmobile.presenter.ScreenPresenter;
import com.datacvg.sempmobile.view.ScreenView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 大屏展示
 */
public class ScreenFragment extends BaseFragment<ScreenView, ScreenPresenter>
        implements ScreenView, OnRefreshListener {

    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.smart_screen)
    SmartRefreshLayout smartScreen ;
    @BindView(R.id.recycler_screen)
    RecyclerView recyclerScreen ;

    private ScreenAdapter adapter ;

    private String screenType = Constants.DESC ;
    private List<ScreenBean> screenBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_screen;
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
        tvTitle.setText(resources.getString(R.string.screen));
        tvRight.setText(resources.getString(R.string.time_sequence));
        Drawable drawable = resources.getDrawable(R.mipmap.screen_desc);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tvRight.setCompoundDrawables(null,null,drawable,null);
        tvRight.setCompoundDrawablePadding(40);
        smartScreen.setEnableAutoLoadMore(false);
        smartScreen.setOnRefreshListener(this);
        smartScreen.setEnableRefresh(true);
        adapter = new ScreenAdapter(mContext,screenBeans);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerScreen.setLayoutManager(layoutManager);
        recyclerScreen.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        recyclerScreen.setAdapter(adapter);

        getPresenter().getScreenList(screenType);
    }

    @Override
    public void getScreenSuccess(List<ScreenBean> resdata) {
        screenBeans.clear();
        screenBeans.addAll(resdata);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getScreenList(screenType);
    }
}
