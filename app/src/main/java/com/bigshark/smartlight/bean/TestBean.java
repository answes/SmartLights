package com.bigshark.smartlight.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ch on 2017/4/26.
 *
 * @email 869360026@qq.com
 */

public class TestBean {

    /**
     * code : 1
     * data : {"appid":"wx35bd3eeb5d531eaf","partnerId":"1459496802","prepayId":"wx2017042622542917831fbf1b0169000240","nonceStr":"jxu9vW3JSgTh3dtl","timeStamp":1493218470,"package":"Sign=WXPay","sign":"3B5E4E05FDF65E01F59403F19E6B6C49"}
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
         * partnerId : 1459496802
         * prepayId : wx2017042622542917831fbf1b0169000240
         * nonceStr : jxu9vW3JSgTh3dtl
         * timeStamp : 1493218470
         * package : Sign=WXPay
         * sign : 3B5E4E05FDF65E01F59403F19E6B6C49
         */

        @SerializedName("appid")
        private String appid;
        @SerializedName("partnerId")
        private String partnerId;
        @SerializedName("prepayId")
        private String prepayId;
        @SerializedName("nonceStr")
        private String nonceStr;
        @SerializedName("timeStamp")
        private long timeStamp;
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

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
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
