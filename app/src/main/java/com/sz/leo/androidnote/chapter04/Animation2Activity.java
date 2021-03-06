package com.sz.leo.androidnote.chapter04;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/5/24
 * @email：lei.lu@e-at.com
 */
public class Animation2Activity extends AppCompatActivity {
    private Button btnAdd, btnRemove;
    private LinearLayout layoutPictures;
    private LinearLayout.LayoutParams lps;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation2);

        init();
    }

    private void init() {
        btnAdd = findViewById(R.id.btn_add);
        btnRemove = findViewById(R.id.btn_remove);
        layoutPictures = findViewById(R.id.layout_pictures);

        lps = new LinearLayout.LayoutParams(150, 150);
        //依次对CHANGE_APPEARING、APPEARING、DISAPPEARING和CHANGE_DISAPPEARING类型的过渡动画进行了设置
        LayoutTransition transition = new LayoutTransition();

        //当多个子View要执行同一个类型的动画时，就可以通过该方法来设置子View之间执行动画的间隙，
        //默认为0毫秒
        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, transition.getDuration(LayoutTransition.APPEARING));
        transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);

        ObjectAnimator appearingAnimator = ObjectAnimator.ofPropertyValuesHolder((Object) null,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f));
        //为指定类型的过渡动画设置自定义的属性动画
        transition.setAnimator(LayoutTransition.APPEARING, appearingAnimator);
        //为指定类型的过渡动画设置执行动画的周期，默认为300毫秒
        transition.setDuration(LayoutTransition.CHANGE_APPEARING, transition.getDuration(LayoutTransition.APPEARING));
        //为指定类型的过渡动画设置延迟执行的时间，默认与过渡动画的类型相关
        transition.setStartDelay(LayoutTransition.APPEARING, transition.getDuration(LayoutTransition.APPEARING));


        ObjectAnimator disappearingAnimator = ObjectAnimator
                .ofPropertyValuesHolder(
                        (Object) null,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.0f),
                        PropertyValuesHolder.ofFloat("alpha", 1.0f, 0));
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearingAnimator);
        transition.setDuration(LayoutTransition.DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));
        transition.setStartDelay(LayoutTransition.DISAPPEARING, 0);

        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        transition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, transition.getDuration(LayoutTransition.DISAPPEARING));

        layoutPictures.setLayoutTransition(transition);
    }

    public void add(View view) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.timg6);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        layoutPictures.addView(imageView, 0, lps);
    }

    public void remove(View view) {
        int count = layoutPictures.getChildCount();
        if (count > 0) {
            layoutPictures.removeViewAt(0);
        }
    }
}
