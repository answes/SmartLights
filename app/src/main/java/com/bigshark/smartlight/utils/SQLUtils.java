package com.bigshark.smartlight.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import com.bigshark.smartlight.bean.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlbs1 on 2017/5/10.
 */

public class SQLUtils {
    public static void saveUser(Activity activity ,String userId){
        SharedPreferences mySharedPreferences= activity.getSharedPreferences("userdata",
                Activity.MODE_PRIVATE);
    //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
    //用putString的方法保存数据
        editor.putString("userId", userId);
    //提交当前数据
        editor.commit();
    }

    /**
     * 查询数据
     * @param activity
     * @return
     */
    public static String getUser(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences("userdata",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String userName =sharedPreferences.getString("userId", "");
        if(!userName.isEmpty()){
            return userName;
        }
        return null;
    }

    /**
     *  清空数据
     * @param activity
     */
    public static void clearUser(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences("userdata",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    public static void saveEqu(Activity activity ,String equName,String equId){
        SharedPreferences mySharedPreferences= activity.getSharedPreferences("equ",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        if(mySharedPreferences.getString(equName,"").isEmpty()){
            editor.putString("equName", equName);
            editor.putString("equId", equId);
        }else {
            if("equName".equals(mySharedPreferences.getString("equName",""))){
                return;
            }
            String newName  = mySharedPreferences.getString("equName","").concat(",").concat(equName);
            String newId  = mySharedPreferences.getString("equId","").concat(",").concat(equId);
            editor.putString("equName", newName);
            editor.putString("equId", newId);
        }
        //提交当前数据
        editor.commit();
    }

    /**
     * 查询数据
     * @param activity
     * @return
     */
    public static List<Equipment> getEqus(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences("equ",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String equName =sharedPreferences.getString("equName", "");
        String equId =sharedPreferences.getString("equId", "");
        List<Equipment> datas = new ArrayList<>();
        if(!equName.isEmpty()){
            String [] equNames = equName.split(",");
            String [] equIds = equId.split(",");
            for(int i=0;i<equNames.length;i++){
                datas.add(new Equipment(equNames[i],equIds[i]));
            }
            return datas;
        }
        return null;
    }

    /**
     *  清空数据
     * @param activity
     */
    public static void clearEqu(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences("equ",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 保存是否播放语音
     * @param activity
     * @param isOpenVoice
     *
     */
    public static void appConfig(Activity activity ,boolean isOpenVoice){
        SharedPreferences mySharedPreferences= activity.getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("isOpenVoice",isOpenVoice);
        //提交当前数据
        editor.commit();
    }

    public static  void appAutoConfig(Activity activity,boolean isAuto){
        SharedPreferences mySharedPreferences= activity.getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putBoolean("isAuto",isAuto);
        //提交当前数据
        editor.commit();
    }

    public static  boolean getAutoConfig(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("isAuto", false);
    }

    /**
     * 查询是否播放语音数据  默认为播放
     * @param activity
     * @return
     */
    public static boolean getConfig(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences("config",
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        return sharedPreferences.getBoolean("isOpenVoice", true);
    }


}
