package com.datacvg.dimp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public class CircleNumberView extends View {

    Paint paint;
    String number = "99";
    int padding = 10;
    float textSize = 15.0f;
    float radius;

    public void setNumber(String number) {
        this.number = number;
        invalidate();
        if(Integer.parseInt(number) > 0){
            setVisibility(VISIBLE);
        }
    }

    private void initView() {
        if (paint == null) {
            paint = new Paint();
            paint.setTextSize(DisplayUtils.sp2px(textSize));
            //如果需要透明度，透明度应该加在前两位
            paint.setColor(AndroidUtils.getContext().getResources().getColor(R.color.c_da3a16));
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
        }
        //文字所占的区域，用来确定文字的起始坐标
        Rect rect = null;
        if (rect == null) {
            rect = new Rect();
        }

        paint.getTextBounds("99+", 0, "99+".length(), rect);
        float viewWidth = rect.width();
        float viewHeight = rect.height();
        if (viewWidth >= viewHeight) {
            radius = viewWidth + padding;
        } else {
            radius = viewHeight + padding;
        }
    }

    private void initAtters(Context context, @Nullable AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleNumberView);
        textSize = array.getFloat(R.styleable.CircleNumberView_textSize, 12.0f);
        padding = array.getInt(R.styleable.CircleNumberView_textPadding, 10);

        number = array.getString(R.styleable.CircleNumberView_textNumber);
        if (TextUtils.isEmpty(number)) {
            number = "0";
            setVisibility(GONE);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(radius / 2.0f, radius / 2.0f, radius / 2.0f, paint);
        Paint paint1 = new Paint();
        paint1.setTextSize(DisplayUtils.sp2px(textSize));
        paint1.setColor(AndroidUtils.getContext().getResources().getColor(R.color.c_FFFFFF));
        paint1.setAntiAlias(true);
        paint1.setFilterBitmap(true);
        ;
        paint1.setTextAlign(Paint.Align.CENTER);
        Rect rect1 = null;
        if (rect1 == null) {
            rect1 = new Rect();
        }
        if (Integer.parseInt(number) > 0){
            setVisibility(VISIBLE);
        }
        if (Integer.parseInt(number) > 99) {
            paint1.getTextBounds("99+", 0, "99+".length(), rect1);
        } else {
            paint1.getTextBounds(number, 0, number.length(), rect1);
        }

        //为了让文字纵向居中，计算正确的Y值
        Paint.FontMetrics fontMetrics = paint1.getFontMetrics();
        float y = radius / 2.0f + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2.0f;

        float startX = radius / 2.0f - rect1.width() / 2.0f;
        startX = radius / 2.0f;
        if (Integer.parseInt(number) > 99) {
            canvas.drawText("99+", startX + getPaddingLeft(), y + getPaddingTop(), paint1);
        } else {
            canvas.drawText(number, startX + getPaddingLeft(), y + getPaddingTop(), paint1);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) radius, (int) radius);
    }

    public CircleNumberView(Context context) {
        super(context);
        initView();
    }

    public CircleNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAtters(context, attrs);
        initView();
    }

    public CircleNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtters(context, attrs);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAtters(context, attrs);
        initView();
    }
}
