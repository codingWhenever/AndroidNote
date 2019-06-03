package com.sz.leo.androidnote.chapter06.photoWall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/6/3
 * @email：lei.lu@e-at.com
 */
public class PhotoWallActivity extends AppCompatActivity {
    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private GridView mGridView;
    private PhotoWallAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        init();
    }

    private void init() {
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mGridView = findViewById(R.id.photo_wall);
        mAdapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls,
                mGridView);
        mGridView.setAdapter(mAdapter);
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int numColumns = (int) Math.floor(mGridView
                        .getWidth()
                        / (mImageThumbSize + mImageThumbSpacing));
                if (numColumns > 0) {
                    int columnWidth = (mGridView.getWidth() / numColumns)
                            - mImageThumbSpacing;
                    mAdapter.setItemHeight(columnWidth);
                    mGridView.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);

                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cancelAllTasks();
    }
}
