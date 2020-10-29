package com.datacvg.sempmobile.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.DisplayUtils;
import com.datacvg.sempmobile.baseandroid.utils.PLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-27
 * @Description : 折线图
 */
public class LineChart extends View {
    //图表标题
    private String graphTitle = "";
    //标题字体的大小
    private int graphTitleSize = 18;
    //标题的字体颜色
    private int graphTitleColor = Color.RED;
    //x轴名称
    private String xAxisName = "";
    //y轴名称
    private String yAxisName = "";
    //坐标轴字体颜色
    private int axisTextSize = 10;
    //坐标轴字体颜色
    private int axisTextColor = Color.parseColor("#E6E6E6");
    //x y坐标线条的颜色
    private int axisLineColor = Color.parseColor("#E6E6E6");
    //x，y坐标线的宽度
    private int axisLineWidth = 2;
    private Paint mPaint;
    private int screenWith, screenHeight;
    //视图的宽度
    private int width;
    //视图的高度
    private int height;
    //起点x坐标值
    private int originalX;
    //起点y坐标值
    private int originalY;
    //y轴等份划分
    private int axisDivideSizeY = 1 ;
    //柱状图宽度
    float cellWidth = 0f ;

    //标题距离x轴的距离
    private int titleMarginXaxis = 60;
    //x y轴刻度的高度
    private int xAxisScaleHeight = 5;
    //刻度的最大值
    private double maxValue;
    private double minValue;
    //y轴空留部分高度
    private int yMarign = 30;

    //柱状图数据
    private List<Double> columnList = new ArrayList<>();

    private List<String> xTitles = new ArrayList<>();
    //柱状图颜色
    private List<Integer> columnColors = new ArrayList<>();
    private float cellValue = 0f;
    private int ceil = 0;

    public LineChart(Context context) {
        this(context, null);
    }

