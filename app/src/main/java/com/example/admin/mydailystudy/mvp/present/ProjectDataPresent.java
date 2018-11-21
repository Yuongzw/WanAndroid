package com.example.admin.mydailystudy.mvp.present;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface ProjectDataPresent {

    void getProjectTab();

    void getProjectDetailData(int page, int id);

    void collectArticle(int id);

    void cancelCollected(int id);
}
