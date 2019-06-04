package com.sz.leo.androidnote;

import android.app.Application;

/**
 * @author：leo
 * @date：2019/6/4
 * @email：lei.lu@e-at.com
 */
public class XApplication extends Application {
    private static XApplication instance;

    public static XApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
