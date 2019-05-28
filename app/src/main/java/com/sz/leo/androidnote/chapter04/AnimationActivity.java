package com.sz.leo.androidnote.chapter04;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.sz.leo.androidnote.R;
import com.sz.leo.androidnote.chapter03.DensityUtils;
import com.sz.leo.androidnote.chapter04.customView.FailingBallActivity;
import com.sz.leo.androidnote.chapter04.recyclerView.GridRecyclerViewActivity;
import com.sz.leo.androidnote.chapter04.recyclerView.RecyclerViewActivity;

/**
 * @author：leo
 * @date：2019/5/24
 * @email：lei.lu@e-at.com
 */
public class AnimationActivity extends AppCompatActivity {
    private ImageView imgAlpha;
    private ImageView imgScale;
    private ImageView imgTranslate;
    private ImageView imgRotate;
    private ImageView imgAnimDrawable;
    private Button btnOpen;
    private ImageView ivLauncher;
    private int mHiddenViewHeight = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        imgAlpha = findViewById(R.id.image_alpha);
        imgScale = findViewById(R.id.image_scale);
        imgTranslate = findViewById(R.id.image_translate);
        imgRotate = findViewById(R.id.image_rotate);
        imgAnimDrawable = findViewById(R.id.image_anim_drawable);
        btnOpen = findViewById(R.id.btn_open);
        ivLauncher = findViewById(R.id.iv_launcher);
        mHiddenViewHeight = DensityUtils.dp2px(this, 100);
        Log.i(this.getClass().getSimpleName(), "mHiddenViewHeight : " + mHiddenViewHeight);

//        Animation aniAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
//        aniAlpha.setFillAfter(true);
//        imgAlpha.startAnimation(aniAlpha);
        //利用ObjectAnimator实现透明度动画
        ObjectAnimator.ofFloat(imgAlpha, "alpha", 1, 0, 1)
                .setDuration(2000)
                .start();

//        Animation aniScale = AnimationUtils.loadAnimation(this, R.anim.scale);
//        aniScale.setFillAfter(true);
//        imgScale.startAnimation(aniScale);

        //利用AnimatorSet和ObjectAnimator实现缩放动画
        AnimatorSet set = new AnimatorSet();
        imgScale.setPivotX(imgScale.getWidth() / 2);
        imgScale.setPivotY(imgScale.getHeight() / 2);
        set.playTogether(ObjectAnimator.ofFloat(imgScale, "scaleX", 1, 0).setDuration(5000),
                ObjectAnimator.ofFloat(imgScale, "scaleY", 1, 0).setDuration(5000));
        set.start();

//        Animation aniTranslate = AnimationUtils.loadAnimation(this, R.anim.translate);
//        aniTranslate.setFillAfter(true);
//        imgTranslate.startAnimation(aniTranslate);
        //利用AnimatorSet和ObjectAnimator实现位移动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(imgTranslate, "translationX", 20, 100).setDuration(2000),
                ObjectAnimator.ofFloat(imgTranslate, "translationY", 20, 100).setDuration(2000));
        animatorSet.start();

//        Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
//        animRotate.setFillAfter(true);
//        imgRotate.startAnimation(animRotate);
        //利用ObjectAnimator实现旋转动画
        imgRotate.setPivotX(imgRotate.getWidth() / 2);
        imgRotate.setPivotY(imgRotate.getHeight() / 2);
        ObjectAnimator.ofFloat(imgRotate, "rotation", 0, 360)
                .setDuration(1500)
                .start();

        //逐帧动画
        imgAnimDrawable.setImageResource(R.drawable.anim_list);
        AnimationDrawable animationDrawable = (AnimationDrawable) imgAnimDrawable.getDrawable();
        animationDrawable.start();

        findViewById(R.id.iv_launcher).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(AnimationActivity.this, FailingBallActivity.class));
                return true;
            }
        });
    }

    public void open(View view) {
        if (ivLauncher.getVisibility() != View.VISIBLE) {
            animateOpen(ivLauncher);
        } else {
            animateClose(ivLauncher);
        }
    }

    private void animateOpen(View view) {
        view.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = createAnimator(view, 0, mHiddenViewHeight);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(1000).start();
    }

    private void animateClose(final View view) {
        ValueAnimator valueAnimator = createAnimator(view, mHiddenViewHeight, 0);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(1000).start();
    }

    private ValueAnimator createAnimator(final View view, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams lps = view.getLayoutParams();
                lps.height = (int) animation.getAnimatedValue();
                view.setLayoutParams(lps);
            }
        });
        return valueAnimator;
    }

    public void animator2(View view) {
        startActivity(new Intent(this, Animation2Activity.class));
    }

    public void recyclerViewAnimation(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void gridViewAnimation(View view) {
        startActivity(new Intent(this, GridRecyclerViewActivity.class));
    }
}
