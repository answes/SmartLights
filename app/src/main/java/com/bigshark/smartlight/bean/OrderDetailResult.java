package com.bigshark.smartlight.bean;

/**
 * Created by jlbs1 on 2017/4/27.
 */

public class OrderDetailResult {

    private int code;
    private String extra;
    private OrderDetail data;
    public class  OrderDetail{
        private String id;
        private String order_num;
        private String username;
        private String address;
        private String tel;
        private String gitems;
        private String gmoney;
        private String omoney;
        private String paytype;
        private String status;
        private String pay_tm;
        private String send_tm;
        private String exp_com;
        private String exp_num;
        private String finish_tm;
        private String cancel_tm;
        private String buyer_id;
        private String cre_tm;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getGitems() {
            return gitems;
        }

        public void setGitems(String gitems) {
            this.gitems = gitems;
        }

        public String getGmoney() {
            return gmoney;
        }

        public void setGmoney(String gmoney) {
            this.gmoney = gmoney;
        }

        public String getOmoney() {
            return omoney;
        }

        public void setOmoney(String omoney) {
            this.omoney = omoney;
        }

        public String getCre_tm() {
            return cre_tm;
        }

        public void setCre_tm(String cre_tm) {
            this.cre_tm = cre_tm;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPay_tm() {
            return pay_tm;
        }

        public void setPay_tm(String pay_tm) {
            this.pay_tm = pay_tm;
        }

        public String getSend_tm() {
            return send_tm;
        }

        public void setSend_tm(String send_tm) {
            this.send_tm = send_tm;
        }

        public String getExp_com() {
            return exp_com;
        }

        public void setExp_com(String exp_com) {
            this.exp_com = exp_com;
        }

        public String getExp_num() {
            return exp_num;
        }

        public void setExp_num(String exp_num) {
            this.exp_num = exp_num;
        }

        public String getFinish_tm() {
            return finish_tm;
        }

        public void setFinish_tm(String finish_tm) {
            this.finish_tm = finish_tm;
        }

        public String getCancel_tm() {
            return cancel_tm;
        }

        public void setCancel_tm(String cancel_tm) {
            this.cancel_tm = cancel_tm;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
        this.data = data;
    }
}
