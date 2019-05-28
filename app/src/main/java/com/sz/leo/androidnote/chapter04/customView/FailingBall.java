package com.sz.leo.androidnote.chapter04.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/5/28
 * @email：lei.lu@e-at.com
 */
public class FailingBall extends View {
    private int mWidth;
    private int mHeight;
    private float mStartX = 0;
    private float mStartY = 0;
    private float mEdgeLength = 200;
    private RectF mRect = new RectF(mStartX, mStartY, mStartX + mEdgeLength,
            mStartY + mEdgeLength);
    private float mFixedX = 0;
    private float mFixedY = 0;
    private Paint mPaint;

    private GestureDetector mGestureDetector;
    private float mSpeedX = 0;
    private float mSpeedY = 0;
    private Boolean mXFixed = false;
    private Boolean mYFixed = false;

    private Boolean mCanFail = false;
    private Bitmap mBitmap;


    public FailingBall(Context context) {
        this(context, null);
    }

    public FailingBall(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }


    public FailingBall(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(context, simpleOnGestureListener);

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mStartX = (w - mEdgeLength) / 2;
        mStartY = (h - mEdgeLength) / 2;
        refreshRectByCurrentPoint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawOval(mRect, mPaint);
        canvas.drawBitmap(mBitmap, new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight()), mRect, mPaint);
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mStartX = mStartX + mSpeedX / 30;
            mStartY = mStartY + mSpeedY / 30;
            mSpeedX *= 0.97;
            mSpeedY *= 0.97;

            if (Math.abs(mSpeedX) < 10) {
                mSpeedX = 0;
            }
            if (Math.abs(mSpeedY) < 10) {
                mSpeedY = 0;
            }
            if (refreshRectByCurrentPoint()) {
                if (mXFixed) {
                    mSpeedX = -mSpeedX;
                }
                if (mYFixed) {
                    mSpeedY = -mSpeedY;
                }
            }
            invalidate();
            if (mSpeedX == 0 && mSpeedY == 0) {
                mHandler.removeCallbacks(this);
                return;
            }
            mHandler.postDelayed(this, 33);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (contains(event.getX(), event.getY())) {
                    mCanFail = true;
                    mFixedX = event.getX() - mStartX;
                    mFixedY = event.getY() - mStartY;
                    mSpeedX = 0;
                    mSpeedY = 0;
                } else {
                    mCanFail = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mCanFail) {
                    break;
                }
                mStartX = event.getX() - mFixedX;
                mStartY = event.getY() - mFixedY;
                if (refreshRectByCurrentPoint()) {
                    mFixedX = event.getX() - mStartX;
                    mFixedY = event.getY() - mStartY;
                }
                invalidate();
                break;
        }
        return true;
    }

    private boolean contains(float x, float y) {
        float radius = mEdgeLength / 2;
        float centerX = mRect.left + radius;
        float centerY = mRect.top + radius;
        return Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)) <= radius;
    }

    /**
     * 刷新当前位置
     *
     * @return
     */
    private boolean refreshRectByCurrentPoint() {
        Boolean fixed = false;
        mXFixed = false;
        mYFixed = false;
        if (mStartX < 0) {
            mStartX = 0;
            fixed = true;
            mXFixed = true;
        }
        if (mStartY < 0) {
            mStartY = 0;
            fixed = true;
            mYFixed = true;
        }
        if (mStartX + mEdgeLength > mWidth) {
            mStartX = mWidth - mEdgeLength;
            fixed = true;
            mXFixed = true;
        }
        if (mStartY + mEdgeLength > mHeight) {
            mStartY = mHeight - mEdgeLength;
            mYFixed = true;
            fixed = true;
        }
        mRect.left = mStartX;
        mRect.top = mStartY;
        mRect.right = mStartX + mEdgeLength;
        mRect.bottom = mStartY + mEdgeLength;
        return fixed;
    }

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!mCanFail) {
                return false;
            }
            mSpeedX = velocityX;
            mSpeedY = velocityY;
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, 0);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };
}
