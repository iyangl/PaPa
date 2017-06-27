package com.dasheng.papa.mvp.rank.child;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;

public interface RankContact {

    public static interface View extends BaseView {

        void onRefreshSuccess(ApiListResBean<ResponseItemBean> apiBean);

        void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {

        Observable<ApiListResBean<ResponseItemBean>> refresh(int type_id);

        Observable<ApiListResBean<ResponseItemBean>> loadMore(int type_id, int page);
    }
}
