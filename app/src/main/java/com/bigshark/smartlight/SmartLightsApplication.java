package com.bigshark.smartlight;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bigshark.smartlight.bean.City;
import com.bigshark.smartlight.bean.LoginResult;
import com.bigshark.smartlight.bean.Province;
import com.bigshark.smartlight.utils.Contact;
import com.bigshark.smartlight.utils.GetJsonDataUtil;
import com.bigshark.smartlight.utils.SQLUtils;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigShark on 2017/1/16.
 */

public class SmartLightsApplication extends Application {

    public static LoginResult.User USER = null;
    public static RequestQueue queue;//volley 队列
    public static boolean isOpenVioce;  //是否播放语音
    public static  boolean isAutoClose;

    //开启线程初始化 省市区数据 及附属数据
    public static List<Province> provinces = new ArrayList<>();
    public static List<List<City>> cities = new ArrayList<>();
    public static List<List<List<String>>> areas = new ArrayList<>();

    public  static String cityName = "";

    @Override
    public void onCreate() {
        super.onCreate();
        SupportMultipleScreensUtil.init(this);

        initVolley();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //initJson();
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(SmartLightsApplication.this, null);
                // 将该app注册到微信
                msgApi.registerApp("wx35bd3eeb5d531eaf");
                initJson();
                initFireWare();
            }
        }).start();
        initPhoto();
        //initErrorHandler();
    }
    private void initPhoto() {
        FunctionOptions options = new FunctionOptions.Builder()
                .setType( FunctionConfig.TYPE_IMAGE) // 图片or视频 FunctionConfig.TYPE_IMAGE  TYPE_VIDEO
                .setCompress(true) //是否压缩
                .setEnableQualityCompress(true) //是否启质量压缩
                .setMaxSelectNum(1) // 可选择图片的数量
                .setMinSelectNum(1)// 图片或视频最低选择数量，默认代表无限制
                .setSelectMode(FunctionConfig.MODE_SINGLE) // 单选 or 多选 FunctionConfig.MODE_SINGLE FunctionConfig.MODE_MULTIPLE
                .setShowCamera(true) //是否显示拍照选项 这里自动根据type 启动拍照或录视频
                .setEnablePreview(true) // 是否打开预览选项
                .setEnableCrop(true) // 是否打开剪切选项
                .setCircularCut(true)// 是否采用圆形裁剪
                .setCropW(400) // cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
                .setCropH(400) // cropH-->裁剪高度 值不能小于100 如果值大于图片原始宽高 将返回原图大小
                .setMaxB(202400) // 压缩最大值 例如:200kb  就设置202400，202400 / 1024 = 200kb左右
                .setImageSpanCount(4) // 每行个数
                .setCompressFlag(1) // 1 系统自带压缩 2 luban压缩
                .setImmersive(false)// 是否改变状态栏字体颜色(黑色)
                .setNumComplete(false) // 0/9 完成  样式
                .create();
        PictureConfig.getInstance().init(options);
    }


    private void initErrorHandler() {
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(this);
    }

    private void initFireWare(){
        Contact.fireWave = new GetJsonDataUtil().getFirewave(this,"fireware.bin");
        Contact.fireWave.setVersionCode(1);
    }

    public void initJson() {
        Gson gson = new Gson();
        String jsonData = new GetJsonDataUtil().getJson(this, "province.json").trim();
        provinces = gson.fromJson(jsonData, new TypeToken<List<Province>>() {
        }.getType());
        for (int i = 0; i < provinces.size(); i++) {
            List<City> listCity = provinces.get(i).getCity();
            List<List<String>> province_Area = new ArrayList<>();
            cities.add(listCity);
            for (int j = 0; j < listCity.size(); j++) {
                List<String> city_area = new ArrayList<>();
                for (int a = 0; a < listCity.get(j).getString().size(); a++) {
                    city_area.add(listCity.get(j).getString().get(a));
                }
                province_Area.add(city_area);
            }
            areas.add(province_Area);
        }
    }

    /**
     * c初始化Volley
     */
    private void initVolley() {
        queue = Volley.newRequestQueue(this);
    }

}
