package com.dasheng.papa.bean;

import android.databinding.BaseObservable;

import java.util.List;

public class ApiBean<T extends ResBean> extends BaseObservable {

    private int status;
    private String msg;
    private int num;
    private int total;
    private List<T> res;
    private List<T> banner;

    public List<T> getBanner() {
        return banner;
    }

    public void setBanner(List<T> banner) {
        this.banner = banner;
    }

    public ApiBean() {
    }

    public List<T> getRes() {
        return res;
    }

    public void setRes(List<T> res) {
        this.res = res;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
