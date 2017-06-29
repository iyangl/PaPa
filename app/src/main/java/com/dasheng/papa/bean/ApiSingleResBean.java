package com.dasheng.papa.bean;

import java.io.Serializable;

public class ApiSingleResBean<T extends ResBean> extends ApiBean implements Serializable{

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
