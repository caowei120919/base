package com.datacvg.dimp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.PLog;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-01-12
 * @Description :
 */
public class VerticalProgressBar extends View {
    private Paint paint;
    private int progress;
    private int width;
    private int height;

    public VerticalProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;
        height = getMeasuredHeight() - 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(0, 0 , width, progress / 100f * height,
                paint);
        PLog.e(height + "........." + progress / 100f * height + "");
        super.onDraw(canvas);
    }

    /**
     * 拿到文字宽度
     * @param str 传进来的字符串
     * return 宽度
     */
    private int getTextWidth(String str) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    /** 设置progressbar进度 */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }
}
