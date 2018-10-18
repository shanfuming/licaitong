package com.zqzr.licaitong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.utils.DisplayFormat;

/**
 * Created by chenwei on 16/6/23.
 */
public class CountProgress extends View {
    private float m1Dip;
    private float m1Sp;
    private int   paddingToBottom;
    /**
     * 内部padding
     */
    private float innerPadding;
    /**
     * 进度跳的矩形
     */
    private RectF progressRectF;
    private Paint progressPaint;
    private Paint progressScalePaint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 文字大小
     */
    private float textSize;
    /**
     * 文字的颜色
     */
    private int   colorText;
    private Rect mBounds = new Rect();
    private String scaleText;
    /**
     * 进度条高度
     */
    private float  progressHeight;
    /**
     * 进度条背景色
     */
    private int    colorBottom;
    /**
     * 进度条前台颜色
     */
    private int    colorTop;
    /**
     * 进度值
     */
    private float scale = 0f;
    /**
     * 进度条与文字的位置
     * 0->Vertical
     * 1—>Horizontal
     */
    private int orientation;

    public CountProgress(Context context) {
        this(context, null);
    }

    public CountProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        m1Dip = getResources().getDisplayMetrics().density;
        m1Sp = getResources().getDisplayMetrics().scaledDensity;

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CountProgress, defStyleAttr, 0);
        if (attrs != null) {
            progressHeight = a.getDimension(R.styleable.CountProgress_proHeight, dips(8));
            colorBottom = a.getColor(R.styleable.CountProgress_proColorBottom, getResources().getColor(R.color.product_detail_bottom));
            colorTop = a.getColor(R.styleable.CountProgress_proColorTop, getResources().getColor(R.color.white));
            textSize = a.getDimension(R.styleable.CountProgress_textSizes, sp(16));
            innerPadding = a.getDimension(R.styleable.CountProgress_Padding, dips(10));
            orientation = a.getInteger(R.styleable.CountProgress_orientation, 0);
            colorText = a.getColor(R.styleable.CountProgress_textColors, getResources().getColor(R.color.text_white));

            //            scale = a.getFloat(R.styleable.CountProgress_scale, 0);

            a.recycle();
        }

        progressPaint = new Paint();
        progressPaint.setColor(colorBottom);
        progressPaint.setStyle(Paint.Style.FILL);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeJoin(Paint.Join.ROUND);

        progressScalePaint = new Paint();
        progressScalePaint.setColor(colorTop);
        progressScalePaint.setStyle(Paint.Style.FILL);
        progressScalePaint.setAntiAlias(true);
        progressScalePaint.setStrokeCap(Paint.Cap.SQUARE);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(colorText);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        scaleText = scale + "%";
        textPaint.getTextBounds(scaleText, 0, scaleText.length(), mBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result  = 0;
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        int mode    = MeasureSpec.getMode(measureSpec);
        int size    = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }

        if (orientation == 0 && !isWidth && (mBounds.height() + innerPadding + progressHeight > result)) {
            throw new RuntimeException("the height must be heighter than result");
        }
        if (orientation == 1 && !isWidth && (Math.max(mBounds.height(), progressHeight) > result)) {
            throw new RuntimeException("the height between text and progress must be heighter than result");
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (orientation == 0) {
            drawVerticalProgress(canvas);
        } else {
            drawHorizontalProgress(canvas);
        }
        Log.i("MyProgressBar", "progressHeight====>" + progressHeight);
    }

    /**
     * 绘制上下层的progress
     *
     * @param canvas
     */
    private void drawVerticalProgress(Canvas canvas) {
        //绘制文字
        float textStart = 0;
        if ((getMeasuredWidth() * (scale / 100) < mBounds.width() / 2)) {
            textStart = mBounds.width() / 2;
        } else if (getMeasuredWidth() * ((100 - scale) / 100) < mBounds.width() / 2) {
            textStart = getMeasuredWidth() - mBounds.width() / 2;
        } else {
            textStart = getMeasuredWidth() * (scale / 100);
        }
        canvas.drawText(scaleText, textStart, mBounds.height(), textPaint);
        //画底层progress
        canvas.drawRect(0, mBounds.height() + innerPadding, getMeasuredWidth(), mBounds.height() + innerPadding + progressHeight, progressPaint);
        //画上层progress
        canvas.drawRect(0, mBounds.height() + innerPadding, getMeasuredWidth() * (scale / 100), mBounds.height() + innerPadding + progressHeight,
                progressScalePaint);
    }

    /**
     * 绘制在同一层的progress
     *
     * @param canvas
     */
    private void drawHorizontalProgress(Canvas canvas) {

        //画底层progress
        canvas.drawRect(0, getMeasuredHeight() / 2 - progressHeight / 2, getMeasuredWidth() - mBounds.width(), getMeasuredHeight() / 2 + progressHeight / 2,
                progressPaint);
        //画上层progress
        canvas.drawRect(0, getMeasuredHeight() / 2 - progressHeight / 2, (getMeasuredWidth() - mBounds.width()) * (scale / 100), getMeasuredHeight() / 2 +
                        progressHeight / 2,
                progressScalePaint);
        //绘制文字
        float textStart = 0;
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(scaleText, getMeasuredWidth() - mBounds.width(), getMeasuredHeight() / 2 + progressHeight / 2, textPaint);
    }

    private float dips(final float dips) {
        return dips * m1Dip;
    }

    private float sp(final int sp) {
        return sp * m1Sp;
    }

    //////////////////////////////////////
    //get & setO
    /////////////////////////////////////

    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;

        scaleText = DisplayFormat.doubleFormat(scale) + "%";
        textPaint.getTextBounds(scaleText, 0, scaleText.length(), mBounds);
        invalidate();
    }
}
