package com.example.admin.mydailystudy.mvp.present;

public interface SystemDetailDataPresent {

    void getSystemDetail(int page, int id);

    void collectArticle(int id);

    void cancelCollected(int id);
}
