package com.bigshark.smartlight.bean;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ch on 2017/5/25.
 *
 * @email 869360026@qq.com
 * 这个类专门用来生成蓝牙数据规则
 */

public class BLuetoothData {
    //头文件55 55 55 55 指令代码1字节 数据长度2字节 数据内容N字节 教研2字节 尾巴 aa aa aa aa
    private  static  int[] head = new int[]{0x55, 0x55, 0x55, 0x55};
    private static int[] bottom = new int[]{0xaa, 0xaa, 0xaa, 0xaa};

    //回复刹车指令
    private static byte[] replyBrake = new byte[]{0x02, 0x00, 0x00};

    //回复电量指令
    private static byte[] replayBatery = new byte[]{0x04, 0x00, 0x00};

    //回复转向
    private static byte[] replayTurnTo = new byte[]{0x06, 0x00, 0x00};

    //左转
    private static byte[] turnLeft = new byte[]{0x08, 0x01, 0x00, 0x01};

    //右转
    private static byte[] turnRight = new byte[]{0x08, 0x01, 0x00, 0x02};

    //寻车
    private static byte[] findCar = new byte[]{0x0a, 0x00, 0x00};

    //开锁
    private static byte[] openAlert = new byte[]{0x0c, 0x01, 0x00, 0x02};

    //关锁
    private static byte[] closeAlert = new byte[]{0x0c, 0x01, 0x00, 0x01};

    //固件版本
    private static byte[] firmwareVersoin = new byte[]{0x15,0x00,0x00};

    private static byte[] replyState = new byte[]{0x13,0x01,0x00,0x00};
    /**
     * 获得校验码
     *
     * @param
     * @return
     *
     */
    public static  byte[] getCheckNumber(byte[] buffer, int size) {
//        short crc = 0x0000;
//
//        for (int i = 0; i < chars.length; i++) {
//            crc += (chars[i]) & 0x00FF;
//            crc = (short) ~((crc << 1) & 0xFFFE);
//            crc += 1;
//            if ((i % 8) == 0) {
//                crc ^= 0x1021;
//            }
//        }
//        crc = (short) ((~crc) + 1);

    short crc = 0x0000;

        for (int i = 0; i < size; i++) {
        crc += (((short) buffer[i]) & 0x00FF);
        crc = (short) ~((crc << 1) & 0xFFFE);
        crc += 1;
        if ((i % 8) == 0) {
            crc ^= 0x1021;
        }
    }
    crc = (short) ((~crc) + 1);

        return shortToByte(crc);
}

    /**
     * 注释：short到字节数组的转换！
     *
     * @param
     * @return
     */
    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        b[1] = new Integer(temp & 0xff).byteValue();
        temp = temp >> 8; // 向右移8位
        b[0] = new Integer(temp & 0xff).byteValue();
        return b;
    }

    /**
     * 获得回复刹车的指令
     */
    public static byte[] getReplayBrake(){
        return  getData(replyBrake);
    }

    public static byte[] getReplayBatery() {
        return  getData(replayBatery);
    }

    public static byte[] getReplayTurnTo() {
        byte[] bytes = new byte[]{0x55,0x55,0x55,0x55,0x06,0x00,0x00, (byte) 0xb2, (byte) 0xa0, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa};
        //55 55 55 55 06 00 00 B2 A0 AA AA AA AA
        return  bytes;
    }

    public static byte[] getTurnLeft() {
        return  getData(turnLeft);
    }

    public static byte[] getTurnRight() {
        return  getData(turnRight);
    }

    public static byte[] getFindCar() {
        return  getData(findCar);
    }

    public static byte[] getOpenAlert() {
        return getData(openAlert);
    }

    public static byte[] getFirmwareVersoin(){
        return getData(firmwareVersoin);
    }

    public static byte[] getCloseAlert() {
        return getData(closeAlert);
    }

    /**
     * 获得数据
     * @param bytes 指令及内容
     */
    public static byte[] getData(byte[] bytes){
        ArrayList<Byte> arrayList = new ArrayList();

        for (int i=0;i<4;i++){
            arrayList.add((byte) 0x55);
        }
//        StringBuffer sb = new StringBuffer();
//        for(int i=0;i<bytes.length;i++){
//            arrayList.add(bytes[i]);
//            sb.append(String.format("%02d",bytes[i]));
//        }

        //加入指令代码
        for(int i=0;i<bytes.length;i++){
            arrayList.add(bytes[i]);
        }

        byte[] checkNumbers = getCheckNumber(bytes,bytes.length);
        for(int i=1;i>=0;i--){
            arrayList.add(checkNumbers[i]);
        }

        for (int i=0;i<4;i++){
            arrayList.add((byte) 0xaa);
        }
        byte[] returnBytes = new byte[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            returnBytes[i] = (arrayList.get(i));
        }
        return returnBytes;
    }

    /**
     * 固件升级版本 小端法
     */
    public static byte[] getFirmwareUp(FireWave file){
        //开始固件升级
        String hexString = Long.toHexString(file.getLength());
        byte[] bytes = new byte[]{0x10,0x04,0x00,
                (byte) Integer.parseInt(hexString.substring(2,4),16),
                (byte) Integer.parseInt(hexString.substring(0,2),16),
                (byte) file.getPackgeSize(),
                (byte) file.getVersionCode()
        };
       return getData(bytes);
    }

    public static byte[] getReplyState(){
        return getData(replyState);
    }
}
