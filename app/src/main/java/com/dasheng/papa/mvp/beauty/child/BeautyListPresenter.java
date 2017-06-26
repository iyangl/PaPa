package com.dasheng.papa.mvp.beauty.child;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;

import rx.Observable;

public class BeautyListPresenter {

    private BeautyListContact.View mView;
    private BeautyListContact.Model mModel;

    public BeautyListPresenter(BeautyListContact.View view) {
        mView = view;
        mModel = initModel();
    }

    public void loadPics(String id) {
        mModel.loadPics(id)
                .subscribe(new BaseSubscriber<ApiSingleResBean<BeautyPicBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e);
                    }

                    @Override
                    public void onNext(ApiSingleResBean<BeautyPicBean> beautyPicBeanApiSingleResBean) {
                        mView.onLoadPicsSuccess(beautyPicBeanApiSingleResBean);
                    }
                });
    }


    private BeautyListContact.Model initModel() {
        return new BeautyListContact.Model() {
            @Override
            public Observable<ApiSingleResBean<BeautyPicBean>> loadPics(String id) {
                return ApiFactory.loadPics(id, mView);
            }
        };
    }

}
