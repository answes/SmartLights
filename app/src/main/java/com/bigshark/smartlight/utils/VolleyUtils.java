package com.bigshark.smartlight.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bigshark.smartlight.R;

/**
 * Created by Dream on 16/5/30.
 */
public class VolleyUtils {

    //Volley框架
    public static void loadImage(Context context, NetworkImageView imageView, String url){
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        imageView.setDefaultImageResId(R.drawable.logo);
        imageView.setErrorImageResId(R.drawable.logo);
        imageView.setImageUrl(url,imageLoader);
    }
}
