package com.datacvg.sempmobile.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.activity.NewTaskActivity;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.presenter.ActionPresenter;
import com.datacvg.sempmobile.view.ActionView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 行动方案
 */
public class ActionFragment extends BaseFragment<ActionView, ActionPresenter>
        implements ActionView {
    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.tv_all)
    TextView tvAll ;
    @BindView(R.id.tv_issued)
    TextView tvIssued ;
    @BindView(R.id.tv_received)
    TextView tvReceived ;
    @BindView(R.id.tv_wait)
    TextView tvWait ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_action;
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
        tvTitle.setText(resources.getString(R.string.action));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_add));
        tvAll.setSelected(true);
    }

    @OnClick({R.id.img_right,R.id.tv_all,R.id.tv_issued,R.id.tv_received,R.id.tv_wait})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_right :
                    mContext.startActivity(new Intent(mContext, NewTaskActivity.class));
                break;

            case R.id.tv_all :
                    tvAll.setSelected(true);
                    tvIssued.setSelected(false);
                    tvReceived.setSelected(false);
                    tvWait.setSelected(false);
                break;

            case R.id.tv_issued :
                    tvAll.setSelected(false);
                    tvIssued.setSelected(true);
                    tvReceived.setSelected(false);
                    tvWait.setSelected(false);
                break;

            case R.id.tv_received :
                    tvAll.setSelected(false);
                    tvIssued.setSelected(false);
                    tvReceived.setSelected(true);
                    tvWait.setSelected(false);
                break;

            case R.id.tv_wait :
                    tvAll.setSelected(false);
                    tvIssued.setSelected(false);
                    tvReceived.setSelected(false);
                    tvWait.setSelected(true);
                break;
        }
    }
}
