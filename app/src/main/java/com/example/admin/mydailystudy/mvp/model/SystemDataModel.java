package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface SystemDataModel {
    void getSystemData(INetListener<Object, Throwable, Object> listener);
}

