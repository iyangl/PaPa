package com.dasheng.papa.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(Constant.Api.BASE_URL + url).centerCrop().into(imageView);
    }

    public static void loadImageWithoutScale(Context context, String url, ImageView imageView) {
        if (!url.startsWith("http")) {
            url = Constant.Api.BASE_URL + url;
        }
        Glide.with(context).load(url).into(imageView);
    }
}