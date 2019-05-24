package com.sz.leo.androidnote.chapter04;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.sz.leo.androidnote.R;

import java.util.Arrays;

/**
 * @author：leo
 * @date：2019/5/24
 * @email：lei.lu@e-at.com
 */
public class ListAnimActivity extends AppCompatActivity {
    private ListView listView;
    private String[] data = {"alpha", "scale", "translate", "rotate", "view", "viewGroup", "value", "child", "children",
            "alpha", "scale", "translate", "rotate", "view", "viewGroup", "value", "child", "children",
            "alpha", "scale", "translate", "rotate", "view", "viewGroup", "value", "child", "children",
            "alpha", "scale", "translate", "rotate", "view", "viewGroup", "value", "child", "children"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_anim);

        init();
    }

    private void init() {
        listView = findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(this, Arrays.asList(data)));

        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.item_entry));
        lac.setDelay(0.5f);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listView.setLayoutAnimation(lac);
    }
}
