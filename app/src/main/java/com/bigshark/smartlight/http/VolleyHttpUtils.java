package com.bigshark.smartlight.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bigshark.smartlight.SmartLightsApplication;
import com.google.gson.Gson;


import java.util.Map;

/**
 * Created by ch on 2017/4/3.
 *
 * @email 869360026@qq.com
 */

public class VolleyHttpUtils{

    public  void getData(String url,final Map<String,String> reqestParams,final HttpResult httpResult){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null){
                    httpResult.erro("服务端未返回数据，请稍后再试。");
                }else{
                    httpResult.succss(response);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if(error == null){
                    httpResult.erro("请求参数出错，请稍后再试。");
                }else if(error.networkResponse.statusCode >=500){
                    httpResult.erro("服务端，请稍后再试。");
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return reqestParams;
            }
        };
        SmartLightsApplication.queue.add(stringRequest);
        SmartLightsApplication.queue.start();
    }

    public void postData(String url,final Map<String,String> requestParams,final HttpResult httpResult){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response == null){
                    httpResult.erro("服务端未返回数据，请稍后再试。");
                }else{
                    httpResult.succss(response);
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if(error == null){
                    httpResult.erro("请求参数出错，请稍后再试。");
                }else if(error.networkResponse == null || error.networkResponse.statusCode >=500){
                    httpResult.erro("服务端，请稍后再试。");
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return requestParams;
            }
        };
        SmartLightsApplication.queue.add(stringRequest);
        SmartLightsApplication.queue.start();
    }

   public interface HttpResult{
        void succss(String t);
        void erro(String msg);
    }

}
