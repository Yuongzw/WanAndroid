package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.bean.ProjectTabBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.ProjectDataModel;
import com.example.admin.mydailystudy.mvp.view.fragment.ProjectDetailFragment;
import com.example.admin.mydailystudy.mvp.view.fragment.ProjectFragment;
import com.example.admin.mydailystudy.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProjectDataModelImpl implements ProjectDataModel {

    private ProjectFragment fragment;
    private ProjectDetailFragment fragment1;
    private Subscriber subscriber;

    public ProjectDataModelImpl(BaseFragment fragment) {
        if (fragment instanceof ProjectDetailFragment) {
            this.fragment1 = (ProjectDetailFragment) fragment;
        } else {
            this.fragment = (ProjectFragment) fragment;
        }

    }

    @Override
    public void getProjectTab(final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<ProjectTabBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(ProjectTabBean projectTabBean) {
                listener.success(projectTabBean.getData());
            }
        };
        if (fragment != null){
            fragment.addSubscription(subscriber);
        }
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getProjectTabData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getProjectDetailData(int page, int id, final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<ProjectDetailDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(ProjectDetailDataBean projectDetailDataBean) {
                listener.success(projectDetailDataBean.getData().getDatas());
            }
        };
        if (fragment1 != null){
            fragment1.addSubscription(subscriber);
        }
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getProjectDetailData(page, id)
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
            public void onNext(WanAndroidBaseResponse response) {
                listener.success(response);
            }
        };
        if (fragment != null){
            fragment.addSubscription(subscriber);
        }
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
            public void onNext(WanAndroidBaseResponse response) {
                listener.success(response);
            }
        };
        if (fragment != null){
            fragment.addSubscription(subscriber);
        }
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().cancelColleted(id)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }
}
