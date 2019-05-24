package com.sz.leo.androidnote.chapter01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.leo.androidnote.R;


/**
 * singleTop-栈顶复用模式
 * 该模式分3种情况
 * <p>
 * 当前栈中已有该Activity的实例并且该实例位于栈顶时，不会新建实例，而是复用栈顶的实例，并且会将Intent对象传入，回调onNewIntent方法
 * 当前栈中已有该Activity的实例但是该实例不在栈顶时，其行为和standard启动模式一样，依然会创建一个新的实例
 * 当前栈中不存在该Activity的实例时，其行为同standard启动模式
 *        standard和singleTop启动模式都是在原任务栈中新建Activity实例，不会启动新的Task，即使你指定了taskAffinity属性。
 *
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class ActivitySingleTop extends BaseActivity {
    private Button jump, jump2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singletop);
        jump = findViewById(R.id.btn_singletop);
        jump2 = findViewById(R.id.btn_other);

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySingleTop.this, ActivitySingleTop.class);
                startActivity(intent);
            }
        });

        jump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitySingleTop.this, OtherTopActivity.class);
                startActivity(intent);
            }
        });
    }
}
