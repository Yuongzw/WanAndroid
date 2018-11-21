package com.example.admin.mydailystudy.base;

import android.arch.lifecycle.LifecycleOwner;

/**
 * Description : mvp模式抽象vp的解绑和绑定过程
 */

public interface MvpCallback<V extends IView & LifecycleOwner,P extends IPresenter<V>> {
    //创建Presenter  调用在init中
    P createPresenter();

    //创建View
    V createView();

    P getPresenter();

    void setPresenter(P presenter);

    V getMvpView();

    void setMvpView(V view);
}