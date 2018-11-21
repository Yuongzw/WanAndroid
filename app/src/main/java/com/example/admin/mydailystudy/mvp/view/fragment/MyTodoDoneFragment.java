package com.example.admin.mydailystudy.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.TodoListDataBean;

import java.util.List;

@SuppressLint("ValidFragment")
public class MyTodoDoneFragment extends BaseFragment {

    private ExpandableListView expandableListView;
    private TextView tv_todo_title;
    private ImageView iv_add;

    private List<String> groupDoneList;


    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.todo_fragment_layout;
    }

    @Override
    public void initView() {
        expandableListView = mFragmentView.findViewById(R.id.expandableListView);
        tv_todo_title = mFragmentView.findViewById(R.id.tv_todo_title);
        iv_add = mFragmentView.findViewById(R.id.iv_add);
    }

    @Override
    protected void loadData() {
        iv_add.setVisibility(View.GONE);
        tv_todo_title.setText("已完成清单");
//        for (int i = 0; i < groupDoneList.size(); i++) {
//            expandableListView.expandGroup(i);
//        }
    }

    public void setGroupDoneList(List<String> groupDoneList) {
        this.groupDoneList = groupDoneList;
    }


}
