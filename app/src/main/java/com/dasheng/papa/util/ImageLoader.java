package com.dasheng.papa.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.xiaopan.sketch.SketchImageView;

public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(Constant.Api.BASE_URL + url).centerCrop().into(imageView);
    }

    public static void loadImageWithoutScale(Context context, String url, ImageView imageView) {
        if (!url.startsWith("http")) {
            url = Constant.Api.BASE_URL + url;
        }
        if (url.endsWith(".gif")) {
            Glide.with(context).load(url).asBitmap().into(imageView);
        } else {
            Glide.with(context).load(url).into(imageView);
        }
    }

    public static void loadSketch(String url, SketchImageView imageView) {
        if (!url.startsWith("http")) {
            url = Constant.Api.BASE_URL + url;
        }
        imageView.displayImage(url);
    }
}