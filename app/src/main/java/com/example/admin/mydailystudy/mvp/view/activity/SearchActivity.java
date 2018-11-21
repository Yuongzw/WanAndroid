package com.example.admin.mydailystudy.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.admin.mydailystudy.MyConstants;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.bean.HotKeyDataBean;
import com.example.admin.mydailystudy.bean.SearchByKeyDataBean;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.mvp.present.HotKeyDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.HotKeyDataPresentImpl;
import com.example.admin.mydailystudy.mvp.view.adapter.HomeRecyclerViewAdapter;
import com.example.admin.mydailystudy.mvp.view.adapter.SearchResultAdapter;
import com.example.admin.mydailystudy.mvp.view.view.HotKeyDataView;
import com.example.admin.mydailystudy.mvp.view.widget.EditTextWithClean;
import com.example.admin.mydailystudy.utils.SimplePaddingDecoration;
import com.nex3z.flowlayout.FlowLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity implements HotKeyDataView {
    @Bind(R.id.editTextWithClean)
    EditTextWithClean editTextWithClean;
    @Bind(R.id.tv_cancel)
    TextView tv_cancel;
    @Bind(R.id.ll_history_and_hot)
    LinearLayout ll_history_and_hot;
    @Bind(R.id.flow_layout)
    FlowLayout flow_layout;
    @Bind(R.id.rv_result)
    RecyclerView rv_result;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;

    private List<HotKeyDataBean.DataBean> dataBeans = new ArrayList<>();
    List<SearchByKeyDataBean.DataBean.DatasBean> datasBeans = new ArrayList<>();
    private HotKeyDataPresent present;

    private int mPageNum;
    private boolean isRefresh;
    private String keyWord;
    private SearchResultAdapter adapter;

    @Override
    public int inflateContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
//        toolbar.setTitle("搜索");
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        present = new HotKeyDataPresentImpl(this);
        present.getHotKeyData();
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPageNum = MyConstants.PAGE_NUMBER_DEFAULT;
                isRefresh = true;
                present.getSearchResult(mPageNum, keyWord);

            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isRefresh = false;
                present.getSearchResult(++mPageNum, keyWord);
//                getHomeData(++mPageNum, isRefresh);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_result.setLayoutManager(linearLayoutManager);
        adapter = new SearchResultAdapter(R.layout.rv_system_detail_item, datasBeans, this, keyWord);
        rv_result.setAdapter(adapter);
        rv_result.addItemDecoration(new SimplePaddingDecoration(this));
        setEditTextWithCleanEvent();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void setData(List<HotKeyDataBean.DataBean> dataBeans) {
        this.dataBeans.clear();
        this.dataBeans.addAll(dataBeans);
        setFlowLayout(dataBeans);

    }

    @Override
    public void setSearchResultData(List<SearchByKeyDataBean.DataBean.DatasBean> datasBeans) {
        smartRefreshLayout.setVisibility(View.VISIBLE);
        ll_history_and_hot.setVisibility(View.GONE);
        if (isRefresh) {
            this.datasBeans.clear();
            this.datasBeans.addAll(datasBeans);
            smartRefreshLayout.finishRefresh();
            adapter.setKeyWord(keyWord);
            adapter.notifyDataSetChanged();
        } else {
            if (datasBeans != null && datasBeans.size() > 0){
                this.datasBeans.addAll(datasBeans);
                smartRefreshLayout.finishLoadmore();
                adapter.setKeyWord(keyWord);
                adapter.notifyDataSetChanged();
            } else {
                // 这个方法是在最后一页，没有更多数据时调用的，会在页面底部标记没有更多数据
                smartRefreshLayout.finishLoadmoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                // 这个方法最重要，当在最后一页调用完上一个完成加载并标记没有更多数据的方法时，需要将refreshLayout的状态更改为还有更多数据的状态，此时就需要调用此方法，参数为false代表还有更多数据，true代表没有更多数据
                smartRefreshLayout.resetNoMoreData();
            }
        }
    }

    private void setFlowLayout(List<HotKeyDataBean.DataBean> dataBeans) {
        for (final HotKeyDataBean.DataBean dataBean : dataBeans) {
            final TextView textView = new TextView(this);
            textView.setPadding(16, 8, 16, 8);
            textView.setText(dataBean.getName());
            textView.setTextSize(14);
            textView.setBackgroundResource(R.drawable.flow_layout_bg);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPageNum = 0;
                    keyWord = textView.getText().toString();
                    smartRefreshLayout.autoRefresh();
//                    present.getSearchResult(mPageNum, keyWord);
//                    Intent intent = new Intent(this, SearchActivity.class);
//                    intent.putExtra("id", childrenBean.getId());
//                    intent.putExtra("name", childrenBean.getName());
//                    startActivity(intent);
                }
            });
            flow_layout.addView(textView);
        }
    }

    public static void hideKeyBoard(View v) {
        /*隐藏软键盘*/
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    private void setEditTextWithCleanEvent() {
        editTextWithClean.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击键盘搜索
                    mPageNum = 0;
//                    present.getSearchResult(mPageNum, editTextWithClean.getText().toString());
                    keyWord = editTextWithClean.getText().toString();
                    smartRefreshLayout.autoRefresh();
                    ll_history_and_hot.setVisibility(View.GONE);
                    smartRefreshLayout.setVisibility(View.VISIBLE);
                    hideKeyBoard(editTextWithClean);
                }
                return false;
            }
        });

        editTextWithClean.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (editTextWithClean.getText().toString().isEmpty()){
                    ll_history_and_hot.setVisibility(View.VISIBLE);
                    smartRefreshLayout.setVisibility(View.GONE);
                }
            }
        });

    }

}
