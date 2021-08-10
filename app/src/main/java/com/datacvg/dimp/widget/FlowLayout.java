package com.datacvg.dimp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

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
        //为所有的标签childView计算宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取高的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //建议的高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //布局的宽度采用建议宽度（match_parent或者size），如果设置wrap_content也是match_parent的效果
        int width = MeasureSpec.getSize(widthMeasureSpec) ;

        int height ;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //其他情况下（AT_MOST、UNSPECIFIED）需要计算计算高度
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
                    if(widthSpace >= childW){
                        //如果剩余的宽度大于此标签的宽度，那就将此标签放到本行
                        widthSpace -= childW;
                    }else{
                        row ++;    //增加一行
                        //如果剩余的宽度不能摆放此标签，那就将此标签放入一行
                        widthSpace = width-childW;
                    }
                    //减去标签左右间距
                    widthSpace -= (view.getPaddingLeft() + view.getPaddingRight());
                }
                //由于每个标签的高度是相同的，所以直接获取第一个标签的高度即可
                int childH = getChildAt(0).getMeasuredHeight();
                //最终布局的高度=标签高度*行数+行距*(行数-1)
                height = (childH * row) + (paddingTop + paddingBottom) * (row-1);
            }
        }

        //设置测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row = 0;
        int right = 0;
        int botom;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childW = childView.getMeasuredWidth();
            int childH = childView.getMeasuredHeight();
            //右侧位置=本行已经占有的位置+当前标签的宽度
            right += childW;
            //底部位置=已经摆放的行数*（标签高度+行距）+当前标签高度
            botom = row * (childH + childView.getPaddingBottom()
                    + childView.getPaddingTop()) + childH;
            // 如果右侧位置已经超出布局右边缘，跳到下一行
            // if it can't drawing on a same line , skip to next line
            if (right > (r - childView.getPaddingLeft() - childView.getPaddingRight())){
                row++;
                right = childW;
                botom = row * (childH + childView.getPaddingBottom()
                        + childView.getPaddingTop()) + childH;
            }
            childView.layout(right - childW, botom - childH,right,botom);

            right += childView.getPaddingLeft() + childView.getPaddingRight();
        }
    }

}
