package com.sz.leo.androidnote.chapter03;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author：leo
 * @date：2019/5/23
 * @email：lei.lu@e-at.com
 */
public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有子view，这会触发每个子view的onMeasure方法
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(getMaxChildWidth(), getTotalHeight());
            } else if (heightMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(widthSize, getTotalHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(getMaxChildWidth(), heightSize);
            }
        }
    }

    /**
     * 获取子view中宽度最大值
     *
     * @return
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getMeasuredWidth() > maxWidth) {
                maxWidth = child.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    /**
     * 获取子view的总高度
     *
     * @return
     */
    private int getTotalHeight() {
        int childCount = getChildCount();
        int total = 0;
        for (int i = 0; i < childCount; i++) {
            total += getChildAt(i).getMeasuredHeight();
        }
        return total;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int currentHeight = t;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            child.layout(l, currentHeight, l + width, currentHeight + height);
            currentHeight += height;
        }
    }
}
