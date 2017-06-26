package com.dasheng.papa.mvp.beauty;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.util.Constant;

import rx.Observable;

public class BeautyPresenter {

    private BeautyContact.View mView;
    private BeautyContact.Model mModel;

    public BeautyPresenter(BeautyContact.View view) {
        mView = view;
        mModel = initModel();
    }

    public void refresh() {
        mModel.refresh()
                .subscribe(new BaseSubscriber<ApiSingleResBean<ImgBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiSingleResBean<ImgBean> imgBeanApiBean) {
                        mView.onRefresh(imgBeanApiBean);
                    }
                });
    }

    public void loadMore(int page) {
        mModel.loadMore(page)
                .subscribe(new BaseSubscriber<ApiSingleResBean<ImgBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiSingleResBean<ImgBean> imgBeanApiBean) {
                        mView.onLoadMoreSuccess(imgBeanApiBean);
                    }
                });
    }

    private BeautyContact.Model initModel() {
        return new BeautyContact.Model() {
            @Override
            public Observable<ApiSingleResBean<ImgBean>> refresh() {
                return ApiFactory.img(null, "0", Constant.Api.SINGLE_PAGE_SIZE, mView);
            }

            @Override
            public Observable<ApiSingleResBean<ImgBean>> loadMore(int page) {
                return ApiFactory.img(null, String.valueOf(page), Constant.Api.SINGLE_PAGE_SIZE, mView);
            }
        };
    }

}
