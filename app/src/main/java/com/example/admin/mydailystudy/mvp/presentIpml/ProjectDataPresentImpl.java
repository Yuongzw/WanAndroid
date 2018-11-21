package com.example.admin.mydailystudy.mvp.presentIpml;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.bean.ProjectTabBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.ProjectDataModel;
import com.example.admin.mydailystudy.mvp.modelImpl.ProjectDataModelImpl;
import com.example.admin.mydailystudy.mvp.present.ProjectDataPresent;
import com.example.admin.mydailystudy.mvp.view.view.ProjectDataView;

import java.util.List;

public class ProjectDataPresentImpl implements ProjectDataPresent {

    private ProjectDataModel model;
    private ProjectDataView view;

    public ProjectDataPresentImpl(ProjectDataView view) {
        this.view = view;
        model = new ProjectDataModelImpl((BaseFragment) view);
    }

    @Override
    public void getProjectTab() {
        model.getProjectTab(new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setTabData((List<ProjectTabBean.DataBean>) o);
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
    public void getProjectDetailData(int page, int id) {
        model.getProjectDetailData(page, id, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setProjectDetailData((List<ProjectDetailDataBean.DataBean.DatasBean>) o);
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
