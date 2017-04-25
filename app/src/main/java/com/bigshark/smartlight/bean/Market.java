package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by bigShark on 2016/12/28.
 */

public class Market {
    private int code;
    private String extra;
    private List<Goods> data;

   public class Goods {
        private int id;
        private String name;
        private String fig;
        private String img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

    public List<Goods> getData() {
        return data;
    }

    public void setData(List<Goods> data) {
        this.data = data;
    }
}
