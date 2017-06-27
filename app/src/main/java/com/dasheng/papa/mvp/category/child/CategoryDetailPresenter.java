package com.dasheng.papa.mvp.category.child;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.util.Constant;

import rx.Observable;

public class CategoryDetailPresenter {

    private CategoryDetailContact.View mView;
    private CategoryDetailContact.Model mModel;

    public CategoryDetailPresenter(CategoryDetailContact.View mView) {
        this.mView = mView;
        this.mModel = initModel();
    }

    public void refresh(int type_id) {
        mModel.refresh(type_id)
                .subscribe(new BaseSubscriber<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiListResBean<ResponseItemBean> responseItemBeanApiListResBean) {
                        mView.onRefreshSuccess(responseItemBeanApiListResBean);
                    }
                });
    }

    public void loadMore(int type_id, int page) {
        mModel.loadMore(type_id, page)
                .subscribe(new BaseSubscriber<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiListResBean<ResponseItemBean> responseItemBeanApiListResBean) {
                        mView.onLoadMoreSuccess(responseItemBeanApiListResBean);
                    }
                });
    }


    private CategoryDetailContact.Model initModel() {
        return new CategoryDetailContact.Model() {
            @Override
            public Observable<ApiListResBean<ResponseItemBean>> refresh(int type_id) {
                return ApiFactory.get_Content(String.valueOf(type_id), null,
                        null, "0", Constant.Api.SINGLE_PAGE_SIZE, mView);
            }

            @Override
            public Observable<ApiListResBean<ResponseItemBean>> loadMore(int type_id, int page) {
                return ApiFactory.get_Content(String.valueOf(type_id), null,
                        null, String.valueOf(page), Constant.Api.SINGLE_PAGE_SIZE, mView);
            }
        };
    }
}
