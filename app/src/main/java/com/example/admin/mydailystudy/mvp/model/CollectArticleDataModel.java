package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface CollectArticleDataModel {

    void getCollectArticleData(int page, INetListener<Object, Throwable, Object> listener);

    void cancelCollection(int id, int originId, INetListener<Object, Throwable, Object> listener);
}
