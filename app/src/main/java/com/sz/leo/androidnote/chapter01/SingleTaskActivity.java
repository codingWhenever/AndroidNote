package com.sz.leo.androidnote.chapter01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.leo.androidnote.R;


/**
 * singleTask-栈内复用模式
 * <p>
 * singleTask启动模式启动Activity时，首先会根据taskAffinity去寻找当前是否存在一个对应名字的任务栈
 * <p>
 * 如果不存在，则会创建一个新的Task，并创建新的Activity实例入栈到新创建的Task中去
 * 如果存在，则得到该任务栈，查找该任务栈中是否存在该Activity实例
 *               如果存在实例，则将它上面的Activity实例都出栈，然后回调启动的Activity实例的onNewIntent方法
 *               如果不存在该实例，则新建Activity，并入栈
 * ---------------------
 *
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class SingleTaskActivity extends BaseActivity {

    private Button jump, jump2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singletask);
        jump = findViewById(R.id.btn_singletask);
        jump2 = findViewById(R.id.btn_othertask);

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });
        jump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleTaskActivity.this, OtherTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}
