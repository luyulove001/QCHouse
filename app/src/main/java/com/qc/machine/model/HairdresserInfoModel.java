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

    public class Cmt {
        private String bn;
        private String t;
        private String cn;
        private String tp;

        public String getBn() {
            return bn;
        }

        public void setBn(String bn) {
            this.bn = bn;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public String getTp() {
            return tp;
        }

        public void setTp(String tp) {
            this.tp = tp;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        private String m;
    }

    public class DresserInfo {
        private String headpic;
        private String wcnt;
        private String barberid;
        private String grade;
        private String queueno;
        private String realname;

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getWcnt() {
            return wcnt;
        }

        public void setWcnt(String wcnt) {
            this.wcnt = wcnt;
        }

        public String getBarberid() {
            return barberid;
        }

        public void setBarberid(String barberid) {
            this.barberid = barberid;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getQueueno() {
            return queueno;
        }

        public void setQueueno(String queueno) {
            this.queueno = queueno;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getChktime() {
            return chktime;
        }

        public void setChktime(String chktime) {
            this.chktime = chktime;
        }

        private String chktime;
    }
}
