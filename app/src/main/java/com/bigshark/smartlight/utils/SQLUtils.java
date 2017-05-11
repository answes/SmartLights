package com.bigshark.smartlight.utils;

import android.app.Activity;
import android.content.SharedPreferences;

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

}
