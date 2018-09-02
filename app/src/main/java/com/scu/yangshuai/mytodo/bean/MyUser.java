package com.scu.yangshuai.mytodo.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by yangshuai on 2018/3/7.
 *
 * @Description:
 */

public class MyUser extends BmobUser {

    private String avatarUrl;
    private boolean keepLogin;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avaterUrl) {
        this.avatarUrl = avatarUrl;
    }


    public boolean isKeepLogin() {
        return keepLogin;
    }

    public void setKeepLogin(boolean keepLogin) {
        this.keepLogin = keepLogin;
    }
}
