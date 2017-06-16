package com.dasheng.papa.mvp.home;

public class HomePresenter {

    private HomeContact.View view;
    private HomeContact.Model model;

    public HomePresenter(HomeContact.View view) {
        this.view = view;
        this.model = getModel();
    }

    private HomeContact.Model getModel() {
        return new HomeContact.Model() {
        };
    }
}
