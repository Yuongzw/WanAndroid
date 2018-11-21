package com.example.admin.mydailystudy.base;

import android.view.View;

import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.view.adapter.MyTodoAdapter;

import java.util.List;

public abstract class BasePager {

    public BaseActivity mActivity;

    public View mRootView;// 根布局对象

    public BasePager(BaseActivity activity) {
        mActivity = activity;
        mRootView = initViews();
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public void initData() {

    }

    public MyTodoAdapter getTodoAdapter() {
        return null;
    }

    public void setGroupTodoList(List<String> groupTodoList) {

    }

    public void setChildTodoList(List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList) {
    }

    public void setPresent(TodoDataPresent present){}
}
