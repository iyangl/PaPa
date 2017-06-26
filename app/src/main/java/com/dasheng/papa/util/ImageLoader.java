package com.dasheng.papa.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageLoader {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(Constant.Api.BASE_URL + url).into(imageView);
    }
}