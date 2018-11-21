package com.example.admin.mydailystudy.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.mvp.view.activity.SystemDetailActivity;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

public class SystemRecyclerViewAdapter extends BaseQuickAdapter<SystemDataBean.DataBean, BaseViewHolder> {

    private Context context;
    public SystemRecyclerViewAdapter(int layoutResId, @Nullable List<SystemDataBean.DataBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, SystemDataBean.DataBean item) {
        viewHolder.setText(R.id.tv_head, item.getName());
        List<SystemDataBean.DataBean.ChildrenBean> children = item.getChildren();
        setFlowLayout(viewHolder, children);
    }

    private void setFlowLayout(BaseViewHolder viewHolder,List<SystemDataBean.DataBean.ChildrenBean> children) {
        ((FlowLayout)viewHolder.getView(R.id.flow_layout)).removeAllViews();
        for (final SystemDataBean.DataBean.ChildrenBean childrenBean : children) {
            TextView textView = new TextView(context);
            textView.setPadding(16, 8, 16, 8);
            textView.setText(childrenBean.getName());
            textView.setTextSize(14);
            textView.setBackgroundResource(R.drawable.flow_layout_bg);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SystemDetailActivity.class);
                    intent.putExtra("id", childrenBean.getId());
                    intent.putExtra("name", childrenBean.getName());
                    context.startActivity(intent);
                }
            });
            ((FlowLayout)viewHolder.getView(R.id.flow_layout)).addView(textView);
        }
    }

}
