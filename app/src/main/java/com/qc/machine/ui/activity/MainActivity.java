package com.qc.machine.ui.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qc.machine.R;
import com.qc.machine.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.ring1)
    View ring1;
    @Bind(R.id.ring2)
    View ring2;
    @Bind(R.id.ring3)
    View ring3;
    @Bind(R.id.ring4)
    View ring4;
    @Bind(R.id.iv_active)
    ImageView ivActive;
    @Bind(R.id.active_qrcode)
    ImageView ivActiveQrcode;
    @Bind(R.id.lyt_active)
    RelativeLayout lytActive;
    @Bind(R.id.tv_sn)
    TextView tvSn;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_work_time)
    TextView tvWorkTime;
    @Bind(R.id.tv_shopname)
    TextView tvShopname;
    @Bind(R.id.shop_qrcode)
    ImageView ivShopQrcode;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.headpic1)
    ImageView ivHeadpic1;
    @Bind(R.id.headpic2)
    ImageView ivHeadpic2;
    @Bind(R.id.lv1)
    ImageView lv1;
    @Bind(R.id.lv2)
    ImageView lv2;
    @Bind(R.id.queue_number)
    TextView tvQueueNum1;
    @Bind(R.id.queue_number2)
    TextView tvQueueNum2;
    @Bind(R.id.current_num)
    TextView tvCurrentNum1;
    @Bind(R.id.current_num2)
    TextView tvCurrentNum2;
    @Bind(R.id.time)
    TextView tvTime1;
    @Bind(R.id.time2)
    TextView tvTime2;
    @Bind(R.id.hair_time)
    TextView tvHairTime;
    @Bind(R.id.hair_disc)
    TextView tvHairDisc;
    @Bind(R.id.good_time)
    TextView tvGoodTime;
    @Bind(R.id.good_disc)
    TextView tvGoodDisc;
    @Bind(R.id.bad_time)
    TextView tvBadTime;
    @Bind(R.id.bad_disc)
    TextView tvBadDisc;
    @Bind(R.id.comment_time)
    TextView tvCommentTime;
    @Bind(R.id.comment_disc)
    TextView tvCommentDisc;


    @Override
    protected void initArgs(Intent intent) {

    }

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // ImmersionBar.with(this).init();
        setRingColor(ring2, getResources().getColor(R.color.color_good));
        setRingColor(ring3, getResources().getColor(R.color.color_bad));
        setRingColor(ring4, getResources().getColor(R.color.color_comment));
    }

    @Override
    protected void initData() {
        tvTime.setText(getNowTime());
    }

    private void setRingColor(View ring, int color) {
        LayerDrawable ld = (LayerDrawable) ring.getBackground();
        GradientDrawable gd = (GradientDrawable) ld.findDrawableByLayerId(R.id.out);
        gd.setStroke(3, color);
        gd = (GradientDrawable) ld.findDrawableByLayerId(R.id.in);
        gd.setColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ImmersionBar.with(this).destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    public String getNowTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm", Locale.CHINA);
        return sdf.format(c.getTime());
    }
}
