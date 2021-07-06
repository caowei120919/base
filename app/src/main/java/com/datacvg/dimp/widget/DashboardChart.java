package com.datacvg.dimp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.datacvg.dimp.bean.Arc;
import com.datacvg.dimp.bean.IndexChartBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-05-31
 * @Description :
 */
public class DashboardChart extends View {
    private int bgcolor = Color.RED ;
    /**
     * 画笔
     */
    private Paint mPaint = null ;
    /**
     * 高度
     */
    private int mHeight = 0 ;
    /**
     * 宽度
     */
    private int mWidth = 0 ;
    /**
     * 半径
     */
    private float mRadius = 0;
    /**
     * 圆点X值
     */
    private float mPointX = 0 ;
    /**
     * 圆点Y值
     */
    private float mPointY = 0 ;
    /**
     * y值总数
     */
    private float mSum = 0f ;
    /**
     * 最大值
     */
    private float mMax = 0f ;
    /**
     * 最小值
     */
    private float mMin = 0f ;
    /**
     * 指针值
     */
    private float mValue = 0f ;
    /**
     * 开始时间
     */
    private String mStartDate = null ;
    /**
     * 结束时间
     */
    private String mEndDate = null ;
    /**
     * 时间进度条
     */
    private float mTimeProgress = 0 ;
    /**
     * color
     */
    private List<Integer> mColors = new ArrayList<Integer>() ;
    /**
     * Arc放入的是每个扇形的起始角度，与结束角度
     */
    private List<Arc> mArcs = new ArrayList<Arc>() ;
    /**
     * 矩形的左边位置
     */
    private float mLeft = 0 ;
    /**
     * 矩形的右边的位置
     */
    private float mRight = 0 ;
    /**
     * 矩形的顶边位置
     */
    private float mTop = 0 ;
    /**
     * 矩形的底边位置
     */
    private float mBottom = 0 ;
    /**
     * 圆和view的距离
     */
    private float PADDING = 80 ;
    /**
     * 文字缩放比例
     */
    private float mTextScale = 0.03f ;
    /**
     * 文字画笔
     */
    private Paint mTextPaint = null ;
    /**
     * 文字大小
     */
    private float mTextSize = 0 ;
    /**
     * 1弧度代表的值
     */
    private float mDegreeScale = 0 ;
    private List<IndexChartBean.ThresholdArrBean> mThresholdArrBeans = new ArrayList<IndexChartBean.ThresholdArrBean>() ;

    private int mBigCirColor  = 0 ;

    public DashboardChart(Context context) {
        super(context);
    }

    public DashboardChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DashboardChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        initView();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void initView() {
        mHeight = getHeight() ;
        mWidth = getWidth() ;
        mPointX = (mWidth / 2) ;
        mPointY = (mHeight / 2 + 60) ;
        mLeft = ((mWidth - mHeight) / 2 + PADDING) ;
        mRight = ((mWidth - mHeight) / 2 + mHeight - PADDING) ;
        mTop = PADDING ;
        mBottom = (mHeight - PADDING) ;
        mRadius = (mRight - mLeft) / 2 ;
        mTextSize = mTextScale * mWidth ;
        initPaint() ;
    }

    private void initPaint() {
        mPaint = new Paint() ;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(40f);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(22f);
        mTextPaint = new Paint() ;
        mTextPaint.setColor(Color.parseColor("#999999"));
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //底层弧形
        drawOval(canvas);
        //刻度
        drawLine(canvas);
        //绘制刻度值
        drawLineValue(canvas);
        //画圆心
        drawCyclePoint(canvas);
        //画指针值
        drawValue(canvas);
        //画指针
        drawPointer(canvas);
        if (mStartDate == null) {
            return;
        }
        //画底层块
        drawTimeLine(canvas);
    }

