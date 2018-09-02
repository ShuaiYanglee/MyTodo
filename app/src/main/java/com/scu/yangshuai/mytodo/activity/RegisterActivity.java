package com.scu.yangshuai.mytodo.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.yangshuai.mytodo.MyApplication;
import com.scu.yangshuai.mytodo.R;
import com.scu.yangshuai.mytodo.bean.MyUser;
import com.scu.yangshuai.mytodo.controller.ActivityCollector;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    EditText etRegisterPhone;
    EditText etRegisterUsername;
    EditText etRegisterPassword;
    Button btnRegister;
    TextView tvToLogin;
    MyUser registerUser = new MyUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        Log.d(TAG, "onCreate: ");
    }

    void init() {
        ActivityCollector.activityList.add(this);
        etRegisterPhone = findViewById(R.id.et_register_phone);
        etRegisterPhone.setOnClickListener(this);
        etRegisterUsername = findViewById(R.id.et_register_username);
        etRegisterUsername.setOnClickListener(this);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterPassword.setOnClickListener(this);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        tvToLogin = findViewById(R.id.tv_to_login);
        tvToLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (checkUserInfoValid()) {
                    registerUser.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterActivity.this, "恭喜注册成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                MyApplication application = (MyApplication) getApplication();
                                application.setCurrentUser(registerUser);
                            } else {
                                Log.e(TAG, e.getMessage());
                                if (e.getMessage().contains("mobilePhoneNumber")) {
                                    Toast.makeText(RegisterActivity.this, "电话号码已被使用，请使用其他电话号码注册", Toast.LENGTH_SHORT).show();
                                }
                                if (e.getMessage().contains("username"))
                                    Toast.makeText(RegisterActivity.this, "用户名已被使用，请使用其他用户名", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.tv_to_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
        }
    }

    //校验用户输入的注册信息是否正确
    private boolean checkUserInfoValid() {
        String phoneNums = etRegisterPhone.getText().toString();
        String userPassword = etRegisterPassword.getText().toString();
        String userName = etRegisterUsername.getText().toString();
        if (!isMobileNO(phoneNums)) {
            Toast.makeText(this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)) {
                Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                registerUser.setMobilePhoneNumber(phoneNums);
                registerUser.setUsername(userName);
                registerUser.setPassword(userPassword);
                registerUser.setAvatarUrl("http://test.com");
                return true;
            }
        }
    }

    private static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
}
