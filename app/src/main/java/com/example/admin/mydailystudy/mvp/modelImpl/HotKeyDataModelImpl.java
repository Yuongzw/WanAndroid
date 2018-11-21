package com.example.admin.mydailystudy.mvp.modelImpl;

import android.support.annotation.MainThread;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.HotKeyDataBean;
import com.example.admin.mydailystudy.bean.SearchByKeyDataBean;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.HotKeyDataModel;
import com.example.admin.mydailystudy.mvp.view.activity.SearchActivity;
import com.example.admin.mydailystudy.net.MyRetrofit;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotKeyDataModelImpl implements HotKeyDataModel {

    private SearchActivity activity;
    private Subscriber<HotKeyDataBean> subscriber;
    private Subscriber<SearchByKeyDataBean> subscriber1;

    public HotKeyDataModelImpl(SearchActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getHotKeyData(final INetListener<Object, Throwable, Object> listener) {
        subscriber = new Subscriber<HotKeyDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(HotKeyDataBean hotKeyDataBean) {
                listener.success(hotKeyDataBean.getData());
            }
        };
        activity.addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getHotKeyData()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    public void getSearchResult(int page, String keyWord, final INetListener<Object, Throwable, Object> listener) {
        subscriber1 = new Subscriber<SearchByKeyDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.failed(e);
            }

            @Override
            public void onNext(SearchByKeyDataBean searchByKeyDataBean) {
                List<SearchByKeyDataBean.DataBean.DatasBean> datas = searchByKeyDataBean.getData().getDatas();
                for (int j = 0; j < datas.size(); j++) {
                    String[] split = datas.get(j).getTitle().split("<em class='highlight'>");
                    String str = "";
                    String str1 = "";
                    for (int i = 0; i < split.length; i++) {
                        str += split[i];
                    }
                    String[] split1 = str.split("</em>");
                    for (int i = 0; i < split1.length; i++) {
                        str1 += split1[i];
                    }
                    datas.get(j).setTitle(str1);
                }

                listener.success(datas);
            }
        };
        activity.addSubscription(subscriber1);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getSearchData(page, keyWord)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);
    }
}