    private void drawTimeLine(Canvas canvas) {
        float lineoffset = 70f ;
        float blankHeight = mBottom - mPointY ;
        float cycleRaduis = 20f ;
        int cycleColor = Color.parseColor("#52D6B5") ;
        mPaint.setColor(cycleColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mLeft - lineoffset, mBottom - blankHeight / 2, cycleRaduis, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mLeft - lineoffset, mBottom - blankHeight / 2, 10f, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mLeft - lineoffset + cycleRaduis - 5, mBottom - blankHeight / 2 - 10f,
                mRight + lineoffset - cycleRaduis + 5, mBottom - blankHeight / 2 + 10, mPaint);
        canvas.drawCircle(mRight + lineoffset, mBottom - blankHeight / 2, cycleRaduis, mPaint);
        mPaint.setColor(Color.parseColor("#DBE5E7"));
        canvas.drawCircle(mRight + lineoffset, mBottom - blankHeight / 2, 10f, mPaint);
        //画滑块
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(cycleColor);
        float slideLength = mRight - mLeft + cycleRaduis - 5 ;
        float valueLength = slideLength * (1 - mTimeProgress) ;
        canvas.drawRect(mLeft - lineoffset + cycleRaduis - 5, mBottom - blankHeight / 2 - 10f,
                mRight + lineoffset - valueLength, mBottom - blankHeight / 2 + 10, mPaint);
        canvas.drawCircle(mRight + lineoffset - valueLength, mBottom - blankHeight / 2, cycleRaduis, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mRight - valueLength + lineoffset, mBottom - blankHeight / 2, 10f, mPaint);

        //画滑块值
        String startValue = mStartDate ;
        String endValue = mEndDate ;
        mTextPaint.setTextSize(mTextSize * 1.3f);
        float startWidth = mTextPaint.measureText(startValue);
        float endWidth = mTextPaint.measureText(endValue);
        canvas.drawText(startValue, mLeft - lineoffset - startWidth / 2
                , mBottom - blankHeight / 2 + mTextSize * 2, mTextPaint);
        canvas.drawText(endValue, mRight + lineoffset - endWidth / 2
                , mBottom - blankHeight / 2 + mTextSize * 2, mTextPaint);
    }

    private void drawPointer(Canvas canvas) {
        float valueDegrees = (mValue - mMin) * mDegreeScale ;
        canvas.rotate(-90 + valueDegrees, mPointX, mPointY);
        canvas.drawLine(mPointX, mPointY - mRadius + 40, mPointX, mPointY, mPaint);
        canvas.restore();
    }

    private void drawValue(Canvas canvas) {
        String value = mValue + "";
        mTextPaint.setTextSize(mTextSize * 2.5f );
        mTextPaint.setColor(bgcolor);
        float valueWidth = mTextPaint.measureText(value);
        canvas.drawText(value, mPointX - valueWidth / 2
                , mPointY - mRadius / 2 + mTextSize, mTextPaint);
    }

