package com.dasheng.papa.bean;

public class ApiSingleResBean<T extends ResBean> extends ApiBean {

    private T res;

    public ApiSingleResBean() {
    }

    public T getRes() {
        return res;
    }

    public void setRes(T res) {
        this.res = res;
    }
}
