package com.bigshark.smartlight.utils;

import android.app.Activity;
import android.graphics.Rect;

/**
 * Created by jlbs1 on 2017/6/17.
 */

public class ScreenUtils {
    public static  int getActivityHeight(Activity activity){
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        System.out.println("top:"+outRect.top +" ; left: "+outRect.left) ;
        return outRect.height();
    }
}
