package com.example.admin.mydailystudy.mvp.view.view;

import com.example.admin.mydailystudy.bean.HomeBannerDataBean;
import com.example.admin.mydailystudy.bean.HomeDataBean;

import java.util.List;

public interface HomeDataView {

    void setHomeData(List<HomeDataBean.DataBean.DatasBean> datasBeans);

    void setBannerData(List<HomeBannerDataBean.DataBean> bannerBeans);
}
