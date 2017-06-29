package com.dasheng.papa.mvp.video;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.VideoDetailBean;

import rx.Observable;

public class VideoDetailPresenter {

    private VideoDetailContact.View mView;
    private VideoDetailContact.Model mModel;

    public VideoDetailPresenter(VideoDetailContact.View mView) {
        this.mView = mView;
        this.mModel = initModel();
    }

    public void refresh(int id) {
        mModel.refresh(id)
                .subscribe(new BaseSubscriber<ApiSingleResBean<VideoDetailBean>>() {
                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(ApiSingleResBean<VideoDetailBean> responseItemBeanApiListResBean) {
                        mView.onRefreshSuccess(responseItemBeanApiListResBean);
                    }
                });
    }

    public void zan(String id, final String status) {
        mModel.zan(id, status)
                .subscribe(new BaseSubscriber<ApiBean>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiBean apiSingleResBean) {
                        mView.onZanSuccess(apiSingleResBean, status);
                    }
                });
    }

    private VideoDetailContact.Model initModel() {
        return new VideoDetailContact.Model() {
            @Override
            public Observable<ApiSingleResBean<VideoDetailBean>> refresh(int id) {
                return ApiFactory.get_content_detail(String.valueOf(id), mView);
            }

            @Override
            public Observable<ApiBean> zan(String id, String status) {
                return ApiFactory.zan(id, status, mView);
            }
        };
    }
}
