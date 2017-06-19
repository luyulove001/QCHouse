package com.qc.machine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.qc.machine.R;
import com.qc.machine.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity {
    @Bind(R.id.login)
    Button btnLogin;
    @Bind(R.id.exit)
    Button btnExit;
    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }
}
