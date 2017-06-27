package com.dasheng.papa.bean;

import java.util.List;

public class VideoDetailBean extends ResBean {
    private List<ResponseItemBean> head;
    private List<ResponseItemBean> foot;

    public List<ResponseItemBean> getHead() {
        return head;
    }

    public void setHead(List<ResponseItemBean> head) {
        this.head = head;
    }

    public List<ResponseItemBean> getFoot() {
        return foot;
    }

    public void setFoot(List<ResponseItemBean> foot) {
        this.foot = foot;
    }
}
