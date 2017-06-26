package com.dasheng.papa.api;

import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.HomeResponseBean;
import com.dasheng.papa.bean.ImgBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PaPaApiService {

    /**
     * 获取视频信息列表接口
     *
     * @param type_id 主题id
     * @param status  状态
     * @param id      id
     * @param page    页码
     * @param size    每页个数
     * @return
     */
    @GET("get_content")
    Observable<ApiListResBean<HomeResponseBean>> get_Content(@Query("type_id") String type_id,
                                                             @Query("status") String status,
                                                             @Query("id") String id,
                                                             @Query("page") String page,
                                                             @Query("size") String size);

    /**
     * 获取图片接口
     *
     * @param id 图片id，null为获取所有图片
     * @return
     */
    @GET("img")
    Observable<ApiSingleResBean<ImgBean>> img(@Query("id") String id,
                                              @Query("page") String page,
                                              @Query("size") String size);
}
