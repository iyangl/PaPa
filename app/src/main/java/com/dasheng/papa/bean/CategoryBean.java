package com.dasheng.papa.bean;

import android.support.annotation.DrawableRes;

import java.io.Serializable;

public class CategoryBean extends ApiBean implements Serializable{

    public CategoryBean() {
    }

    public CategoryBean(String title, int drawable, int id) {
        this.title = title;
        this.drawable = drawable;
        this.id = id;
    }

    private String title;
    private int drawable;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
