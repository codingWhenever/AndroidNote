package com.sz.leo.androidnote.chapter04.recyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.sz.leo.androidnote.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：leo
 * @date：2019/5/27
 * @email：lei.lu@e-at.com
 */
public class GridRecyclerViewActivity extends AppCompatActivity {
    private GridRecyclerView recyclerView;
    private AppCompatSpinner spinner;

    private RecyclerViewActivity.AnimationItem[] animationItems;
    private RecyclerViewActivity.AnimationItem selectedItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler_view);
        init();
    }

    private RecyclerViewActivity.AnimationItem[] getAnimations() {
        return new RecyclerViewActivity.AnimationItem[]{
                new RecyclerViewActivity.AnimationItem("slide from bottom", R.anim.grid_layout_animation_from_bottom),
                new RecyclerViewActivity.AnimationItem("Scale", R.anim.grid_layout_animation_scale),
                new RecyclerViewActivity.AnimationItem("Scale random", R.anim.grid_layout_animation_scale_random)};
    }

    private void init() {
        animationItems = getAnimations();
        if (animationItems != null && animationItems.length > 0) {
            selectedItem = animationItems[0];
        }
        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new RecyclerViewAdapter());
        recyclerView.addItemDecoration(new RecyclerViewActivity.ItemOffsetDecoration(20));

        setupSpinner();
        runLayoutAnimation(selectedItem);
    }

    private void setupSpinner() {
        List<String> itemNames = new ArrayList<>();
        for (RecyclerViewActivity.AnimationItem item : animationItems) {
            itemNames.add(item.getName());
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this, R.style.Theme_AppCompat);
        spinner.setAdapter(new ArrayAdapter<>(contextThemeWrapper, R.layout.item_spinner, itemNames));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = animationItems[position];
                runLayoutAnimation(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void runLayoutAnimation(RecyclerViewActivity.AnimationItem selectedItem) {
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(
                GridRecyclerViewActivity.this,
                selectedItem.getResourceId());
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public void reload(View view) {
        runLayoutAnimation(selectedItem);
    }
}
