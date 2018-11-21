package com.example.admin.mydailystudy.mvp.presentIpml;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.HomeBannerDataBean;
import com.example.admin.mydailystudy.bean.HomeDataBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.HomeDataModel;
import com.example.admin.mydailystudy.mvp.modelImpl.HomeDataModelImpl;
import com.example.admin.mydailystudy.mvp.present.HomeDataPresent;
import com.example.admin.mydailystudy.mvp.view.view.HomeDataView;

import java.util.List;

public class HomeDataPresentImpl implements HomeDataPresent {

    private HomeDataModel model;
    private HomeDataView view;

    public HomeDataPresentImpl(HomeDataView view) {
        this.view = view;
        model = new HomeDataModelImpl((BaseFragment) view);
    }

    @Override
    public void getHomeBannerData() {
        model.getHomeBannerData(new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setBannerData((List<HomeBannerDataBean.DataBean>) o);
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
    public void getHomeData(int mPageNum) {
        model.getHomeData(mPageNum, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setHomeData((List<HomeDataBean.DataBean.DatasBean>) o);
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
    public void collectArticle(int id) {
        model.collectArticle(id, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse reponse = (WanAndroidBaseResponse) o;
                if (reponse.getErrorCode() == 0) {
                    Toast.makeText((Context) view, "收藏成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText((Context) view, "收藏失败！", Toast.LENGTH_SHORT).show();
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

    @Override
    public void cancelCollected(int id) {
        model.cancelCollected(id, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse reponse = (WanAndroidBaseResponse) o;
                if (reponse.getErrorCode() == 0) {
                    Toast.makeText((Context) view, "取消收藏成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText((Context) view, "取消收藏失败！",Toast.LENGTH_SHORT).show();
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
