package com.example.admin.mydailystudy.mvp.view.view;

import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.bean.ProjectTabBean;

import java.util.List;

public interface ProjectDataView {
    void setTabData(List<ProjectTabBean.DataBean> dataBeans);

    void setProjectDetailData(List<ProjectDetailDataBean.DataBean.DatasBean> datasBeans);
}
