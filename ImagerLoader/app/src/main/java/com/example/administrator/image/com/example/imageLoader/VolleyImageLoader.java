package com.example.administrator.image.com.example.imageLoader;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.image.com.example.imagecache.ImageCache;
import com.example.administrator.image.imageInterface.OnLoadImageListener;
import com.example.administrator.imagerloader.R;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class VolleyImageLoader {
    private Context context;
    private ImageView imageView;
    private String url;
    private ImageLoader imageLoader;


    public VolleyImageLoader(Context context, ImageView imageView, String url) {
        this.context = context;
        this.imageView = imageView;
        this.url = url;
    }

    public void downloadImage() {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(mQueue, new ImageCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.drawable.default_image, R.drawable.failed_image);
        imageLoader.get(url, listener, 200, 200);
    }
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
