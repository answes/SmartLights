package com.bigshark.smartlight.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.SmartLightsApplication;
import com.bigshark.smartlight.weight.CustomArcView;

/**
 * Created by jlbs1 on 2017/5/31.
 */

public class MediaPlayerUtils {
    private Context context;
    private CustomArcView arcView;  //背景变色


    private MediaPlayer playerLeft;
    private MediaPlayer playeRight;
    private MediaPlayer playEnd;
    //private MediaPlayer playShache;
    private MediaPlayer playTime;


    public MediaPlayerUtils(Context context,CustomArcView arcView) {
        this.arcView = arcView;
        this.context = context;
        init();
    }

    public void init() {
        playerLeft =  MediaPlayer.create(context, R.raw.left);
        playeRight = MediaPlayer.create(context,R.raw.right);
//        playShache = MediaPlayer.create(context,R.raw.shache);
        playEnd = MediaPlayer.create(context,R.raw.end);
        playTime = MediaPlayer.create(context,R.raw.time);
    }

    public void release() {
        playerLeft.release();
        playeRight.release();
//        playShache.release();
        playEnd.release();
        playTime.release();
//        playShache.release();
    }

    /**
     * 左转
     */
    public void palyLeftMedia() {
        if(SmartLightsApplication.isOpenVioce){
           if(!playerLeft.isPlaying()){
               playerLeft.start();
           }
        }
    }

    /**
     * 右转
     */
    public void palyRightMedia() {
        if(SmartLightsApplication.isOpenVioce) {
            if(!playeRight.isPlaying()){
                playeRight.start();
            }
        }
    }

    /**
     * 波放刹车
     */
    public void palyShacheMedia() {
        if (SmartLightsApplication.isOpenVioce) {
//            if(!playShache.isPlaying()){
//                playShache.start();
//            }
        }
    }

    public void stopSahceMedia(){

    }

    public void playEnd(){
        if (SmartLightsApplication.isOpenVioce) {
            if(!playEnd.isPlaying()){
                playEnd.start();
            }
        }
    }

    public void setPlayTime(){
        if (SmartLightsApplication.isOpenVioce) {
            if(!playTime.isPlaying()){
                playTime.start();
            }
        }
    }
}
