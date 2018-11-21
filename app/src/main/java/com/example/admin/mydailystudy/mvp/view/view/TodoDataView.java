package com.example.admin.mydailystudy.mvp.view.view;

import com.example.admin.mydailystudy.bean.TodoListDataBean;

import java.util.List;

public interface TodoDataView {


    void setGroupList(List<String> groups);

    void setChildList(List<List<TodoListDataBean.DataBean.DatasBean>> childList);

    void refresh();

}
