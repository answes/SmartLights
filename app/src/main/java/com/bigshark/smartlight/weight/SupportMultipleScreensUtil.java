package com.bigshark.smartlight.weight;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.bigshark.smartlight.R;

/**
 * Created by bigShark on 2017/1/11.
 */

public class SupportMultipleScreensUtil {

    public static final int BASE_SCREEN_WINDTH = 1080;
    public static final int BASE_SCREEN_HPGHT = 1920;
    public static final float BASE_SCREEN_WIDTH_FLOAT = 1080F;
    public static final float BASE_SCREEN_HPGHT_FLOAT = 1920F;
    public static float scale = 1.0F;

    public SupportMultipleScreensUtil(){};

    public static void init(Context context){
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;
        scale = (float) widthPixels/BASE_SCREEN_WIDTH_FLOAT;
    }

    public static int getScaleValue(int value){
        return (int)Math.ceil((double)(scale*(float)value));
    }

    public static void scale(View view){
        if(null != view){
            if(view instanceof ViewGroup){
                scaleViewGroup((ViewGroup)view);
            }else{
               scaleView(view); 
            }
        }
    }

    private static void scaleView(View view) {
        //Object isScale = view.getTag(R.id.is_scale_size_tag);
    }

    private static void scaleViewGroup(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if(view instanceof ViewGroup){
                scaleViewGroup((ViewGroup) view);
            }
            scaleView(view);
        }
    }

}
