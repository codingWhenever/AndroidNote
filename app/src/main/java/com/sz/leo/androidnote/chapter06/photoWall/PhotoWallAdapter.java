package com.sz.leo.androidnote.chapter06.photoWall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.sz.leo.androidnote.R;
import com.sz.leo.androidnote.chapter06.libcore.io.DiskLruCache;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author：leo
 * @date：2019/5/31
 * @email：lei.lu@e-at.com
 */
public class PhotoWallAdapter extends ArrayAdapter<String> {
    private Set<BitmapWorkerTask> taskCollection;
    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private GridView mPhotoWall;
    private int mItemHeight = 0;

    public PhotoWallAdapter(Context context, int textViewResourceId, String[] objects, GridView photoWall) {
        super(context, textViewResourceId, objects);
        mPhotoWall = photoWall;
        taskCollection = new HashSet<BitmapWorkerTask>();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        try {
            File cacheDir = getDiskCacheDir(context, "thumb");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 10 * 1024 * 1024);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.photo_layout, parent);

        } else {
            view = convertView;
        }

        ImageView imageView = view.findViewById(R.id.photo);
        if (imageView.getLayoutParams().height != mItemHeight) {
            imageView.getLayoutParams().height = mItemHeight;
        }
        imageView.setTag(url);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        loadBitmaps(imageView, url);
        return view;
    }

    private void loadBitmaps(ImageView imageView, String url) {

    }

    /**
     * @param context
     * @return
     */
    private int getAppVersion(Context context) {
        return 0;
    }

    /**
     * @param context
     * @param thumb
     * @return
     */
    private File getDiskCacheDir(Context context, String thumb) {
        return null;
    }


    static class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private String url;

        @Override
        protected Bitmap doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
