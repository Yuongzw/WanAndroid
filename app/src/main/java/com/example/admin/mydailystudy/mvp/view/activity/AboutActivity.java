package com.example.admin.mydailystudy.mvp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;

import butterknife.Bind;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;



    @Override
    public int inflateContentView() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Glide.with(this)
                .load("https://cn.bing.com/az/hprichbg/rb/ChateauGaillard_ZH-CN10606001857_1920x1080.jpg")
                .into(imageView);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }
}
