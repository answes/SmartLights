package com.bigshark.smartlight.pro.index.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.amap.api.maps2d.model.LatLng;
import com.bigshark.smartlight.bean.UpLoadRecord;

import java.util.List;

/**
 * Created by ch on 2017/4/18.
 *
 * @email 869360026@qq.com
 */

public class MapLocationRecive extends BroadcastReceiver {
    public static final String ACTION = "send_recive_location";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_DATA2="extra_data2";
    public  MapLocationRecive(OnLocationReciveListener onLocationReciveListener){
        this.onLocationReciveListener = onLocationReciveListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(onLocationReciveListener!=null){
            onLocationReciveListener.onRevice((UpLoadRecord) intent.getSerializableExtra(EXTRA_DATA));
        }
    }

    private OnLocationReciveListener onLocationReciveListener;
    public interface OnLocationReciveListener{
        void onRevice(UpLoadRecord record);
    }
}
