package com.sz.leo.androidnote.chapter01;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/5/22
 * @email：lei.lu@e-at.com
 */
public class OtherTopActivity extends BaseActivity {
    private Button jump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        jump = findViewById(R.id.btn_standard);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherTopActivity.this, ActivitySingleTop.class));
            }
        });
    }
}
