package com.dasheng.papa.mvp.search;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;

public interface SearchContact {
    public static interface View extends BaseView {

        void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {

        Observable<ApiListResBean<ResponseItemBean>> loadMore(String keyword,
                                                              int page);
    }
}
