package com.example.admin.mydailystudy.mvp.present;

public interface LoginAndRegisterPresent {
    void registerUser(String username, String password, String repassword, boolean checkBox);

    void userLogin(String username, String password, boolean checkBox);

    void userLogout();
}
