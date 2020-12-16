package com.datacvg.dimp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.IndexTreeBean;
import com.datacvg.dimp.bean.IndexTreeListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-11
 * @Description :
 */
public class IndexTreeViewFlower extends ViewGroup {

    public enum IndexTreeType {
        INDEX_LARGE(1),
        INDEX_MIDDLE(2),
        INDEX_SMALL(3);

        private int value;

        IndexTreeType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        public static IndexTreeType of(int intValue) {
            switch (intValue) {
                case 1:
                    return INDEX_LARGE;
                case 2 :
                    return INDEX_MIDDLE ;
                default:
                    return INDEX_SMALL ;
            }
        }
    }

    /**
     * 最大宽高
     */
    private int mMaxWidthPX;
    private int mMaxHeightPX;

    /**
     * 纵向间隔与横向间隔
     */
    private int mMarginTop ;
    private int mPaddingLeft ;
    private int mMarginLeft ;

    /**
     *
     */
    private int mItemWithPx ;
    private int mItemHeightPx;

    private View mParentView ;
    private Path mPath ;
    private Paint mPaint ;

    private List<IndexTreeBean> parentBeans = new ArrayList<>();
    private List<IndexTreeBean> childBeans = new ArrayList<>();
    private OnClickChangeListener clickChangeListener ;

    public void setType(IndexTreeType type) {
        this.type = type;
        invalidate();
    }

    public void setClickChangeListener(OnClickChangeListener clickChangeListener) {
        this.clickChangeListener = clickChangeListener;
    }

    public void setEditStatus(boolean editStatus) {
        isEditStatus = editStatus;
    }

    /**
     * 是否处于编辑可创建状态,默认为false
     */
    private boolean isEditStatus = false ;

    private IndexTreeType type = IndexTreeType.of(1) ;

    public IndexTreeViewFlower(Context context) {
        this(context,null,0);
    }

