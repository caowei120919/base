package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.presenter.IndexTreePresenter;
import com.datacvg.dimp.view.IndexTreeView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description : 指标树页面
 */
public class IndexTreeActivity extends BaseActivity<IndexTreeView, IndexTreePresenter>
        implements IndexTreeView {
    @BindView(R.id.img_create)
    ImageView imgCreate ;
    @BindView(R.id.img_edit)
    ImageView imgEdit ;
    @BindView(R.id.img_share)
    ImageView imgShare ;
    @BindView(R.id.tv_smallSize)
    TextView tvSmallSize ;
    @BindView(R.id.tv_middleSize)
    TextView tvMiddleSize ;
    @BindView(R.id.tv_bigSize)
    TextView tvBigSize ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;

    private boolean isEditStatus = false ;
    private IndexTreeNeedBean indexTreeNeedBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_tree;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvBigSize.setTextColor(resources.getColor(R.color.c_000000));
        tvBigSize.setTypeface(Typeface.DEFAULT_BOLD);
        tvSmallSize.setTextColor(resources.getColor(R.color.c_999999));
        tvMiddleSize.setTextColor(resources.getColor(R.color.c_999999));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        indexTreeNeedBean = (IndexTreeNeedBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(indexTreeNeedBean == null){
            finish();
            return;
        }
        tvTitle.setText(LanguageUtils.isZh(mContext) ? indexTreeNeedBean.getDimension_clName()
                : indexTreeNeedBean.getDimension_flName());
    }

    @OnClick({R.id.back,R.id.img_share,R.id.img_edit,R.id.img_create,R.id.tv_smallSize
            ,R.id.tv_middleSize,R.id.tv_bigSize})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back :
                finish();
                break;

            /**
             * 分享
             */
            case R.id.img_share :

                break;

            case R.id.img_edit :
                if(!isEditStatus){
                    imgEdit.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_index_edit));
                    imgCreate.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_task_creat));
                }else{
                    imgEdit.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_index_common));
                    imgCreate.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_task_common));
                }
                isEditStatus = !isEditStatus ;
                break;

            case R.id.img_create :
                if(!isEditStatus){
                    return;
                }
                break;

            case R.id.tv_smallSize :
                tvSmallSize.setTextColor(resources.getColor(R.color.c_000000));
                tvSmallSize.setTypeface(Typeface.DEFAULT_BOLD);
                tvMiddleSize.setTextColor(resources.getColor(R.color.c_999999));
                tvBigSize.setTextColor(resources.getColor(R.color.c_999999));
                break;

            case R.id.tv_middleSize :
                tvMiddleSize.setTextColor(resources.getColor(R.color.c_000000));
                tvMiddleSize.setTypeface(Typeface.DEFAULT_BOLD);
                tvSmallSize.setTextColor(resources.getColor(R.color.c_999999));
                tvBigSize.setTextColor(resources.getColor(R.color.c_999999));
                break;

            case R.id.tv_bigSize :
                tvBigSize.setTextColor(resources.getColor(R.color.c_000000));
                tvBigSize.setTypeface(Typeface.DEFAULT_BOLD);
                tvSmallSize.setTextColor(resources.getColor(R.color.c_999999));
                tvMiddleSize.setTextColor(resources.getColor(R.color.c_999999));
                break;
        }
    }
}
