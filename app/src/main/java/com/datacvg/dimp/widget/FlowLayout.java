package com.datacvg.dimp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.datacvg.dimp.R;

/**
 * FileName: FlowLayout
 * Author: 曹伟
 * Date: 2019/10/11 16:58
 * Description:
 */

public class FlowLayout extends ViewGroup {
    private int paddingTop = 0 ;
    private int paddingBottom = 0 ;

    public FlowLayout(Context context) {
        this(context, null);
    }
    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) ;

        int height ;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int childCount = getChildCount();
            if(childCount<=0){
                height = 0;
            }else{
                int row = 1;
                int widthSpace = width;
                for(int i = 0;i<childCount; i++){
                    View view = getChildAt(i);
                    //获取标签宽度
                    int childW = view.getMeasuredWidth();
                    paddingBottom = view.getPaddingBottom() ;
                    paddingTop = view.getPaddingTop() ;
                    if(widthSpace >= childW ){
                        widthSpace -= childW;
                    }else{
                        row ++;
                        widthSpace = width-childW;
                    }
                    //减去标签左右间距
                    widthSpace -= (view.getPaddingLeft() + view.getPaddingRight());
                }
                int childH = getChildAt(0).getMeasuredHeight();
                height = (childH * row) + (paddingTop + paddingBottom) * (row-1);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row = 0;
        int right = 0;
        int bottom;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childW = childView.getMeasuredWidth();
            int childH = childView.getMeasuredHeight();
            right += childW;
            bottom = row * (childH + childView.getPaddingBottom()
                    + childView.getPaddingTop()) + childH;
            if (right > (r - childView.getPaddingLeft() - childView.getPaddingRight())){
                row++;
                right = childW;
                bottom = row * (childH + childView.getPaddingBottom()
                        + childView.getPaddingTop()) + childH;
            }
            childView.layout(right - childW, bottom - childH,right,bottom);
            right += childView.getPaddingLeft() + childView.getPaddingRight();
        }
    }

}
