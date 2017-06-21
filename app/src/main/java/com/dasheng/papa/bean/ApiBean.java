package com.dasheng.papa.bean;

import android.databinding.BaseObservable;

public class ApiBean extends BaseObservable {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
