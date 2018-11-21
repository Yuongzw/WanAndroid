package com.example.admin.mydailystudy.mvp.presentIpml;

import com.example.admin.mydailystudy.bean.HotKeyDataBean;
import com.example.admin.mydailystudy.bean.SearchByKeyDataBean;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.HotKeyDataModel;
import com.example.admin.mydailystudy.mvp.modelImpl.HotKeyDataModelImpl;
import com.example.admin.mydailystudy.mvp.present.HotKeyDataPresent;
import com.example.admin.mydailystudy.mvp.view.activity.SearchActivity;
import com.example.admin.mydailystudy.mvp.view.view.HotKeyDataView;

import java.util.List;

public class HotKeyDataPresentImpl implements HotKeyDataPresent {

    private HotKeyDataModel model;
    private HotKeyDataView view;

    public HotKeyDataPresentImpl(HotKeyDataView view) {
        this.view = view;
        model = new HotKeyDataModelImpl((SearchActivity) view);
    }

    @Override
    public void getHotKeyData() {
        model.getHotKeyData(new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setData((List<HotKeyDataBean.DataBean>) o);
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }

    @Override
    public void getSearchResult(int page, String keyWord) {
        model.getSearchResult(page, keyWord, new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setSearchResultData((List<SearchByKeyDataBean.DataBean.DatasBean>) o);
            }

            @Override
            public void failed(Throwable throwable) {

            }

            @Override
            public void loading(Object o) {

            }
        });
    }
}
