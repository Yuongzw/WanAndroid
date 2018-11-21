package com.example.admin.mydailystudy.mvp.view.fragment;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.mvp.present.SystemDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.SystemDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.activity.SearchActivity;
import com.example.admin.mydailystudy.mvp.view.adapter.SystemAdapter;
import com.example.admin.mydailystudy.mvp.view.adapter.SystemRecyclerViewAdapter;
import com.example.admin.mydailystudy.mvp.view.view.SystemDataView;
import com.example.admin.mydailystudy.utils.SimplePaddingDecoration;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SystemFragment extends BaseFragment implements SystemDataView {
    List<SystemDataBean.DataBean> dataBeans;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ImageView iv_search;

    private SystemDataPresent present;
    private SystemRecyclerViewAdapter adapter;

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.system_fragment_layout;
    }


    @Override
    public void initView() {
        toolbar = mFragmentView.findViewById(R.id.toolbar);
        toolbar.setTitle("体系");
        recyclerView = mFragmentView.findViewById(R.id.rv_out);
        iv_search = mFragmentView.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
    }

    @Override
    protected void loadData() {
        dataBeans = new ArrayList<>();
        present = new SystemDataPresentImpl(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimplePaddingDecoration(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

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
        adapter = new SystemRecyclerViewAdapter(R.layout.system_rv_item, dataBeans, getActivity());
        recyclerView.setAdapter(adapter);
        present.getSystemData();
    }

    @Override
    public void setSystemData(List<SystemDataBean.DataBean> dataBeans) {
        this.dataBeans.clear();
        this.dataBeans.addAll(dataBeans);
        adapter.notifyDataSetChanged();
    }
}
