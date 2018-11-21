package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.TodoDataModel;
import com.example.admin.mydailystudy.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TodoDataModelImpl implements TodoDataModel {
    private BaseActivity activity;
    private Subscriber subscriber;

    public TodoDataModelImpl(BaseActivity fragment) {
        this.activity = fragment;
    }

    @Override
    public void getTodoList(int type, int page, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<TodoListDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(TodoListDataBean listDataBean) {
                listener.success(listDataBean);
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getTodoList(type, page)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    @Override
    public void getTodoDoneList(int type, int page, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<TodoListDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(TodoListDataBean listDataBean) {
                listener.success(listDataBean);
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getTodoDoneList(type, page)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    @Override
    public void addTodo(String title, String content, String date, int type, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<WanAndroidBaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(WanAndroidBaseResponse wanAndroidBaseResponse) {
                listener.success(wanAndroidBaseResponse);
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().addTodo(title, content, date, type)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者

    }

    @Override
    public void updateTodo(int id, String title, String content, String date, int status, int type, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<WanAndroidBaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(WanAndroidBaseResponse wanAndroidBaseResponse) {
                listener.success(wanAndroidBaseResponse);
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().updateTodo(id, title, content, date, status, type)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    @Override
    public void deleteTodo(int id, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<WanAndroidBaseResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(WanAndroidBaseResponse wanAndroidBaseResponse) {
                listener.success(wanAndroidBaseResponse);
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().deleteTodo(id)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }
}
