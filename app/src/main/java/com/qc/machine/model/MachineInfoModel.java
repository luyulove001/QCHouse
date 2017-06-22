package com.qc.machine.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/19.
 */

public class MachineInfoModel implements Serializable {
    private String videourl;
    private String shopid;
    private String sn;
    private String actpic;
    private String actprice;
    private String price;
    private String findBy;
    private String mid;
    private String[] pics1;

    public String getActprice() {
        return actprice;
    }

    public void setActprice(String actprice) {
        this.actprice = actprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFindBy() {
        return findBy;
    }

    public void setFindBy(String findBy) {
        this.findBy = findBy;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String[] getPics1() {
        return pics1;
    }

    public void setPics1(String[] pics1) {
        this.pics1 = pics1;
    }

    private String servicetel;
    private String adtype;
    private String validsts;
    private String shopname;
    private String opentime;
    private String endflag;

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getActpic() {
        return actpic;
    }

    public void setActpic(String actpic) {
        this.actpic = actpic;
    }

    public String getServicetel() {
        return servicetel;
    }

    public void setServicetel(String servicetel) {
        this.servicetel = servicetel;
    }

    public String getAdtype() {
        return adtype;
    }

    public void setAdtype(String adtype) {
        this.adtype = adtype;
    }

    public String getValidsts() {
        return validsts;
    }

    public void setValidsts(String validsts) {
        this.validsts = validsts;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getEndflag() {
        return endflag;
    }

    public void setEndflag(String endflag) {
        this.endflag = endflag;
    }

    @Override
    public String toString() {
        return "MachineInfoModel{" +
                "videourl='" + videourl + '\'' +
                ", shopid='" + shopid + '\'' +
                ", sn='" + sn + '\'' +
                ", actpic='" + actpic + '\'' +
                ", servicetel='" + servicetel + '\'' +
                ", adtype='" + adtype + '\'' +
                ", validsts='" + validsts + '\'' +
                ", shopname='" + shopname + '\'' +
                ", opentime='" + opentime + '\'' +
                ", endflag='" + endflag + '\'' +
                '}';
    }
}
