package com.dasheng.papa.util;

import android.text.TextUtils;

public class UrlUtils {

    private static final String BASE_URL = Constant.Api.BASE_URL;

    public static String formatUrl(String url) {
        return TextUtils.isEmpty(url) ? "" : String.format("%s%s", BASE_URL, url);
    }
}
