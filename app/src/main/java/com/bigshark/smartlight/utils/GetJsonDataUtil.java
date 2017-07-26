package com.bigshark.smartlight.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.bigshark.smartlight.bean.FireWave;

import java.io.BufferedReader;
import java.io.File;
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
            File file = new File("file:///android_asset/"+fileName);
            List<byte[]> datas = new ArrayList<>();
            inputStream = assetManager.open(fileName);
            long lenth = inputStream.available();
            byte[] flush = new byte[(int) lenth];
            while (inputStream.read(flush)!=-1){

            }
            inputStream.close();
            //copy 数据
            for(int i=0;i<lenth;i=i+2048){
                if(i+2048>lenth){
                    byte[] bs = new byte[(int) (lenth-i)];
                    System.arraycopy(file, i, bs, 0, bs.length);
                    datas.add(bs);
                }else{
                    //小于的时候
                    byte[] bs = new byte[2048];
                    System.arraycopy(file, i, bs, 0, 2048);
                    datas.add(bs);
                }
            }

            fileWave.setBytes(datas);
            fileWave.setLength((int) lenth);
            fileWave.setPackgeSize(datas.size());
            return fileWave;
        } catch (IOException e) {

        }
        return null;
    }
}
