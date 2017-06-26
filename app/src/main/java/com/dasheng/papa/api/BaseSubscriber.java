package com.dasheng.papa.api;

import com.dasheng.papa.R;
import com.dasheng.papa.app;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.NetWorkUtil;
import com.dasheng.papa.util.ToastUtil;

import rx.Subscriber;

public abstract class BaseSubscriber<T extends ApiBean> extends Subscriber<T> {
    @Override
    public void onStart() {
        if (!NetWorkUtil.isNetWorkAvailable(app.getInstance())) {
            ToastUtil.show(CommonUtils.getString(R.string.network_unavailable));
            onCompleted();
        }
        super.onStart();
    }
}
