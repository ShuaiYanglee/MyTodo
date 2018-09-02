package com.scu.yangshuai.mytodo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.scu.yangshuai.mytodo.bean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by yangshuai on 2018/3/7.
 *
 * @Description:
 */

public class MyApplication extends Application {

    public MyUser currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"73ec2714387dd7073a8b2c1d4b1b147f");
        Fresco.initialize(this);
        currentUser = BmobUser.getCurrentUser(MyUser.class);
    }

    public void setCurrentUser(MyUser currentUser) {
        this.currentUser = currentUser;
    }

    public MyUser getCurrentUser() {
        return currentUser;
    }
}
