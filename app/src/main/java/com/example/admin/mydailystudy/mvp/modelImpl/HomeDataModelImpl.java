package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.HomeBannerDataBean;
import com.example.admin.mydailystudy.bean.HomeDataBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.HomeDataModel;
import com.example.admin.mydailystudy.mvp.view.fragment.StudyFragment;
import com.example.admin.mydailystudy.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeDataModelImpl implements HomeDataModel {

    private StudyFragment fragment;
    private Subscriber subscriber;

    public HomeDataModelImpl(BaseFragment fragment) {
        this.fragment = (StudyFragment) fragment;
    }

    @Override
    public void getHomeBannerData(final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<HomeBannerDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomeBannerDataBean homeBannerDataBean) {
                listener.success(homeBannerDataBean.getData());
            }
        };
        fragment.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getHomeBannerData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getHomeData(int mPageNum, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<HomeDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomeDataBean homeDataBean) {
              listener.success(homeDataBean.getData().getDatas());
            }
        };
        fragment.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getHomeData(mPageNum)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
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
        fragment.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().collectArticle(id)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
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
        fragment.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().cancelColleted(id)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

}
