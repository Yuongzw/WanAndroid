package com.example.admin.mydailystudy.mvp.present;

public interface CollectArticleDataPresent {
    void getCollectArticle(int page);

    void cancelCollect(int id, int originId);
}
