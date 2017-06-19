package com.qc.machine.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import talex.zsw.baselibrary.util.ACache;
import talex.zsw.baselibrary.util.StringUtils;
import talex.zsw.baselibrary.view.SweetAlertDialog.SweetAlertDialog;

public abstract class BaseActivity extends AppCompatActivity {
    protected BaseApplication mApplication;
    private InputMethodManager mInputMethodManager;
    public ACache mACache;

    protected abstract void initArgs(Intent intent);

    protected abstract void initView(Bundle bundle);

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        mApplication.addActivity(this);
        if (mACache == null) {
            mACache = ACache.get(this);
        }
        mInputMethodManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            initArgs(getIntent());
            initView(savedInstanceState);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        hideInputMethod();
    }

    @Override
    protected void onDestroy() {
        mApplication.removeActivity(this);
        mACache = null;
        super.onDestroy();
    }

    /**
     * 隐藏键盘
     */
    public void hideInputMethod() {
        try {
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ignored) {

        }
    }

    /**
     * 公共的加载提示对话框
     */
    public SweetAlertDialog sweetAlertDialog;

    /**
     * 成功 错误 警告 异常 提示框(只用来更改提示信息和状态类型和设置是否可以返回取消对话框)
     */
    public void sweetDialog(String information, int type) {
        sweetDialog(information, type, false);
    }

    /**
     * 成功 错误 警告 异常 提示框(只用来更改提示信息和状态类型和设置是否可以返回取消对话框)
     */
    public void sweetDialog(String information, int type, boolean cancelable) {
        if (sweetAlertDialog == null || !sweetAlertDialog.isShowing()) {
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        //0正常,1错误,2成功,3警告,5进度条
        sweetAlertDialog.setTitleText(information);
        sweetAlertDialog.changeAlertType(type);
        sweetAlertDialog.setCancelable(cancelable);//不让点击返回按钮取消对话框
        sweetAlertDialog.setConfirmText("确定");
        sweetAlertDialog.show();
    }

    public void sweetContextDialog(String information, String context, int type, boolean cancelable) {
        if (sweetAlertDialog == null || !sweetAlertDialog.isShowing()) {
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        //0正常,1错误,2成功,3警告,5进度条
        sweetAlertDialog.setTitleText(information);
        sweetAlertDialog.setContentText(context);
        sweetAlertDialog.changeAlertType(type);
        sweetAlertDialog.setCancelable(cancelable);//不让点击返回按钮取消对话框
        sweetAlertDialog.setConfirmText("确定");
        sweetAlertDialog.show();
    }

    /**
     * 关闭提示框
     */
    public void closeDialog() {
        if (sweetAlertDialog == null) {
            return;
        }
        sweetAlertDialog.dismiss();
    }

    public void showDialog(int type, String title, String content, String confirmText,
                           String cancelText, SweetAlertDialog.OnSweetClickListener confirmListener,
                           SweetAlertDialog.OnSweetClickListener cancelListener) {
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.changeAlertType(type);
        } else {
            sweetAlertDialog = new SweetAlertDialog(BaseActivity.this, type);
            sweetAlertDialog.setCancelable(false);
        }
        if (!StringUtils.isBlank(title)) {
            sweetAlertDialog.setTitleText(title);
        }
        if (!StringUtils.isBlank(content)) {
            sweetAlertDialog.setContentText(content);
        }
        if (!StringUtils.isBlank(confirmText)) {
            sweetAlertDialog.setConfirmText(confirmText);
        }
        if (!StringUtils.isBlank(cancelText)) {
            sweetAlertDialog.setCancelText(cancelText);
        }
        if (confirmListener != null) {
            sweetAlertDialog.setConfirmClickListener(confirmListener);
        }
        if (confirmListener != null) {
            sweetAlertDialog.setCancelClickListener(cancelListener);
        }
        if (!sweetAlertDialog.isShowing()) {
            sweetAlertDialog.show();
        }
    }
}
