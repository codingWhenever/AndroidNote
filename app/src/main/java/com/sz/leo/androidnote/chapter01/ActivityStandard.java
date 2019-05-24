package com.sz.leo.androidnote.chapter01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.leo.androidnote.R;

/**
 * standard-默认模式
 *        这个模式是默认的启动模式，即标准模式，在不指定启动模式的前提下，
 * 系统默认使用该模式启动Activity，每次启动一个Activity都会重写创建一个新的实例，
 * 不管这个实例存不存在，这种模式下，谁启动了该模式的Activity，该Activity就属于启动它的Activity的任务栈中。
 * 这个Activity它的onCreate()，onStart()，onResume()方法都会被调用。
 *
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class ActivityStandard extends BaseActivity {
    private Button jump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        jump = findViewById(R.id.btn_standard);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityStandard.this, ActivityStandard.class);
                startActivity(intent);
            }
        });
    }
}
