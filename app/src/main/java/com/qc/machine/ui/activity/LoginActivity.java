package com.qc.machine.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qc.machine.R;
import com.qc.machine.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import talex.zsw.baselibrary.util.klog.KLog;


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

    private void updateUseState(String sn) {
        OkGo.get("http://106.15.61.209:7881/plazz/api/qchapi/" + "getShopMachineInfo")
                .tag(this)
                .params("token", "1234567890")
                .params("sn", sn)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        KLog.i(string + "  " + response.toString());
                    }
                });
    }
}
