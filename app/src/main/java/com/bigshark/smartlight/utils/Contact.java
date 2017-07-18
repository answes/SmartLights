package com.bigshark.smartlight.utils;

import android.os.Environment;

import com.bigshark.smartlight.bean.FireWave;

/**
 * Created by ch on 2017/5/10.
 *
 * @email 869360026@qq.com
 */

public class Contact {
    public static String BASE_FILE_URL = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static int firewareVersion = 0;
    public static FireWave fireWave = null;
}
