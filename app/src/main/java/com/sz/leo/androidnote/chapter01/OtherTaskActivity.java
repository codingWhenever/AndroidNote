package com.sz.leo.androidnote.chapter01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sz.leo.androidnote.R;


/**
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class OtherTaskActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_task);
        findViewById(R.id.btn_singletask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherTaskActivity.this, SingleTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}
