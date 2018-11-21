package com.example.admin.mydailystudy.mvp.view.activity;

import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.base.BaseActivity;
import com.example.admin.mydailystudy.mvp.present.LoginAndRegisterPresent;
import com.example.admin.mydailystudy.mvp.presentIpml.LoginAndRegisterPresentImpl;
import com.example.admin.mydailystudy.mvp.view.view.LoginAndRegistView;
import com.example.admin.mydailystudy.utils.SharedPreferenceUtil;
import com.github.ybq.android.spinkit.SpinKitView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

public class LoginActivity extends BaseActivity implements LoginAndRegistView, View.OnClickListener {

    @Bind(R.id.et_account)
    EditText et_account;
    @Bind(R.id.iv_account_delete)
    ImageView iv_account_delete;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.iv_password_delete)
    ImageView iv_password_delete;
    @Bind(R.id.bt_sign_in)
    Button bt_sign_in;
    @Bind(R.id.bt_login)
    Button bt_login;
    @Bind(R.id.spin_kit)
    SpinKitView spin_kit;
    @Bind(R.id.ll_repassword)
    LinearLayout ll_repassword;
    @Bind(R.id.et_repassword)
    EditText et_repassword;
    @Bind(R.id.iv_repassword_delete)
    ImageView iv_repassword_delete;
    @Bind(R.id.tv_register_or_login)
    TextView tv_register_or_login;
    @Bind(R.id.cb_remember_user_pwd)
    CheckBox cb_remember_user_pwd;

    private LoginAndRegisterPresent present;

    private boolean isLogin = true;


    @Override
    public int inflateContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        et_account.addTextChangedListener(new MyEditTextWatcher(iv_account_delete));
        et_password.addTextChangedListener(new MyEditTextWatcher(iv_password_delete));
        et_repassword.addTextChangedListener(new MyEditTextWatcher(iv_repassword_delete));
        iv_password_delete.setOnClickListener(this);
        iv_account_delete.setOnClickListener(this);
        bt_sign_in.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        iv_repassword_delete.setOnClickListener(this);
        tv_register_or_login.setOnClickListener(this);
        present = new LoginAndRegisterPresentImpl(this);
        checkAutoLogin();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void showLoading() {
        if (spin_kit != null){
            spin_kit.setVisibility(View.VISIBLE);
            bt_sign_in.setEnabled(false);
            bt_login.setEnabled(false);
        }

    }

    @Override
    public void hideLoading() {
        if (spin_kit != null) {
            spin_kit.setVisibility(View.INVISIBLE);
            bt_sign_in.setEnabled(true);
            bt_login.setEnabled(true);
        }
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
        Message message = new Message();
        message.what = 2;
        EventBus.getDefault().post(message);
        finish();
    }

    @Override
    public void registerFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show();
        Message message = new Message();
        message.what = 2;
        EventBus.getDefault().post(message);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                String loginAccount = et_account.getText().toString().trim();
                String loginPassword = et_password.getText().toString().trim();
                present.userLogin(loginAccount, loginPassword, cb_remember_user_pwd.isChecked());
                break;
            case R.id.bt_sign_in:
                String account = et_account.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String repassword = et_repassword.getText().toString().trim();
                present.registerUser(account, password, repassword, cb_remember_user_pwd.isChecked());
                break;
            case R.id.iv_account_delete:
                et_account.setText("");
                break;
            case R.id.iv_password_delete:
                et_password.setText("");
                break;
            case R.id.iv_repassword_delete:
                et_repassword.setText("");
                break;
            case R.id.tv_register_or_login:
                isLogin = !isLogin;
                if (isLogin) {
                    ll_repassword.setVisibility(View.GONE);
                    tv_register_or_login.setText("去注册");
                } else {
                    ll_repassword.setVisibility(View.VISIBLE);
                    tv_register_or_login.setText("去登录");
                }
                break;
            default:
                break;
        }
    }
    private void checkAutoLogin() {
        boolean rememberPwd = (boolean) SharedPreferenceUtil.getData(this, "remember_pwd", false);
        if (rememberPwd) {
            cb_remember_user_pwd.setChecked(true);
            String name = (String) SharedPreferenceUtil.getData(this, "user_name", "");
            String pwd = (String) SharedPreferenceUtil.getData(this, "user_pwd", "");
            et_account.setText(name);
            et_password.setText(pwd);
        } else {
            cb_remember_user_pwd.setChecked(false);
        }
    }

    class MyEditTextWatcher implements TextWatcher {
        private View view;

        public MyEditTextWatcher(View viwe){
            this.view = viwe;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }
}
