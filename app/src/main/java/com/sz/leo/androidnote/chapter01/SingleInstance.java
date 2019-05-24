package com.sz.leo.androidnote.chapter01;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sz.leo.androidnote.R;


/**
 * singleInstance-全局唯一模式
 * <p>
 * 该模式具备singleTask模式的所有特性外，与它的区别就是，这种模式下的Activity会单独占用一个Task栈，
 * 具有全局唯一性，即整个系统中就这么一个实例，由于栈内复用的特性，后续的请求均不会创建新的Activity实例，
 * 除非这个特殊的任务栈被销毁了。以singleInstance模式启动的Activity在整个系统中是单例的，
 * 如果在启动这样的Activiyt时，已经存在了一个实例，那么会把它所在的任务调度到前台，重用这个实例。
 * ---------------------
 *
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class SingleInstance extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
    }
}
