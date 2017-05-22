package com.bigshark.smartlight.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bigshark.smartlight.utils.DensityUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 参考了 http://blog.csdn.net/chen_zhang_yu/article/details/50859398
 * Created by jlbs1 on 2017/5/20.
 */

public class CurveChartView extends View {
    private static final String TAG = "CurveChartView";
    private final int STEP = 12;
    private boolean haveFocuse = false;
    // 曲线图显示模式
    private int model;
    // 按周显示
    public static final int MODEL_WEEK = 0;
    // 控件宽高
    private int viewWidth, viewHeigth;
    // 每一份的水平、竖直尺寸
    private float averageWidth, averageHeigth;
    // 画笔
    Paint grayPaint, bluePaint, blackPaint, whitePaint;
    private String[] weekDay = new String[] { "日", "一", "二", "三", "四", "五", "六" };
    // x、y轴坐标值
    private List<Integer> pointX, pointY;
    // 曲线路径
    private Path curvePath;

    public CurveChartView(Context context) {
        super(context);
        init();
    }

    public CurveChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurveChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        model = MODEL_WEEK;// 默认为显示周数据
        grayPaint = new Paint();
        grayPaint.setColor(Color.parseColor("#F7F7F9"));
        grayPaint.setStrokeWidth(1.5f);
        bluePaint = new Paint();
        bluePaint.setColor(Color.parseColor("#2E88CE"));
        bluePaint.setAntiAlias(true);
        bluePaint.setStyle(Paint.Style.STROKE);
        blackPaint = new Paint();
        blackPaint.setColor(Color.parseColor("#969696"));
        blackPaint.setStrokeWidth(3.0f);
        whitePaint = new Paint();
        whitePaint.setColor(Color.parseColor("#ffffff"));
        whitePaint.setStrokeWidth(2.5f);

        pointX = new ArrayList<Integer>();

        curvePath = new Path();

