package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.SystemDetailBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.SystemDetailModel;
import com.example.admin.mydailystudy.mvp.view.activity.SystemDetailActivity;
import com.example.admin.mydailystudy.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SystemDetailModelImpl implements SystemDetailModel {
    private SystemDetailActivity activity;
    private Subscriber subscriber;

    public SystemDetailModelImpl(BaseActivity activity) {
        this.activity = (SystemDetailActivity) activity;
    }

    @Override
    public void getSystemDetailData(int page, int id, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<SystemDetailBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SystemDetailBean systemDetailBean) {
                listener.success(systemDetailBean.getData().getDatas());
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(activity).myGetNetData().getSystemDetailData(page, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void collectArticle(int id, final INetListener<Object, Throwable, Object> listener) {
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
        MyRetrofit.getInstance(activity).myGetNetData().collectArticle(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void cancelCollected(int id, final INetListener<Object, Throwable, Object> listener) {
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
        MyRetrofit.getInstance(activity).myGetNetData().cancelColleted(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
