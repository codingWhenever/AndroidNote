package com.sz.leo.androidnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sz.leo.androidnote.chapter01.BaseActivity;


public class MainActivity extends BaseActivity {

    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.tv_content);
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SingleTaskActivity.class));

                Intent intent = new Intent();
                intent.setAction("com.sz.leo.singleinstance");
                startActivity(intent);
            }
        });
    }

}
