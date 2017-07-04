package com.dasheng.papa.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(Constant.Api.BASE_URL + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
                .into(imageView);
    }

    public static void loadXMLImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(Constant.Api.BASE_URL + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().into(iv);
    }
}