package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

import java.util.List;

public interface HomeDataModel {

    void getHomeBannerData(INetListener<Object, Throwable, Object> listener);

    void getHomeData(int mPageNum, INetListener<Object, Throwable, Object> listener);

    void collectArticle(int id, INetListener<Object, Throwable, Object> listener);

    void cancelCollected(int id, INetListener<Object, Throwable, Object> listener);
}
