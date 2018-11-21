package com.example.admin.mydailystudy.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.bean.SystemDetailBean;
import com.example.admin.mydailystudy.event.ColletedEvent;
import com.example.admin.mydailystudy.mvp.present.SystemDetailDataPresent;
import com.example.admin.mydailystudy.mvp.view.activity.LoginActivity;
import com.jaren.lib.view.LikeView;
import com.thefinestartist.finestwebview.FinestWebView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Random;

public class SystemDetailRvAdapter  extends BaseQuickAdapter<SystemDetailBean.DataBean.DatasBean, BaseViewHolder> {

    private final int[] array;
    private Context context;
    private int[] imageResources;
    private SystemDetailDataPresent present;

    public SystemDetailRvAdapter(int layoutResId, @Nullable List<SystemDetailBean.DataBean.DatasBean> data, Context context, SystemDetailDataPresent present) {
        super(layoutResId, data);
        this.context = context;
        this.present = present;
        array = new int[]{
                context.getResources().getColor(R.color.cardView),
                context.getResources().getColor(R.color.cardView1),
                context.getResources().getColor(R.color.cardView2),
                context.getResources().getColor(R.color.colorAccent),
                context.getResources().getColor(R.color.xxblue)};
        imageResources = new int[]{
                R.drawable.squirrel,
                R.drawable.bear,
                R.drawable.bee,
                R.drawable.butterfly,
                R.drawable.cat,
                R.drawable.deer,
                R.drawable.dolphin,
                R.drawable.eagle,
                R.drawable.horse,
                R.drawable.elephant,
                R.drawable.owl,
                R.drawable.peacock,
                R.drawable.pig,
                R.drawable.rat,
                R.drawable.snake,
                R.drawable.bat
        };
    }

    @Override
    protected void convert(BaseViewHolder holder, SystemDetailBean.DataBean.DatasBean item) {
        setContentHolder(holder, item);
    }

    private void setContentHolder(@NonNull BaseViewHolder holder, final SystemDetailBean.DataBean.DatasBean item) {
        holder.setText(R.id.tv_author, item.getAuthor());
        holder.setText(R.id.tv_date, item.getNiceDate());
        holder.setText(R.id.tv_title, item.getTitle());
        Random random = new Random();
        Random random1 = new Random();
        int i = random.nextInt(array.length);
        int j = random1.nextInt(imageResources.length);
        ((CardView)holder.getView(R.id.cardView)).setCardBackgroundColor(array[i]);
        holder.setImageResource(R.id.imageView, imageResources[j]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FinestWebView.Builder(context).show(item.getLink());
            }
        });
        final LikeView likeView = holder.getView(R.id.lv);
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
    }

}
