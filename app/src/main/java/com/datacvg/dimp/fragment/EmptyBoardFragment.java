package com.datacvg.dimp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.AddIndexPageActivity;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.event.CompleteEvent;
import com.datacvg.dimp.event.EditEvent;
import com.datacvg.dimp.event.PageCompleteEvent;
import com.datacvg.dimp.presenter.EmptyBoardPresenter;
import com.datacvg.dimp.view.EmptyBoardView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-15
 * @Description : 空看板页
 */
public class EmptyBoardFragment extends BaseFragment<EmptyBoardView, EmptyBoardPresenter>
        implements EmptyBoardView {
    @BindView(R.id.rel_addOrDelete)
    RelativeLayout relAddOrDelete ;
    @BindView(R.id.lin_edit)
    LinearLayout linEdit ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_empty_board;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {

    }

    @Override
    protected void setupData() {

    }

    @OnClick({R.id.lin_deletePage,R.id.lin_addPage})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.lin_addPage :
                PLog.e("添加页");
                Intent intent = new Intent(mContext, AddIndexPageActivity.class);
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,"0");
                mContext.startActivity(intent);
                break;

            case R.id.lin_deletePage :
                ToastUtils.showLongToast(resources.getString(R.string.temporarily_no_data));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EditEvent event){
        relAddOrDelete.setVisibility(View.VISIBLE);
        linEdit.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CompleteEvent event){
        if(isFragmentVisible()){
            relAddOrDelete.setVisibility(View.GONE);
            linEdit.setVisibility(View.GONE);
            EventBus.getDefault().post(new PageCompleteEvent());
        }
    }

    public static EmptyBoardFragment newInstance() {
        Bundle args = new Bundle();
        EmptyBoardFragment fragment = new EmptyBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
