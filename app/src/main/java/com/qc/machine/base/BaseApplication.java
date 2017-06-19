package com.qc.machine.base;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

import talex.zsw.baselibrary.util.klog.KLog;

public class BaseApplication extends Application {

    private List<Activity> activityList = new LinkedList<>();
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(true);//初始化KLog
    }


    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public void exit() {
        exitApp();
        System.exit(0);
    }

    public void exitApp() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        // 杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
