package com.bigshark.smartlight.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jlbs1 on 2017/6/9.
 */

public class CustomArcView extends View {
    private Paint mPaint;
    private Paint bgPaint;
    private boolean isLeft, isRight, isShache,fuyuan;
    /**
     * 圆的宽度
     */
    private int mCircleWidth = 12;
    private String defultColor = "#4f505a";

    public CustomArcView(Context context) {
        super(context);
    }

    public CustomArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);//取消锯齿
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(mCircleWidth);
        bgPaint.setColor(Color.parseColor(defultColor));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//取消锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(Color.RED);
    }

    public CustomArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLeftDraw() {
        isRight = false;
        isShache = false;
        isLeft = true;
        fuyuan = false;
        //重绘
        this.postInvalidate();
    }

    public void setRihgtDraw() {
        isRight = true;
        isShache = false;
        isLeft = false;
        fuyuan = false;
        //重绘
        this.postInvalidate();
    }

    public void setShacheDraw() {
        isRight = false;
        isShache = true;
        isLeft = false;
        fuyuan = false;
        //重绘
        this.postInvalidate();
    }

    public void setFuyuanDraw() {
        isRight = false;
        isShache = false;
        fuyuan = true;
        isLeft = false;
        //重绘
        this.postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        float x = (getWidth() - getHeight() / 2)/2 ;
//        float y = getHeight() / 4;
//
        RectF oval = new RectF(8,5,
                getWidth()-8, getHeight()-5);
        drawBGArc(canvas, oval);
        drawLeftArc(canvas, oval);
        drawRightArc(canvas, oval);
        drawShacheArc(canvas, oval);
        drawFuyuanArc(canvas, oval);
    }

    private void drawFuyuanArc(Canvas canvas, RectF oval) {
        if (fuyuan) {
            mPaint.setColor(Color.parseColor(defultColor));
            canvas.drawArc(oval, 110, 320, false, mPaint);
        }
    }

    private void drawLeftArc(Canvas canvas, RectF oval) {
        if (isLeft) {
            canvas.drawArc(oval, 110, 160, false, mPaint);
        }
    }

    private void drawRightArc(Canvas canvas, RectF oval) {
        if (isRight) {
            canvas.drawArc(oval, 270, 160, false, mPaint);
        }
    }

    private void drawShacheArc(Canvas canvas, RectF oval) {
        if (isShache) {
            canvas.drawArc(oval, 110, 320, false, mPaint);
        }
    }


    private void drawBGArc(Canvas canvas, RectF oval) {
        canvas.drawArc(oval, 110, 320, false, bgPaint);
    }
}
