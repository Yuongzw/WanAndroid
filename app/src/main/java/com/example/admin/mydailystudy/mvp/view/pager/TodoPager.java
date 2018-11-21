package com.example.admin.mydailystudy.mvp.view.pager;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.base.BasePager;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.view.activity.MyDialogActivity;
import com.example.admin.mydailystudy.mvp.view.activity.MyTodoActivity;
import com.example.admin.mydailystudy.mvp.view.adapter.MyTodoAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoPager extends BasePager {

    private ExpandableListView expandableListView;
    private TextView tv_todo_title;
    private ImageView iv_add;
    private MyTodoAdapter todoAdapter;
    private List<String> groupTodoList;
    private List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList;

    public TodoPager(BaseActivity activity) {
        super(activity);
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.todo_fragment_layout, null);
        expandableListView = view.findViewById(R.id.expandableListView);
        tv_todo_title = view.findViewById(R.id.tv_todo_title);
        iv_add = view.findViewById(R.id.iv_add);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        tv_todo_title.setText("代办清单");
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, MyDialogActivity.class);
                intent.putExtra("type", ((MyTodoActivity)mActivity).mPage);
                intent.putExtra("mode", 0);//0：添加；1：修改；2：查看
                mActivity.startActivityForResult(intent, 0);
//                mActivity.startActivity(intent);
            }
        });
        groupTodoList = new ArrayList<>();
        childTodoList = new ArrayList<>();
        todoAdapter = new MyTodoAdapter(mActivity, groupTodoList, childTodoList);
        todoAdapter.setMode(0);
        expandableListView.setAdapter(todoAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList = todoAdapter.getChildTodoList();
                Intent intent = new Intent(mActivity, MyDialogActivity.class);
                intent.putExtra("mode", 1);
                intent.putExtra("type", ((MyTodoActivity)mActivity).mPage);
                intent.putExtra("title", childTodoList.get(groupPosition).get(childPosition).getTitle());
                intent.putExtra("content", childTodoList.get(groupPosition).get(childPosition).getContent());
                intent.putExtra("id", childTodoList.get(groupPosition).get(childPosition).getId());
                intent.putExtra("date", childTodoList.get(groupPosition).get(childPosition).getDateStr());
                intent.putExtra("status", childTodoList.get(groupPosition).get(childPosition).getStatus());
                mActivity.startActivityForResult(intent, 1);
                return true;
            }
        });

    }

    @Override
    public void setPresent(TodoDataPresent present) {
        super.setPresent(present);
        getTodoAdapter().setPresent(present);
    }

    @Override
    public MyTodoAdapter getTodoAdapter() {
        return todoAdapter;
    }

    @Override
    public void setGroupTodoList(List<String> groupTodoList) {
        this.groupTodoList.clear();
        this.groupTodoList.addAll(groupTodoList);
    }

    @Override
    public void setChildTodoList(List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList) {
        this.childTodoList.clear();
        this.childTodoList.addAll(childTodoList);
        todoAdapter.notifyDataSetChanged();
    }
}