    public IndexTreeViewFlower(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public IndexTreeViewFlower(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.reset();
        mPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.W4));
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        mPath.reset();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        final int childCount = getChildCount() ;
        for (int i = 0 ; i < childCount ; i++){
            View childView = getChildAt(i);
            childView.measure(widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        final int childCount = getChildCount();
        for(int count = 0 ; count < childCount ;count++){
            View childView = getChildAt(count);
            IndexTreeBean bean = (IndexTreeBean) childView.getTag();
            int parentLeft = bean.getRes_level()* mMarginLeft + mPaddingLeft;
            int parentTop = mMarginTop *(bean.getViewPosition() + 1) + mItemHeightPx * bean.getViewPosition() ;
            setChildViewFrame(childView,parentLeft,parentTop,mItemWithPx,mItemHeightPx);
        }
    }

    private void setChildViewFrame(View childView, int left, int top
            , int mItemWithPx, int mItemHeightPx) {
        childView.layout(left,top,left + mItemWithPx,top + mItemHeightPx);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawViewLeftLine(canvas);
    }

    /**
     * 绘制view左边阈值颜色线
     * @param canvas
     */
    private void drawViewLeftLine(Canvas canvas) {
        int count = getChildCount();
        for (int i = 0 ; i < count ; i ++){
            View childView = getChildAt(i);
            IndexTreeBean bean = (IndexTreeBean) childView.getTag();
            /**
             * 竖线
             */
            mPath.reset();
            mPaint.setColor(Color.parseColor(bean.getIndex_default_color()));
            mPath.moveTo(childView.getLeft(),childView.getTop());
            mPath.lineTo(childView.getLeft(),childView.getBottom());
            canvas.drawPath(mPath,mPaint);
            /**
             * 横线  去除根元素
             */
            if(bean.getRes_level() != 0){
                mPath.reset();
                mPaint.setColor(Color.parseColor(bean.getIndex_default_color()));
                mPath.moveTo(childView.getLeft() - getContext().getResources().getDimension(R.dimen.W40)
                        ,childView.getTop() + mItemHeightPx / 2);
                mPath.lineTo(childView.getLeft(),childView.getTop() + mItemHeightPx / 2);
                canvas.drawPath(mPath,mPaint);

                drawLinkParent(canvas,bean
                        ,(int)(childView.getLeft() - getContext().getResources().getDimension(R.dimen.W40))
                        ,childView.getTop() + mItemHeightPx / 2);
            }
        }
    }

    private void drawLinkParent(Canvas canvas ,IndexTreeBean bean, int endX, int endY) {
        int parentPosition = 0 ;
        mPath.reset();
        for (int i = 0 ; i < getChildCount() ; i ++ ){
            IndexTreeBean indexTreeBean = (IndexTreeBean) getChildAt(i).getTag();
            if(indexTreeBean.getI_res_id().equals(bean.getI_res_parentid())){
                mPaint.setColor(Color.parseColor(indexTreeBean.getIndex_default_color()));
                parentPosition = i ;
                break;
            }
        }
        View parentView = getChildAt(parentPosition);
        mPath.moveTo(parentView.getLeft()+mItemWithPx/2,parentView.getBottom());
        mPath.lineTo(endX,endY);
        canvas.drawPath(mPath,mPaint);
    }

    /**
     * 绘制指标树
     * @param bean 指标树结构
     * @param type  展示类型,大 1 中 2 小 3
     */
    public void drawIndexTree(IndexTreeListBean bean, int type ){
        recycleAllView() ;
        sortIndexTreeBean(bean);
        initDefaultValue(type) ;
        for (IndexTreeBean indexTreeBean : childBeans){
            initData(indexTreeBean,type);
        }
        invalidate();
    }

    /**
     * 入参bean做排序操作
     * @param beans
     */
    private void sortIndexTreeBean(List<IndexTreeBean> beans) {
        childBeans.clear();
        parentBeans.clear();
        /**
         * 找到最顶级层次结构
         */
        int level = -1 ;
        for (IndexTreeBean indexTreeBean : beans){
            if(level == -1 || level > indexTreeBean.getRes_level()){
                level = indexTreeBean.getRes_level() ;
            }
        }

        for (IndexTreeBean indexTreeBean : beans){
            if(indexTreeBean.getRes_level() == level){
                parentBeans.add(indexTreeBean);
            }
        }
        for (IndexTreeBean treeBean : parentBeans){
            treeBean.setRes_level(0);
            if (treeBean.getNodes() != null && treeBean.getNodes().size() > 0){
                getFinalChildBeans(treeBean);
            }else{
                childBeans.add(treeBean);
            }
        }

        /**
         * 找到最大的层级关系
         *      确定view最大宽度
         */
        int heightLevel = - 1 ;
        int position = 0 ;
        for (IndexTreeBean indexTreeBean : childBeans){
            indexTreeBean.setViewPosition(position);
            position ++ ;
            if (indexTreeBean.getRes_level() > heightLevel){
                heightLevel = indexTreeBean.getRes_level() ;
            }
        }
        mMaxWidthPX = Math.max(DisplayUtils.getWidth()
                ,(heightLevel - 1) * mMarginLeft + mItemWithPx);
        mMaxHeightPX = Math.max(DisplayUtils.getHeight(),childBeans.size() * mItemHeightPx + mMarginTop);
    }

    /**
     * 获得最后子元素
     */
    private void getFinalChildBeans(IndexTreeBean bean) {
        childBeans.add(bean);
        for (IndexTreeBean indexTreeBean : bean.getNodes()){
            indexTreeBean.setRes_level(bean.getRes_level() + 1);
            if (indexTreeBean.getNodes() != null && indexTreeBean.getNodes().size() > 0){
                getFinalChildBeans(indexTreeBean);
            }else{
                childBeans.add(indexTreeBean);
            }
        }
    }

    /**
     * 初始化相关参数配置
     *      后续扩展时可以根据大小图类型更改间距设置
     */
    private void initDefaultValue(int type) {
        mMarginTop = (int) getContext().getResources().getDimension(R.dimen.H20);
        mPaddingLeft = (int) getContext().getResources().getDimension(R.dimen.H30);
        switch (IndexTreeType.of(type)){
            case INDEX_LARGE:
                mItemWithPx = (int) getContext().getResources().getDimension(R.dimen.W360);
                mItemHeightPx = (int) getContext().getResources().getDimension(R.dimen.H260);
                break;

            case INDEX_MIDDLE:
                mItemWithPx = (int) getContext().getResources().getDimension(R.dimen.W360);
                mItemHeightPx = (int) getContext().getResources().getDimension(R.dimen.H130);
                break;

            case INDEX_SMALL:
                mItemWithPx = (int) getContext().getResources().getDimension(R.dimen.W360);
                mItemHeightPx = (int) getContext().getResources().getDimension(R.dimen.H60);
                break;
        }
        mMarginLeft = mItemWithPx / 2 + (int) getContext().getResources().getDimension(R.dimen.W40);
    }

    /**
     * 重新规整视图
     *     初始化所有参数
     */
    private void recycleAllView() {
        removeAllViews();
    }

    /**
     * 初始化指标树数据
     * @param bean
     * @param type
     */
    private void initData(IndexTreeBean bean, int type) {
        this.type = IndexTreeType.of(type) ;
        if(bean != null){
            switch (this.type){
                case INDEX_LARGE:
                    mParentView = createLargeViewAndBindValues(bean);
                    break;

                case INDEX_MIDDLE:
                    mParentView = createMiddleViewAndBindValues(bean);
                    break;

                default:
                    mParentView = createSmallViewAndBindValues(bean);
                    break;
            }
        }
        mParentView.setTag(bean);
    }

    /**
     * 最小化状态下视图展示以及绑定数据
     * @param bean
     */
    private View createSmallViewAndBindValues(IndexTreeBean bean) {
        View smallView = LayoutInflater.from(getContext())
                .inflate(R.layout.item_index_tree_small,this,false);
        TextView tvTitle = smallView.findViewById(R.id.tv_indexName);
        tvTitle.setText(bean.getName());
        CheckBox cbSelect = smallView.findViewById(R.id.cb_select);
        cbSelect.setVisibility(isEditStatus ? View.VISIBLE : View.GONE);
        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clickChangeListener.OnClickChange(b,bean);
            }
        });
        this.addView(smallView);
        return smallView ;
    }

    /**
     * 中等大小状态下展示的视图以及数据绑定
     * @param bean
     */
    private View createMiddleViewAndBindValues(IndexTreeBean bean) {
        View midView = LayoutInflater.from(getContext())
                .inflate(R.layout.item_index_tree_mid,this,false);
        TextView tvTitle = midView.findViewById(R.id.tv_indexName);
        tvTitle.setText(bean.getName());
        TextView tvValueName = midView.findViewById(R.id.tv_valueName);
        TextView tvValue = midView.findViewById(R.id.tv_value);
        if(bean.getVals() != null && bean.getVals().size() > 0){
            tvValueName.setText(bean.getVals().get(0).getValue_description());
            tvValue.setText(bean.getVals().get(0).getIndex_data()
                    + bean.getVals().get(0).getValue_unit());
            tvValue.setTextColor(Color.parseColor(bean.getIndex_default_color()));
        }
        CheckBox cbSelect = midView.findViewById(R.id.cb_select);
        cbSelect.setVisibility(isEditStatus ? View.VISIBLE : View.GONE);
        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clickChangeListener.OnClickChange(b,bean);
            }
        });
        this.addView(midView);
        return midView ;
    }

    /**
     * 最大化状态下视图以及视图展示的数据
     * @param bean
     */
    private View createLargeViewAndBindValues(IndexTreeBean bean) {
        View largeView = LayoutInflater.from(getContext())
                .inflate(R.layout.item_index_tree_big,this,false);
        TextView tvTitle = largeView.findViewById(R.id.tv_indexName);
        tvTitle.setText(bean.getName());
        TextView tvValueName = largeView.findViewById(R.id.tv_valueName);
        TextView tvValue = largeView.findViewById(R.id.tv_value);
        TextView tvValueNameOne = largeView.findViewById(R.id.tv_valueNameOne);
        TextView tvValueOne = largeView.findViewById(R.id.tv_valueOne) ;
        TextView tvValueNameTwo = largeView.findViewById(R.id.tv_valueNameTwo);
        TextView tvValueTwo = largeView.findViewById(R.id.tv_valueTwo) ;
        if(bean.getVals() != null && bean.getVals().size() > 0){
            tvValueName.setText(bean.getVals().get(0).getValue_description());
            tvValue.setText(bean.getVals().get(0).getIndex_data()
                    + bean.getVals().get(0).getValue_unit());
            tvValue.setTextColor(Color.parseColor(bean.getIndex_default_color()));
        }
        if(bean.getVals() != null && bean.getVals().size() > 1){
            tvValueNameOne.setText(bean.getVals().get(1).getValue_description());
            tvValueOne.setText(bean.getVals().get(1).getIndex_data()
                    + bean.getVals().get(1).getValue_unit());
            tvValueOne.setTextColor(Color.parseColor(bean.getIndex_default_color()));
        }
        if(bean.getVals() != null && bean.getVals().size() > 2){
            tvValueNameTwo.setText(bean.getVals().get(2).getValue_description());
            tvValueTwo.setText(bean.getVals().get(2).getIndex_data()
                    + bean.getVals().get(2).getValue_unit());
            tvValueTwo.setTextColor(Color.parseColor(bean.getIndex_default_color()));
        }
        CheckBox cbSelect = largeView.findViewById(R.id.cb_select);
        cbSelect.setVisibility(isEditStatus ? View.VISIBLE : View.GONE);
        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clickChangeListener.OnClickChange(b,bean);
            }
        });
        this.addView(largeView);
        return largeView;
    }

    public interface OnClickChangeListener{
        void OnClickChange(boolean isClick,IndexTreeBean bean);
    }
}
