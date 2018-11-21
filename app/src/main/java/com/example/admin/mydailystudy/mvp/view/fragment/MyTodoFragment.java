package com.example.admin.mydailystudy.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.event.ColletedEvent;
import com.example.admin.mydailystudy.event.TodoEvent;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.TodoDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.adapter.MyTodoAdapter;
import com.example.admin.mydailystudy.mvp.view.view.TodoDataView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MyTodoFragment extends BaseFragment implements TodoDataView {

    private ExpandableListView expandableListView;
    private TextView tv_todo_title;
    private ImageView iv_add;

    private MyTodoAdapter todoAdapter;
    private List<String> groupTodoList = new ArrayList<>();
    private List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList = new ArrayList<>();

    private TodoDataPresent present;
    private int type;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.todo_fragment_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Subscribe
    public void Event(TodoEvent todoEvent) {
        type = todoEvent.getType();
        int position = todoEvent.getPosition();
        if (position == 0) {
            present.getTodoList(type, 1);
        }else {
            present.getTodoDoneList(type, 1);
        }
    }

    @Override
    public void initView() {
        expandableListView = mFragmentView.findViewById(R.id.expandableListView);
        tv_todo_title = mFragmentView.findViewById(R.id.tv_todo_title);
        iv_add = mFragmentView.findViewById(R.id.iv_add);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        type = bundle.getInt("type");
        todoAdapter = new MyTodoAdapter(getActivity(), groupTodoList, childTodoList);
        expandableListView.setAdapter(todoAdapter);
        present = new TodoDataPresentImpl(this);
        if (position == 0) {
            tv_todo_title.setText("代办清单");
            iv_add.setVisibility(View.VISIBLE);
            present.getTodoList(type, 1);
        } else {
            tv_todo_title.setText("已完成清单");
            iv_add.setVisibility(View.GONE);
            present.getTodoDoneList(type, 1);
        }
    }

    @Override
    public void setGroupList(List<String> groups) {
        groupTodoList.clear();
        groupTodoList.addAll(groups);
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public void setChildList(List<List<TodoListDataBean.DataBean.DatasBean>> childList) {
        childTodoList.clear();
        childTodoList.addAll(childList);
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public void refresh() {

    }
}
