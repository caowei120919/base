package com.datacvg.dimp.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.IndexChartBean;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.RegEx;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class BulletChart extends View {
    private Paint mPaint;
    private float mHeight;
    private float mWidth;
    private Rect mLegendColorRect;
    private Rect mSamePeriodRect;
    private Rect mActualRect;
    private float mPlan;
    private float mSamePeriod;
    private float mActual;
    private float mYearRate;
    private float mMonthRate;
    private float mChartPlan;
    private float mChartSamePeriod;
    private float mChartActual;
    private int mDottedLineColor;
    private int mThickColor;
    private int mFineColor;
    private float mMaxValue;
    private float mUnitSize;
    private float mSize;
    private int mSamePeriodRectL;
    private int mSamePeriodRectT;
    private int mSamePeriodRectR;
    private int mSamePeriodRectB;
    private int mActualRectL;
    private int mActualRectT;
    private int mActualRectR;
    private int mActualRectB;
    private int mLegendColorRectL;
    private int mLegendColorRectT;
    private int mLegendColorRectR;
    private int mLegendColorRectB;
    private PointF mStartPoint;
    private PointF mEndPoint;
    private String mSign;
    private Bitmap upSign;
    private Bitmap downSign;
    private String mUnit;
    private float mUnitX;
    private float mUnitY;
    private boolean mDrawChartFlag;
    private List<String> mKeyList;
    @Nullable
    private Map resultMap;
    @NotNull
    public IndexChartBean bean;
    @Nullable
    private List<String> mValueList;
    private boolean mDrawFlag;
    private String mPlanText;
    private String mSameTermText;
    private String mActualText;
    private String mYearRateText;
    private String mMonthRateText;
    private Rect rect;
    private int mDrawChart;
    private static final int DEFAULT_DOTTED_LINE_COLOR = Color.rgb(153, 153, 153);
    private static final int DEFAULT_THICK_COLOR = Color.rgb(237, 237, 237);
    private static final int DEFAULT_FINE_COLOR = Color.rgb(135, 173, 250);

    public BulletChart(Context context) {
        super(context);
        initPaint();
    }

    public BulletChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public BulletChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public BulletChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint() {
        //画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true) ;
        mPaint.setStyle(Paint.Style.FILL);

        //设置默认颜色
        mDottedLineColor = DEFAULT_DOTTED_LINE_COLOR ;
        mThickColor = DEFAULT_THICK_COLOR ;
        mFineColor = DEFAULT_FINE_COLOR ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mDrawFlag) {
            return ;
        }
        super.onDraw(canvas) ;
        if (mDrawChartFlag) {
            getChartValues() ;
        }
        initPoints(mChartPlan, mChartSamePeriod, mChartActual) ;
        if (mDrawChart > 0) {
            //绘制单位
            drawUnit(canvas) ;
        }
        //绘制图例颜色以及同比环比箭头
        drawLegendColor(canvas) ;
        //绘制图例的文字
        drawLegendText(canvas) ;
        drawDefaultValue(canvas) ;
        //绘制子弹图
        drawChart(canvas) ;
    }

    private void drawChart(Canvas canvas) {
        if (mDrawChartFlag) {
            if (bean != null && bean.getChart_data() != null && bean.getChart_data().size() > 0) {
                for (IndexChartBean.ChartDataBean chartData : bean.getChart_data()){
                        if(chartData.getValue_type().equals("same_period")){
                            if(!TextUtils.isEmpty(chartData.getValue_data())){
                                //绘制同期条形图
                                mPaint.setColor(mThickColor) ;
                                if (mSamePeriod > 0) {
                                    canvas.drawRect(mSamePeriodRect, mPaint) ;
                                } else {
                                    mPaint.setStyle(Paint.Style.STROKE) ;
                                    mPaint.setStrokeWidth(5f);
                                    Path path = new Path() ;
                                    path.moveTo(mSamePeriodRectL, mSamePeriodRectB);
                                    path.lineTo(mSamePeriodRectR, mSamePeriodRectB);
                                    canvas.drawPath(path, mPaint);
                                }
                                mPaint.reset();
                            }
                            break ;
                        }
                    if (chartData.getValue_type().equals("actual")){
                        if(!TextUtils.isEmpty(chartData.getValue_data())){
                            mPaint.setColor(mFineColor);
                            //数值不为0直接画
                            if (mActual > 0) {
                                canvas.drawRect(mActualRect, mPaint);
                            } else {
                                mPaint.setStyle(Paint.Style.STROKE);
                                mPaint.setStrokeWidth(5f) ;
                                Path path = new Path() ;
                                path.moveTo(mActualRectL, mActualRectB);
                                path.lineTo(mActualRectR, mActualRectB);
                                canvas.drawPath(path, mPaint) ;
                            }
                            mPaint.reset();
                        }
                        break ;
                    }
                }

                for (IndexChartBean.ChartDataBean chartData : bean.getChart_data()){
                    if(chartData.getValue_type().equals("plan")){
                        if(!TextUtils.isEmpty(chartData.getValue_data())){
                            mPaint.setColor(mDottedLineColor);
                            mPaint.setPathEffect(new DashPathEffect(new float[]{10f, 10f}, 0f));
                            mPaint.setStyle(Paint.Style.STROKE);
                            mPaint.setStrokeWidth(5f);
                            mPaint.setAlpha(255);
                            Path path = new Path() ;
                            path.moveTo(mStartPoint.x, mStartPoint.y);
                            path.lineTo(mEndPoint.x, mEndPoint.y);
                            canvas.drawPath(path, mPaint);
                            mPaint.reset();
                        }
                        break ;
                    }
                }
            }
        }
    }

    private void drawDefaultValue(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.LEFT) ;
        mPaint.setTextSize(mWidth/10) ;
        mPaint.setColor(Color.parseColor(bean.getIndex_default_color()));
        canvas.drawText(bean.getIndex_data(),
                mWidth / 24 + mLegendColorRectR,
                mHeight * 1/4 ,
                mPaint);
        mPaint.reset();
    }

    private void drawLegendText(Canvas canvas) {
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(mSize) ;
        mPaint.setColor(DEFAULT_DOTTED_LINE_COLOR) ;
        if (mKeyList != null && mKeyList.size() > 0) {
            for (int i =0 ; i < mValueList.size() ; i++) {
                canvas.drawText(getAbbreviated(mKeyList.get(i), 1),
                        mWidth / 24 + mLegendColorRectR,
                        (mLegendColorRectT + mLegendColorRectB - mWidth * 3f * i / 16) / 2 + mSize / 3,
                        mPaint);
            }
        }
        mPaint.setTextAlign (Paint.Align.RIGHT);
        if (mValueList != null && mValueList.size() > 0) {
            for (int i =0 ; i < mValueList.size() ; i++) {
                if (!TextUtils.isEmpty(mValueList.get(i))) {
                    if (mKeyList.get(i) == mYearRateText || mKeyList.get(i) == mMonthRateText) {
                        canvas.drawText(percentFormat(mValueList.get(i)),
                        mWidth * 24 / 25,
                                (mLegendColorRectT + mLegendColorRectB - mWidth * 3f * i / 16) / 2 + mSize / 3,
                                mPaint);
                    } else {
                        canvas.drawText(getAbbreviated(mValueList.get(i), 2),
                                mWidth * 24 / 25,
                                (mLegendColorRectT + mLegendColorRectB - mWidth * 3f * i / 16) / 2 + mSize / 3,
                                mPaint);
                    }
                }
            }
        }
        mPaint.reset();
    }

    private String percentFormat(String value) {
        String formatValue = String.format("%.2f", Math.abs(java.lang.Double.valueOf(value)));
        return formatValue ;
    }

    private String getAbbreviated(String value, int flag) {
        String myText = value ;
        if (!TextUtils.isEmpty(value) && value.length() > 0) {
            float textWidth = mPaint.measureText(value) ;
            if (flag == 1) {
                int subIndex = mPaint.breakText(value, 0, value.length()
                        , true, 40f, null) ;
                if (value.length() > 2){
                    myText = value.substring(0, subIndex + 2) + "..." ;
                }
            } else if (flag == 2) {
                if (textWidth > 220) {
                    int subIndex = mPaint.breakText(value, 0, value.length()
                            , true, 48f, null) ;
                    if(subIndex > 4){
                        myText = value.substring(0, subIndex + 4) + "..." ;
                    }
                }
            }
        }
        return myText ;
    }

    private void drawLegendColor(Canvas canvas) {
        downSign = BitmapFactory.decodeResource(getResources(),R.mipmap.bullet_down);
        upSign = BitmapFactory.decodeResource(getResources(),R.mipmap.bullet_up);
        if (mKeyList != null && mKeyList.size() > 0) {
            for (int i =0 ; i < mKeyList.size() ;i ++) {
                //判断传入值是什么，对其配置相应的画笔
                if (mKeyList.get(i) == mPlanText) {
                    //绘制虚线的图例
                    mPaint.setColor(mDottedLineColor);
                    mPaint.setPathEffect(new DashPathEffect(new float[]{10f, 10f}, 0f));
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(5f) ;
                    mPaint.setAlpha(255);
                    Path path = new Path() ;
                    path.moveTo(mLegendColorRectL, mLegendColorRectT + (1 - 6 * i) * mWidth / 64);
                    path.lineTo(mLegendColorRectR, mLegendColorRectT + (1 - 6 * i) * mWidth / 64);
                    canvas.drawPath(path, mPaint);
                } else if (mKeyList.get(i) == mSameTermText) {
                    //绘制同期的图例
                    mPaint.setColor(mThickColor);
                    rect = new Rect(mLegendColorRectL,
                            Integer.valueOf((mLegendColorRectT - i * 3f * mWidth / 32) + ""),
                            mLegendColorRectR, Integer.valueOf((mLegendColorRectB - i * 3f * mWidth / 32) + ""));
                    canvas.drawRect(rect, mPaint);
                } else if (mKeyList.get(i) == mActualText) {
                    //绘制实际的图例
                    mPaint.setColor(mFineColor);
                    rect = new Rect(mLegendColorRectL,
                            Integer.valueOf((mLegendColorRectT - i * 3f * mWidth / 32) + ""),
                            mLegendColorRectR,
                            Integer.valueOf((mLegendColorRectB - i * 3f * mWidth / 32) + ""));
                    canvas.drawRect(rect, mPaint) ;
                } else if (mKeyList.get(i) == mYearRateText || mKeyList.get(i) == mMonthRateText) {
                    //绘制同比或者环比的向上或向下的标识箭头
                    if (!TextUtils.isEmpty(mValueList.get(i))) {
                        if (java.lang.Float.valueOf(mValueList.get(i)) < 0) {
                            rect = new Rect(Integer.valueOf((mSize * 13 / 4 + mHeight / 24 + mLegendColorRectR)+ ""),
                                    Integer.valueOf(((mLegendColorRectT + mLegendColorRectB - mWidth * i * 3f / 16) / 2 - 5 * mSize / 12) + ""),
                                    Integer.valueOf((mSize * 31 / 8 + mHeight / 24 + mLegendColorRectR) + ""),
                                    Integer.valueOf(((mLegendColorRectT + mLegendColorRectB - mWidth * i * 3f / 16) / 2 + mSize * 11 / 24) + "")) ;
                            canvas.drawBitmap(downSign, new Rect(0, 0, downSign.getWidth()
                                    , downSign.getHeight()), rect, null);
                        } else if (java.lang.Float.valueOf(mValueList.get(i)) > 0) {
                            rect = new Rect(Integer.valueOf((mSize * 13 / 4 + mHeight / 24 + mLegendColorRectR) + ""),
                                    Integer.valueOf(((mLegendColorRectT + mLegendColorRectB - mWidth * i * 3f / 16) / 2 - 5 * mSize / 12) + ""),
                                    Integer.valueOf((mSize * 31 / 8 + mHeight / 24 + mLegendColorRectR) + ""),
                                    Integer.valueOf(((mLegendColorRectT + mLegendColorRectB - mWidth * i * 3f / 16) / 2 + mSize * 11 / 24) + "")) ;
                            canvas.drawBitmap(upSign,new Rect(0, 0, upSign.getWidth(), upSign.getHeight()), rect, null);
                        } else if (java.lang.Float.valueOf(mValueList.get(i)) == 0f) {
                            return ;
                        }
                    }
                }
                mPaint.reset() ;
            }
        }
        upSign.recycle();
        downSign.recycle();
        upSign = null ;
        downSign = null ;
    }

    private void drawUnit(Canvas canvas) {
        mPaint.setTextSize(mUnitSize);
        mPaint.setColor(DEFAULT_DOTTED_LINE_COLOR) ;
        mPaint.setTextAlign(Paint.Align.RIGHT) ;
        canvas.drawText(mUnit, mUnitX - 50, mUnitY, mPaint) ;
        mPaint.reset();
    }

    private void initPoints(float mChartPlan, float mChartSamePeriod, float mChartActual) {
//实际矩形颜色标识坐标
        mLegendColorRectL = (int) (mWidth / 2);
        mLegendColorRectT = (int) (mHeight * 19 / 20 - mWidth / 16) ;
        mLegendColorRectR = (int) (mLegendColorRectL + mWidth / 32);
        mLegendColorRectB = (int) (mHeight * 19 / 20 - mWidth / 32);
        mLegendColorRect = new Rect(mLegendColorRectL, mLegendColorRectT
                , mLegendColorRectR, mLegendColorRectB) ;

        //单位坐标
        mUnitX = mWidth ;
        mUnitY = mHeight * 99 / 100 ;

        //计算粗条状图矩形坐标
        mSamePeriodRectL = (int) (mWidth * 3 / 16);
        mSamePeriodRectT = (int) (mHeight * 99 / 100 - mChartSamePeriod);
        mSamePeriodRectR = (int) (mWidth * 11 / 32);
        mSamePeriodRectB = (int) (mHeight * 99 / 100);
        mSamePeriodRect = new Rect(mSamePeriodRectL, mSamePeriodRectT
                , mSamePeriodRectR, mSamePeriodRectB) ;

        //计算细条状图矩形坐标
        mActualRectL = (int) (mWidth * 29 / 128);
        mActualRectT = (int) (mHeight * 99 / 100 - mChartActual);
        mActualRectR = (int) (mWidth * 39 / 128);
        mActualRectB = mSamePeriodRectB ;
        mActualRect = new Rect(mActualRectL, mActualRectT, mActualRectR, mActualRectB) ;

        //虚线图首尾端点坐标
        mStartPoint = new PointF() ;
        mEndPoint = new PointF() ;

        mStartPoint.x = mWidth * 21 / 128 ;
        mStartPoint.y = mHeight * 99 / 100 - mChartPlan ;
        mEndPoint.x = mWidth * 47 / 128 ;
        mEndPoint.y = mHeight * 99 / 100 - mChartPlan ;
    }

    private void getChartValues() {
        mMaxValue = mHeight * 19 / 20 ;
        Boolean hasActual = false ;
        Boolean hasPlan = false ;
        Boolean hasPeriod = false ;
        for (IndexChartBean.ChartDataBean chartBean : bean.getChart_data()){
            if(chartBean.getValue_type().equals("actual")){
                hasActual = true ;
                mPlan = Float.valueOf(chartBean.getValue_data()) ;
            }

            if(chartBean.getValue_type().equals("same_period")){
                mSamePeriod = Float.valueOf(chartBean.getValue_data());
                hasPeriod = true ;
            }

            if(chartBean.getValue_type().equals("plan")){
                mActual = Float.valueOf(chartBean.getValue_data()) ;
                hasPlan = true ;
            }

        }
        if (!hasActual && !hasPlan && !hasPeriod){
            if ((mPlan >= mSamePeriod) && (mPlan >= mActual)) {
                mChartPlan = mMaxValue ;
                mChartSamePeriod = mMaxValue * mSamePeriod / mPlan ;
                mChartActual = mMaxValue * mActual / mPlan ;
            } else if ((mSamePeriod >= mPlan) && (mSamePeriod >= mActual)) {
                mChartSamePeriod = mMaxValue ;
                mChartPlan = mMaxValue * mPlan / mSamePeriod ;
                mChartActual = mMaxValue * mActual / mSamePeriod ;
            } else if ((mActual >= mPlan) && (mActual >= mSamePeriod)) {
                mChartActual = mMaxValue ;
                mChartPlan = mMaxValue * mPlan / mActual ;
                mChartSamePeriod = mMaxValue * mSamePeriod / mActual ;
            }
        }else{
            if(hasPlan && hasPeriod){
                if (mPlan <= mSamePeriod) {
                    mChartPlan = mMaxValue * mPlan / mSamePeriod ;
                    mChartSamePeriod = mMaxValue ;
                } else {
                    mChartPlan = mMaxValue ;
                    mChartSamePeriod = mMaxValue * mSamePeriod / mPlan ;
                }
            }
            if(hasActual && hasPeriod){
                if (mSamePeriod <= mActual) {
                    mChartSamePeriod = mMaxValue * mSamePeriod / mActual ;
                    mChartActual = mMaxValue ;
                } else {
                    mChartSamePeriod = mMaxValue ;
                    mChartActual = mMaxValue * mActual / mSamePeriod ;
                }
            }
            if(hasActual && hasPlan){
                if (mPlan <= mActual) {
                    mChartPlan = mMaxValue * mPlan / mActual ;
                    mChartActual = mMaxValue ;
                } else {
                    mChartPlan = mMaxValue ;
                    mChartActual = mMaxValue * mActual / mPlan ;
                }
            }
        }
    }

    /**
     * 传入单位
     *
     * @param mUnit
     */
    public void setmUnit(String mUnit) {
        this.mUnit = mUnit ;
    }

    public void setValues(IndexChartBean drawType) {
        mChartPlan = 0f;
        mChartActual = 0f;
        mChartSamePeriod = 0f;
        mDrawChartFlag = false;
        mDrawFlag = false;
        mPlanText = getResources().getString(R.string.plan);
        mSameTermText = getResources().getString(R.string.same_term);
        mActualText = getResources().getString(R.string.actual);
        mYearRateText = getResources().getString(R.string.year_on_year);
        mMonthRateText = getResources().getString(R.string.month_on_month);
        if (drawType == null) {
            return ;
        } else {
            bean = drawType ;
            mKeyList = new ArrayList() ;
            mValueList = new ArrayList() ;
            resultMap = new HashMap(3);
            mDrawChart = 0 ;
            for (IndexChartBean.ChartDataBean chartData : drawType.getChart_data()){
                if(chartData.getValue_type().equals("actual")){
                    mActualText = chartData.getValue_name();
                    mSign = mActualText;
                    mKeyList.add(mSign) ;
                    mValueList.add(chartData.getValue_data()) ;
                    mDrawFlag = true ;
                    mFineColor =Color.parseColor(drawType.getIndex_default_color()) ;
                    if (!TextUtils.isEmpty(chartData.getValue_data())) {
                        mDrawChart++ ;
                        mActual = java.lang.Float.parseFloat(chartData.getValue_data()
                                .replaceAll(",", ""));
                    }
                }

                if(chartData.getValue_type().equals("same_period")){
                    mSameTermText = chartData.getValue_name();
                    mSign = mSameTermText;
                    mKeyList.add(mSign);
                    mValueList.add(chartData.getValue_data());
                    mDrawFlag = true;
                    if (!TextUtils.isEmpty(chartData.getValue_data())) {
                        mDrawChart++ ;
                        mSamePeriod = java.lang.Float.parseFloat(chartData.getValue_data()
                                .replaceAll(",", "")) ;
                    }
                }

                if(chartData.getValue_type().equals("plan")){
                    mPlanText = chartData.getValue_name();
                    mSign = mPlanText;
                    mKeyList.add(mSign);
                    mValueList.add(chartData.getValue_data());
                    mDrawFlag = true ;
                    if (!TextUtils.isEmpty(chartData.getValue_data())) {
                        mDrawChart++ ;
                        mSamePeriod = java.lang.Float.parseFloat(chartData.getValue_data()
                                .replaceAll(",", "")) ;
                    }
                }

                if(chartData.getValue_type().equals("ring_ratio")){
                    mMonthRateText = chartData.getValue_name() ;
                    mSign = mMonthRateText;
                    mKeyList.add(mSign) ;
                    mValueList.add(chartData.getValue_data()) ;
                    mDrawFlag = true ;
                    if (!TextUtils.isEmpty(chartData.getValue_data())) {
                        mDrawChart++ ;
                        mSamePeriod = java.lang.Float.parseFloat(chartData.getValue_data()
                                .replaceAll(",", "")) ;
                    }
                }

                if(chartData.getValue_type().equals("year_on_year")){
                    mYearRateText = chartData.getValue_name() ;
                    mSign = mYearRateText ;
                    mKeyList.add(mSign) ;
                    mValueList.add(chartData.getValue_data()) ;
                    mDrawFlag = true ;
                    if (!TextUtils.isEmpty(chartData.getValue_data())) {
                        mDrawChart++ ;
                        mSamePeriod = java.lang.Float.parseFloat(chartData.getValue_data()
                                .replaceAll(",", ""));
                    }
                }
            }
            if (mDrawChart >= 2 && mDrawChart <= 3) {
                if (!(mPlan <= 0 && mSamePeriod <= 0 && mActual <= 0)) {
                    mDrawChartFlag = true ;
                }
            }
        }
        invalidate() ;
    }
}
