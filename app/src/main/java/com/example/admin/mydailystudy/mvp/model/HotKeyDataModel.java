package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface HotKeyDataModel {
    void getHotKeyData(INetListener<Object, Throwable, Object> listener);
    void getSearchResult(int page, String keyWord, INetListener<Object, Throwable, Object> listener);
}
