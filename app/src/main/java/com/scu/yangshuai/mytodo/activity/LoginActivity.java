package com.scu.yangshuai.mytodo.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scu.yangshuai.mytodo.MyApplication;
import com.scu.yangshuai.mytodo.R;
import com.scu.yangshuai.mytodo.bean.MyUser;
import com.scu.yangshuai.mytodo.controller.ActivityCollector;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "LoginActivity";
    LinearLayout layoutLogin;
    TextView tvToRegister;
    SimpleDraweeView sdvLoginAvatar;
    EditText etLoginUsername;
    EditText etLoginPassword;
    TextView tvForgotPassword;
    Button btnLogin;
    CheckBox cbKeepLogin;
    MyUser currentUser;
    MyApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityCollector.remove(this);
        finish();
    }

    private void init(){
        etLoginUsername = findViewById(R.id.et_login_username);
        etLoginPassword = findViewById(R.id.et_login_password);
        tvToRegister = findViewById(R.id.tv_to_register);
        tvForgotPassword = findViewById(R.id.tv_forget_password);
        sdvLoginAvatar = findViewById(R.id.sdv_login_avatar);
        cbKeepLogin = findViewById(R.id.cb_keep_login);
        btnLogin = findViewById(R.id.btn_login);
        tvToRegister.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        cbKeepLogin.setOnCheckedChangeListener(this);
        application = (MyApplication) getApplication();
        currentUser = application.getCurrentUser();
        if (currentUser == null)
            currentUser = new MyUser();
        etLoginUsername.setText(currentUser.getUsername());
        if (currentUser.getAvatarUrl() !=null )
            sdvLoginAvatar.setImageURI(currentUser.getAvatarUrl());
        else{
            Uri uri = Uri.parse("res://"+this.getPackageName()+"/"+R.drawable.avatar_default);
            sdvLoginAvatar.setImageURI(uri);
        }

    }

    private boolean checkUserInfo(){
        String userName = etLoginUsername.getText().toString();
        String userPassword = etLoginPassword.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword)){
            Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            currentUser.setUsername(userName);
            currentUser.setPassword(userPassword);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_to_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.btn_login:
                if (checkUserInfo()){
                    currentUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.d(TAG, "" + currentUser.isKeepLogin());
                                application.setCurrentUser(currentUser);
                            }
                            else
                                Log.d(TAG, "done: "+e.getMessage());
                        }
                    });
                    currentUser.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null){
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }else {

                                Toast.makeText(LoginActivity.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    

                }
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.cb_keep_login:
                if (cbKeepLogin.isChecked())
                    currentUser.setKeepLogin(true);
                else
                    currentUser.setKeepLogin(false);
        }
    }
}
