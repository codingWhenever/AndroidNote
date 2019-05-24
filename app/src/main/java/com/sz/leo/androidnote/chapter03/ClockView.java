package com.sz.leo.androidnote.chapter03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.sz.leo.androidnote.R;

import java.util.Calendar;

/**
 * @author：leo
 * @date：2019/5/23
 * @email：lei.lu@e-at.com
 */
public class ClockView extends View {
    private Canvas mCanvas;
    private int mBackgroundColor;
    private int mDarkColor;
    private int mLightColor;
    private float mTextSize;
    private float mRadius;
    //刻度线长度
    private float mScaleLength;


    private Paint mTextPaint;
    private Rect mTextRect = new Rect();
    //圆圈画画笔
    private Paint mCirclePaint;
    private float mCircleStrokeWidth = 4;
    private RectF mCircleRectF = new RectF();


    private Paint mScalePaint;

    //時分秒针角度
    private float mHourDegree;
    private float mMinuteDegree;
    private float mSecondDegree;
    //时针画笔
    private Paint mHourHandPaint;
    private Paint mSecondHandPaint;
    private Paint mMinuteHandPaint;
    //时针路径
    private Path mHourHandPath = new Path();
    private Path mMinuteHandPath = new Path();
    private Path mSecondHandPath = new Path();
    //刻度线画笔
    private Paint mScaleLinePaint;
    //刻度圆弧画笔
    private Paint mScaleArcPaint;
    //刻度圆弧的外接矩形
    private RectF mScaleArcRectF = new RectF();

    private SweepGradient mSweepGradient;
    private Matrix mGradientMatrix;

    //指针位移
    private float mCanvasTranslateX;
    private float mCanvasTranslateY;
    private float mMaxCanvasTranslate;

