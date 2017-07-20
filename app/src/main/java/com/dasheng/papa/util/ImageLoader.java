package com.dasheng.papa.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import me.xiaopan.sketch.SketchImageView;

public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(Constant.Api.BASE_URL + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(imageView);
    }

    public static void loadImageWithoutScale(Context context, String url, ImageView imageView) {
        if (!url.startsWith("http")) {
            url = Constant.Api.BASE_URL + url;
        }
        if (url.endsWith(".gif")) {
            Glide.with(context).load(url).asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        } else {
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }

    public static void loadSketch(String url, SketchImageView imageView) {
        if (!url.startsWith("http")) {
            url = Constant.Api.BASE_URL + url;
        }
        imageView.displayImage(url);
    }
}