package com.bigshark.smartlight.pro.base.model;

import android.content.Context;

import com.bigshark.smartlight.mvp.model.IMVPModel;

/**
 * Created by bigShark on 2016/12/19.
 */

public class BaseModel implements IMVPModel {

    private Context context;

    public BaseModel(Context context){
        this.context = context;
    }

    /**
     * 服务器默认前置地址
     */
    public  String getServerUrl(){
        return  "https://www.baidu.com";
    }
}
