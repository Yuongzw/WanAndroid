package com.example.admin.mydailystudy.mvp.view.fragment;

import android.content.Intent;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.mydailystudy.MyApplication;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.HomeBannerDataBean;
import com.example.admin.mydailystudy.bean.HomeDataBean;
import com.example.admin.mydailystudy.event.ColletedEvent;
import com.example.admin.mydailystudy.mvp.present.HomeDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.HomeDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.activity.MainActivity;
import com.example.admin.mydailystudy.mvp.view.activity.SearchActivity;
import com.example.admin.mydailystudy.mvp.view.adapter.HomeRecyclerViewAdapter;
import com.example.admin.mydailystudy.mvp.view.view.HomeDataView;
import com.example.admin.mydailystudy.mvp.view.widget.CircleImageView;
import com.example.admin.mydailystudy.net.MyRetrofit;
import com.example.admin.mydailystudy.utils.SimplePaddingDecoration;
import com.example.admin.mydailystudy.utils.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class StudyFragment extends BaseFragment implements View.OnClickListener, HomeDataView {

    private CircleImageView iv_head;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private ImageView iv_search;

    private Subscriber<HomeDataBean> subscriber;
    private Subscriber<HomeBannerDataBean> bannerSubscriber;

    private List<HomeDataBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    private List<HomeBannerDataBean.DataBean> bannerBeans = new ArrayList<>();

    private HomeRecyclerViewAdapter adapter;

    private RelativeLayout title_bar;

    private int scrollY;

    private int mPageNum;

    private boolean isRefresh;

    private HomeDataPresent present;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.study_fragment_layout;
    }

    public void getHomeData(int page, final boolean isRefresh) {
        subscriber = new Subscriber<HomeDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomeDataBean homeDataBean) {
                if (isRefresh) {
                    datasBeans.clear();
                    datasBeans.addAll(homeDataBean.getData().getDatas());
                    adapter.addHomeInfo(datasBeans, true);
                    mRefreshLayout.finishRefresh();
                }else {
                    datasBeans.addAll(homeDataBean.getData().getDatas());
                    adapter.addHomeInfo(datasBeans, false);
                    mRefreshLayout.finishLoadmore();
                }
                adapter.notifyDataSetChanged();
            }
        };
        addSubscription(subscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getHomeData(page)
                .subscribeOn(Schedulers.io())//请求在io线程中执行
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(subscriber);//设置订阅者
    }

    public void getHomeBannerData() {
        bannerSubscriber = new Subscriber<HomeBannerDataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HomeBannerDataBean homeBannerDataBean) {
                bannerBeans.clear();
                bannerBeans.addAll(homeBannerDataBean.getData());
                adapter.addBanner(bannerBeans);
                adapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        };
        addSubscription(bannerSubscriber);
        MyRetrofit.getInstance(MyApplication.getContext()).myGetNetData().getHomeBannerData()
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bannerSubscriber);
    }

    @Override
    public void initView() {
        iv_head = mFragmentView.findViewById(R.id.iv_head);
        iv_head.setOnClickListener(this);
        mRefreshLayout = mFragmentView.findViewById(R.id.smartRefreshLayout);
        recyclerView = mFragmentView.findViewById(R.id.recyclerView);
        title_bar = mFragmentView.findViewById(R.id.title_bar);
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
        present = new HomeDataPresentImpl(this);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = MyConstants.PAGE_NUMBER_DEFAULT;
                isRefresh = true;
                present.getHomeBannerData();
                present.getHomeData(mPageNum);
//                getHomeData(mPageNum, isRefresh);
//                getHomeBannerData();

            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                present.getHomeData(++mPageNum);
//                getHomeData(++mPageNum, isRefresh);
            }
        });

        mRefreshLayout.autoRefresh();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new HomeRecyclerViewAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimplePaddingDecoration(getActivity()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private float alpha;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                scrollY += dy;
//                float maxHeight = 550;
                float maxHeight = Util.dip2px(180, getActivity());
                alpha = (float) scrollY / maxHeight;
                title_bar.setAlpha(alpha);
                Log.e("dy", dy + "");
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

    @Subscribe
    public void Event(ColletedEvent colletedEvent) {
        if (colletedEvent.isCollected()) {
            present.collectArticle(colletedEvent.getId());
        } else {
            present.cancelCollected(colletedEvent.getId());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                ((MainActivity) getActivity()).openDrawer();
                break;
        }
    }

    @Override
    public void setHomeData(List<HomeDataBean.DataBean.DatasBean> datasBeans) {
        if (isRefresh) {
            this.datasBeans.clear();
            this.datasBeans.addAll(datasBeans);
            adapter.addHomeInfo(this.datasBeans, isRefresh);
            mRefreshLayout.finishRefresh();
        }else {
            this.datasBeans.addAll(datasBeans);
            adapter.addHomeInfo(this.datasBeans, isRefresh);
            mRefreshLayout.finishLoadmore();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setBannerData(List<HomeBannerDataBean.DataBean> bannerBeans) {
        this.bannerBeans.clear();
        this.bannerBeans.addAll(bannerBeans);
        adapter.addBanner(bannerBeans);
        adapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh();
    }
}
