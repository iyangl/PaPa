package com.dasheng.papa.mvp.rank;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.RankItemBean;

import rx.Observable;

public interface RankContact {

    public static interface View extends BaseView {

        void onRefreshSuccess(ApiListResBean<RankItemBean> apiBean);

        void onLoadMoreSuccess(ApiListResBean<RankItemBean> apiBean);

        void onError(Throwable e);
    }

    public static interface Model {

        Observable<ApiListResBean<RankItemBean>> refresh(int type_id);

        Observable<ApiListResBean<RankItemBean>> loadMore(int type_id, int page);
    }
}
