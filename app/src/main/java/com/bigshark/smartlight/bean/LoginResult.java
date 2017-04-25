package com.bigshark.smartlight.bean;

/**
 * Created by ch on 2017/2/21.
 *
 * @email 869360026@qq.com
 */

public class LoginResult {
    private int code;
    private String extra;
    private User data;

    public LoginResult(int code, String extra, User data) {
        this.code = code;
        this.extra = extra;
        this.data = data;
    }

    public LoginResult() {

    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
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
;
    public void setExtra(String extra) {
        this.extra = extra;
    }
   public class User{
        private String id;
        private String user_num;
        private String tel;
        private String name;
        private String height;
        private String age;
        private String weight;
        private String sex;
        private String status;
        private String reg_tm;
        private String btel;
       private int fig;

       public User(String id, String user_num, String tel, String name, String height, String age, String weight, String sex, String status, String reg_tm, String btel, int fig) {
           this.id = id;
           this.user_num = user_num;
           this.tel = tel;
           this.name = name;
           this.height = height;
           this.age = age;
           this.weight = weight;
           this.sex = sex;
           this.status = status;
           this.reg_tm = reg_tm;
           this.btel = btel;
           this.fig = fig;
       }

       public User() {

       }

       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getUser_num() {
           return user_num;
       }

       public void setUser_num(String user_num) {
           this.user_num = user_num;
       }

       public String getTel() {
           return tel;
       }

       public void setTel(String tel) {
           this.tel = tel;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getHeight() {
           return height;
       }

       public void setHeight(String height) {
           this.height = height;
       }

       public String getAge() {
           return age;
       }

       public void setAge(String age) {
           this.age = age;
       }

       public String getWeight() {
           return weight;
       }

       public void setWeight(String weight) {
           this.weight = weight;
       }

       public String getSex() {
           return sex;
       }

       public void setSex(String sex) {
           this.sex = sex;
       }

       public String getStatus() {
           return status;
       }

       public void setStatus(String status) {
           this.status = status;
       }

       public String getReg_tm() {
           return reg_tm;
       }

       public void setReg_tm(String reg_tm) {
           this.reg_tm = reg_tm;
       }

       public String getBtel() {
           return btel;
       }

       public void setBtel(String btel) {
           this.btel = btel;
       }

       public int getFig() {
           return fig;
       }

       public void setFig(int fig) {
           this.fig = fig;
       }
   }
}
