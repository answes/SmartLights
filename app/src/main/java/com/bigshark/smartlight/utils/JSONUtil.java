package com.bigshark.smartlight.utils;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * json����������
 * @author nfy
 *
 */
public class JSONUtil {
	/**
	 * json תʵ����
	 * @param jsonString
	 * @param cls
	 * @return
	 */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        return JSON.parseObject(jsonString,cls);
    }  
    
    /**
     * json תʵ���༯��
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getObjects(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString,cls);
    }
    /**
     * ȡ��Stringֵ
     * @param json
     * @param key
     * @return
     */
    public static String getString(String json, String key){
    	JSONObject jsonObject;
    	String result = null;
		try {
			jsonObject = new JSONObject(json);
	    	result = jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return result;
    }
    /**
     * ȡ��Intֵ
     * @param json
     * @param key
     * @return
     */
    public static int getInt(String json, String key){
    	JSONObject jsonObject;
    	int result = 0;
		try {
			jsonObject = new JSONObject(json);
	    	result = jsonObject.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return result;
    }
    /**
     * ȡ�ö�ӦJsonarray
     */
    public static JSONArray getArray(String json, String key){
    	JSONArray jsonArray = null;
		try {
			jsonArray = new JSONObject(json).getJSONArray(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return jsonArray;
    }
}
