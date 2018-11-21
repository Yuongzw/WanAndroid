package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface UserLoginOrRegisterModel {

    void registerUser(String username, String password, String repassword, INetListener<Object, Throwable, Object> listener);

    void userLogin(String username, String password, INetListener<Object, Throwable, Object> listener);

    void userLogout(INetListener<Object, Throwable, Object> listener);
}
