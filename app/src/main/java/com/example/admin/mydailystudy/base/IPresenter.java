package com.example.admin.mydailystudy.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

/**
 * Description : 顶级Presenter接口
 */

public interface IPresenter<V extends IView & LifecycleOwner> extends LifecycleObserver {

    /**
     * 创建view时调用  调用在initView 之后
     */
    void attachView(V view);

    /**
     * view销毁时调用释放资源
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void detachView();

    Context getContext();
}