        for (int i = 0; i < pointX.size(); i++) {
            Log.i(TAG, "init..  pointX[" + i + "] = " + pointX.get(i) + ",pointY[" + i + "] = " + pointY.get(i));
        }
        Log.d(TAG, "init..");
    }

    /**
     * 设置显示模式 0为显示一周每天数据 否则为显示每月数据
     * @param model
     */
    public void setShowModel(int model) {
        this.model = model;
        initMeasure();
        pointX.clear();
        if (model == MODEL_WEEK)
            for (int i = 0; i < 7; i++) {
                pointX.add((int) ((i + 0.5) * averageWidth));
            }
        else
            for (int i = 0; i < 12; i++)
                pointX.add((int) ((i + 0.5) * averageWidth));
        invalidate();
    }

    /**
     * 设置y坐标上的值
     * @param
     */
    public void setPointsY(final List<Integer> pointY1) {
        if (null != pointY1) {
            if(!haveFocuse)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setYDatas(pointY1);
                    }
                }, 180);
            else
                setYDatas(pointY1);
        }
    }

    private void setYDatas(final List<Integer> pointY1) {
        pointY = pointY1;
        for(int i = 0;i < pointY.size();i++)
            pointY.set(i, transformYCoordinate(pointY.get(i)));
        invalidate();
    }

    /**
     * 将传入的y数据值转换为屏幕坐标值
     *
     * @param y
     * @return
     */
    public int transformYCoordinate(int y) {
        Log.v(TAG, "transformYCoordinate..  viewHeigth = " + viewHeigth);
        return (int) (viewHeigth - ((y / 100f) * viewHeigth));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawGrayBackground(canvas);
        drawYCoordinateNumber(canvas);
        drawYCoordinateLine(canvas);
        drawXCoordinateText(canvas);
        drawCurveChart(canvas);

        super.onDraw(canvas);
    }

    /**
     * 画灰、白色背景
     */
    private void drawGrayBackground(Canvas canvas) {
        Log.d(TAG, "drawGrayBackground..viewWidth = " + viewWidth + ",viewHeigth = " + viewHeigth);
        grayPaint.setStyle(Paint.Style.FILL);
        whitePaint.setStyle(Paint.Style.FILL);

        canvas.drawRect(0, 0, viewWidth, viewHeigth * (10f / 11), grayPaint); // 灰色背景
        canvas.drawRect(0, viewHeigth * (10f / 11), viewWidth, viewHeigth, whitePaint); // 白色背景

    }

    /**
     * 画曲线
     *
     * @param canvas
     */
    private void drawCurveChart(Canvas canvas) {
        curvePath.reset();

        List<Cubic> calculate_x = calculate(pointX);
        List<Cubic> calculate_y = calculate(pointY);

        if (null != calculate_x && null != calculate_y && calculate_y.size() >= calculate_x.size()) {
            curvePath.moveTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));

            for (int i = 0; i < calculate_x.size(); i++) {
                for (int j = 1; j <= STEP; j++) {
                    float u = j / (float) STEP;
                    curvePath.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i).eval(u));
                }
            }
        }

        bluePaint.setStrokeWidth(4);
        bluePaint.setStrokeWidth(4);
        canvas.drawPath(curvePath, bluePaint);

    }

    /**
     * 画Y坐标上横线
     *
     * @param canvas
     */
    private void drawYCoordinateLine(Canvas canvas) {
        for (int i = 1; i < 10; i++)
            canvas.drawLine(0, averageHeigth * i, viewWidth, averageHeigth * i, whitePaint);
    }

    /**
     * 画x坐标文字
     * @param canvas
     */
    private void drawXCoordinateText(Canvas canvas) {
        blackPaint.setTextSize(DensityUtil.sp2px(getContext(), 12));
        if (model == MODEL_WEEK)
            for (int i = 0; i < 7; i++)
                canvas.drawText("星期" + weekDay[i], (0.2f + i) * averageWidth, 10.7f * averageHeigth, blackPaint);
        else {
            for (int i = 0; i < 12; i++)
                canvas.drawText(i + 1 + "月", (0.1f + i) * averageWidth, 10.7f * averageHeigth, blackPaint);
        }
    }

    /**
     * 画y轴坐标值
     * @param canvas
     */
    private void drawYCoordinateNumber(Canvas canvas) {
        Log.d(TAG, "drawYCoordinateNumber..");
        blackPaint.setTextSize(DensityUtil.sp2px(getContext(), 10));
        for (int i = 0; i < 10; i++)
            canvas.drawText("" + (i * 10), viewWidth * (13 / 140), (float) (9.9 - i) * averageHeigth, blackPaint);
    }

    /**
     * 计算曲线.
     * @param x
     * @return
     */
    private List<Cubic> calculate(List<Integer> x) {
        if (null != x && x.size() > 0) {
            int n = x.size() - 1;
            float[] gamma = new float[n + 1];
            float[] delta = new float[n + 1];
            float[] D = new float[n + 1];
            int i;
            /*
             * We solve the equation [2 1 ] [D[0]] [3(x[1] - x[0]) ] |1 4 1 |
             * |D[1]| |3(x[2] - x[0]) | | 1 4 1 | | . | = | . | | ..... | | . |
             * | . | | 1 4 1| | . | |3(x[n] - x[n-2])| [ 1 2] [D[n]] [3(x[n] -
             * x[n-1])]
             *
             * by using row operations to convert the matrix to upper triangular
             * and then back sustitution. The D[i] are the derivatives at the
             * knots.
             */

            gamma[0] = 1.0f / 2.0f;
            for (i = 1; i < n; i++) {
                gamma[i] = 1 / (4 - gamma[i - 1]);
            }
            gamma[n] = 1 / (2 - gamma[n - 1]);

            delta[0] = 3 * (x.get(1) - x.get(0)) * gamma[0];
            for (i = 1; i < n; i++) {
                delta[i] = (3 * (x.get(i + 1) - x.get(i - 1)) - delta[i - 1]) * gamma[i];
            }
            delta[n] = (3 * (x.get(n) - x.get(n - 1)) - delta[n - 1]) * gamma[n];

            D[n] = delta[n];
            for (i = n - 1; i >= 0; i--) {
                D[i] = delta[i] - gamma[i] * D[i + 1];
            }

            /* now compute the coefficients of the cubics */
            List<Cubic> cubics = new LinkedList<Cubic>();
            for (i = 0; i < n; i++) {
                Cubic c = new Cubic(x.get(i), D[i], 3 * (x.get(i + 1) - x.get(i)) - 2 * D[i] - D[i + 1], 2 * (x.get(i) - x.get(i + 1)) + D[i] + D[i + 1]);
                cubics.add(c);
            }
            return cubics;
        }
        return null;
    }

    public class Cubic {

        float a, b, c, d; /* a + b*u + c*u^2 +d*u^3 */

        public Cubic(float a, float b, float c, float d) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        /** evaluate cubic */
        public float eval(float u) {
            return (((d * u) + c) * u + b) * u + a;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            haveFocuse = true;
            initMeasure();
            setShowModel(this.model);
        }
        super.onWindowFocusChanged(hasWindowFocus);
    }

    private void initMeasure() {
        viewWidth = getWidth();
        viewHeigth = getHeight();

        averageHeigth = viewHeigth / 11;
        if (model == MODEL_WEEK)
            averageWidth = viewWidth / 7;
        else
            averageWidth = viewWidth / 12;

    }

}
