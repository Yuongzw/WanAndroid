package com.example.admin.mydailystudy.mvp.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.view.activity.MyTodoActivity;

import java.util.List;

public class MyTodoAdapter extends BaseExpandableListAdapter {
    private List<String> groupList;//外层的数据源
    private List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList;//里层的数据源
    private Context context;
    private ChildViewHolder childViewHolder;
    private int mode;
    private int status;
    private TodoDataPresent present;


    public MyTodoAdapter(Context context, List<String> groupList, List<List<TodoListDataBean.DataBean.DatasBean>> childTodoList) {
        this.context = context;
        this.groupList = groupList;
        this.childTodoList = childTodoList;
    }

    public void setPresent(TodoDataPresent present) {
        this.present = present;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childTodoList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childTodoList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int getGroupId) {
        return getGroupId;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = View.inflate(context, R.layout.exspanlist_group_item, null);
        } else {
            view = convertView;
        }
        //分组名字
        TextView textView = view.findViewById(R.id.tv_date);
        textView.setText(groupList.get(groupPosition));
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.exspanlist_child_item, viewGroup, false);
            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        if (childTodoList.get(groupPosition).get(childPosition).getContent().equals("")) {
            childViewHolder.tv_content_or_finishDate.setVisibility(View.GONE);
        } else {
            childViewHolder.tv_content_or_finishDate.setVisibility(View.VISIBLE);
            childViewHolder.tv_content_or_finishDate.setText(childTodoList.get(groupPosition).get(childPosition).getContent());
        }
        childViewHolder.tv_todo_title.setText(childTodoList.get(groupPosition).get(childPosition).getTitle());
        if (mode == 0) {
            childViewHolder.iv_complete_or_cancel.setImageResource(R.drawable.ic_complete);
        } else {
            childViewHolder.iv_complete_or_cancel.setImageResource(R.drawable.ic_recovery);
        }
        childViewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(groupPosition, childPosition);
            }
        });
        childViewHolder.iv_complete_or_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == 0){
                    status = 1;
                } else {
                    status = 0;
                }
                present.updateTodo(childTodoList.get(groupPosition).get(childPosition).getId(), childTodoList.get(groupPosition).get(childPosition).getTitle(),
                        childTodoList.get(groupPosition).get(childPosition).getContent(), childTodoList.get(groupPosition).get(childPosition).getDateStr(),
                        status, childTodoList.get(groupPosition).get(childPosition).getType());
                childTodoList.get(groupPosition).remove(childPosition);
                if (childTodoList.get(groupPosition).size() == 0) {
                    groupList.remove(groupPosition);
                }
                notifyDataSetChanged();
                ((MyTodoActivity)context).refresh();
            }
        });
        return view;
    }

    private void showDialog(final int groupPosition, final int childPosition) {
        final AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle("删除Todo");
        localBuilder.setMessage("确定要删除该Todo？");
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                present.deleteTodo(childTodoList.get(groupPosition).get(childPosition).getId());
                childTodoList.get(groupPosition).remove(childPosition);
                if (childTodoList.get(groupPosition).size() == 0) {
                    groupList.remove(groupPosition);
                }
                notifyDataSetChanged();
            }
        });
        localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                /**
                 * 确定操作
                 * */
            }
        });

        /***
         * 设置点击返回键不会消失
         * */
        localBuilder.setCancelable(true).create();

        localBuilder.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public List<List<TodoListDataBean.DataBean.DatasBean>> getChildTodoList() {
        return childTodoList;
    }

    public class ChildViewHolder {
        private ImageView iv_complete_or_cancel;
        private TextView tv_todo_title;
        private ImageView iv_delete;
        private TextView tv_content_or_finishDate;

        ChildViewHolder(View v) {
            iv_complete_or_cancel = v.findViewById(R.id.iv_complete_or_cancel);
            tv_todo_title = v.findViewById(R.id.tv_todo_title);
            iv_delete = v.findViewById(R.id.iv_delete);
            tv_content_or_finishDate = v.findViewById(R.id.tv_content_or_finishDate);
        }
    }
}
