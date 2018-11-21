package com.example.admin.mydailystudy.mvp.view.view;

public interface LoginAndRegistView {

    void showLoading();

    void hideLoading();

    void registerSuccess();

    void registerFail(String msg);

    void loginFail(String msg);

    void loginSuccess();

}
