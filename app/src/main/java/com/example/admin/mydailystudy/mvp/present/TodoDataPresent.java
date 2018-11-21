package com.example.admin.mydailystudy.mvp.present;

public interface TodoDataPresent {
    void getTodoList(int type, int page);

    void getTodoDoneList(int type, int page);

    void addTodo(String title, String content, String date, int type);

    void updateTodo(int id, String title, String content, String date, int status, int type);

    void deleteTodo(int id);
}
