package com.qc.machine.ui.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.qc.machine.R;
import com.qc.machine.base.BaseActivity;
import com.qc.machine.model.HairdresserInfoModel;
import com.qc.machine.model.MachineInfoModel;
import com.qc.machine.utils.AsyncImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import talex.zsw.baselibrary.util.klog.KLog;
import talex.zsw.baselibrary.view.JCVideoPlayer.JCVideoPlayer;
import talex.zsw.baselibrary.widget.CircleImageView;

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
    @Bind(R.id.tv_hotline)
    TextView tvHotline;
    @Bind(R.id.tv_shopname)
    TextView tvShopname;
    @Bind(R.id.shop_qrcode)
    ImageView ivShopQrcode;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.headpic1)
    CircleImageView ivHeadpic1;
    @Bind(R.id.headpic2)
    CircleImageView ivHeadpic2;
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
    @Bind(R.id.ad_banner)
    Banner banner;
//    @Bind(R.id.ad_player)
//    JCVideoPlayer player;

    private String mShopId;
    private String mSn;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MachineInfoModel model = (MachineInfoModel) msg.obj;
                    KLog.d("msg.what  " + model.toString());
                    if ("1".equals(model.getValidsts())) {
                        if ("0".equals(model.getEndflag()) || "2".equals(model.getEndflag())) {
                            tvPrice.setText(model.getPrice());
                            //TODO 生成对应价格二维码
                        } else if ("1".equals(model.getEndflag())) {
                        }
                        if ("1".equals(model.getAdtype())) {
                            banner.setVisibility(View.VISIBLE);
                            banner.setImages(model.getPics());
                            banner.start();
                        } else if ("2".equals(model.getAdtype())) {
                            //TODO 视频
                        }
                        tvShopname.setText(model.getShopname());
                        tvWorkTime.setText(model.getOpentime());
                        tvHotline.setText(model.getServicetel());
                        mSn = model.getSn();
                        tvSn.setText(mSn);
                        mShopId = model.getShopid();
                        timer.schedule(task, 1000, 10000);
                    } else if ("0".equals(model.getValidsts())) {
                        Toast.makeText(MainActivity.this, "秘钥已过期，请重新充值", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    HairdresserInfoModel model1 = (HairdresserInfoModel) msg.obj;
                    tvHairTime.setText(model1.getCmt1().getT());
                    tvHairDisc.setText(model1.getCmt1().getBn() + "理发师为顾客" + model1.getCmt1().getCn() + "理发");
                    tvGoodTime.setText(model1.getCmt2().getT());
                    tvGoodDisc.setText("顾客" + model1.getCmt1().getCn() + "给" + model1.getCmt2().getBn() + "理发师好评");
                    tvBadTime.setText(model1.getCmt3().getT());
                    tvBadDisc.setText("顾客" + model1.getCmt1().getCn() + "给" + model1.getCmt2().getBn() + "理发师差评");
                    tvCommentTime.setText(model1.getCmt4().getT());
                    tvCommentDisc.setText("顾客" + model1.getCmt4().getCn() + "评价" + model1.getCmt4().getBn() + ":" +
                            model1.getCmt4().getM());


                    switch (model1.getLst().size()) {
                        case 4:
                            KLog.i("3");
                        case 3:
                            KLog.i("2");
                        case 2:
                            AsyncImageLoader.setImageViewFromUrl(model1.getLst().get(1).getHeadpic(), ivHeadpic2);
                            tvCurrentNum2.setText(model1.getLst().get(1).getWcnt());
                            tvQueueNum2.setText(model1.getLst().get(1).getQueueno());
                            tvTime2.setText(model1.getLst().get(1).getChktime());
                            setGradeImage(model1, 1, lv2);
                        case 1:
                            AsyncImageLoader.setImageViewFromUrl(model1.getLst().get(0).getHeadpic(), ivHeadpic1);
                            tvCurrentNum1.setText(model1.getLst().get(0).getWcnt());
                            tvQueueNum1.setText(model1.getLst().get(0).getQueueno());
                            tvTime1.setText(model1.getLst().get(0).getChktime());
                            setGradeImage(model1, 0, lv1);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    private void setGradeImage(HairdresserInfoModel model1, int position, ImageView lv) {
        switch (model1.getLst().get(position).getGrade()) {
            case "0":
            case "1":
            case "2":
                lv.setImageBitmap(null);
                break;
            case "3":
            case "4":
                lv.setImageResource(R.drawable.silver);
                break;
            case "5":
                lv.setImageResource(R.drawable.gold);
        }
    }

    private Timer timer = new Timer(true);
    private TimerTask task = new TimerTask() {
        public void run() {
            updateBarberInfo(mShopId);
        }
    };

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
        initBanner();
    }

    private void initBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new AsyncImageLoader());
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.isAutoPlay(true);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    @Override
    protected void initData() {
        tvTime.setText(getNowTime());
        getMachineInfo("100009");
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

    private void updateBarberInfo(String shopId) {
        OkGo.get("http://106.15.61.209:7881/plazz/api/qchapi/" + "getShopBarberList")
                .tag(this)
                .params("token", "1234567890")
                .params("shopid", shopId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String string, Call call, Response response) {
                        KLog.i(string + "  " + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            if ("100000".equals(jsonObject.getString("code"))) {
                                String str = jsonObject.getString("data");
                                Gson gson = new Gson();
                                HairdresserInfoModel model = gson.fromJson(str, HairdresserInfoModel.class);
                                KLog.d(model.toString());
                                Message msg = new Message();
                                msg.what = 2;
                                msg.obj = model;
                                mHandler.sendMessage(msg);
                            } else {
                                Toast.makeText(MainActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getMachineInfo(String sn) {
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
                                String str = jsonObject.getString("data");
                                Gson gson = new Gson();
                                MachineInfoModel model = gson.fromJson(str, MachineInfoModel.class);
                                KLog.d(model.toString());
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = model;
                                mHandler.sendMessage(msg);
                            } else {
                                Toast.makeText(MainActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
