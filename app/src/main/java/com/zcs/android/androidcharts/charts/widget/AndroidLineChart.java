package com.zcs.android.androidcharts.charts.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.zcs.android.androidcharts.charts.bean.ChartBean;
import com.zcs.android.androidcharts.charts.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线图
 * <p>
 * Created by ZengCS on 2017/9/26.
 * E-mail:zcs@sxw.cn
 * Add:成都市天府软件园E3-3F
 */
public class AndroidLineChart extends View {
    private static final int COLOR_AXIS = Color.parseColor("#333333");// X,Y轴颜色
    private static final int COLOR_SPLIT_LINE = Color.parseColor("#E2E2E2");// 分隔线颜色
    private static final int COLOR_TEXT = Color.parseColor("#333333");// 文本颜色
    private static final int COLOR_VAL = Color.parseColor("#777777");// 文本颜色
    private static final int COLOR_CIRCLE = Color.parseColor("#1399EB");// 圆点颜色
    private static final int COLOR_SHADOW = Color.parseColor("#116CC67F");// 圆点颜色
    private static final int COLOR_LINE = Color.parseColor("#6CC67F");// 折线颜色
    private static final int TEXT_SIZE = 14;// dp
    private static final int TEXT_SIZE_VAL = 12;// dp
    private static final float RADIUS = 7;// 圆点半径-px

    private boolean showVal = true;// 是否显示值
    private boolean showCircle = true;// 是否显示圆点
    private boolean showShadow = true;// 是否填充

    private DensityUtil mDensityUtil;

    private String xTag = "";
    private String yTag = "";

    private Paint axisPaint, splintPaint, textPaint, valPaint, linePaint, circlePaint, shadowPaint;
    private Path linePath, shadowPath;
    private List<CustomPoint> mPointList = new ArrayList<>();

    private int mWidth, mHeight;
    private int xStart, yStart, xEnd, yEnd;
    private static final int[] MARGINS = {72, 72, 72, 72};// top,right,bottom,left
    private int columnCount = 11;
    private int rowCount = 6;
    private ChartBean mChartBean;

    public AndroidLineChart(Context context) {
        this(context, null);
    }