    private float mDefaultPadding;
    private float mPaddingLeft;
    private float mPaddingRight;
    private float mPaddingTop;
    private float mPaddingBottom;

    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClockView);

        mBackgroundColor = ta.getColor(R.styleable.ClockView_clock_backgroundColor, Color.parseColor("#237EAD"));
        mLightColor = ta.getColor(R.styleable.ClockView_clock_lightColor, Color.parseColor("#ffffff"));
        mDarkColor = ta.getColor(R.styleable.ClockView_clock_darkColor, Color.parseColor("#80ffffff"));
        mTextSize = ta.getDimension(R.styleable.ClockView_clock_textSize, DensityUtils.sp2px(context, 14));
        ta.recycle();

        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mDarkColor);
        //居中绘制文字
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(mTextSize);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleStrokeWidth);
        mCirclePaint.setColor(mDarkColor);

        mScaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleLinePaint.setStyle(Paint.Style.STROKE);
        mScaleLinePaint.setColor(mBackgroundColor);

        mScaleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleArcPaint.setStyle(Paint.Style.STROKE);

        mGradientMatrix = new Matrix();

        mSecondHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondHandPaint.setStyle(Paint.Style.FILL);
        mSecondHandPaint.setColor(mLightColor);

        mHourHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourHandPaint.setStyle(Paint.Style.FILL);
        mHourHandPaint.setColor(mDarkColor);

        mMinuteHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinuteHandPaint.setStyle(Paint.Style.FILL);
        mMinuteHandPaint.setColor(mLightColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int measureSpec) {
        int defaultSize = 800;
        int model = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (model) {
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return Math.min(size, defaultSize);
            case MeasureSpec.UNSPECIFIED:
                return defaultSize;
            default:
                return defaultSize;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w - getPaddingLeft() - getPaddingRight(),
                h - getPaddingTop() - getPaddingBottom()) / 2;
        mDefaultPadding = 0.12f * mRadius;
        mPaddingLeft = mDefaultPadding + w / 2 - mRadius + getPaddingLeft();
        mPaddingRight = mDefaultPadding + w / 2 - mRadius + getPaddingRight();
        mPaddingTop = mDefaultPadding + h / 2 - mRadius + getPaddingTop();
        mPaddingBottom = mDefaultPadding + h / 2 - mRadius + getPaddingBottom();

        mScaleLength = 0.12f * mRadius;
        mScaleLinePaint.setStrokeWidth(0.012f * mRadius);
        mScaleArcPaint.setStrokeWidth(mScaleLength);

        //梯度扫描渐变，以(w/2,h/2)为中心点，两种起止颜色梯度渐变
        //float数组表示，[0,0.75)为起始颜色所占比例，[0.75,1}为起止颜色渐变所占比例
        mSweepGradient = new SweepGradient(w / 2, h / 2, new int[]{mDarkColor, mLightColor}, new float[]{0.75f, 1});

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        getCurrentTime();
        drawOutSideArc();
        drawScaleLine();
        drawSecondNeedle();
        drawMinuteNeedle();
        drawHourHand();
        invalidate();
    }

    /**
     * 画外圈的2、6、9、12和弧线
     */
    private void drawOutSideArc() {
        String[] timeList = new String[]{"12", "3", "6", "9"};
        //计算数字的高度
        mTextPaint.getTextBounds(timeList[1], 0, timeList[1].length(), mTextRect);
        mCircleRectF.set(mPaddingLeft + mTextRect.width() / 2 + mCircleStrokeWidth / 2,
                mPaddingTop + mTextRect.height() / 2 + mCircleStrokeWidth / 2,
                getWidth() - mPaddingRight - mTextRect.width() / 2 - mCircleStrokeWidth / 2,
                getHeight() - mPaddingBottom - mTextRect.height() / 2 - mCircleStrokeWidth / 2);
        mCanvas.drawText(timeList[0], getWidth() / 2, mCircleRectF.top + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[1], mCircleRectF.right, getHeight() / 2 + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[2], getWidth() / 2, mCircleRectF.bottom + mTextRect.height() / 2, mTextPaint);
        mCanvas.drawText(timeList[3], mCircleRectF.left, getHeight() / 2 + mTextRect.height() / 2, mTextPaint);
        //画连接数字的4段弧线
        for (int i = 0; i < 4; i++) {
            mCanvas.drawArc(mCircleRectF, 5 + 90 * i, 80, false, mCirclePaint);
        }
    }

    private void drawScaleLine() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);
        mScaleArcRectF.set(mPaddingLeft + 1.5f * mScaleLength + mTextRect.height() / 2,
                mPaddingTop + 1.5f * mScaleLength + mTextRect.height() / 2,
                getWidth() - mPaddingRight - mTextRect.height() / 2 - 1.5f * mScaleLength,
                getHeight() - mPaddingBottom - mTextRect.height() / 2 - 1.5f * mScaleLength);

        //matrix默认会在三点钟方向开始颜色的渐变，为了吻合钟表十二点钟顺时针旋转的方向，把秒针旋转的角度减去90度
        mGradientMatrix.setRotate(mSecondDegree - 90, getWidth() / 2, getHeight() / 2);
        mSweepGradient.setLocalMatrix(mGradientMatrix);
        mScaleArcPaint.setShader(mSweepGradient);
        mCanvas.drawArc(mScaleArcRectF, 0, 360, false, mScaleArcPaint);

        //画背景色刻度线
        for (int i = 0; i < 200; i++) {
            mCanvas.drawLine(getWidth() / 2, mPaddingTop + mScaleLength + mTextRect.height() / 2,
                    getWidth() / 2, mPaddingTop + 2 * mScaleLength + mTextRect.height() / 2, mScaleLinePaint);
            mCanvas.rotate(1.8f, getWidth() / 2, getHeight() / 2);
        }
        mCanvas.restore();
    }

    /**
     * 秒针
     */
    private void drawSecondNeedle() {
        mCanvas.save();
        mCanvas.rotate(mSecondDegree, getWidth() / 2, getHeight() / 2);
        mSecondHandPath.reset();
        float offset = mPaddingTop + mTextRect.height() / 2;

        mSecondHandPath.moveTo(getWidth() / 2, offset + 0.26f * mRadius);
        mSecondHandPath.lineTo(getWidth() / 2 - 0.05f * mRadius, offset + 0.34f * mRadius);
        mSecondHandPath.lineTo(getWidth() / 2 + 0.05f * mRadius, offset + 0.34f * mRadius);
        mSecondHandPath.close();
        mSecondHandPaint.setColor(mLightColor);
        mCanvas.drawPath(mSecondHandPath, mSecondHandPaint);
        mCanvas.restore();
    }

    /**
     * 绘制分针
     */
    private void drawMinuteNeedle() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX * 2f, mCanvasTranslateY * 2f);
        mCanvas.rotate(mMinuteDegree, getWidth() / 2, getHeight() / 2);
        mMinuteHandPath.reset();

        float offset = mPaddingTop + mTextRect.height() / 2;
        mMinuteHandPath.moveTo(getWidth() / 2 - 0.01f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mMinuteHandPath.lineTo(getWidth() / 2 - 0.008f * mRadius, offset + 0.365f * mRadius);
        mMinuteHandPath.quadTo(getWidth() / 2, offset + 0.345f * mRadius,
                getWidth() / 2 + 0.008f * mRadius, offset + 0.365f * mRadius);
        mMinuteHandPath.lineTo(getWidth() / 2 + 0.01f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mMinuteHandPath.close();
        mMinuteHandPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawPath(mMinuteHandPath, mMinuteHandPaint);

        mCircleRectF.set(getWidth() / 2 - 0.03f * mRadius, getHeight() / 2 - 0.03f * mRadius,
                getWidth() / 2 + 0.03f * mRadius, getHeight() / 2 + 0.03f * mRadius);
        mMinuteHandPaint.setStyle(Paint.Style.STROKE);
        mMinuteHandPaint.setStrokeWidth(0.02f * mRadius);
        mCanvas.drawArc(mCircleRectF, 0, 360, false, mMinuteHandPaint);
        mCanvas.restore();
    }

    /**
     * 绘制时针
     */
    private void drawHourHand() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX * 1.2f, mCanvasTranslateY * 1.2f);
        mCanvas.rotate(mHourDegree, getWidth() / 2, getHeight() / 2);
        mHourHandPath.reset();
        float offset = mPaddingTop + mTextRect.height() / 2;
        mHourHandPath.moveTo(getWidth() / 2 - 0.018f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mHourHandPath.lineTo(getWidth() / 2 - 0.009f * mRadius, offset + 0.48f * mRadius);
        mHourHandPath.quadTo(getWidth() / 2, offset + 0.46f * mRadius,
                getWidth() / 2 + 0.009f * mRadius, offset + 0.48f * mRadius);
        mHourHandPath.lineTo(getWidth() / 2 + 0.018f * mRadius, getHeight() / 2 - 0.03f * mRadius);
        mHourHandPath.close();
        mHourHandPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawPath(mHourHandPath, mHourHandPaint);

        mCircleRectF.set(getWidth() / 2 - 0.03f * mRadius, getHeight() / 2 - 0.03f * mRadius,
                getWidth() / 2 + 0.03f * mRadius, getHeight() / 2 + 0.03f * mRadius);
        mHourHandPaint.setStyle(Paint.Style.STROKE);
        mHourHandPaint.setStrokeWidth(0.01f * mRadius);
        mCanvas.drawArc(mCircleRectF, 0, 360, false, mHourHandPaint);
        mCanvas.restore();
    }

    /**
     * 获取当前时间
     */
    private void getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        float milliSecond = calendar.get(Calendar.MILLISECOND);
        float second = calendar.get(Calendar.SECOND) + milliSecond / 1000;
        float minute = calendar.get(Calendar.MINUTE) + second / 60;
        float hour = calendar.get(Calendar.HOUR) + minute / 60;
        mSecondDegree = second / 60 * 360;
        mMinuteDegree = minute / 60 * 360;
        mHourDegree = hour / 12 * 360;
    }
}
