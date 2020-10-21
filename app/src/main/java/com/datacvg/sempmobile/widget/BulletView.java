package com.datacvg.sempmobile.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-21
 * @Description : 子弹图
 */
public class BulletView extends View {

    private Paint mChartPain = null ;
    private int mHeight = 0 ;
    private int mWidth = 0 ;

    /**
     * 虚线(计划)颜色、粗条状图(同期)颜色、细条状图(实际)颜色
     */
    private int mPlanColor = 0 ;


    public BulletView(Context context) {
        super(context);
    }

    public BulletView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BulletView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
