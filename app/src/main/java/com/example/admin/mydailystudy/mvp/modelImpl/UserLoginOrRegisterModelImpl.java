package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.UserRegisterAndLoginBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.UserLoginOrRegisterModel;
import com.example.admin.mydailystudy.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserLoginOrRegisterModelImpl implements UserLoginOrRegisterModel {

    private BaseActivity activity;
    private Subscriber subscriber;

    public UserLoginOrRegisterModelImpl(BaseActivity activity) {
        this.activity =  activity;
    }

    @Override
    public void registerUser(String username, String password, String repassword, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<UserRegisterAndLoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(UserRegisterAndLoginBean userRegisterAndLoginBean) {

                listener.success(userRegisterAndLoginBean);
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().registerUser(username, password, repassword)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void userLogin(String username, String password, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<UserRegisterAndLoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(UserRegisterAndLoginBean userRegisterAndLoginBean) {

                listener.success(userRegisterAndLoginBean);

            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().userLogin(username, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void userLogout(final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<WanAndroidBaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(WanAndroidBaseResponse reponse) {
                listener.success(reponse);
            }
        };

        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().logout()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
