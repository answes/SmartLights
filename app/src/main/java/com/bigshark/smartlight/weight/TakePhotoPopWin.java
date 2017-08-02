package com.bigshark.smartlight.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

/**
 * Created by jlbs1 on 2017/6/9.
 */

public class TakePhotoPopWin extends PopupWindow {
    private Context mContext;

    private View view;

    private LinearLayout llOpen,llClose,llFind;
    private ImageView close;


    public TakePhotoPopWin(Context mContext, View.OnClickListener itemsOnClick) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.activity_index_popupwindow, null);
        SupportMultipleScreensUtil.scale(view);
        llOpen = (LinearLayout) view.findViewById(R.id.ll_open);
        llClose = (LinearLayout) view.findViewById(R.id.ll_close);
        llFind = (LinearLayout) view.findViewById(R.id.ll_find);
        close = (ImageView) view.findViewById(R.id.iv_close);

        // 取消按钮
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置按钮监听
        llOpen.setOnClickListener(itemsOnClick);
        llClose.setOnClickListener(itemsOnClick);
        llFind.setOnClickListener(itemsOnClick);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }
}
