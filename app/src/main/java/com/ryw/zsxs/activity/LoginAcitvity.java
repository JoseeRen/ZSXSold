package com.ryw.zsxs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ryw.zsxs.R;
import com.ryw.zsxs.app.Constant;
import com.ryw.zsxs.base.BaseActivity;
import com.ryw.zsxs.utils.MD5utils;
import com.ryw.zsxs.utils.SpUtils;
import com.ryw.zsxs.utils.XutilsHttp;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by gui on 2017/6/9.
 */

public class LoginAcitvity extends BaseActivity {

    private static final int IS_SUCCESS = 0;
    public static final int IS_ERROR = 1;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.et_login_pass)
    EditText etLoginPass;
    @BindView(R.id.bt_login_denglu)
    Button btLoginDenglu;
    @BindView(R.id.tv_login_findPass)
    TextView tvLoginFindPass;


    private String username;
    private String password;


    @Override
    public int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }




    private void login() {
        username = etLoginName.getText().toString().trim();
        password = etLoginPass.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            HashMap<String, String> map = new HashMap<>();
            map.put("Action", "Login");
            map.put("user", username);
            map.put("pwd", MD5utils.encode(password));
            map.put("ip", SpUtils.getIPAddress(this));
            XutilsHttp.getInstance().get(Constant.HOSTNAME, map, new XutilsHttp.XCallBack() {
                @Override
                public void onResponse(String result) {
                    Toast.makeText(LoginAcitvity.this, "登录成功", Toast.LENGTH_LONG).show();
                    SpUtils.putBoolean(LoginAcitvity.this, Constant.IS_LOGIN, true);
                    finish();
                }
            });


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_login_register, R.id.bt_login_denglu, R.id.tv_login_findPass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login_denglu:
                login();
                break;
            case R.id.tv_login_register:
                //注册
                Intent intent = new Intent(LoginAcitvity.this, RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_login_findPass:
                //找回密码
                Intent intent1 = new Intent(LoginAcitvity.this, BackpasswordActivity.class);
                startActivity(intent1);

                break;
        }
    }
}
