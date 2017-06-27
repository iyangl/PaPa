package com.dasheng.papa.mvp.rank.child;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.util.Constant;

import rx.Observable;

public class RankPresenter {
    private RankContact.View mView;
    private RankContact.Model mModel;

    public RankPresenter(RankContact.View mView) {
        this.mView = mView;
        this.mModel = initModel();
    }

    public void refresh(int day_type) {
        mModel.refresh(day_type)
                .subscribe(new BaseSubscriber<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiListResBean<ResponseItemBean> apiListResBean) {
                        mView.onRefreshSuccess(apiListResBean);
                    }
                });
    }

    public void loadMore(int day_type, int page) {
        mModel.loadMore(day_type, page)
                .subscribe(new BaseSubscriber<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiListResBean<ResponseItemBean> apiListResBean) {
                        mView.onLoadMoreSuccess(apiListResBean);
                    }
                });
    }

    private RankContact.Model initModel() {
        return new RankContact.Model() {
            @Override
            public Observable<ApiListResBean<ResponseItemBean>> refresh(int day_type) {
                return ApiFactory.rank(String.valueOf(day_type), "0", Constant.Api.SINGLE_PAGE_SIZE, mView);
            }

            @Override
            public Observable<ApiListResBean<ResponseItemBean>> loadMore(int day_type, int page) {
                return ApiFactory.rank(String.valueOf(day_type), String.valueOf(page),
                        Constant.Api.SINGLE_PAGE_SIZE, mView);
            }
        };
    }
}
