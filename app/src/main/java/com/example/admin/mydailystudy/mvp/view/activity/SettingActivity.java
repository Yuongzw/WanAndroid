package com.example.admin.mydailystudy.mvp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.utils.FileCacheUtil;

import butterknife.Bind;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.cardView)
    CardView cardView;
    @Bind(R.id.tv_cache_size)
    TextView tv_cache_size;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_search)
    ImageView iv_search;

    private AlertDialog alertDialog;

    @Override
    public int inflateContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        toolbar.setTitle("设置");
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_search.setVisibility(View.GONE);
        try {
            String totalCacheSize = FileCacheUtil.getTotalCacheSize(this);
            tv_cache_size.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDialog();
                try {
                    tv_cache_size.setText(FileCacheUtil.getTotalCacheSize(getApplicationContext()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initDialog()
    {
        //创建AlertDialog的构造器的对象
        AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this);
        //设置构造器标题
        builder.setTitle("清除缓存");
        //构造器对应的图标
        //构造器内容,为对话框设置文本项(之后还有列表项的例子)
        builder.setMessage("清除缓存会导致下载的内容被删除，是否确定？");
        //为构造器设置确定按钮,第一个参数为按钮显示的文本信息，第二个参数为点击后的监听事件，用匿名内部类实现
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //第一个参数dialog是点击的确定按钮所属的Dialog对象,第二个参数which是按钮的标示值
                FileCacheUtil.clearAllCache(SettingActivity.this);
                String totalCacheSize = null;
                try {
                    totalCacheSize = FileCacheUtil.getTotalCacheSize(SettingActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tv_cache_size.setText(totalCacheSize);
                Toast.makeText(SettingActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
            }
        });
        //为构造器设置取消按钮,若点击按钮后不需要做任何操作则直接为第二个参数赋值null
        builder.setNegativeButton("取消",null);
        //为构造器设置一个比较中性的按钮，比如忽略、稍后提醒等
        //利用构造器创建AlertDialog的对象,实现实例化
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }
}
