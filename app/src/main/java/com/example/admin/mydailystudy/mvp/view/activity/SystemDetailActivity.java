package com.example.admin.mydailystudy.mvp.view.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.bean.SystemDetailBean;
import com.example.admin.mydailystudy.event.ColletedEvent;
import com.example.admin.mydailystudy.mvp.present.SystemDetailDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.SystemDetailDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.adapter.SystemDetailRvAdapter;
import com.example.admin.mydailystudy.mvp.view.view.SystemDataView;
import com.example.admin.mydailystudy.mvp.view.view.SystemDetailDataView;
import com.example.admin.mydailystudy.utils.SimplePaddingDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SystemDetailActivity extends BaseActivity implements SystemDetailDataView {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rv_sys_detail)
    RecyclerView rv_sys_detail;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    private SystemDetailRvAdapter adapter;

    private List<SystemDetailBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    private boolean isRefresh = true;
    private int mPageNum;
    private SystemDetailDataPresent present;

    @Override
    public int inflateContentView() {
        return R.layout.activity_system_detail;
    }

    @Override
    protected void initData() {
        present = new SystemDetailDataPresentImpl(this);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        final int id = getIntent().getIntExtra("id", 0);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = MyConstants.PAGE_NUMBER_DEFAULT;
                isRefresh = true;
                present.getSystemDetail(mPageNum, id);

            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                present.getSystemDetail(++mPageNum, id);
            }
        });
        smartRefreshLayout.autoRefresh();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_sys_detail.setLayoutManager(linearLayoutManager);
        rv_sys_detail.addItemDecoration(new SimplePaddingDecoration(this));
        adapter = new SystemDetailRvAdapter(R.layout.rv_system_detail_item, datasBeans, this, present);
        rv_sys_detail.setAdapter(adapter);

    }


    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }


    @Override
    public void setData(List<SystemDetailBean.DataBean.DatasBean> datasBeans) {
        if (isRefresh) {
            this.datasBeans.clear();
            smartRefreshLayout.finishRefresh();
        } else {
            smartRefreshLayout.finishLoadmore();
        }
        this.datasBeans.addAll(datasBeans);
        adapter.notifyDataSetChanged();
    }
}