    private void drawCyclePoint(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10f);
        canvas.drawCircle(mPointX, mPointY, 20f, mPaint) ;
        canvas.save() ;
    }

    private void drawLineValue(Canvas canvas) {
        mTextPaint.setColor(Color.parseColor("#999999"));
        for (int i = 0 ; i < 11 ; i++) {
            mPaint.setStrokeWidth(5f);
            String data = String.format("%.2f", (mMax - mMin) / 10 * i + mMin) ;
            float textWidth = mTextPaint.measureText(data);
            //刻度值的位置是半径再加上一定的偏移量100
            float r = mRadius + 100 ;
            double sin = Math.sin(Math.toRadians((90 + 18f * i)));
            double cos = Math.cos(Math.toRadians((90 + 18f * i)));
            double offsetx = (r * sin);
            double offsety = (r * cos);
            double startX = mPointX - offsetx - textWidth / 2 ;
            double startY = mPointY + offsety + 15f ;
            canvas.drawText(data, Float.valueOf(startX + ""), Float.valueOf(startY + ""), mTextPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        canvas.rotate(-90f, mPointX, mPointY);
        mPaint.setColor(Color.parseColor("#BCBCBC")) ;
        mTextPaint.setTextSize(mTextSize);
        for (int i = 0 ; i < 51 ; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(5f);
                canvas.drawLine(mPointX, mPointY - mRadius - 30f, mPointX
                        , mPointY - mRadius - 60f, mPaint);
            } else {
                mPaint.setStrokeWidth(3f);
                canvas.drawLine(mPointX, mPointY - mRadius - 30f
                        , mPointX, mPointY - mRadius - 40f, mPaint) ;
            }
            canvas.rotate(3.6f, mPointX, mPointY) ;
        }
        canvas.restore() ;
    }

    private void drawOval(Canvas canvas) {
        RectF oval = new RectF(mLeft, mTop + 60, mRight, mBottom + 60) ;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40f);
        mPaint.setColor(Color.parseColor("#999999"));
        //底部弧形
        if (mColors != null && mColors.size() > 0) {
            mPaint.setColor(mColors.get(0));
        }
        //从180度开始绘制，绘制180度
        canvas.drawArc(oval, 180f, 180f, false, mPaint);
        //数据弧形
        for (int i = 1 ; i < mArcs.size(); i++) {
            if (mColors != null && mColors.size() > 1) {
                mPaint.setColor(mColors.get(i));
            }
            canvas.drawArc(oval, mArcs.get(i).getStartAngle() + 180
                    , mArcs.get(i).getEndAngle(), false, mPaint);
        }
        canvas.save();
    }

    private Float toFloat(String src)  {
        if (TextUtils.isEmpty(src)) {
            return 0f ;
        } else {
            return java.lang.Float.parseFloat(src);
        }
    }

    /**
     * 绘制图
     */
    public void drawData(IndexChartBean chartBean) {
        if(chartBean == null){
            mMin = 0f ;
            mMax = 0f ;
            mValue = 0f ;
            mColors.clear() ;
            invalidate() ;
            return ;
        }
        float max =  getMax(chartBean) ;
        float min = getMin(chartBean) ;
        /**
         * 中间最好
         */
        if (Integer.valueOf(chartBean.getIndex_threshold_type()) == 3){
            float midRight = toFloat(chartBean.getIndex_threshold_value());
            if(Math.abs(max - midRight) > Math.abs(min - midRight)){
                mMax = max;
                mMin = midRight - Math.abs(max - midRight);
            }else{
                mMin = min;
                mMax = midRight + Math.abs(min - midRight);
            }
        }else{
            mMin = min;
            mMax = max;
        }
        if(mMax > 0 && mMin > 0){
            mMin = toFloat((mMin - mMax * 0.2) + "");
            mMax = toFloat((max * 1.2) + "") ;
        }else if(mMin < 0 && mMax < 0){
            mMin = toFloat((mMin - Math.abs(max * 0.2)) + "");
            mMax = toFloat(((max * 0.8)) + "") ;
        }else if(mMax == 0f || mMax == 0f ){
            if(mMax == 0f){
                mMax = toFloat((mMax + Math.abs(min * 0.2)) + "") ;
                mMin = toFloat(((min *1.2)) + "") ;
            }else{
                mMin = toFloat((mMin - (max * 0.2)) + "") ;
                mMax = toFloat(((max *1.2)) + "") ;
            }
        }else{
            mMin = toFloat((mMin - (mMax * 0.2)) + "") ;
            mMax = toFloat(((max * 1.2)) + "") ;
        }
        /**
         * 计算指针指示位置
         */
        double length = 0.0;
        if (TextUtils.isEmpty(chartBean.getIndex_data())){
            length = 100.0;
        }else{
            length = java.lang.Double.valueOf(chartBean.getIndex_data());
        }
        double percent = (length - mMin) / (mMax - mMin);
        bgcolor = Color.parseColor(chartBean.getIndex_default_color());
        if (percent > 1) {
            percent = 1.0;
        } else if (percent < 0) {
            percent = 0.0;
        }
        mTimeProgress = toFloat(percent+"");

        mArcs.clear();
        if (TextUtils.isEmpty(chartBean.getIndex_data())){
            mValue = 0f;
        }else{
            mValue = java.lang.Float.valueOf(chartBean.getIndex_data());
        }
        mSum = mMax - mMin;
        if(Integer.valueOf(chartBean.getIndex_threshold_type()) == 1){    //越大越好
            forBig(chartBean);
        }else if(Integer.valueOf(chartBean.getIndex_threshold_type()) == 2){  //越小越好
            forSmall(chartBean);
        }else{  //中间最好
            forMid(chartBean);
        }
        invalidate();
    }

    /**
     * 中间最好
     */
    private void forMid(IndexChartBean chartBean) {
        mThresholdArrBeans = forAscMid(chartBean) ;
        mColors.clear() ;
        if(chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0){
            mColors.add(Color.parseColor(chartBean.getColorArr().get(mBigCirColor)));
        }else{
            mColors.add(Color.rgb(245,245,245));
        }
        mDegreeScale = 180f / mSum;
        Arc arc = new Arc();
        arc.setStartAngle(0 * mDegreeScale);
        arc.setEndAngle(180f);
        mArcs.add(arc);
        int index = 1 ;
        for (int i = 1;i< mThresholdArrBeans.size() + 1;i++){
            mColors.add(Color.parseColor(chartBean.getColorArr().get(Integer
                    .valueOf(mThresholdArrBeans.get(i -1).getType()) - 1)));
            /**
             * 左右两端切断
             */
            if(toFloat(mThresholdArrBeans.get(i-1).getScaleEnd()) < toFloat(mThresholdArrBeans.get(i-1).getScaleStart())){
                Arc arc1 = new Arc();
                arc1.setStartAngle(0f);
                arc1.setEndAngle((toFloat(mThresholdArrBeans.get(i-1).getScaleEnd()) - mMin)/(mMax - mMin) *180);
                mArcs.add(arc1);

                mColors.add(Color.parseColor(chartBean.getColorArr().get(Integer.valueOf(mThresholdArrBeans.get(i-1).getType()) - 1)));
                Arc arc2 = new Arc() ;
                arc2.setStartAngle((toFloat(mThresholdArrBeans.get(i-1).getScaleStart()) - mMin)/(mMax - mMin) *180);
                arc2.setEndAngle((mMax - toFloat(mThresholdArrBeans.get(i-1).getScaleStart()))/(mMax - mMin) *180);
                mArcs.add(arc2) ;
                index ++ ;
            }else{
                Arc arc3 = new Arc() ;
                arc3.setStartAngle((mMax - toFloat(mThresholdArrBeans.get(i-1).getScaleStart())) /(mMax - mMin) *180);
                arc3.setEndAngle((mMax - toFloat(mThresholdArrBeans.get(i-1).getScaleEnd())) /(mMax - mMin) *180);
                mArcs.add(arc3);
            }
        }
    }

    /**
     * 越小越好
     */
    private void forSmall(IndexChartBean chartBean) {
        mThresholdArrBeans = forAsc(chartBean) ;
        mColors.clear() ;
        if(chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0){
            mColors.add(Color.parseColor(chartBean.getColorArr().get(mBigCirColor)));
        }else{
            mColors.add(Color.rgb(245,245,245));
        }
        mDegreeScale = 180f / mSum ;
        Arc arc = new Arc() ;
        arc.setStartAngle(180 * mDegreeScale);
        arc.setEndAngle(Math.abs(0 * mDegreeScale));
        mArcs.add(arc);
        for (int i = 1 ; i <  mThresholdArrBeans.size() + 1 ; i++){
            mColors.add(Color.parseColor(chartBean.getColorArr().get(Integer.valueOf(mThresholdArrBeans.get(i-1).getType()) - 1)));
            Arc arc1 = new Arc() ;
            arc1.setStartAngle(180f);
            arc1.setEndAngle(- Math.abs((mMax  - toFloat(mThresholdArrBeans.get(i-1).getThreshold_value()))) /(mMax - mMin) *180);
            mArcs.add(arc1);
        }
    }

    /**
     * 越大越好绘制数据  value升序排序
     */
    private void forBig(IndexChartBean chartBean) {
        mThresholdArrBeans = forAsc(chartBean);
        mColors.clear();
        if(chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0){
            mColors.add(Color.parseColor(chartBean.getColorArr().get(mBigCirColor)));
        }else{
            mColors.add(Color.rgb(245,245,245));
        }
        mDegreeScale = 180f / mSum;
        Arc arc = new Arc();
        arc.setStartAngle( 0 * mDegreeScale);
        arc.setEndAngle(Math.abs(180 * mDegreeScale));
        mArcs.add(arc);
        for (int i = 1 ; i < mThresholdArrBeans.size() + 1;i++){
            Arc arc1 = new Arc() ;
            mColors.add(Color.parseColor(chartBean.getColorArr().get(Integer.valueOf(mThresholdArrBeans.get(i-1).getType()) - 1)));
            arc1.setStartAngle(0f);
            arc1.setEndAngle((mMax  - toFloat(mThresholdArrBeans.get(i-1).getThreshold_value())) /(mMax - mMin) *180);
            mArcs.add(arc1);
        }
    }

    /**
     * 升序
     */
    List<IndexChartBean.ThresholdArrBean> forAsc(IndexChartBean chartBean) {
        List<IndexChartBean.ThresholdArrBean> arrBeans = chartBean.getThresholdArr();
        if(chartBean.getThresholdArr().size() == 1){
            mBigCirColor = Integer.valueOf(chartBean.getThresholdArr().get(0).getType());
            return  chartBean.getThresholdArr();
        }
        for (int i= 0 ; i < arrBeans.size();i++) {
            //第i趟比较
            for (int j = 0 ; j < arrBeans.size() - i - 1;j++) {
                mBigCirColor = Math.max(mBigCirColor,Integer.valueOf(arrBeans.get(j + 1).getType()));
                if (toFloat(arrBeans.get(j).getThreshold_value())
                        > toFloat(arrBeans.get(j + 1).getThreshold_value())) {
                    IndexChartBean.ThresholdArrBean temp = arrBeans.get(j + 1);
                    arrBeans.remove(j+1);
                    arrBeans.add(j,temp) ;
                }
            }
        }
        return arrBeans;
    }


    /**
     * 中间最好升序
     */
    List<IndexChartBean.ThresholdArrBean> forAscMid(IndexChartBean chartBean)  {
        if(chartBean.getThresholdArr().size() == 1){
            mBigCirColor = Integer.valueOf(chartBean.getThresholdArr().get(0).getType());
            return  chartBean.getThresholdArr();
        }
        List<IndexChartBean.ThresholdArrBean> arrBeans = chartBean.getThresholdArr();
        for (int i = 0;i< arrBeans.size();i++) {
            //第i趟比较
            for (int j = 0 ;j < arrBeans.size() - i - 1; j++) {
                //开始进行比较，如果arr[j]比arr[j+1]的值大，那就交换位置
                mBigCirColor = Math.max(mBigCirColor,Integer.valueOf(arrBeans.get(j + 1).getType()));
                if (toFloat(arrBeans.get(j).getScaleStart()) > toFloat(arrBeans.get(j + 1).getScaleStart())) {
                    IndexChartBean.ThresholdArrBean  temp= arrBeans.get(j+1);
                    arrBeans.remove(j+1);
                    arrBeans.add(j,temp);
                }
            }
        }
        return arrBeans;
    }

    /**
     * 获取最大刻度
     */
    private float getMax(IndexChartBean chartBean){
        double max = 0.0 ;
        if (TextUtils.isEmpty(chartBean.getIndex_data())){
            max = 100.0f ;
        }else{
            max = java.lang.Double.valueOf(chartBean.getIndex_data().replace(",",""));
        }
        if (chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0) {
            for (IndexChartBean.ThresholdArrBean bean : chartBean.getThresholdArr()) {
                max = Math.max(max, java.lang.Double.valueOf(bean.getThreshold_value())) ;
                if(!TextUtils.isEmpty(bean.getScaleEnd())){
                    return getStartMax(chartBean) ;
                }
            }
        }
        return toFloat((max) + "");
    }

    /**
     * 提供了阈值范围，获取最大最小
     */
    private float getStartMax(IndexChartBean chartBean){
        float max = 0f ;
        if (chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0) {
            for (IndexChartBean.ThresholdArrBean bean : chartBean.getThresholdArr()) {
                if(!TextUtils.isEmpty(bean.getScaleStart())){
                    max = Math.max(max,java.lang.Float.valueOf(bean.getScaleStart()));
                }
                if (!TextUtils.isEmpty(bean.getScaleEnd())){
                    max = Math.max(max,java.lang.Float.valueOf(bean.getScaleEnd())) ;
                }
            }
        }
        return max ;
    }

    private float getEndMin(IndexChartBean chartBean) {
        float min = 0f;
        if (chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0) {
            for (IndexChartBean.ThresholdArrBean bean : chartBean.getThresholdArr()) {
                if(!TextUtils.isEmpty(bean.getScaleStart())){
                    min = Math.min(min,java.lang.Float.valueOf(bean.getScaleStart()));
                }
                if (!TextUtils.isEmpty(bean.getScaleEnd())){
                    min = Math.min(min,java.lang.Float.valueOf(bean.getScaleEnd()));
                }
            }
        }
        return min ;
    }

    /**
     * 获取最小刻度
     */
    private float getMin(IndexChartBean chartBean) {
        double min = 0.0;
        if(TextUtils.isEmpty(chartBean.getIndex_data())){
            min = 0.0;
        }else{
            min = java.lang.Double.valueOf(chartBean.getIndex_data().replace(",",""));
        }
        if (chartBean.getThresholdArr() != null && chartBean.getThresholdArr().size() > 0) {
            for (IndexChartBean.ThresholdArrBean bean : chartBean.getThresholdArr()) {
                min = Math.min(min, java.lang.Double.valueOf(bean.getThreshold_value()));
                if(!TextUtils.isEmpty(bean.getScaleStart())){
                    return getEndMin(chartBean);
                }
            }
        }
        return toFloat((min) + "");
    }
}
