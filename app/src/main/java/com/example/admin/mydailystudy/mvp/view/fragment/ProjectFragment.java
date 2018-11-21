package com.example.admin.mydailystudy.mvp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.ProjectDetailDataBean;
import com.example.admin.mydailystudy.bean.ProjectTabBean;
import com.example.admin.mydailystudy.mvp.present.ProjectDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.ProjectDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.activity.SearchActivity;
import com.example.admin.mydailystudy.mvp.view.view.ProjectDataView;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends BaseFragment implements ProjectDataView {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager vp_project;
    private ImageView iv_search;

    private List<ProjectTabBean.DataBean> dataBeans = new ArrayList<>();
    private ProjectDataPresent present;
    private MyViewPagerAdapter pagerAdapter;
    private List<String> titles = new ArrayList<>();

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.project_fragment_layout;
    }


    @SuppressLint("NewApi")
    @Override
    public void initView() {
        toolbar = mFragmentView.findViewById(R.id.toolbar);
        toolbar.setTitle("项目");
        tabLayout = mFragmentView.findViewById(R.id.tabLayout);
        vp_project = mFragmentView.findViewById(R.id.vp_project);
        pagerAdapter = new MyViewPagerAdapter(getActivity().getSupportFragmentManager());
        vp_project.setAdapter(pagerAdapter);
        vp_project.setCurrentItem(0);
        tabLayout.setupWithViewPager(vp_project);
        iv_search = mFragmentView.findViewById(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });


//        vp_project.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    protected void loadData() {
        present = new ProjectDataPresentImpl(this);
        present.getProjectTab();
    }

    @Override
    public void setTabData(List<ProjectTabBean.DataBean> dataBeans) {
        this.dataBeans.clear();
        this.dataBeans.addAll(dataBeans);
        titles.clear();
        for (ProjectTabBean.DataBean dataBean : this.dataBeans) {
            titles.add(dataBean.getName());
        }
        pagerAdapter.setList(titles);
    }

    @Override
    public void setProjectDetailData(List<ProjectDetailDataBean.DataBean.DatasBean> datasBeans) {

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private List<String> titles;

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.titles = new ArrayList<>();
        }

        /**
         * 标题数据列表
         *
         * @param datas
         */
        public void setList(List<String> datas) {
            this.titles.clear();
            this.titles.addAll(datas);
            notifyDataSetChanged();
        }


        @Override
        public Fragment getItem(int position) {
            ProjectDetailFragment fragment = new ProjectDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", dataBeans.get(position).getId());
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.size();
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            String title = titles.get(position);
            if (title == null) {
                title = "";
            }
            return title;
        }
    }
}
