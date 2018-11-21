package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.CollectArticleBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.CollectArticleDataModel;
import com.example.admin.mydailystudy.net.MyRetrofit;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectArticleDataModelImpl implements CollectArticleDataModel {

    private BaseActivity activity;
    private Subscriber subscriber;

    public CollectArticleDataModelImpl(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getCollectArticleData(int page, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<CollectArticleBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            listener.failed(e);
            }

            @Override
            public void onNext(CollectArticleBean collectArticleBean) {
                List<CollectArticleBean.DataBean.DatasBean> datas = collectArticleBean.getData().getDatas();
                listener.success(datas);
            }

        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getCollected(page)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    @Override
    public void cancelCollection(int id, int originId, final INetListener<Object, Throwable, Object> listener) {
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
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().cancelColleted(id, originId)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }
}
