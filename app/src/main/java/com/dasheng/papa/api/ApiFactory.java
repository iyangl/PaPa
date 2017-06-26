package com.dasheng.papa.api;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;
import com.dasheng.papa.bean.HomeResponseBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.bean.RankItemBean;
import com.dasheng.papa.rx.RxUtils;

import rx.Observable;

public class ApiFactory {

    private static PaPaApiService paPaApi;

    public static PaPaApiService getApi() {
        if (paPaApi == null) {
            synchronized (ApiFactory.class) {
                if (paPaApi == null) {
                    paPaApi = ServiceGenerator.createService(PaPaApiService.class);
                }
            }
        }
        return paPaApi;
    }


    public static Observable<ApiListResBean<HomeResponseBean>> get_Content(String type_id,
                                                                           String status,
                                                                           String id,
                                                                           String page,
                                                                           String size,
                                                                           BaseView view) {
        return getApi().get_Content(type_id, status, id, page, size)
                .lift(new BaseValueValidOperator<ApiListResBean<HomeResponseBean>>())
                .compose(RxUtils.<ApiListResBean<HomeResponseBean>>rxSchedulerHelper(view));
    }

    public static Observable<ApiSingleResBean<ImgBean>> img(String id, String page, String size, BaseView view) {
        return getApi().img(id, page, size)
                .lift(new BaseValueValidOperator<ApiSingleResBean<ImgBean>>())
                .compose(RxUtils.<ApiSingleResBean<ImgBean>>rxSchedulerHelper(view));
    }

    public static Observable<ApiSingleResBean<BeautyPicBean>> loadPics(String id, BaseView view) {
        return getApi().loadPics(id)
                .lift(new BaseValueValidOperator<ApiSingleResBean<BeautyPicBean>>())
                .compose(RxUtils.<ApiSingleResBean<BeautyPicBean>>rxSchedulerHelper(view));
    }

    public static Observable<ApiListResBean<RankItemBean>> rank(String day_type, String page,
                                                                String size, BaseView view) {
        return getApi().rank(day_type, page, size)
                .lift(new BaseValueValidOperator<ApiListResBean<RankItemBean>>())
                .compose(RxUtils.<ApiListResBean<RankItemBean>>rxSchedulerHelper(view));
    }
}
