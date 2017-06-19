package com.qc.machine.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */

public class HairdresserInfoModel implements Serializable {
    private Cmt cmt1;
    private Cmt cmt2;
    private Cmt cmt3;
    private Cmt cmt4;

    private List<DresserInfo> lst;

    public Cmt getCmt1() {
        return cmt1;
    }

    public void setCmt1(Cmt cmt1) {
        this.cmt1 = cmt1;
    }

    public Cmt getCmt2() {
        return cmt2;
    }

    public void setCmt2(Cmt cmt2) {
        this.cmt2 = cmt2;
    }

    public Cmt getCmt3() {
        return cmt3;
    }

    public void setCmt3(Cmt cmt3) {
        this.cmt3 = cmt3;
    }

    public Cmt getCmt4() {
        return cmt4;
    }

    public void setCmt4(Cmt cmt4) {
        this.cmt4 = cmt4;
    }

    public List<DresserInfo> getLst() {
        return lst;
    }

    public void setLst(List<DresserInfo> lst) {
        this.lst = lst;
    }

    private class Cmt {
        private String bn;
        private String t;
        private String cn;
        private String tp;
    }

    private class DresserInfo {
        private String headpic;
        private String wcnt;
        private String barberid;
        private String grade;
        private String queueno;
        private String realname;
        private String chktime;
    }
}
