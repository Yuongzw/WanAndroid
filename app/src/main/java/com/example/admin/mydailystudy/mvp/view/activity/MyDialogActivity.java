package com.example.admin.mydailystudy.mvp.view.activity;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.mvp.present.TodoDataPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.TodoDataPresentImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyDialogActivity extends Activity implements View.OnClickListener {
    @Bind(R.id.btn_cancel)
    Button btn_cancel;
    @Bind(R.id.btn_yes)
    Button btn_yes;
    @Bind(R.id.et_date)
    EditText et_date;
    @Bind(R.id.iv_cha)
    ImageView iv_cha;
    @Bind(R.id.et_title)
    EditText et_title;
    @Bind(R.id.et_content)
    EditText et_content;

    private int type, mode, id, status;
    private String title, content, date;

    private TodoDataPresent present;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dialog);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.3
        p.width = d.getWidth(); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        mode = getIntent().getIntExtra("mode", 0);
        id = getIntent().getIntExtra("id", 0);
        status = getIntent().getIntExtra("status", 0);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        date = getIntent().getStringExtra("date");
        initData();
        present = new TodoDataPresentImpl(new MyTodoActivity());
    }


    protected void initData() {
        et_date.setFocusable(false);
        btn_cancel.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
        et_date.setOnClickListener(this);
        iv_cha.setOnClickListener(this);
        if (mode == 0) {
            et_date.setText(getDate(new Date()));
            btn_yes.setText("保存");
        } else if (mode == 1) {
            btn_yes.setText("修改");
            et_date.setText(date);
            et_content.setText(content);
            et_title.setText(title);
        } else {
            btn_cancel.setText("关闭");
            btn_yes.setVisibility(View.GONE);
            et_date.setText(date);
            et_content.setText(content);
            et_title.setText(title);
            et_title.setFocusable(false);
            et_content.setFocusable(false);
            et_date.setEnabled(false);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_yes:
                if (et_title.getText().toString().equals("")) {
                    Toast.makeText(this, "标题不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                title = et_title.getText().toString();
                content = et_content.getText().toString();
                date = et_date.getText().toString();
                if (mode == 0) {//添加
                    present.addTodo(title, content, date, type);
                    setResult(RESULT_OK);
                } else if (mode == 1) {//修改
                    present.updateTodo(id, title, content, date, status, type);
                    setResult(RESULT_OK);
                }
                finish();
                break;
            case R.id.et_date:
                //时间选择器
                TimePickerView pvTime = new TimePickerBuilder(MyDialogActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String t = getDate(date);
                        if (isDate2Bigger(t, getDate(new Date()))) {
                            et_date.setText(t);
                        } else {
                            Toast.makeText(MyDialogActivity.this, "选择的日期不能比今天小！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).build();
                pvTime.show();
                break;
            case R.id.iv_cha:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String date1, String date2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(date1);
            dt2 = sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    private String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
