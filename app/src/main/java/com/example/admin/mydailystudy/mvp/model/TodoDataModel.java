package com.example.admin.mydailystudy.mvp.model;

import com.example.admin.mydailystudy.mvp.INetListener;

public interface TodoDataModel {
    void getTodoList(int type, int page, INetListener<Object, Throwable, Object> listener);

    void getTodoDoneList(int type, int page, INetListener<Object, Throwable, Object> listener);

    void addTodo(String title, String content, String date, int type, INetListener<Object, Throwable, Object> listener);

    void updateTodo(int id, String title, String content, String date, int status, int type, INetListener<Object, Throwable, Object> listener);

    void deleteTodo(int id, INetListener<Object, Throwable, Object> listener);
}
