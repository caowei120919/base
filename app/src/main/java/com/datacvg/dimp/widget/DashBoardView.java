package com.datacvg.dimp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.bean.ChartBean;
import com.datacvg.dimp.widget.bean.ArcBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-27
 * @Description :
 */
public class DashBoardView extends View {

    private static final int DEFAULT_COLOR_TITLE = ContextCompat
            .getColor(AndroidUtils.getContext(),R.color.c_dbdbdb);
    private static final int DEFAULT_TEXT_SIZE_DIAL = 11;
    private static final int DEFAULT_STROKE_WIDTH = 8;
    private static final int DEFAULT_RADIUS_DIAL = 138;
    private static final int DEFAULT_TITLE_SIZE = 16;
    /**分成100分，有101个标识*/
    private static final int DEFAULT_VALUE_COUNT = 101 ;
    private int colorDialLower;
    private int textSizeDial;
    private int strokeWidthDial;
    private String titleDial;
    private int titleDialSize;
    private int titleDialColor;

    private int radiusDial;
    private int mRealRadius;
    private float currentValue = 0;

    private Paint arcPaint;
    private RectF mRect;
    private Paint pointerPaint;
    private Paint.FontMetrics fontMetrics;
    private Paint titlePaint;
    private Path pointerPath;
    private float mMaxValue = 100 ;
    private float mMinValue = 0 ;
    /**间隔度量*/
    private float intervalValue = 1 ;
    /**最底层色值对应的下标*/
    private int theBigArcColorPosition = -1 ;
    /**绘制弧度集合*/
    private List<ArcBean> arcBeans = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();


    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化属性
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs){
        //获得样式属性
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DashBoardView);
        colorDialLower = attributes.getColor(R.styleable.DashBoardView_color_dial_lower, DEFAULT_COLOR_TITLE);
        textSizeDial = (int) attributes.getDimension(R.styleable.DashBoardView_text_size_dial, sp2px(DEFAULT_TEXT_SIZE_DIAL));
        strokeWidthDial = (int) attributes.getDimension(R.styleable.DashBoardView_stroke_width_dial, dp2px(DEFAULT_STROKE_WIDTH));
        radiusDial = (int) attributes.getDimension(R.styleable.DashBoardView_radius_circle_dial, dp2px(DEFAULT_RADIUS_DIAL));
        titleDial = attributes.getString(R.styleable.DashBoardView_text_title_dial);
        if(TextUtils.isEmpty(titleDial)){
            titleDial = "";
        }
        titleDialSize = (int) attributes.getDimension(R.styleable.DashBoardView_text_title_size, dp2px(DEFAULT_TITLE_SIZE));
        titleDialColor = attributes.getColor(R.styleable.DashBoardView_text_title_color, DEFAULT_COLOR_TITLE);
    }

    private void initPaint(){
        //圆弧画笔
        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStrokeWidth(strokeWidthDial);

        //指针画笔
        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setTextSize(textSizeDial);
        pointerPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = pointerPaint.getFontMetrics();

        //标题画笔
        titlePaint = new Paint();
        titlePaint.setAntiAlias(true);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setFakeBoldText(true);

        //指针条
        pointerPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec/2 + dp2px(30));
        radiusDial = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight())/2;
        mRealRadius = radiusDial - strokeWidthDial - DisplayUtils.dp2px(30);
        mRect = new RectF(-mRealRadius, -mRealRadius, mRealRadius, mRealRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**画弧度*/
        drawArc(canvas);
        drawPointerLine(canvas);
        drawTitleDial(canvas);
        drawPointer(canvas);
    }

    /**
     *
     * @param canvas
     */
    private void drawArc(Canvas canvas){
        if(theBigArcColorPosition == -1){
            canvas.translate(getPaddingLeft() + radiusDial, getPaddingTop() + radiusDial);
            arcPaint.setColor(colorDialLower);
            canvas.drawArc(mRect, 180, 180, false, arcPaint);
            return;
        }
        canvas.translate(getPaddingLeft() + radiusDial, getPaddingTop() + radiusDial);
        for (int i = 0 ; i < colors.size() ; i++){
            arcPaint.setColor(colors.get(i));
            canvas.drawArc(mRect, arcBeans.get(i).getStartAngle(), arcBeans.get(i).getSweepAngle(), false, arcPaint);
        }
    }

    /**
     * 画指针线
     * @param canvas
     */
    private void drawPointerLine(Canvas canvas){
        canvas.rotate(180);
        for (int i=0; i < DEFAULT_VALUE_COUNT; i++){
            pointerPaint.setColor(colorDialLower);
            if (i % 10 == 0){
                pointerPaint.setStrokeWidth(5);
                canvas.drawLine(mRealRadius + strokeWidthDial, 0, mRealRadius + strokeWidthDial + DisplayUtils.dp2px(5), 0, pointerPaint);
                drawPointerText(canvas, i);
            }else {
                pointerPaint.setStrokeWidth(3);
                canvas.drawLine(mRealRadius + strokeWidthDial, 0, mRealRadius + strokeWidthDial + DisplayUtils.dp2px(3), 0, pointerPaint);
            }
            canvas.rotate(1.8f);
        }
    }

    /**
     * 绘制轮盘刻度值
     * @param canvas
     * @param i
     */
    private void drawPointerText(Canvas canvas, int i){
        canvas.save();
        int currentCenterX = (int) (radiusDial - strokeWidthDial - pointerPaint.measureText(String.valueOf(i)) / 2);
        canvas.translate(currentCenterX, 0);
        canvas.rotate(180 - 1.8f * i);

        int textBaseLine = (int) ((fontMetrics.bottom- fontMetrics.top) /2 - fontMetrics.bottom);
        canvas.drawText(String.valueOf(mMinValue + intervalValue*i), 0 , textBaseLine, pointerPaint);
        canvas.restore();
    }

    /**
     * 绘制显示数值
     * @param canvas
     */
    private void drawTitleDial(Canvas canvas){
        titlePaint.setColor(titleDialColor);
        titlePaint.setTextSize(titleDialSize);
        canvas.rotate( 0);
        canvas.drawText(titleDial, 0, -radiusDial* 1/2, titlePaint);
    }

    /**
     * 绘制指针
     * @param canvas
     */
    private void drawPointer(Canvas canvas){
        float currentDegree = (float) ((currentValue - mMinValue)/(mMaxValue - mMinValue) * 180 + 180);
        canvas.rotate(currentDegree);

        pointerPath.moveTo(radiusDial - strokeWidthDial - DisplayUtils.dp2px(48), 0);
        pointerPath.lineTo(0, -dp2px(3));
        pointerPath.lineTo(-12, 0);
        pointerPath.lineTo(0, dp2px(3));
        pointerPath.close();
        canvas.drawPath(pointerPath,pointerPaint);
    }


    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    /**
     * 设置仪表盘数据
     * @param chartValue
     */
    public void setChartValue(ChartBean chartValue){
        List<ChartBean.ThresholdArrBean> thresholdArrBeans = new ArrayList<>();
        if(chartValue == null){
            return;
        }
        mMaxValue = 100 ;
        mMinValue = 0 ;
        colors.clear();
        arcBeans.clear();
        theBigArcColorPosition = -1 ;
        getMaxAndMinValue(chartValue);
        thresholdArrBeans = sortValue(chartValue);
        if (theBigArcColorPosition == -1){
            colors.add(DEFAULT_COLOR_TITLE);
            arcBeans.add(new ArcBean(180,180));
            return;
        }
        currentValue = Float.parseFloat(chartValue.getDefault_value());
        titleDial = chartValue.getDefault_value() + "";
        titleDialColor = Color.parseColor(chartValue.getIndex_default_color());
        colors.add(Color.parseColor(chartValue.getColorArr().get(theBigArcColorPosition)));
        arcBeans.add(new ArcBean(180,180));
        for (ChartBean.ThresholdArrBean bean : thresholdArrBeans){
            if(!TextUtils.isEmpty(chartValue.getIndex_threshold_type())){
               switch (Integer.valueOf(chartValue.getIndex_threshold_type())){
                   case 1 :
                            colors.add(Color.parseColor(chartValue.getColorArr().get(Integer.valueOf(bean.getType()) -1)));
                            ArcBean arcBean = new ArcBean() ;
                            arcBean.setStartAngle(180);
                            arcBean.setSweepAngle((Float.valueOf(bean.getThreshold_value()) - mMinValue) * intervalValue);
                            arcBeans.add(arcBean);
                       break;

                   case 2 :
                           colors.add(Color.parseColor(chartValue.getColorArr().get(Integer.valueOf(bean.getType()) - 1)));
                           ArcBean arcBean2 = new ArcBean() ;
                           arcBean2.setStartAngle(0);
                           arcBean2.setSweepAngle(-(mMaxValue - (Float.valueOf(bean.getThreshold_value())))/(mMaxValue - mMinValue) * 180);
                           arcBeans.add(arcBean2);
                       break;

                   case 3 :
                       /**
                        * 初始值大于结束值，表示被割断，在左侧右侧有两条圆弧
                        */
                       if(Float.valueOf(bean.getScaleStart()) > Float.valueOf(bean.getScaleEnd())){
                           colors.add(Color.parseColor(chartValue.getColorArr().get(Integer.valueOf(bean.getType()) -1)));
                           ArcBean arcBean3 = new ArcBean() ;
                           arcBean3.setStartAngle(0);
                           arcBean3.setSweepAngle(-((mMaxValue - (Float.valueOf(bean.getScaleStart())))/(mMaxValue - mMinValue) * 180));
                           arcBeans.add(arcBean3);

                           colors.add(Color.parseColor(chartValue.getColorArr().get(Integer.valueOf(bean.getType()) -1)));
                           ArcBean arcBean4 = new ArcBean() ;
                           arcBean4.setStartAngle(180);
                           arcBean4.setSweepAngle((((Float.valueOf(bean.getScaleEnd())) - mMinValue)/(mMaxValue - mMinValue) * 180));
                           arcBeans.add(arcBean4);
                       }else{
                           colors.add(Color.parseColor(chartValue.getColorArr().get(Integer.valueOf(bean.getType()) -1)));
                           ArcBean arcBean5 = new ArcBean() ;
                           arcBean5.setStartAngle(180 * (mMaxValue - Float.valueOf(bean.getScaleStart()))/(mMaxValue - mMinValue) + 180);
                           arcBean5.setSweepAngle((Float.valueOf(bean.getScaleEnd()) - Float.valueOf(bean.getScaleStart()))/(mMaxValue - mMinValue) * 180);
                           arcBeans.add(arcBean5);
                       }
                       break;
               }
            }

        }
        invalidate();
    }

    /**
     * 阈值排序
     * @param chartValue
     * @return
     */
    private List<ChartBean.ThresholdArrBean> sortValue(ChartBean chartValue) {
        if (chartValue.getThresholdArr() == null || chartValue.getThresholdArr().size() == 0){
            return null;
        }
        ChartBean.ThresholdArrBean[] thresholdArrBeans = new ChartBean.ThresholdArrBean[chartValue.getThresholdArr().size()];
        chartValue.getThresholdArr().toArray(thresholdArrBeans);
        if(thresholdArrBeans.length == 1){
            theBigArcColorPosition = Math.max(theBigArcColorPosition, Integer.parseInt(thresholdArrBeans[0].getType()));
            return Arrays.asList(thresholdArrBeans) ;
        }
        for (int i = 0 ; i < thresholdArrBeans.length ; i++) {
            //第i趟比较
            for (int j = 0 ; j < thresholdArrBeans.length - i - 1 ; j++) {
                if(!TextUtils.isEmpty(thresholdArrBeans[j + 1].getType())){
                    theBigArcColorPosition = Math.max(theBigArcColorPosition, Integer.parseInt(thresholdArrBeans[j + 1].getType()));
                }
                /**中间最好*/
                if(!TextUtils.isEmpty(chartValue.getIndex_threshold_type())
                        && Integer.valueOf(chartValue.getIndex_threshold_type()) == 3){
                    theBigArcColorPosition = Math.max(theBigArcColorPosition, Integer.parseInt(thresholdArrBeans[j + 1].getType()));
                    if(!TextUtils.isEmpty(thresholdArrBeans[j].getScaleStart())
                            && !TextUtils.isEmpty(thresholdArrBeans[j+1].getScaleStart())){
                        if (Float.valueOf(thresholdArrBeans[j].getScaleStart())
                                > Float.valueOf(thresholdArrBeans[j + 1].getScaleStart())) {
                            ChartBean.ThresholdArrBean bean = thresholdArrBeans[j];
                            thresholdArrBeans[j] =  thresholdArrBeans[j + 1] ;
                            thresholdArrBeans[j + 1] = bean ;
                        }
                    }
                }else{
                    if(!TextUtils.isEmpty(thresholdArrBeans[j].getThreshold_value())
                            && !TextUtils.isEmpty(thresholdArrBeans[j+1].getThreshold_value())){
                        if (Float.valueOf(thresholdArrBeans[j].getThreshold_value())
                                > Float.valueOf(thresholdArrBeans[j + 1].getThreshold_value())) {
                            ChartBean.ThresholdArrBean bean = thresholdArrBeans[j];
                            thresholdArrBeans[j] =  thresholdArrBeans[j + 1] ;
                            thresholdArrBeans[j + 1] = bean ;
                        }
                    }
                }
            }
        }
        List<ChartBean.ThresholdArrBean> thresholdArrBeanList = Arrays.asList(thresholdArrBeans);
        return thresholdArrBeanList;
    }

    /**
     * 获取最值
     * @param chartValue
     */
    private void getMaxAndMinValue(ChartBean chartValue) {
        if(!TextUtils.isEmpty(chartValue.getDefault_value())){
            mMaxValue = Float.valueOf(chartValue.getDefault_value());
            mMinValue = Float.valueOf(chartValue.getDefault_value());
        }
        /**
         * 有阈值，比较所有阈值，取得最大值
         * type 1 越大越好  2 越小越好  3 中间最好
         */
        if(chartValue.getThresholdArr() != null && chartValue.getThresholdArr().size() > 0){
            mMinValue = mMaxValue ;
            for (ChartBean.ThresholdArrBean bean: chartValue.getThresholdArr()){
                if(!TextUtils.isEmpty(bean.getScaleEnd())){
                    mMaxValue = Math.max(mMaxValue, Float.valueOf(bean.getScaleEnd()));
                    mMinValue = Math.min(mMinValue, Float.valueOf(bean.getScaleEnd()));
                }

                if(!TextUtils.isEmpty(bean.getScaleStart())){
                    mMaxValue = Math.max(mMaxValue, Float.valueOf(bean.getScaleStart()));
                    mMinValue = Math.min(mMinValue, Float.valueOf(bean.getScaleStart()));
                }

                if(!TextUtils.isEmpty(bean.getThreshold_value())){
                    mMaxValue = Math.max(mMaxValue, Float.valueOf(bean.getThreshold_value()));
                    mMinValue = Math.min(mMinValue, Float.valueOf(bean.getThreshold_value()));
                }
            }

            /**
             * 中间最好状态下，最值需要变换
             */
            if(!TextUtils.isEmpty(chartValue.getIndex_threshold_type())
                    && Integer.valueOf(chartValue.getIndex_threshold_type()) == 3){
                float midValue = Float.parseFloat(chartValue.getIndex_threshold_value());
                float theMidChange = Math.max(Math.abs(mMaxValue - midValue)
                        , Math.abs(mMinValue - midValue));
                mMaxValue = midValue + theMidChange ;
                mMinValue = midValue - theMidChange ;
            }

            mMinValue = (float) (mMinValue - Math.abs(mMaxValue) * 0.2);
            mMaxValue = (float) (mMaxValue + Math.abs(mMaxValue) * 0.2);
            intervalValue = (mMaxValue - mMinValue)/100 ;
        }
    }
}
