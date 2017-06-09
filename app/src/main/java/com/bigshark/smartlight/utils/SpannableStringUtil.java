package com.bigshark.smartlight.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * Created by jlbs1 on 2017/6/7.
 */

public class SpannableStringUtil {

    public static SpannableString setTextSpannable(String content, int size) {
        SpannableString ss = new SpannableString(content);
        ss.setSpan(new ForegroundColorSpan(Color.WHITE), 0, content.length(),
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(size), 0, content.length(),
                //setSpan时需要指定的 flag,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括).
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
