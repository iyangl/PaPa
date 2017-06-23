package com.dasheng.papa.api;

import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.bean.HomeResponseBean;

import java.util.HashMap;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface PaPaApiService {

    /**
     * 获取视频信息列表接口
     *
     * @param options status：3代表轮播图，不传代表下面的列表 type_id 列表类型，鬼知道都是什么值 id:视频id
     * @return
     */
    @GET("get_content")
    Observable<ApiBean<HomeResponseBean>> get_Content(@QueryMap HashMap<String, Integer> options);

    @GET("get_content")
    Observable<ApiBean<HomeResponseBean>> get_Content(@Query("type_id") String type_id,
                                                      @Query("status") String status,
                                                      @Query("id") String id,
                                                      @Query("page") String page,
                                                      @Query("size") String size);

}
