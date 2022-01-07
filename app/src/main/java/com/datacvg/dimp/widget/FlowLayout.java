package com.datacvg.dimp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.PLog;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: FlowLayout
 * Author: 曹伟
 * Date: 2019/10/11 16:58
 * Description:
 */

public class FlowLayout extends ViewGroup {
    private int mScreenWidth;
    private int horizontalSpace, verticalSpace;
    private float mDensity;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mDensity = context.getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //确定此容器的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量子View的宽高
        int childCount = getChildCount();
        View child = null;
        //子view摆放的起始位置
        int left = getPaddingLeft();
        //一行view中将最大的高度存于此变量，用于子view进行换行时高度的计算
        int maxHeightInLine = 0;
        //存储所有行的高度相加，用于确定此容器的高度
        int allHeight = 0;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            //测量子View宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //两两对比，取得一行中最大的高度
            if (child.getMeasuredHeight() + child.getPaddingTop() + child.getPaddingBottom() > maxHeightInLine) {
                maxHeightInLine = child.getMeasuredHeight() + child.getPaddingTop() + child.getPaddingBottom();
            }
            left += child.getMeasuredWidth() + dip2px(horizontalSpace) + child.getPaddingLeft() + child.getPaddingRight();
            if (left >= widthSize - getPaddingRight() - getPaddingLeft()) {
                left = getPaddingLeft();
                //累积行的总高度
                allHeight += maxHeightInLine + dip2px(verticalSpace);
                maxHeightInLine = 0;
            }
        }
        allHeight += maxHeightInLine;

        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = mScreenWidth;
        }

        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = allHeight + getPaddingBottom() + getPaddingTop();
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //摆放子view
            View child = null;
            //初始子view摆放的左上位置
            int left = getPaddingLeft();
            int top = getPaddingTop();
            //一行view中将最大的高度存于此变量，用于子view进行换行时高度的计算
            int maxHeightInLine = 0;
            for (int i = 0, len = getChildCount(); i < len; i++) {
                child = getChildAt(i);
                //从第二个子view开始算起
                //因为第一个子view默认从头开始摆放
                if (i > 0) {
                    //两两对比，取得一行中最大的高度
                    if (getChildAt(i - 1).getMeasuredHeight() > maxHeightInLine) {
                        maxHeightInLine = getChildAt(i - 1).getMeasuredHeight();
                    }
                    //当前子view的起始left为 上一个子view的宽度+水平间距
                    left += getChildAt(i - 1).getMeasuredWidth() + dip2px(horizontalSpace);
                    if (left + child.getMeasuredWidth() >= getWidth() - getPaddingRight() - getPaddingLeft()) {
                        left = getPaddingLeft();
                        top += maxHeightInLine + dip2px(verticalSpace);
                        maxHeightInLine = 0;
                    }
                }
                //摆放子view
                child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            }
        }
    }

    /**
     * dp转为px
     *
     * @param dpValue
     * @return
     */
    private int dip2px(float dpValue) {
        return (int) (dpValue * mDensity + 0.5f);
    }

    /**
     * 设置子view间的水平间距 单位dp
     *
     * @param horizontalSpace
     */
    public void setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
    }

    /**
     * 设置子view间的垂直间距 单位dp
     *
     * @param verticalSpace
     */
    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }
}
