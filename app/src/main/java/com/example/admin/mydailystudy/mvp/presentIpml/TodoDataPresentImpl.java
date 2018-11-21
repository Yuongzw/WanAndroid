package com.example.admin.mydailystudy.mvp.presentIpml;

import android.content.Context;
import android.widget.Toast;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.bean.WanAndroidBaseResponse;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.TodoDataModel;
import com.example.admin.mydailystudy.mvp.modelImpl.TodoDataModelImpl;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.view.view.TodoDataView;

import java.util.ArrayList;
import java.util.List;

public class TodoDataPresentImpl implements TodoDataPresent {
    private TodoDataView view;
    private TodoDataModel model;

    public TodoDataPresentImpl(TodoDataView view) {
        this.view = view;
        model = new TodoDataModelImpl((BaseActivity) view);
    }

    @Override
    public void getTodoList(int type, int page) {
        model.getTodoList(type, page, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                TodoListDataBean listDataBean = (TodoListDataBean) o;
                List<TodoListDataBean.DataBean.DatasBean> datas = listDataBean.getData().getDatas();
                List<String> group = new ArrayList<>();
                List<List<TodoListDataBean.DataBean.DatasBean>> child = new ArrayList<>();
                //        按日期分类
                String date = datas.get(0).getDateStr();
                List<TodoListDataBean.DataBean.DatasBean> sameList = new ArrayList<>();
                child.clear();
                for (int i = 0; i < datas.size(); i++) {
                    if (date.equals(datas.get(i).getDateStr())) {
                        sameList.add(datas.get(i));

                    } else {
                        List<TodoListDataBean.DataBean.DatasBean> tempList = new ArrayList<>();
                        tempList.addAll(sameList);
                        child.add(tempList);
                        group.add(tempList.get(0).getDateStr());
                        view.setGroupList(group);
                        view.setChildList(child);
                        sameList.clear();
                        date = datas.get(i).getDateStr();
                        sameList.add(datas.get(i));
                    }
                    if (i == datas.size() - 1) {
                        List<TodoListDataBean.DataBean.DatasBean> tempList = new ArrayList<>();
                        tempList.addAll(sameList);
                        child.add(tempList);
                        group.add(tempList.get(0).getDateStr());
                        view.setGroupList(group);
                        view.setChildList(child);
                    }
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
    public void getTodoDoneList(int type, int page) {
        model.getTodoDoneList(type, page, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                TodoListDataBean listDataBean = (TodoListDataBean) o;
                List<TodoListDataBean.DataBean.DatasBean> datas = listDataBean.getData().getDatas();
                List<String> group = new ArrayList<>();
                List<List<TodoListDataBean.DataBean.DatasBean>> child = new ArrayList<>();
                //        按日期分类
                String date = datas.get(0).getDateStr();
                List<TodoListDataBean.DataBean.DatasBean> sameList = new ArrayList<>();
                child.clear();
                for (int i = 0; i < datas.size(); i++) {
                    if (date.equals(datas.get(i).getDateStr())) {
                        sameList.add(datas.get(i));

                    } else {
                        List<TodoListDataBean.DataBean.DatasBean> tempList = new ArrayList<>();
                        tempList.addAll(sameList);
                        child.add(tempList);
                        group.add(tempList.get(0).getDateStr());
                        view.setGroupList(group);
                        view.setChildList(child);
                        sameList.clear();
                        date = datas.get(i).getDateStr();
                        sameList.add(datas.get(i));
                    }
                    if (i == datas.size() - 1) {
                        List<TodoListDataBean.DataBean.DatasBean> tempList = new ArrayList<>();
                        tempList.addAll(sameList);
                        child.add(tempList);
                        group.add(tempList.get(0).getDateStr());
                        view.setGroupList(group);
                        view.setChildList(child);
                    }
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
    public void addTodo(String title, String content, String date, int type) {
        model.addTodo(title, content, date, type, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse response = (WanAndroidBaseResponse) o;
                if (response.getErrorCode() == 0) {
                    Toast.makeText(MyApplication.getContext(), "添加成功", Toast.LENGTH_SHORT).show();
//                    view.refresh();
                } else {
                    Toast.makeText(MyApplication.getContext(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
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
    public void updateTodo(int id, String title, String content, String date, int status, int type) {
        model.updateTodo(id, title, content, date, status, type, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse response = (WanAndroidBaseResponse) o;
                if (response.getErrorCode() == 0) {
                    Toast.makeText(MyApplication.getContext(), "更新成功", Toast.LENGTH_SHORT).show();
//                    view.refresh();
                } else {
                    Toast.makeText(MyApplication.getContext(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
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
    public void deleteTodo(int id) {
        model.deleteTodo(id, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                WanAndroidBaseResponse response = (WanAndroidBaseResponse) o;
                if (response.getErrorCode() == 0) {
                    Toast.makeText(MyApplication.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyApplication.getContext(), response.getErrorMsg(), Toast.LENGTH_SHORT).show();
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
