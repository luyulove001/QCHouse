package com.qc.machine.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.qc.machine.R;
import com.qc.machine.base.BaseActivity;
import com.qc.machine.base.BaseApplication;
import com.qc.machine.model.HairdresserInfoModel;
import com.qc.machine.model.MachineInfoModel;
import com.qc.machine.utils.AsyncImageLoader;
import com.qc.machine.utils.BitmapQRUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import talex.zsw.baselibrary.util.klog.KLog;
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
    @Bind(R.id.lyt_normal)
    RelativeLayout lytNormal;
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
    @Bind(R.id.ad_player)
    VideoView player;
    @Bind(R.id.lyt_head)
    RelativeLayout lytHead;
    @Bind(R.id.barber2)
    RelativeLayout lytBaber2;
    @Bind(R.id.lyt_show_area)
    RelativeLayout lytShowArea;
    @Bind(R.id.lyt_comment)
    RelativeLayout lytComment;

    private String mShopId;
    private String mSn;
    private String headpic1 = "", headpic2 = "";
    private Bitmap bitmap;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    MachineInfoModel model = (MachineInfoModel) msg.obj;
                    if ("1".equals(model.getValidsts())) {
                        if ("0".equals(model.getEndflag()) || "2".equals(model.getEndflag())) {
                            lytNormal.setVisibility(View.VISIBLE);
                            lytActive.setVisibility(View.GONE);
                            tvPrice.setText(model.getPrice());
                            bitmap = BitmapQRUtils.generateQRCode(getPayUrl(mSn, model.getPrice()));
                            ivShopQrcode.setImageBitmap(bitmap);
                        } else if ("1".equals(model.getEndflag())) {
                            lytActive.setVisibility(View.VISIBLE);
                            lytNormal.setVisibility(View.GONE);
                            bitmap = BitmapQRUtils.generateQRCode(getPayUrl(mSn, model.getActprice()));
                            ivActiveQrcode.setImageBitmap(bitmap);
                            Glide.with(getApplicationContext()).load(model.getActpic()).into(ivActive);
                        }
                        if ("1".equals(model.getAdtype())) {
                            banner.setVisibility(View.VISIBLE);
                            player.setVisibility(View.GONE);
                            banner.setImages(model.getPics());
                            banner.start();
                        } else if ("2".equals(model.getAdtype())) {
                            player.setVisibility(View.VISIBLE);
                            banner.setVisibility(View.GONE);
                            String url = model.getVideourl();
                            String fileName = "/sdcard/QCMachine/" + url.substring(url.lastIndexOf("/") + 1);
                            File f = new File(fileName);
                            if (f.exists()) {
                                final Uri videoUri = Uri.parse(fileName);
                                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        player.setVideoURI(videoUri);
                                        player.start();
                                    }
                                });
                                player.setVideoURI(videoUri);
                                player.start();
                            } else {
                                HttpProxyCacheServer proxy = BaseApplication.getProxy(MainActivity.this);
                                final String proxyUrl = proxy.getProxyUrl(url);
                                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        player.setVideoPath(proxyUrl);
                                        player.start();
                                    }
                                });
                                player.setVideoPath(proxyUrl);
                                player.start();
                                downloadMp4(url);
                            }
                        }
                        tvShopname.setText(model.getShopname());
                        tvWorkTime.setText(model.getOpentime());
                        tvHotline.setText(model.getServicetel());
                        mSn = model.getSn();
                        tvSn.setText(String.format(getResources().getString(R.string.sn), mSn));
                        mShopId = model.getShopid();
                        timer.schedule(task, 0, 10000);
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
                    if (model1.getLst().size() == 1) {
                        lytBaber2.setVisibility(View.GONE);
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) lytHead.getLayoutParams();
                        params.bottomMargin = (int) getResources().getDimension(R.dimen.y20);
                        lytHead.setLayoutParams(params);
                        params = (RelativeLayout.LayoutParams) lytShowArea.getLayoutParams();
                        params.height = (int) getResources().getDimension(R.dimen.y428);
                        params.topMargin = (int) getResources().getDimension(R.dimen.y20);
                        lytShowArea.setLayoutParams(params);
                        params = (RelativeLayout.LayoutParams) lytComment.getLayoutParams();
                        params.topMargin = (int) getResources().getDimension(R.dimen.y30);
                        lytComment.setLayoutParams(params);
                    }
                    switch (model1.getLst().size()) {
                        case 4:
                            msg.what = 4;
                            mHandler.sendMessageDelayed(msg, 5000);
                            break;
                        case 3:
                            msg.what = 3;
                            mHandler.sendMessageDelayed(msg, 5000);
                            break;
                        case 2:
                            showCuterTwo(model1, 1);
                        case 1:
                            showCuterOne(model1, 0);
                    }
                    break;
                case 3:
                    HairdresserInfoModel model2 = (HairdresserInfoModel) msg.obj;
                    showCuterOne(model2, 1);
                    showCuterTwo(model2, 2);
                    break;
                case 4:
                    HairdresserInfoModel model3 = (HairdresserInfoModel) msg.obj;
                    showCuterOne(model3, 2);
                    showCuterTwo(model3, 3);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    private void downloadMp4(String s) {
        OkGo.get(s)
                .tag(this)
                .execute(new FileCallback("/sdcard/QCMachine/", s.substring(s.lastIndexOf("/") + 1)) {
                    @Override
                    public void onBefore(BaseRequest request) {
                        KLog.d("正在下载中");
                    }

                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        KLog.d("下载完成---" + file.getAbsolutePath());
                    }

                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress
                                + "  " + networkSpeed);
                    }

                    @Override
                    public void onError(Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onError(call, response, e);
                        KLog.d("下载出错");
                    }
                });
    }

    private void showCuterOne(HairdresserInfoModel model, int position) {
        if (!headpic1.equals(model.getLst().get(position).getHeadpic())) {
            Glide.with(getApplicationContext()).load(model.getLst().get(position).getHeadpic()).into(ivHeadpic1);
            headpic1 = model.getLst().get(position).getHeadpic();
        }
        tvCurrentNum1.setText(model.getLst().get(position).getWcnt());
        tvQueueNum1.setText(model.getLst().get(position).getQueueno());
        tvTime1.setText(model.getLst().get(position).getChktime());
        setGradeImage(model, position, lv1);
    }

    private void showCuterTwo(HairdresserInfoModel model, int position) {
        if (!headpic2.equals(model.getLst().get(position).getHeadpic())) {
            Glide.with(getApplicationContext()).load(model.getLst().get(position).getHeadpic()).into(ivHeadpic2);
            headpic2 = model.getLst().get(position).getHeadpic();
        }
        tvCurrentNum2.setText(model.getLst().get(position).getWcnt());
        tvQueueNum2.setText(model.getLst().get(position).getQueueno());
        tvTime2.setText(model.getLst().get(position).getChktime());
        setGradeImage(model, position, lv2);
    }

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
        getMachineInfo(intent.getStringExtra("sn"));
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
        IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
        registerReceiver(timeReceiver, filter);
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
        OkGo.getInstance().cancelTag(this);
        unregisterReceiver(timeReceiver);
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
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            if ("100000".equals(jsonObject.getString("code"))) {
                                String str = jsonObject.getString("data");
                                Gson gson = new Gson();
                                HairdresserInfoModel model = gson.fromJson(str, HairdresserInfoModel.class);
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
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            if ("100000".equals(jsonObject.getString("code"))) {
                                String str = jsonObject.getString("data");
                                Gson gson = new Gson();
                                MachineInfoModel model = gson.fromJson(str, MachineInfoModel.class);
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

    public String getPayUrl(String sn, String amount) {
        return "http://km.qchouses.com?sn=" + sn + "&amount=" + amount;
    }

    private final BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_TICK)) {
                tvTime.setText(getNowTime());
            }
        }
    };
}
