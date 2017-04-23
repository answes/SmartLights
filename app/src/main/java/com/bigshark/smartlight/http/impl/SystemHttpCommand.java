package com.bigshark.smartlight.http.impl;


import com.bigshark.smartlight.http.IHttpCommand;
import com.bigshark.smartlight.http.IRequestParam;
import com.bigshark.smartlight.http.utils.HttpUtils;

import java.util.HashMap;


/**
 * Created by Dream on 16/5/28.
 */
public class SystemHttpCommand implements IHttpCommand<HashMap<String,Object>> {

    @Override
    public String execute(String url, IRequestParam<HashMap<String, Object>> requestParam) {
        try {
            return HttpUtils.post(url,requestParam.getRequestParam());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
