package com.example.admin.mydailystudy.mvp.presentIpml;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.UserRegisterAndLoginBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.UserLoginOrRegisterModel;
import com.example.admin.mydailystudy.mvp.modelImpl.UserLoginOrRegisterModelImpl;
import com.example.admin.mydailystudy.mvp.present.LoginAndRegisterPresent;
import com.example.admin.mydailystudy.mvp.view.view.LoginAndRegistView;
import com.example.admin.mydailystudy.utils.SharedPreferenceUtil;

public class LoginAndRegisterPresentImpl implements LoginAndRegisterPresent {

    private LoginAndRegistView view;

    private UserLoginOrRegisterModel model;

    public LoginAndRegisterPresentImpl(LoginAndRegistView view) {
        this.view = view;
        model = new UserLoginOrRegisterModelImpl((BaseActivity) this.view);
    }

    @Override
    public void registerUser(final String username, final String password, String repassword, final boolean checkBox) {
        view.showLoading();
        model.registerUser(username, password, repassword, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                UserRegisterAndLoginBean loginBean = (UserRegisterAndLoginBean) o;
                if (loginBean.getErrorCode() == -1) {
                    view.registerFail(loginBean.getErrorMsg());

                } else {
                    saveUserInfo(username, password, checkBox);
                    MyConstants.isLogin = true;
                    view.registerSuccess();
                }
                view.hideLoading();
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }

    @Override
    public void userLogin(final String username, final String password, final boolean checkBox) {
        view.showLoading();
        model.userLogin(username, password, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                UserRegisterAndLoginBean loginBean = (UserRegisterAndLoginBean) o;
                if (loginBean.getErrorCode() == -1) {
                    view.loginFail(loginBean.getErrorMsg());

                } else {
                    MyConstants.isLogin = true;
                    saveUserInfo(username, password, checkBox);
                    view.loginSuccess();
                }
                view.hideLoading();
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }

    @Override
    public void userLogout() {
        model.userLogout(new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse reponse = (WanAndroidBaseResponse) o;
                if (reponse.getErrorCode() == 0) {
                    MyConstants.isLogin = false;
                }

            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }

    private void saveUserInfo(String name, String pwd, boolean checkBox){
        SharedPreferenceUtil.saveData(MyApplication.getContext(), "remember_pwd", checkBox);
        SharedPreferenceUtil.saveData(MyApplication.getContext(),"user_name", name);
        SharedPreferenceUtil.saveData(MyApplication.getContext(),"user_pwd", pwd);
    }
}
