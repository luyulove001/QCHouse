package com.qc.machine.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qc.machine.ui.activity.LoginActivity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.setClass(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}