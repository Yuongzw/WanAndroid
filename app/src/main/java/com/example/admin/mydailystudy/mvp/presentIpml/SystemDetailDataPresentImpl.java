package com.example.admin.mydailystudy.mvp.presentIpml;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.SystemDetailBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.SystemDetailModel;
import com.example.admin.mydailystudy.mvp.modelImpl.SystemDetailModelImpl;
import com.example.admin.mydailystudy.mvp.present.SystemDetailDataPresent;
import com.example.admin.mydailystudy.mvp.view.view.SystemDetailDataView;

import java.util.List;

public class SystemDetailDataPresentImpl implements SystemDetailDataPresent {

    private SystemDetailModel model;

    private SystemDetailDataView view;

    public SystemDetailDataPresentImpl(SystemDetailDataView view){
        this.view = view;
        model = new SystemDetailModelImpl((BaseActivity) view);
    }

    @Override
    public void getSystemDetail(int page, int id) {
        model.getSystemDetailData(page, id, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setData((List<SystemDetailBean.DataBean.DatasBean>) o);
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
                    Toast.makeText((Context) view, "收藏成功！",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText((Context) view, "收藏失败！",Toast.LENGTH_SHORT).show();
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
