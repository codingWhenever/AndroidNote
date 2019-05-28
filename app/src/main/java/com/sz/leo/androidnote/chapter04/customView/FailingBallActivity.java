package com.sz.leo.androidnote.chapter04.customView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/5/28
 * @email：lei.lu@e-at.com
 */
public class FailingBallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failing_ball);
    }
}
