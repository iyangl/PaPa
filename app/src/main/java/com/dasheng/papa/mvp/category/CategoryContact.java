package com.dasheng.papa.mvp.category;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;

public interface CategoryContact {

    public static interface View extends BaseView {
        void onLoadCategoryInfoSuccess(ApiListResBean<ResponseItemBean> apiBean);
    }

    public static interface Model {
        Observable<ApiListResBean<ResponseItemBean>> loadCategoryInfo();
    }
}
