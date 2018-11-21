package com.example.admin.mydailystudy.mvp.view.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.bean.ProjectTabBean;
import com.example.admin.mydailystudy.mvp.present.ProjectDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.ProjectDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.adapter.ProjectDetailRvAdapter;
import com.example.admin.mydailystudy.mvp.view.view.ProjectDataView;
import com.example.admin.mydailystudy.utils.SimplePaddingDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ProjectDetailFragment extends BaseFragment implements ProjectDataView {

    private RecyclerView rv_project_detail;
    private SmartRefreshLayout smartRefreshLayout;
    private ProjectDetailRvAdapter adapter;
    private List<ProjectDetailDataBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    private int mPageNum = 1;
    private ProjectDataPresent present;
    private boolean isRefresh = true;
    private int id;

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.projectdetail_fragment_layout;
    }

    @Override
    public void initView() {
        rv_project_detail = mFragmentView.findViewById(R.id.rv_project_detail);
        smartRefreshLayout = mFragmentView.findViewById(R.id.smartRefreshLayout);
    }

    @Override
    protected void loadData() {
        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        present = new ProjectDataPresentImpl(this);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = 1;
                isRefresh = true;
                present.getProjectDetailData(mPageNum, id);

            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                present.getProjectDetailData(++mPageNum, id);
            }
        });

        smartRefreshLayout.autoRefresh();
        adapter = new ProjectDetailRvAdapter(R.layout.project_detail_rv_item, datasBeans, getContext(), present);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_project_detail.setLayoutManager(linearLayoutManager);
        rv_project_detail.setAdapter(adapter);
        rv_project_detail.addItemDecoration(new SimplePaddingDecoration(MyApplication.getContext()));
        rv_project_detail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 50) { //向下滑动，隐藏底部导航栏
                    Message message = new Message();
                    message.what = NAVIGATION_HIDE;
                    EventBus.getDefault().post(message);
                } else if (dy < -50) {
                    Message message = new Message();
                    message.what = NAVIGATION_SHOW;
                    EventBus.getDefault().post(message);
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void setTabData(List<ProjectTabBean.DataBean> dataBeans) {

    }

    @Override
    public void setProjectDetailData(List<ProjectDetailDataBean.DataBean.DatasBean> datasBeans) {
        if (isRefresh) {
            this.datasBeans.clear();
            smartRefreshLayout.finishRefresh();
        }
        this.datasBeans.addAll(datasBeans);
        adapter.notifyDataSetChanged();
        smartRefreshLayout.finishLoadmore();
    }
}
