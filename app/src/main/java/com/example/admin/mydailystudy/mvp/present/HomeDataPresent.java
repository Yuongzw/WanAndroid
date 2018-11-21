package com.example.admin.mydailystudy.mvp.present;

public interface HomeDataPresent {

    void getHomeBannerData();

    void getHomeData(int mPageNum);

    void collectArticle(int id);

    void cancelCollected(int id);
}
