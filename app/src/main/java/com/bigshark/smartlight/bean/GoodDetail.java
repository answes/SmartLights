package com.bigshark.smartlight.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bigShark on 2016/12/28.
 */

public class GoodDetail {

    /**
     * code : 1
     * data : {"id":"8","name":"车灯","code":"0001","color":"1","price":"129.00","num":"10000","content":"<h2 style=\"text-align:center;\">\r\n\t<strong>这款车灯不仅颜色亮丽，而且还具备蓝牙功能<\/strong>\r\n<\/h2>\r\n<h2 style=\"text-align:center;\">\r\n\t<strong><span class=\" newTimeFactor_before_abs m\" style=\"color:#666666;font-family:arial;font-size:13px;\">&nbsp;<\/span><span style=\"color:#333333;font-family:arial;font-size:13px;background-color:#FFFFFF;\">喜欢骑车的人都会给坐骑安一个车灯,可是走南闯北很多年,有一种安装在车轮上、能把车轮变成电子屏幕的<\/span><span style=\"color:#CC0000;font-family:arial;font-size:13px;\">智能车灯<\/span><span style=\"color:#333333;font-family:arial;font-size:13px;background-color:#FFFFFF;\">你见过吗?下面我们就看看它有多神奇吧<\/span><\/strong> \r\n<\/h2>\r\n<h2 style=\"text-align:center;\">\r\n\t<strong><img src=\"http://pybike.idc.zhonxing.com/Uploads/Editor/2017-04-20/58f854005e7bd.jpg\" alt=\"\" /><\/strong> \r\n<\/h2>\r\n<h2 style=\"text-align:center;\">\r\n\t<strong><img src=\"http://pybike.idc.zhonxing.com/Uploads/Editor/2017-04-20/58f85425dc7b6.jpg\" alt=\"\" /><\/strong> \r\n<\/h2>\r\n<h2 style=\"text-align:center;\">\r\n\t<strong><img src=\"http://pybike.idc.zhonxing.com/Uploads/Editor/2017-04-20/58f85434cdd71.jpg\" alt=\"\" /><\/strong> \r\n<\/h2>","fig":"14,15","status":"1","cre_tm":"1492509467","express":"0.00","imglist":null,"img":["http://pybike.idc.zhonxing.com/Uploads/Picture/2017-04-18/58f5e2788d56e.jpg","http://pybike.idc.zhonxing.com/Uploads/Picture/2017-04-18/58f5e3167fe77.png"],"color_txt":"红"}
     * extra :
     */

    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private Goods data;
    @SerializedName("extra")
    private String extra;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Goods getData() {
        return data;
    }

    public void setData(Goods data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public static class Goods {
        /**
         * id : 8
         * name : 车灯
         * code : 0001
         * color : 1
         * price : 129.00
         * num : 10000
         * content : <h2 style="text-align:center;">
         <strong>这款车灯不仅颜色亮丽，而且还具备蓝牙功能</strong>
         </h2>
         <h2 style="text-align:center;">
         <strong><span class=" newTimeFactor_before_abs m" style="color:#666666;font-family:arial;font-size:13px;">&nbsp;</span><span style="color:#333333;font-family:arial;font-size:13px;background-color:#FFFFFF;">喜欢骑车的人都会给坐骑安一个车灯,可是走南闯北很多年,有一种安装在车轮上、能把车轮变成电子屏幕的</span><span style="color:#CC0000;font-family:arial;font-size:13px;">智能车灯</span><span style="color:#333333;font-family:arial;font-size:13px;background-color:#FFFFFF;">你见过吗?下面我们就看看它有多神奇吧</span></strong>
         </h2>
         <h2 style="text-align:center;">
         <strong><img src="http://pybike.idc.zhonxing.com/Uploads/Editor/2017-04-20/58f854005e7bd.jpg" alt="" /></strong>
         </h2>
         <h2 style="text-align:center;">
         <strong><img src="http://pybike.idc.zhonxing.com/Uploads/Editor/2017-04-20/58f85425dc7b6.jpg" alt="" /></strong>
         </h2>
         <h2 style="text-align:center;">
         <strong><img src="http://pybike.idc.zhonxing.com/Uploads/Editor/2017-04-20/58f85434cdd71.jpg" alt="" /></strong>
         </h2>
         * fig : 14,15
         * status : 1
         * cre_tm : 1492509467
         * express : 0.00
         * imglist : null
         * img : ["http://pybike.idc.zhonxing.com/Uploads/Picture/2017-04-18/58f5e2788d56e.jpg","http://pybike.idc.zhonxing.com/Uploads/Picture/2017-04-18/58f5e3167fe77.png"]
         * color_txt : 红
         */

        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("code")
        private String code;
        @SerializedName("color")
        private String color;
        @SerializedName("price")
        private String price;
        @SerializedName("num")
        private String num;
        @SerializedName("content")
        private String content;
        @SerializedName("fig")
        private String fig;
        @SerializedName("status")
        private String status;
        @SerializedName("cre_tm")
        private String creTm;
        @SerializedName("express")
        private String express;
        @SerializedName("imglist")
        private Object imglist;
        @SerializedName("color_txt")
        private String colorTxt;
        @SerializedName("img")
        private List<String> img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFig() {
            return fig;
        }

        public void setFig(String fig) {
            this.fig = fig;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreTm() {
            return creTm;
        }

        public void setCreTm(String creTm) {
            this.creTm = creTm;
        }

        public String getExpress() {
            return express;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public Object getImglist() {
            return imglist;
        }

        public void setImglist(Object imglist) {
            this.imglist = imglist;
        }

        public String getColorTxt() {
            return colorTxt;
        }

        public void setColorTxt(String colorTxt) {
            this.colorTxt = colorTxt;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
