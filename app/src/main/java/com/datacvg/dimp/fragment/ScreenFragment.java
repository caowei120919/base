package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.ScreenDetailActivity;
import com.datacvg.dimp.adapter.ScreenAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.ScreenListBean;
import com.datacvg.dimp.event.SwitchUserEvent;
import com.datacvg.dimp.presenter.ScreenPresenter;
import com.datacvg.dimp.view.ScreenView;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 大屏展示
 */
public class ScreenFragment extends BaseFragment<ScreenView, ScreenPresenter>
        implements ScreenView, OnRefreshListener, ScreenAdapter.ScreenItemClickListener {

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
        adapter = new ScreenAdapter(mContext,screenBeans,this);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerScreen.setLayoutManager(layoutManager);
        recyclerScreen.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.left = (int) resources.getDimension(R.dimen.W25);
                if (parent.getChildLayoutPosition(view) % 2 ==0) {
                    outRect.left = 0;
                }
            }
        });
        recyclerScreen.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getPresenter().getScreenList(screenType);
    }

    @Override
    public void getScreenSuccess(ScreenListBean resdata) {
        PLog.e(new Gson().toJson(resdata));
        if(smartScreen.isRefreshing()){
            smartScreen.finishRefresh();
        }
        screenBeans.clear();
        screenBeans.addAll(resdata);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getPresenter().getScreenList(screenType);
    }

    @OnClick({R.id.tv_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_right :
                    switch (screenType){
                        case Constants.DESC :
                                screenType = Constants.ASC ;
                                Drawable drawable = resources.getDrawable(R.mipmap.screen_asc);
                                drawable.setBounds(0,0,drawable.getMinimumWidth()
                                        ,drawable.getMinimumHeight());
                                tvRight.setCompoundDrawables(null,null
                                        ,drawable,null);
                                tvRight.setCompoundDrawablePadding(40);
                            break;

                        case Constants.ASC :
                                screenType = Constants.DESC ;
                                Drawable drawableAsc = resources.getDrawable(R.mipmap.screen_desc);
                            drawableAsc.setBounds(0,0,drawableAsc.getMinimumWidth()
                                    ,drawableAsc.getMinimumHeight());
                                tvRight.setCompoundDrawables(null,null
                                        ,drawableAsc,null);
                                tvRight.setCompoundDrawablePadding(40);
                            break;
                    }
                getPresenter().getScreenList(screenType);
                break;
        }
    }

    @Override
    public void onScreenClick(int position) {
        Intent intent = new Intent(mContext, ScreenDetailActivity.class);
        intent.putExtra("title",resources.getString(R.string.wit_screen)
                + (position + 1));
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,screenBeans.get(position));
        mContext.startActivity(intent);
    }
}
