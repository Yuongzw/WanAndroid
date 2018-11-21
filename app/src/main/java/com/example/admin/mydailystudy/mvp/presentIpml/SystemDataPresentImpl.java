package com.example.admin.mydailystudy.mvp.presentIpml;

import com.example.admin.mydailystudy.base.BaseFragment;
import com.example.admin.mydailystudy.bean.SystemDataBean;
import com.example.admin.mydailystudy.mvp.INetListener;
import com.example.admin.mydailystudy.mvp.model.SystemDataModel;
import com.example.admin.mydailystudy.mvp.modelImpl.SystemDataModelImpl;
import com.example.admin.mydailystudy.mvp.present.SystemDataPresent;
import com.example.admin.mydailystudy.mvp.view.view.SystemDataView;

import java.util.List;

public class SystemDataPresentImpl implements SystemDataPresent {

    private SystemDataView view;
    private SystemDataModel model;
    
    public SystemDataPresentImpl(SystemDataView view) {
        this.view = view;
        model = new SystemDataModelImpl((BaseFragment) view);
    }

    @Override
    public void getSystemData() {
        model.getSystemData(new INetListener<Object, Throwable, Object>() {
            @Override
            public void success(Object o) {
                view.setSystemData((List<SystemDataBean.DataBean>) o);
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
