package com.dasheng.papa.bean;

import java.io.Serializable;
import java.util.List;

public class ApiListResBean<T extends ResBean> extends ApiBean implements Serializable{
    private List<T> res;
    private List<T> banner;

    public List<T> getBanner() {
        return banner;
    }

    public void setBanner(List<T> banner) {
        this.banner = banner;
    }

    public List<T> getRes() {
        return res;
    }

    public void setRes(List<T> res) {
        this.res = res;
    }
}
