package com.sz.leo.androidnote.chapter04.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;

/**
 * @author：leo
 * @date：2019/5/27
 * @email：lei.lu@e-at.com
 */
public class GridRecyclerView extends RecyclerView {
    public GridRecyclerView(@NonNull Context context) {
        super(context);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GridRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {
        final LayoutManager layoutManager = getLayoutManager();
        if (getAdapter() != null && layoutManager instanceof GridLayoutManager) {

            GridLayoutAnimationController.AnimationParameters animationParams =
                    (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;
            if (animationParams == null) {
                animationParams = new GridLayoutAnimationController.AnimationParameters();
                params.layoutAnimationParameters = animationParams;
            }
            final int columns = ((GridLayoutManager) layoutManager).getSpanCount();
            animationParams.count = count;
            animationParams.index = index;
            animationParams.columnsCount = columns;
            animationParams.rowsCount = count / columns;

            final int invertedIndex = count - 1 - index;
            animationParams.column = columns - 1 - (invertedIndex % columns);
            animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;
        } else {
            super.attachLayoutAnimationParameters(child, params, index, count);
        }
    }
}
