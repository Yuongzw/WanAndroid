package com.example.admin.mydailystudy.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

public class SystemAdapter extends  RecyclerView.Adapter {

    private Context context;
    private MyViewHolder viewHolder;
    private List<SystemDataBean.DataBean> dataBeans;

    public SystemAdapter(Context context, List<SystemDataBean.DataBean> dataBeans) {
        this.context = context;
        this.dataBeans = dataBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.system_rv_item, viewGroup, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((MyViewHolder)viewHolder).tv_head.setText(dataBeans.get(position).getName());
        setFlowLayout(viewHolder, position);

    }

    private void setFlowLayout(RecyclerView.ViewHolder viewHolder, int position) {
        ((MyViewHolder)viewHolder).flowLayout.removeAllViews();
        for (SystemDataBean.DataBean.ChildrenBean childrenBean : dataBeans.get(position).getChildren()) {
            TextView textView = new TextView(context);
            textView.setPadding(16, 0, 16, 0);
            textView.setText(childrenBean.getName());
            textView.setTextSize(14);
            textView.setBackgroundResource(R.drawable.flow_layout_bg);
            ((MyViewHolder)viewHolder).flowLayout.addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_head;

        private FlowLayout flowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_head = itemView.findViewById(R.id.tv_head);
            flowLayout = itemView.findViewById(R.id.flow_layout);
        }
    }
}
