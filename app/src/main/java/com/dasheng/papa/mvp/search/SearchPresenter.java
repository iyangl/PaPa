package com.dasheng.papa.mvp.search;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;
import rx.functions.Action1;

public class SearchPresenter {

    private SearchContact.View mView;
    private SearchContact.Model mModel;

    public SearchPresenter(SearchContact.View mView) {
        this.mView = mView;
        this.mModel = initModel();
    }

    public void loadMore(String keyword, final int page) {
        mModel.loadMore(keyword, page)
                .subscribe(new Action1<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void call(ApiListResBean<ResponseItemBean> responseItemBeanApiListResBean) {
                        mView.onLoadMoreSuccess(responseItemBeanApiListResBean);
                    }
                });
    }

    private SearchContact.Model initModel() {
        return new SearchContact.Model() {
            @Override
            public Observable<ApiListResBean<ResponseItemBean>> loadMore(String keyword, int page) {
                return ApiFactory.search(keyword, page, mView);
            }
        };
    }
}
