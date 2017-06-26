package com.dasheng.papa.mvp.rank;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.RankItemBean;
import com.dasheng.papa.util.Constant;

import rx.Observable;

public class RankPresenter {
    private RankContact.View mView;
    private RankContact.Model mModel;

    public RankPresenter(RankContact.View mView) {
        this.mView = mView;
        this.mModel = initModel();
    }

    public void refresh(int type_id) {
        mModel.refresh(type_id)
                .subscribe(new BaseSubscriber<ApiListResBean<RankItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiListResBean<RankItemBean> apiListResBean) {
                        mView.onRefreshSuccess(apiListResBean);
                    }
                });
    }

    public void loadMore(int type_id, int page) {
        mModel.loadMore(type_id, page)
                .subscribe(new BaseSubscriber<ApiListResBean<RankItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiListResBean<RankItemBean> apiListResBean) {
                        mView.onRefreshSuccess(apiListResBean);
                    }
                });
    }

    private RankContact.Model initModel() {
        return new RankContact.Model() {
            @Override
            public Observable<ApiListResBean<RankItemBean>> refresh(int type_id) {
                return ApiFactory.rank(String.valueOf(type_id), "0", Constant.Api.SINGLE_PAGE_SIZE, mView);
            }

            @Override
            public Observable<ApiListResBean<RankItemBean>> loadMore(int type_id, int page) {
                return ApiFactory.rank(String.valueOf(type_id), String.valueOf(page),
                        Constant.Api.SINGLE_PAGE_SIZE, mView);
            }
        };
    }
}
