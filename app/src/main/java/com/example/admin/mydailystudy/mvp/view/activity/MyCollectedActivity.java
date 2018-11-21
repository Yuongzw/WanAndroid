package com.example.admin.mydailystudy.mvp.view.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.CollectArticleBean;
import com.example.admin.mydailystudy.mvp.present.CollectArticleDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.CollectArticleDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.adapter.CollectedRvAdapter;
import com.example.admin.mydailystudy.mvp.view.adapter.HomeRecyclerViewAdapter;
import com.example.admin.mydailystudy.mvp.view.view.CollectArticleDataView;
import com.example.admin.mydailystudy.mvp.view.widget.LoadingStatusLayout;
import com.example.admin.mydailystudy.utils.SimplePaddingDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MyCollectedActivity extends BaseActivity implements CollectArticleDataView {

    @Bind(R.id.rv_my_collect)
    RecyclerView rv_my_collect;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.loadingStatusLayout)
    LoadingStatusLayout loadingStatusLayout;
    @Bind(R.id.iv_search)
    ImageView iv_search;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private CollectedRvAdapter adapter;
    private List<CollectArticleBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    private int pageNum;
    private boolean isRefresh;

    private CollectArticleDataPresent present;

    @Override
    public int inflateContentView() {
        return R.layout.activity_my_collected;
    }

    @Override
    protected void initData() {
        iv_search.setVisibility(View.GONE);
        toolbar.setTitle("我的收藏");
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new CollectedRvAdapter(R.layout.collected_rv_item, datasBeans, this);
        present = new CollectArticleDataPresentImpl(this);
        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 0;
                isRefresh = true;
                present.getCollectArticle(pageNum);
            }
        });
//
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
//                rv_my_collect.useDefaultLoadMore();
                present.getCollectArticle(++pageNum);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_my_collect.setLayoutManager(linearLayoutManager);
        rv_my_collect.addItemDecoration(new SimplePaddingDecoration(this));

        adapter.setListener(new CollectedRvAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int id, int originId) {
                present.cancelCollect(id, originId);
            }
        });
        rv_my_collect.setAdapter(adapter);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void setData(List<CollectArticleBean.DataBean.DatasBean> datasBeans) {
        if (isRefresh) {
            this.datasBeans.clear();
            refreshLayout.finishRefresh();
        }
        loadingStatusLayout.setStatus(LoadingStatusLayout.SUCCESS_STATUS);
        refreshLayout.finishLoadmore();
        this.datasBeans.addAll(datasBeans);
        adapter.notifyDataSetChanged();
    }
}
