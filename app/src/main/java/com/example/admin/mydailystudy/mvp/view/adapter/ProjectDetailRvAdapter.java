package com.example.admin.mydailystudy.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.mvp.present.ProjectDataPresent;
import com.example.admin.mydailystudy.mvp.view.activity.LoginActivity;
import com.jaren.lib.view.LikeView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

public class ProjectDetailRvAdapter extends BaseQuickAdapter<ProjectDetailDataBean.DataBean.DatasBean, BaseViewHolder> {

    private Context context;
    private ProjectDataPresent present;

    public ProjectDetailRvAdapter(int layoutResId, @Nullable List<ProjectDetailDataBean.DataBean.DatasBean> data, Context context, ProjectDataPresent present) {
        super(layoutResId, data);
        this.context = context;
        this.present = present;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final ProjectDetailDataBean.DataBean.DatasBean item) {
        viewHolder.setText(R.id.tv_title, item.getTitle());
        viewHolder.setText(R.id.tv_desc, item.getDesc());
        viewHolder.setText(R.id.tv_author, item.getAuthor());
        viewHolder.setText(R.id.tv_date, item.getNiceDate());
        Glide.with(context)
                .load(item.getEnvelopePic())
                .into((ImageView) viewHolder.getView(R.id.iv_photo));
        final LikeView likeView = viewHolder.getView(R.id.lv);
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyConstants.isLogin) {
                    if (likeView.isChecked()) {
                        likeView.setChecked(false);
                        present.cancelCollected(item.getId());

                    } else {
                        likeView.setChecked(true);
                        present.collectArticle(item.getId());
                    }
                } else {
                    Toast.makeText(context, "您还没有登录！", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        viewHolder.getView(R.id.ll_project).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FinestWebView.Builder(context).show(item.getLink());
            }
        });
    }
}
