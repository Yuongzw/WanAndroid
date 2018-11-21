package com.example.admin.mydailystudy.mvp.presentIpml;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.CollectArticleBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.CollectArticleDataModel;
import com.example.admin.mydailystudy.mvp.modelImpl.CollectArticleDataModelImpl;
import com.example.admin.mydailystudy.mvp.present.CollectArticleDataPresent;
import com.example.admin.mydailystudy.mvp.view.view.CollectArticleDataView;

import java.util.List;

public class CollectArticleDataPresentImpl implements CollectArticleDataPresent {

    private CollectArticleDataView view;
    private CollectArticleDataModel model;

    public CollectArticleDataPresentImpl(CollectArticleDataView view) {
        this.view = view;
        model = new CollectArticleDataModelImpl((BaseActivity) view);
    }

    @Override
    public void getCollectArticle(int page) {
        model.getCollectArticleData(page, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                List<CollectArticleBean.DataBean.DatasBean> datas = (List<CollectArticleBean.DataBean.DatasBean>) o;
                view.setData(datas);
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }

    @Override
    public void cancelCollect(int id, int originId) {
        model.cancelCollection(id,originId, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse reponse = (WanAndroidBaseResponse) o;
                if (reponse.getErrorCode() == -1) {
                    Toast.makeText((Context) view, "取消收藏失败！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText((Context) view, "取消收藏成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }
}
