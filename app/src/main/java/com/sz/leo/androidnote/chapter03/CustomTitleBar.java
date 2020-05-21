package com.sz.leo.androidnote.chapter03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sz.leo.androidnote.R;


/**
 * @author：leo
 * @date：2020/5/21
 * @email：lei.lu@e-at.com
 */
public class CustomTitleBar extends RelativeLayout {
    private ImageView leftImg, rightImg;
    private TextView tvTitle;

    private static final int DEFAULT_PADDING = 15;
    private static final int DEFAULT_TITLE_SIZE = 16;

    public CustomTitleBar(Context context) {
        super(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tvTitle = new TextView(context);
        leftImg = new ImageView((context));
        rightImg = new ImageView(context);

        leftImg.setPadding(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);
        rightImg.setPadding(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        String title = array.getString(R.styleable.CustomTitleBar_titleText);

        float titleTextSize = array.getDimension(R.styleable.CustomTitleBar_titleTextSize, DEFAULT_TITLE_SIZE);
        int titleColor = array.getColor(R.styleable.CustomTitleBar_titleTextColor, Color.WHITE);
        Drawable leftDrawable = array.getDrawable(R.styleable.CustomTitleBar_leftImgSrc);
        Drawable rightDrawable = array.getDrawable(R.styleable.CustomTitleBar_rightImgSrc);
        array.recycle();

        leftImg.setImageDrawable(leftDrawable);
        rightImg.setImageDrawable(rightDrawable);
        tvTitle.setText(title);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setTextColor(titleColor);

        //初始化操作
        initView();

    }

    private void initView() {
        //设置并添加子view
        LayoutParams leftParams = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        this.addView(leftImg, leftParams);

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);
        this.addView(tvTitle, titleParams);

        LayoutParams rightParams = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));
        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        this.addView(rightImg, rightParams);

        //添加点击事件
        leftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.leftImgClick();
                }
            }
        });

        rightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.rightImgClick();
                }
            }
        });
    }

    private ImgClickerListener listener;

    public interface ImgClickerListener {
        public void leftImgClick();

        public void rightImgClick();
    }

    public void setImgClickListener(ImgClickerListener listener) {
        this.listener = listener;
    }
}
