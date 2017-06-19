package com.dasheng.papa.bean;

import android.support.annotation.DrawableRes;

public class CategoryBean extends ApiBean {

    public CategoryBean() {
    }

    public CategoryBean(String title, int drawable, String tag) {
        this.title = title;
        this.drawable = drawable;
        this.tag = tag;
    }

    private String title;
    private int drawable;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(@DrawableRes int drawable) {
        this.drawable = drawable;
    }
}
