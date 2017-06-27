package com.dasheng.papa.mvp.category.child;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;

public interface CategoryDetailContact {

    public static interface View extends BaseView {
        void onRefreshSuccess(ApiListResBean<ResponseItemBean> apiBean);

        void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {
        Observable<ApiListResBean<ResponseItemBean>> refresh(int id);

        Observable<ApiListResBean<ResponseItemBean>> loadMore(int id, int page);
    }
}
