package com.example.administrator.image.com.example.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.image.com.example.imagecache.ImageCache;
import com.example.administrator.image.imageInterface.OnLoadImageListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class BaseImageLoader {
    private final String TAG = this.getClass().getName();
    private String url;
    private Handler mHandler;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);//控制线程数
    private ImageCache mImageCache;


    public Bitmap getBitmap() {
        return bitmap;
    }

    Bitmap bitmap;

    public BaseImageLoader(String url, final OnLoadImageListener listener) {
        this.url = url;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                bitmap = (Bitmap) msg.obj;
                listener.onLoadImage(bitmap);
            }
        };
        mImageCache = new ImageCache();
    }

    public void downloadImage() {
        Bitmap bitmap = mImageCache.getBitmap(url);
        if (bitmap != null) {
            Log.i(TAG, Thread.currentThread().getName() + "     线程被调用了");
            notifyUI(bitmap);
            Log.i(TAG, Thread.currentThread().getName() + "     线程结束");
        } else {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TAG, Thread.currentThread().getName() + "     线程被调用了");
                        URL imageUrl = new URL(url);
                        HttpURLConnection imageUrlConnection = (HttpURLConnection) imageUrl.openConnection();
                        InputStream in = imageUrlConnection.getInputStream();
                        Bitmap httpBitmap = BitmapFactory.decodeStream(in);
                        mImageCache.putBitmap(url, httpBitmap);
                        notifyUI(httpBitmap);
                        imageUrlConnection.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, Thread.currentThread().getName() + "     线程结束");
                }
            });
        }
//        Thread downloadImage = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.i(TAG,Thread.currentThread().getName() + "     线程被调用了");
//                    URL imageUrl = new URL(url);
//                    HttpURLConnection imageUrlConnection = (HttpURLConnection) imageUrl.openConnection();
//                    InputStream in = imageUrlConnection.getInputStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(in);
//                    imageUrlConnection.disconnect();
//                    Message message = new Message();
//                    message.obj = bitmap;
//                    mHandler.sendMessage(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        downloadImage.start();
    }

    private void notifyUI(Bitmap bitmap) {
        Message message = new Message();
        message.obj = bitmap;
        mHandler.sendMessage(message);
    }

    public boolean isRecycled() {
        return bitmap.isRecycled();
    }

    public void recycle() {
        bitmap.recycle();
    }

}
