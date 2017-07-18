package com.bigshark.smartlight.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.bigshark.smartlight.bean.FireWave;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ch on 2017/4/12.
 *
 * @email 869360026@qq.com
 */

public class GetJsonDataUtil {
    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public FireWave getFirewave(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        FireWave fileWave = new FireWave();
        try {
            AssetManager assetManager = context.getAssets();
            List<byte[]> datas = new ArrayList<>();
            inputStream = assetManager.open(fileName);
            long lenth = inputStream.available();
            byte[] flush = new byte[2048];
            while (inputStream.read(flush)>-1){
                datas.add(flush);
            }
            //删除多余的
            int sy = (int) (lenth%2048);
            if(sy !=0){
                byte[] bs = new byte[sy];
                System.arraycopy(datas.get(datas.size()-1), 0, bs, 0, sy);
                datas.set(datas.size()-1,bs);
            }
            fileWave.setBytes(datas);
            fileWave.setLength((int) lenth);
            fileWave.setLength(datas.size());
            inputStream.close();
            return fileWave;
        } catch (IOException e) {

        }
        return null;
    }
}
