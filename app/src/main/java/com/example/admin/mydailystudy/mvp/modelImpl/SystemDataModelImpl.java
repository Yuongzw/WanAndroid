package com.example.admin.mydailystudy.mvp.modelImpl;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.SystemDataModel;
import com.example.admin.mydailystudy.mvp.view.fragment.SystemFragment;
import com.example.admin.mydailystudy.net.MyRetrofit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SystemDataModelImpl implements SystemDataModel {

    private SystemFragment fragment;
    private Subscriber<SystemDataBean> subscriber;

    public SystemDataModelImpl(BaseFragment fragment) {
        this.fragment = (SystemFragment) fragment;
    }


    @Override
    public void getSystemData(final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<SystemDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(SystemDataBean systemDataBean) {
                listener.success(systemDataBean.getData());
            }
        };
        fragment.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getSystemData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
