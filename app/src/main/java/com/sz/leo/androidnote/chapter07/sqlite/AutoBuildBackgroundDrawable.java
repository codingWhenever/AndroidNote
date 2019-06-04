package com.sz.leo.androidnote.chapter07.sqlite;

import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/6/4
 * @email：lei.lu@e-at.com
 */
public class AutoBuildBackgroundDrawable extends LayerDrawable {
    protected ColorFilter pressedFilter = new LightingColorFilter(PressUtil.getColor(R.color.hover_gray_color), 1);
    protected int disabledAlpha = 100;


    public AutoBuildBackgroundDrawable(Drawable drawable) {
        super(new Drawable[]{drawable});
    }

    @Override
    protected boolean onStateChange(int[] states) {
        boolean enable = false;
        boolean pressed = false;
        for (int state : states) {
            if (state == android.R.attr.state_enabled) {
                enable = true;
            } else if (state == android.R.attr.state_pressed) {
                pressed = true;
            }

            mutate();
            if (enable && pressed) {
                setColorFilter(pressedFilter);
            } else if (!enable) {
                setAlpha(disabledAlpha);
            } else {
                setColorFilter(null);
            }
        }
        return super.onStateChange(states);
    }

    @Override
    public boolean isStateful() {
        return true;
    }
}
