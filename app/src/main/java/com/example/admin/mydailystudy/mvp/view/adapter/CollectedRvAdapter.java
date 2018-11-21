package com.example.admin.mydailystudy.mvp.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.CollectArticleBean;
import com.example.admin.mydailystudy.mvp.view.activity.MyCollectedActivity;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.Random;

public class CollectedRvAdapter extends BaseQuickAdapter<CollectArticleBean.DataBean.DatasBean, BaseViewHolder> {

    private List<CollectArticleBean.DataBean.DatasBean> data;
    private OnItemClickListener listener;
    private Context context;
    private int[] array;

    public CollectedRvAdapter(int layoutResId, @Nullable List<CollectArticleBean.DataBean.DatasBean> data, Context context) {
        super(layoutResId, data);
        this.data = data;
        this.context = context;
        array = new int[]{
                context.getResources().getColor(R.color.cardView),
                context.getResources().getColor(R.color.cardView1),
                context.getResources().getColor(R.color.cardView2),
                context.getResources().getColor(R.color.colorAccent),
                context.getResources().getColor(R.color.xxblue)};
    }

    @Override
    protected void convert(final BaseViewHolder viewHolder, final CollectArticleBean.DataBean.DatasBean item) {
        final SwipeLayout swipeLayout = viewHolder.getView(R.id.swipe_layout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        viewHolder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_collect_date, item.getNiceDate());
        viewHolder.getView(R.id.bottom_wrapper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(viewHolder, data, listener, item);
//                swipeLayout.close();
//                data.remove(viewHolder.getPosition());
//                if (listener != null) {
//                    listener.OnItemClick(item.getId(), item.getOriginId());
//                }
//                notifyDataSetChanged();
            }
        });
        viewHolder.getView(R.id.cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FinestWebView.Builder(context).show(item.getLink());
            }
        });
        Random random = new Random();
        int i = random.nextInt(array.length);
        ((CardView)viewHolder.getView(R.id.cardView)).setCardBackgroundColor(array[i]);
    }

    private void showDialog(final BaseViewHolder viewHolder, final List<CollectArticleBean.DataBean.DatasBean> data, final OnItemClickListener listener, final CollectArticleBean.DataBean.DatasBean item) {
        final AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setTitle("取消收藏");
        localBuilder.setMessage("确定要取消收藏？");
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                data.remove(viewHolder.getPosition());
                if (listener != null) {
                    listener.OnItemClick(item.getId(), item.getOriginId());
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

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int id, int originId);
    }
}
