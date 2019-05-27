package com.sz.leo.androidnote.chapter04.recyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.sz.leo.androidnote.R;

/**
 * @author：leo
 * @date：2019/5/27
 * @email：lei.lu@e-at.com
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_animation);

        init();
    }

    private AnimationItem getAnimation() {
        return new AnimationItem("fall down", R.anim.layout_animation_fall_down);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter());
        int spacing = getResources().getDimensionPixelOffset(R.dimen.cardview_default_elevation);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(30));

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(RecyclerViewActivity.this,
                        getAnimation().getResourceId());
                recyclerView.setLayoutAnimation(controller);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
            }
        }, 300);
    }

    static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mSpacing;

        public ItemOffsetDecoration(int itemOffset) {
            this.mSpacing = itemOffset;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mSpacing, mSpacing, mSpacing, mSpacing);
        }
    }

    static class AnimationItem {
        private final String name;
        private final int resourceId;

        public AnimationItem(String name, int resourceId) {
            this.name = name;
            this.resourceId = resourceId;
        }

        public String getName() {
            return name;
        }

        public int getResourceId() {
            return resourceId;
        }
    }
}
