package com.sz.leo.androidnote.chapter03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class MyView extends View {
    private int defaultSize = 100;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        defaultSize = array.getDimensionPixelSize(R.styleable.MyView_default_size, 100);

        array.recycle();
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                //如果测量模式是最大取值size，此处取最大值
                mySize = size;
                break;
            case MeasureSpec.EXACTLY:
                //如果是固定大小，那就不变
                mySize = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                //如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            default:
                break;
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(defaultSize, widthMeasureSpec);
        int height = getMySize(defaultSize, heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = getMeasuredWidth() / 2;

        int centerX = getLeft() + r;
        int centerY = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(centerX, centerY, r, paint);
    }
}
