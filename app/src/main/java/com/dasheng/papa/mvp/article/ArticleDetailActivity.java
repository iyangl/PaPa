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

import timber.log.Timber;

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
            setTitle(responseItemBean.getTitle());
            binding.web.loadData(responseItemBean.getArticle(), "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