    public LineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取屏幕的宽高
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        screenWith = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        initAttrs(context, attrs);
        initPaint();
    }

    /**
     * //获取自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HistogramView);
        graphTitle = array.getString(R.styleable.HistogramView_graphTitle);
        xAxisName = array.getString(R.styleable.HistogramView_xAxisName);
        yAxisName = array.getString(R.styleable.HistogramView_yAxisName);
        axisTextSize = array.getDimensionPixelSize(R.styleable.HistogramView_axisTextSize, sp2px(axisTextSize));
        axisTextColor = array.getColor(R.styleable.HistogramView_axisTextColor, axisTextColor);
        axisLineColor = array.getColor(R.styleable.HistogramView_axisLineColor, axisLineColor);
        graphTitleSize = array.getDimensionPixelSize(R.styleable.HistogramView_graphTitleSize, sp2px(graphTitleSize));
        graphTitleColor = array.getColor(R.styleable.HistogramView_graphTitleColor, graphTitleColor);
        axisLineWidth = (int) array.getDimension(R.styleable.HistogramView_axisLineWidth, dip2px(axisLineWidth));
        array.recycle();
    }

    /**
     * 初始化paint
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //x轴的起点位置
        originalX = dip2px(30);
        //视图的宽度  空间的宽度减去左边和右边的位置
        width = getMeasuredWidth() - dip2px(50) * 2;
        //y轴的起点位置 空间高度的2/3
        originalY = getMeasuredHeight() - dip2px(30);
        //图表显示的高度为空间高度的一半
        height = getMeasuredHeight() - 2 * dip2px(35);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制标题
        drawTitle(canvas);
        //绘制x轴
        drawXAxis(canvas);
        //绘制y轴
        drawYAxis(canvas);
        //绘制x轴刻度值
        drawXAxisScaleValue(canvas);
        //绘制y轴刻度
        drawYAxisScale(canvas);
        drawYAxisScaleValue(canvas);
        //绘制柱状图
        drawLine(canvas);
    }

    /**
     * 绘制柱状图
     *
     * @param canvas
     */
    protected void drawLine(Canvas canvas) {
        if (columnList != null && columnColors != null) {
            //根据最大值和高度计算比例
            float scale = (float) ((height - dip2px(yMarign)) / (maxValue - minValue));
            for (int i = 1; i < columnList.size(); i++) {
                mPaint.setColor(columnColors.get(i));
                float startX = (float) (originalX + dip2px(15) * i  + cellWidth * (i -1) + cellWidth/2);
                float endX = (float) (originalX + dip2px(15) * (i+1) + cellWidth * i + cellWidth/2);
                float startY = (float) (originalY - (columnList.get(i - 1) - minValue) * scale);
                float endY = (float) (originalY - (columnList.get(i) - minValue) * scale);
                canvas.drawLine(startX , startY, endX , endY, mPaint);
            }
        }
    }

    /**
     * 绘制y轴刻度值
     *
     * @param canvas
     */
    protected void drawYAxisScaleValue(Canvas canvas) {
        try {
            mPaint.setColor(axisTextColor);
            mPaint.setTextSize(axisTextSize);
            int cellHeight = (height - dip2px(yMarign)) / axisDivideSizeY;
            for (int i = 0; i < axisDivideSizeY + 1; i++) {
                if (i == 0) {
                    continue;
                }
                String s = new Double(minValue + ceil * i).intValue() + "";
                PLog.e(s + "," + minValue + ceil * i);
                float v = mPaint.measureText(s);
                canvas.drawText(s,
                        originalX - v - 10,
                        originalY - cellHeight * i + 10,
                        mPaint);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * 绘制y轴刻度
     *
     * @param canvas
     */
    protected void drawYAxisScale(Canvas canvas) {
        mPaint.setColor(axisLineColor);
        mPaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        float cellHeight = (height - dip2px(yMarign)) / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY; i++) {
            canvas.drawLine(originalX,
                    originalY - cellHeight * (i + 1),
                    originalX + width + dip2px(30),
                    originalY - cellHeight * (i + 1),
                    mPaint);
        }
        mPaint.setPathEffect(null);
    }

    /**
     * 绘制x轴刻度值
     *
     * @param canvas
     */
    protected void drawXAxisScaleValue(Canvas canvas) {
        int xTxtMargin = dip2px(15);
        /**
         * 没有数据集的时候
         */
        if (columnList.size() == 0){
            cellWidth = width ;
        }else{
            cellWidth = width - dip2px(30) * (columnList.size() + 1) ;
        }
        mPaint.setColor(axisTextColor);
        mPaint.setTextSize(axisTextSize);
        mPaint.setFakeBoldText(true);
        float cellWidth = width / (columnList.size() + 2);
        for (int i = 0; i < xTitles.size() + 1 ; i++) {
            canvas.save();
            if (i == 0) {
                continue;
            }
            String txt =xTitles.get(i - 1);
            //测量文字的宽度
            float txtWidth = mPaint.measureText(txt);
            canvas.rotate(-20
                    , originalX + dip2px(15) * i  + cellWidth * (i -1) + cellWidth/2- txtWidth/2- dip2px(10)
                    , originalY + xTxtMargin + txtWidth / 2);
            canvas.drawText(txt, originalX + dip2px(15) * i  + cellWidth * (i -1) - dip2px(10),
                    (float) (originalY + xTxtMargin *2),
                    mPaint);
            canvas.restore();
        }
    }

    /**
     * 绘制标题
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        if (!TextUtils.isEmpty(graphTitle)) {
            //绘制标题
            mPaint.setTextSize(graphTitleSize);
            mPaint.setColor(graphTitleColor);
            //设置文字粗体
            mPaint.setFakeBoldText(true);
            //获取文字的宽度
            float measureText = mPaint.measureText(graphTitle);
            canvas.drawText(
                    graphTitle,
                    getWidth() / 2 - measureText / 2,
                    originalY + dip2px(titleMarginXaxis),
                    mPaint
            );
        }
    }

    /**
     * 绘制y轴
     *
     * @param canvas
     */
    protected void drawYAxis(Canvas canvas) {
        mPaint.setColor(axisLineColor);
        mPaint.setStrokeWidth(axisLineWidth);
        canvas.drawLine(originalX, originalY, originalX, originalY - height, mPaint);
    }

    /**
     * 绘制x轴
     *
     * @param canvas
     */
    protected void drawXAxis(Canvas canvas) {
        mPaint.setColor(axisLineColor);
        mPaint.setStrokeWidth(axisLineWidth);
        canvas.drawLine(originalX, originalY, originalX + width +dip2px(30), originalY, mPaint);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 调用该方法进行图表的设置
     * @param chartXTitles
     * @param columnList 柱状图的数据
     * @param columnColors  颜色
     * @param axisDivideSizeY y轴显示的等份数
     */
    public void setColumnInfo(List<String> chartXTitles, List<Double> columnList
            , List<Integer> columnColors, int axisDivideSizeY) {
        this.columnList = columnList;
        this.columnColors = columnColors;
        this.axisDivideSizeY = axisDivideSizeY;
        xTitles = chartXTitles ;
        //获取刻度的最大值
        maxValue = Collections.max(columnList);
        minValue = Collections.min(columnList);
        cellValue = (float) ((maxValue - minValue)/ (axisDivideSizeY + 0f));
        //这里只处理的大于1时的绘制  小于等于1的绘制没有处理
        ceil = (int) Math.ceil(cellValue);
        minValue = Math.floor(minValue - ceil) > 0 ? Math.floor(minValue - ceil) : 0 ;
        maxValue = Math.ceil(maxValue + ceil);
        ceil = (int) Math.ceil((float) ((maxValue - minValue)/ (axisDivideSizeY + 0f)));
        minValue = maxValue - ceil * axisDivideSizeY ;
        PLog.e("TAG", "maxValue-->" + maxValue);
        invalidate();
    }
}
