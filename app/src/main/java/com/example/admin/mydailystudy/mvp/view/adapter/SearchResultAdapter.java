package com.example.admin.mydailystudy.mvp.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.SearchByKeyDataBean;
import com.example.admin.mydailystudy.utils.KeywordUtil;
import com.jaren.lib.view.LikeView;
import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchResultAdapter extends BaseQuickAdapter<SearchByKeyDataBean.DataBean.DatasBean, BaseViewHolder> {
    private final int[] array;
    private final int[] imageResources;
    private Context context;
    private String keyWord;

    public SearchResultAdapter(int layoutResId, @Nullable List<SearchByKeyDataBean.DataBean.DatasBean> data, Context context, String keyWord) {
        super(layoutResId, data);
        this.context = context;
        this.keyWord = keyWord;
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
    protected void convert(BaseViewHolder viewHolder, final SearchByKeyDataBean.DataBean.DatasBean item) {
        viewHolder.setText(R.id.tv_author, item.getAuthor());
        viewHolder.setText(R.id.tv_date, item.getNiceDate());
        viewHolder.setText(R.id.tv_title, item.getTitle());
        //高亮搜索关键字
        if (keyWord != null) {
            String title = item.getTitle();
            SpannableString spannableString = KeywordUtil.matcherSearchTitle(Color.WHITE, title, keyWord);
            viewHolder.setText(R.id.tv_title, spannableString);
        }


        Random random = new Random();
        Random random1 = new Random();
        int i = random.nextInt(array.length);
        int j = random1.nextInt(imageResources.length);
        ((CardView)viewHolder.getView(R.id.cardView)).setCardBackgroundColor(array[i]);
        viewHolder.setImageResource(R.id.imageView, imageResources[j]);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FinestWebView.Builder(context).show(item.getLink());
            }
        });
        final LikeView likeView = viewHolder.getView(R.id.lv);
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (likeView.isChecked()) {
                    likeView.setChecked(false);
                } else {
                    likeView.setChecked(true);
                }
            }
        });
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
