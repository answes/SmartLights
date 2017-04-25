package com.bigshark.smartlight.bean;


import java.util.List;

/**
 * Created by bigShark on 2016/12/28.
 */

public class GoodDetail {
    private int code;
    private String extra;
    private Goods data;

   public class Goods {
        private int id;
        private String name;
        private String fig;
        private List<String> img;
       private String code;
       private String color;
       private String price;
       private String num;
       private String content;
       private String status;
       private String creTm;
       private String express;

       public String getExpress() {
           return express;
       }

       public void setExpress(String express) {
           this.express = express;
       }

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

        public String getFig() {
            return fig;
        }

        public void setFig(String fig) {
            this.fig = fig;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
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

    public Goods getData() {
        return data;
    }

    public void setData(Goods data) {
        this.data = data;
    }
}
