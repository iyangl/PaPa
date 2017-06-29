package com.dasheng.papa.mvp.article;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.bean.VideoDetailBean;
import com.dasheng.papa.databinding.ActivityArticleDetailBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.HtmlUtil;

public class ArticleDetailActivity extends BaseActivity<ActivityArticleDetailBinding>
        implements ArticleDetailContact.View {
    private ResponseItemBean responseItemBean;
    private int mId;
    private ArticleDetailPresenter articleDetailPresenter;

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
            binding.web.loadData(HtmlUtil.getNewContent(responseItemBean.getArticle()),
                    "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
