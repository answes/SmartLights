package com.bigshark.smartlight.bean;

import java.util.List;

/**
 * Created by ch on 2017/7/17.
 *
 * @email 869360026@qq.com
 */

public class FireWave {
    private int versionCode;
    private List<byte[]> bytes;
    private long length;
    private int packgeSize;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public List<byte[]> getBytes() {
        return bytes;
    }

    public void setBytes(List<byte[]> bytes) {
        this.bytes = bytes;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getPackgeSize() {
        return packgeSize;
    }

    public void setPackgeSize(int packgeSize) {
        this.packgeSize = packgeSize;
    }
}