    public AndroidLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AndroidLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDensityUtil = new DensityUtil(context);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 得到整个View的尺寸
        int mViewWidth = getMeasuredWidth();
        int mViewHeight = getMeasuredHeight();
        // 计算图表可用的尺寸
        mWidth = mViewWidth - MARGINS[1] - MARGINS[3];
        mHeight = mViewHeight - MARGINS[0] - MARGINS[2];
        // 找到起点[左上]和终点[右下]的x,y坐标
        xStart = MARGINS[3];
        yStart = MARGINS[0];
        xEnd = mViewWidth - MARGINS[1];
        yEnd = mViewHeight - MARGINS[2];
    }

    private void init() {
        setWillNotDraw(false);

        axisPaint = new Paint();
        axisPaint.setAntiAlias(true);
        axisPaint.setStrokeWidth(mDensityUtil.dip2px(1));
        axisPaint.setColor(COLOR_AXIS);
        axisPaint.setStyle(Paint.Style.STROKE);

        splintPaint = new Paint();
        splintPaint.setAntiAlias(true);
        splintPaint.setStrokeWidth(1);
        splintPaint.setColor(COLOR_SPLIT_LINE);
        splintPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(COLOR_TEXT);
        textPaint.setTextSize(mDensityUtil.dip2px(TEXT_SIZE));

        valPaint = new Paint();
        valPaint.setAntiAlias(true);
        valPaint.setColor(COLOR_VAL);
        valPaint.setTextSize(mDensityUtil.dip2px(TEXT_SIZE_VAL));

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(mDensityUtil.dip2px(2));
        linePaint.setColor(COLOR_LINE);
        linePaint.setStyle(Paint.Style.STROKE);

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(COLOR_CIRCLE);
        circlePaint.setStyle(Paint.Style.FILL);

        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setColor(COLOR_SHADOW);
        shadowPaint.setStyle(Paint.Style.FILL);
        // 线条路径
        linePath = new Path();
        shadowPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 清空点
        mPointList.clear();
        //重置线
        linePath.reset();
        shadowPath.reset();

        // 画分隔线
        drawSplitLine(canvas);
        // 画x,y轴
        drawAxisLine(canvas);
        if (mChartBean != null) {
            // 画折线
            drawLines(canvas);
            // 画点
            drawCircles(canvas);
        }
    }

    /**
     * 画点
     */
    private void drawCircles(Canvas canvas) {
        if (showCircle || showVal) {
            for (CustomPoint p : mPointList) {
                if (showCircle)
                    canvas.drawCircle(p.getX(), p.getY(), RADIUS, circlePaint);
                if (showVal) {
                    String s = p.getCount() + "人";
                    canvas.drawText(s, p.getX() - valPaint.measureText(s) / 2, p.getY() - 9, valPaint);
                }
            }
        }
    }

    /**
     * 画线形图
     */
    private void drawLines(Canvas canvas) {
        float lastX = xEnd;
        float xGap = (float) mWidth / columnCount;
        List<Integer> xValues = mChartBean.getValues();
        for (int i = 0; i < columnCount; i++) {
            int count = xValues.get(i);
            float lineHeight = count / (rowCount * 10f) * mHeight;
            float x = xStart + xGap * i;
            float y = yEnd - lineHeight;
            mPointList.add(new CustomPoint(x, y, count));
            if (i == 0) {
                linePath.moveTo(x, y);
                if (showShadow)
                    shadowPath.moveTo(x, y);
            } else {
                linePath.lineTo(x, y);
                if (showShadow)
                    shadowPath.lineTo(x, y);
            }
            lastX = x;
        }
        canvas.drawPath(linePath, linePaint);
        // 绘制阴影
        if (showShadow) {
            shadowPath.lineTo(lastX, yEnd);// 移动到图表终点
            shadowPath.close();// 使这些点构成封闭的路径记得到多边形
            canvas.drawPath(shadowPath, shadowPaint);
        }
    }


    /**
     * 画分隔线
     */
    private void drawSplitLine(Canvas canvas) {
        float xGap = (float) mWidth / columnCount;
        float yGap = (float) mHeight / rowCount;

        // 画X轴（纵向分割线）
        for (int i = 1; i < columnCount; i++) {
            canvas.drawLine(xStart + xGap * i, yStart, xStart + xGap * i, yEnd, splintPaint);
            String text = String.valueOf(i);
            canvas.drawText(text, xStart + xGap * i - textPaint.measureText(text) / 2, yEnd + mDensityUtil.dip2px(TEXT_SIZE), textPaint);
        }
        if (!TextUtils.isEmpty(xTag))
            canvas.drawText(xTag, xStart + xGap * columnCount - textPaint.measureText(xTag) / 2, yEnd + mDensityUtil.dip2px(TEXT_SIZE), textPaint);

        // 画Y轴（横向分割线）
        for (int i = 0; i < rowCount; i++) {
            canvas.drawLine(xStart, yStart + yGap * i, xEnd, yStart + yGap * i, splintPaint);
            String text = String.valueOf((rowCount - i) * 10);
            canvas.drawText(text, xStart - textPaint.measureText(text) - 5, yStart + yGap * i + mDensityUtil.dip2px(TEXT_SIZE) / 3, textPaint);
        }
        if (!TextUtils.isEmpty(yTag))
            canvas.drawText(yTag, xStart - textPaint.measureText(yTag), yStart - mDensityUtil.dip2px(TEXT_SIZE), textPaint);
    }

    /**
     * 画轴
     */
    private void drawAxisLine(Canvas canvas) {
        // 画X轴
        canvas.drawLine(xStart, yStart, xStart, yEnd, axisPaint);
        // 画Y轴
        canvas.drawLine(xStart, yEnd, xEnd, yEnd, axisPaint);
    }

    /**
     * 对外提供的绑定数据方法
     */
    public void bindChartData(ChartBean bean) {
        this.mChartBean = bean;
        columnCount = bean.getColumnCount();
        rowCount = bean.getRowCount();
        xTag = bean.getxTag();
        yTag = bean.getyTag();

        invalidate();
    }

    public AndroidLineChart withVal(boolean showVal) {
        this.showVal = showVal;
        return this;
    }

    public AndroidLineChart withCircle(boolean showCircle) {
        this.showCircle = showCircle;
        return this;
    }

    public AndroidLineChart withShadow(boolean showShadow) {
        this.showShadow = showShadow;
        return this;
    }

    /**
     * 用于记录点位置的对象
     */
    private class CustomPoint {
        private float x;
        private float y;
        private int count;

        CustomPoint(float x, float y, int count) {
            this.x = x;
            this.y = y;
            this.count = count;
        }

        float getX() {
            return x;
        }

        float getY() {
            return y;
        }

        int getCount() {
            return count;
        }
    }
}
