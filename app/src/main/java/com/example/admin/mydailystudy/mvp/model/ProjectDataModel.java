package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface ProjectDataModel {
    void getProjectTab(INetListener<Object, Throwable, Object> listener);

    void getProjectDetailData(int page, int id, INetListener<Object, Throwable, Object> listener);

    void collectArticle(int id, INetListener<Object, Throwable, Object> listener);

    void cancelCollected(int id, INetListener<Object, Throwable, Object> listener);

}
