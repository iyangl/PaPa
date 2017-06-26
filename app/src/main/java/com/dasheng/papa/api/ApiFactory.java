package com.dasheng.papa.api;

import com.dasheng.papa.base.BaseView;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.bean.HomeResponseBean;
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



    public static Observable<ApiBean<HomeResponseBean>> get_Content(String type_id,
                                                                    String status,
                                                                    String id,
                                                                    String page,
                                                                    String size,
                                                                    BaseView view) {
        return getApi().get_Content(type_id, status, id, page, size)
                .lift(new BaseValueValidOperator<ApiBean<HomeResponseBean>>())
                .compose(RxUtils.<ApiBean<HomeResponseBean>>rxSchedulerHelper(view));
    }
}
