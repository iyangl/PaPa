package com.dasheng.papa.mvp.article;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.VideoDetailBean;

import rx.Observable;

public class ArticleDetailPresenter {

    private ArticleDetailContact.View mView;
    private ArticleDetailContact.Model mModel;

    public ArticleDetailPresenter(ArticleDetailContact.View mView) {
        this.mView = mView;
        this.mModel = initModel();
    }

    public void refresh(int id) {
        mModel.refresh(id)
                .subscribe(new BaseSubscriber<ApiSingleResBean<VideoDetailBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiSingleResBean<VideoDetailBean> responseItemBeanApiListResBean) {
                        mView.onRefreshSuccess(responseItemBeanApiListResBean);
                    }
                });
    }

    private ArticleDetailContact.Model initModel() {
        return new ArticleDetailContact.Model() {
            @Override
            public Observable<ApiSingleResBean<VideoDetailBean>> refresh(int id) {
                return ApiFactory.get_content_detail(String.valueOf(id), mView);
            }
        };
    }
}
