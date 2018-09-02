package com.scu.yangshuai.mytodo.controller;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangshuai on 2018/3/5.
 *
 * @Description: 活动收集器
 */

public class ActivityCollector {

    public static List<Activity> activityList = new ArrayList<>();

    //将当前活动放入活动收集器
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    //将当前活动从活动收集器中删除
    public static void remove(Activity activity){
        activityList.remove(activity);
    }

    //删除活动收集器中的所用活动
    public static void removeAll(){
        for (Activity activity:activityList){
            if (!activity.isFinishing())
                activity.finish();
        }
        activityList.clear();
    }
}
