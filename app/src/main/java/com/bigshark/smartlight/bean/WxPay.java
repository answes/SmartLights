package com.bigshark.smartlight.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ch on 2017/5/14.
 *
 * @email 869360026@qq.com
 */

public class WxPay {
    /**
     * code : 1
     * data : {"appid":"wx35bd3eeb5d531eaf","partnerid":"1459496802","prepayid":"wx20170514175235e278dd3d900087074752","noncestr":"MAifJHitUXjaBvYH","timestamp":1494755555,"package":"Sign=WXPay","sign":"F6AF8131A113CD10CF2EF66DCCC53D3A"}
     * extra :
     */

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private DataBean data;
    @SerializedName("extra")
    private String extra;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public static class DataBean {
        /**
         * appid : wx35bd3eeb5d531eaf
         * partnerid : 1459496802
         * prepayid : wx20170514175235e278dd3d900087074752
         * noncestr : MAifJHitUXjaBvYH
         * timestamp : 1494755555
         * package : Sign=WXPay
         * sign : F6AF8131A113CD10CF2EF66DCCC53D3A
         */

        @SerializedName("appid")
        private String appid;
        @SerializedName("partnerid")
        private String partnerid;
        @SerializedName("prepayid")
        private String prepayid;
        @SerializedName("noncestr")
        private String noncestr;
        @SerializedName("timestamp")
        private int timestamp;
        @SerializedName("package")
        private String packageX;
        @SerializedName("sign")
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
