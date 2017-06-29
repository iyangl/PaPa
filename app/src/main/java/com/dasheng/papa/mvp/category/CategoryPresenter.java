package com.dasheng.papa.mvp.category;

import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;

import rx.Observable;

public class CategoryPresenter {

    private CategoryContact.View view;
    private CategoryContact.Model model;

    public CategoryPresenter(CategoryContact.View view) {
        this.view = view;
        this.model = getModel();
    }

    public void loadCategory() {
        model.loadCategoryInfo()
                .subscribe(new BaseSubscriber<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApiListResBean<ResponseItemBean> responseItemBeanApiListResBean) {
                        view.onLoadCategoryInfoSuccess(responseItemBeanApiListResBean);
                    }
                });
    }


    private CategoryContact.Model getModel() {
        return new CategoryContact.Model() {
            @Override
            public Observable<ApiListResBean<ResponseItemBean>> loadCategoryInfo() {
                return ApiFactory.rank(null, null, null, view);
            }
        };
    }
}
