package com.example.administrator.image.com.example.imageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.image.imageInterface.OnLoadImageListener;
import com.example.administrator.imagerloader.R;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class VolleyImageRequest {
    private static final String TAG = "VolleyLoader";
    Context context;
    OnLoadImageListener listener;
    String url;

    public VolleyImageRequest(Context context, String url, OnLoadImageListener listener) {
        this.context = context;
        this.url = url;
        this.listener = listener;
    }

    public void downloadImage() {
        RequestQueue mQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        Log.e(TAG, Thread.currentThread().getName() + "     下载结束   bitmap Size : "+bitmap.getRowBytes());
                        listener.onLoadImage(bitmap);
                    }
                }, 1000, 1000, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"onErrorResponse");
                BitmapDrawable bd = (BitmapDrawable) ((Activity)context).getResources().getDrawable(R.drawable.failed_image);
                listener.onLoadImage(bd.getBitmap());
            }
        });
        mQueue.add(imageRequest);
    }


}
