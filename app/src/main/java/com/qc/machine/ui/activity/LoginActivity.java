package com.qc.machine.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qc.machine.R;
import com.qc.machine.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import talex.zsw.baselibrary.util.klog.KLog;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.login)
    Button btnLogin;
    @Bind(R.id.exit)
    Button btnExit;
    @Bind(R.id.et_inputKey)
    EditText etInputKey;

    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getSharedPreferences("machine", Context.MODE_PRIVATE);
        String sn = sp.getString("sn", null);
        if (sn != null) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("sn", sn);
            this.finish();
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                doLogin(etInputKey.getText().toString());
                break;
            case R.id.exit:
                finish();
                break;
        }
    }

    private void doLogin(final String sn) {
        OkGo.get("http://106.15.61.209:7881/plazz/api/qchapi/" + "getShopMachineInfo")
                .tag(this)
                .params("token", "1234567890")
                .params("sn", sn)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        KLog.i(string + "  " + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            if ("100000".equals(jsonObject.getString("code"))) {
                                SharedPreferences sp = getSharedPreferences("machine", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("sn", sn);
                                editor.apply();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("sn", sn);
                                LoginActivity.this.finish();
                                startActivity(i);
                            } else {
                                exitDialog(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void exitDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        builder.create().show();
    }
}
