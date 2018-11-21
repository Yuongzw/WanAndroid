package com.example.admin.mydailystudy.mvp.view.view;

import com.example.admin.mydailystudy.bean.HotKeyDataBean;
import com.example.admin.mydailystudy.bean.SearchByKeyDataBean;

import java.util.List;

public interface HotKeyDataView {
    void setData(List<HotKeyDataBean.DataBean> dataBeans);

    void setSearchResultData(List<SearchByKeyDataBean.DataBean.DatasBean> datasBeans);
}
