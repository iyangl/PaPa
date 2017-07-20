package com.dasheng.papa.mvp.article;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.bean.VideoDetailBean;
import com.dasheng.papa.databinding.ActivityArticleDetailBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.HtmlUtil;
import com.dasheng.papa.util.NetWorkUtil;

import timber.log.Timber;

public class ArticleDetailActivity extends BaseActivity<ActivityArticleDetailBinding>
        implements ArticleDetailContact.View {
    private ResponseItemBean responseItemBean;
    private int mId;
    private ArticleDetailPresenter articleDetailPresenter;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
    }

    @Override
    protected void initView() {
        setNavigationIcon();
        setTitle("文章详情");
        responseItemBean = (ResponseItemBean) getIntent()
                .getSerializableExtra(Constant.Intent_Extra.VIDEO_DETAIL_INFO);
        if (responseItemBean != null) {
            mId = Integer.parseInt(responseItemBean.getId());
        }
        initWebView();
    }

    @Override
    protected void initEvent() {
        articleDetailPresenter = new ArticleDetailPresenter(this);
        articleDetailPresenter.refresh(mId);
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onRefreshSuccess(ApiSingleResBean<VideoDetailBean> apiBean) {
        if (apiBean.getRes().getHead() != null && apiBean.getRes().getHead().size() > 0) {
            responseItemBean = apiBean.getRes().getHead().get(0);
            binding.title.setText(responseItemBean.getTitle());
            binding.time.setText(String.format("更新时间：%s", responseItemBean.getAddtime()));
            /*binding.web.loadData(HtmlUtil.getNewContent(responseItemBean.getArticle()),
                    "text/html; charset=UTF-8", null);*/
            binding.web.loadDataWithBaseURL("file:///android_asset/", HtmlUtil.getNewContent(responseItemBean
                            .getArticle()),
                    "text/html; charset=UTF-8", null, null);
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @SuppressLint("JSInterface")
    private void initWebView() {
        mWebView = binding.web;
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        if(NetWorkUtil.isNetWorkAvailable(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //不阻塞加载图片
        webSettings.setBlockNetworkImage(false);
        // 添加js交互接口类，并起别名 imagelistner
        mWebView.addJavascriptInterface(new JSInterface(this), "imagelistner");
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    // 监听
    private class MyWebViewClient extends WebViewClient {

        //调用外部浏览器打开超链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            WebSettings webSettings = view.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setBlockNetworkImage(false);
            //判断webview是否加载了，图片资源
            if (!webSettings.getLoadsImagesAutomatically()) {
                //设置wenView加载图片资源
                webSettings.setLoadsImagesAutomatically(true);
            }

            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }
    }

    // 注入js函数监听
    private void addImageClickListner() {
        //网页内容加载完成之后，将真实图片的值替换回src
        binding.web.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "         window.imagelistner.log(objs.length);" +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "     if(objs[i].src.indexOf(\"loading_image_default.png\")<0) {"
                + "     } else {"
                + "         objs[i].src = objs[i].alt;"
                + "     }" +
                "}" +
                "})()");


        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        binding.web.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    // js通信接口
    public class JSInterface {

        private Context context;

        public JSInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void openImage(String img) {
            Intent intent = new Intent();
            intent.putExtra(Constant.Intent_Extra.WEB_IMAGE_URL, img);
            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
        }

        @JavascriptInterface
        public void log(String log) {
            Timber.d("log: %s", log);
        }
    }
}
