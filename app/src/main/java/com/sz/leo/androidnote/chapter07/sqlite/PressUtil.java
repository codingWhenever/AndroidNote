package com.sz.leo.androidnote.chapter07.sqlite;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.sz.leo.androidnote.XApplication;

/**
 * @author：leo
 * @date：2019/6/4
 * @email：lei.lu@e-at.com
 */
public class PressUtil {
    public static int getColor(int colorResId) {
        Context context = XApplication.getInstance().getApplicationContext();
        return context.getResources().getColor(colorResId);
    }

    public static Drawable getDrawable(int drawableResId) {
        Context context = XApplication.getInstance().getApplicationContext();
        return context.getResources().getDrawable(drawableResId);
    }

    public static AutoBuildBackgroundDrawable getBgDrawable(Drawable drawable) {
        return new AutoBuildBackgroundDrawable(drawable);
    }

    public static AutoBuildBackgroundDrawable getBgDrawable(int resId) {
        return new AutoBuildBackgroundDrawable(getDrawable(resId));
    }
}
