package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface SystemDetailModel {
    void getSystemDetailData(int page, int id, INetListener<Object, Throwable, Object> listener);

    void collectArticle(int id, INetListener<Object, Throwable, Object> listener);

    void cancelCollected(int id, INetListener<Object, Throwable, Object> listener);
}
