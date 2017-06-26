package com.dasheng.papa.api;

import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;
import com.dasheng.papa.bean.HomeResponseBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.bean.RankItemBean;

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
     * @return {@link HomeResponseBean}
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
     * @return {@link ImgBean}
     */
    @GET("img")
    Observable<ApiSingleResBean<ImgBean>> img(@Query("id") String id,
                                              @Query("page") String page,
                                              @Query("size") String size);

    /**
     * 获取图片接口
     *
     * @param id 图片id
     * @return {@link BeautyPicBean}
     */
    @GET("img")
    Observable<ApiSingleResBean<BeautyPicBean>> loadPics(@Query("id") String id);

    /**
     * 获取排行榜接口
     *
     * @param type_id 类型 1：日排行  2：周排行
     * @param page    页码
     * @param size    每页个数
     * @return {@link RankItemBean}
     */
    Observable<ApiListResBean<RankItemBean>> rank(@Query("type_id") String type_id,
                                                  @Query("page") String page,
                                                  @Query("size") String size);
}
