package com.example.administrator.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.administrator.image.com.example.imageLoader.BaseImageLoader;
import com.example.administrator.image.com.example.imageLoader.VolleyImageLoader;
import com.example.administrator.image.com.example.imageLoader.VolleyImageRequest;
import com.example.administrator.image.imageInterface.OnLoadImageListener;
import com.example.administrator.imagerloader.R;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

    ImageView baseImageView;
    ImageView volleyImageRequestView;
    ImageView volleyImageLoaderView;
    NetworkImageView networkImageView;
    ImageView picassoImageView;
    ListView listView;
    Button button;
    BaseImageLoader baseImageLoader;
    VolleyImageRequest volleyImageRequest;
    VolleyImageLoader volleyImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBaseImageLoader();
        initVolleyImageRequest();
        initVolleyImageLoader();
        initNetworkImageView();
    }

    private void initNetworkImageView() {
        networkImageView.setDefaultImageResId(R.drawable.default_image);
        networkImageView.setErrorImageResId(R.drawable.failed_image);
    }

    private void initVolleyImageLoader() {
        String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
        volleyImageLoader = new VolleyImageLoader(this,volleyImageLoaderView,url);
    }

    private void initVolleyImageRequest() {
        String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
        volleyImageRequest = new VolleyImageRequest(this, url, new OnLoadImageListener() {
            @Override
            public void onLoadImage(Bitmap bitmap) {
                volleyImageRequestView.setImageBitmap(bitmap);
            }
        });
    }

    private void initBaseImageLoader() {
        String url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
        baseImageLoader = new BaseImageLoader(url, new OnLoadImageListener() {
            @Override
            public void onLoadImage(Bitmap bitmap) {
                baseImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        baseImageView = (ImageView) findViewById(R.id.image_base);
        volleyImageRequestView = (ImageView) findViewById(R.id.image_volley_imagerequest);
        volleyImageLoaderView = (ImageView) findViewById(R.id.image_volley_imageloader);
        networkImageView = (NetworkImageView) findViewById(R.id.network_imageview);
        picassoImageView = (ImageView) findViewById(R.id.image_picasso);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseImageLoader.downloadImage();
                volleyImageRequest.downloadImage();
                volleyImageLoader.downloadImage();
                networkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
                        volleyImageLoader.getImageLoader());
                Picasso.with(MainActivity.this).load("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg")
                        .resize(100, 100).into(picassoImageView);
            }
        });
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!baseImageLoader.isRecycled()) {
            baseImageLoader.recycle();
        }
    }
}
