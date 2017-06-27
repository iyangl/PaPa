package com.dasheng.papa.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.mvp.article.ArticleDetailActivity;
import com.dasheng.papa.mvp.video.VideoDetailActivity;

public class UrlUtils {

    private static final String BASE_URL = Constant.Api.BASE_URL;

    public static String formatUrl(String url) {
        return TextUtils.isEmpty(url) ? "" : String.format("%s%s", BASE_URL, url);
    }

    public static void jumpToArticleOrVideo(Context context, ResponseItemBean responseItemBean) {
        Intent intent = null;
        if (TextUtils.isEmpty(responseItemBean.getContent())) {
            intent = new Intent(context, ArticleDetailActivity.class);
        } else {
            intent = new Intent(context, VideoDetailActivity.class);
        }
        intent.putExtra(Constant.Intent_Extra.VIDEO_DETAIL_INFO, responseItemBean);
        context.startActivity(intent);
    }
}
