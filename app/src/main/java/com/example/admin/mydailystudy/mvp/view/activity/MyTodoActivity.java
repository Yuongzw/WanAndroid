package com.example.admin.mydailystudy.mvp.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.base.BasePager;
import com.example.admin.mydailystudy.bean.TodoListDataBean;
import com.example.admin.mydailystudy.event.TodoEvent;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.TodoDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.pager.TodoDonePager;
import com.example.admin.mydailystudy.mvp.view.pager.TodoPager;
import com.example.admin.mydailystudy.mvp.view.view.TodoDataView;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MyTodoActivity extends BaseActivity implements TodoDataView {

    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_search)
    ImageView iv_search;
    @Bind(R.id.flow_layout)
    TagFlowLayout flow_layout;
    @Bind(R.id.vp_todo)
    ViewPager vp_todo;

    private List<String> flowDatas;

    private MyViewPagerAdapter pagerAdapter;
    private LayoutInflater mInflater;
    public int mPage;
    private boolean isFirst = true;
    private int mPosition;
    private TodoDataPresent present;

    /**
     * 装2个页面的集合
     */
    private List<BasePager> pagers;

    @Override
    public int inflateContentView() {
        return R.layout.activity_my_todo_acticity;
    }

    @Override
    protected void initData() {
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_search.setVisibility(View.GONE);
        toolbar.setTitle("我的代办");
        flowDatas = new ArrayList<>();
        flowDatas.add("只用这一个");
        flowDatas.add("工作");
        flowDatas.add("学习");
        flowDatas.add("生活");
        mInflater = LayoutInflater.from(this);
        TagAdapter<String> adapter = new TagAdapter<String>(flowDatas) {
            @Override
            public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tab_item, flow_layout, false);
                tv.setText(s);
                return tv;
            }
        };
        flow_layout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {

            @Override
            public boolean onTagClick(View view, int position, com.zhy.view.flowlayout.FlowLayout parent) {
                Toast.makeText(MyTodoActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                mPage = position;
                mPosition = 0;
                present.getTodoList(mPage, 1);
                isFirst = true;
                vp_todo.setCurrentItem(0);
                return false;
            }
        });
        adapter.setSelectedList(0);//默认选中第一个
        flow_layout.setMaxSelectCount(1);
        flow_layout.setAdapter(adapter);
//        setFlowLayout(flowDatas);
        pagers = new ArrayList<>();
        pagers.add(new TodoPager(this));
        pagers.add(new TodoDonePager(this));
        pagerAdapter = new MyViewPagerAdapter();
        vp_todo.setAdapter(pagerAdapter);
        vp_todo.setCurrentItem(0);

        vp_todo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 && isFirst) {
                    mPosition = position;
                    isFirst = false;
                    present.getTodoDoneList(mPage, 1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        present = new TodoDataPresentImpl(this);
        for (int i = 0; i < pagers.size(); i++) {
            pagers.get(i).initData();
            pagers.get(i).setPresent(present);
        }
        present.getTodoList(mPage, 1);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void setGroupList(List<String> groups) {
        pagers.get(mPosition).setGroupTodoList(groups);
    }

    @Override
    public void setChildList(List<List<TodoListDataBean.DataBean.DatasBean>> childList) {
        pagers.get(mPosition).setChildTodoList(childList);
    }

    @Override
    public void refresh() {
        mPosition = 0;
        present.getTodoList(mPage, 1);
        isFirst = true;
        vp_todo.setCurrentItem(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1 || requestCode == 0) && resultCode == RESULT_OK) { //修改或者添加返回
            mPosition = 0;
            present.getTodoList(mPage, 1);
            isFirst = true;
            vp_todo.setCurrentItem(0);
        }
    }

    //    @SuppressLint("ResourceAsColor")
//    private void setFlowLayout(List<String> dataBeans) {
//        for (final String str : dataBeans) {
//            final TextView textView = new TextView(this);
//            textView.setPadding(16, 8, 16, 8);
//            textView.setEnabled(false);
//            textView.setText(str);
//            textView.setTextSize(14);
//            textView.setBackgroundResource(R.drawable.flow_layout_bg);
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    textView.setTextColor(Color.RED);
//                }
//            });
//            flow_layout.addView(textView);
//        }
//    }

    class MyViewPagerAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = pagers.get(position);
            View rootView = pager.mRootView;
            container.addView(rootView);
            return rootView;
        }

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
