package com.scu.yangshuai.mytodo.activity;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scu.yangshuai.mytodo.MyApplication;
import com.scu.yangshuai.mytodo.R;
import com.scu.yangshuai.mytodo.bean.MyUser;
import com.scu.yangshuai.mytodo.controller.ActivityCollector;

import cn.bmob.v3.BmobUser;


public class SplashActivity extends BaseActivity {

    Handler handler = new Handler();
    private static final String TAG = "SplashActivity";
    SimpleDraweeView sdvSplash;
    MyUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        Log.d(TAG, "onCreate: ");
        Toast.makeText(this,"欢迎使用MyTodo",Toast.LENGTH_SHORT).show();
        ActivityCollector.activityList.add(this);
        handler.postDelayed(r,3000);

    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
            MyApplication application = (MyApplication) getApplication();
            currentUser = application.getCurrentUser();
            //Log.d(TAG, "run: "+currentUser.isKeepLogin());
            if (currentUser != null) {
                if (currentUser.isKeepLogin()){
                    //如果用户保持登录，直接跳到主界面
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else {
                    //否则跳到登录界面
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }else {
                startActivity(new Intent(SplashActivity.this,RegisterActivity.class));
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        ActivityCollector.activityList.remove(this);
        finish();
    }

    void init() {
        sdvSplash = findViewById(R.id.sdv_splash);
        Uri uri = Uri.parse("res://"+this.getPackageName()+"/"+R.drawable.activity_splash);
        sdvSplash.setImageURI(uri);
    }

}
