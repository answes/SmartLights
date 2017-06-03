package com.bigshark.smartlight.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.bigshark.smartlight.R;

/**
 * Created by jlbs1 on 2017/5/31.
 */

public class MediaPlayerUtils {
    private Context context;
    private MediaPlayer mediaPlayer;//MediaPlayer对象

    public MediaPlayerUtils(Context context) {
        this.context = context;
    }

    public void init() {
        //当播放完音频资源时，会触发onCompletion事件，可以在该事件中释放音频资源，
        //以便其他应用程序可以使用该资源:
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public void release() {
        if (null != mediaPlayer) {
            mediaPlayer.release();
        }
    }

    public void palyLeftMedia() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.left);
            init();
            mediaPlayer.start();
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            palyLeftMedia();
        }

    }

    public void palyRightMedia() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.right);
            init();
            mediaPlayer.start();
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            palyRightMedia();
        }
    }

}
