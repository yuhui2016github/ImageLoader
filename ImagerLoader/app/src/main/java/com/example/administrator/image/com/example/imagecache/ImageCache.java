package com.example.administrator.image.com.example.imagecache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class ImageCache implements ImageLoader.ImageCache {
    LruCache<String, Bitmap> mImageCache;

    public ImageCache() {
        final int maxMemorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemorySize / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mImageCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }
}
