package com.bigshark.smartlight.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.allen.library.SuperTextView;
import com.android.volley.toolbox.NetworkImageView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.OrderResult;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.VolleyUtils;

import java.util.ArrayList;
import java.util.List;

/**该类是我的订单中的商品列表，采用for循环遍历要显示的数据，然后相应的设置多少个商品。
 * Created by bigShark on 2017/4/22.
 */

public class OrderGoodListView extends LinearLayout {

    private  List<OrderResult.Gitem> data;
    private LayoutInflater layoutInflater ;


    public OrderGoodListView(Context context) {
        super(context);
    }

    public OrderGoodListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderGoodListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(List<OrderResult.Gitem> data){
        if(null == data){
            data = new ArrayList<>();
        }
        this.data = data;
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     */
    private void notifyDataSetChanged() {
        removeAllViews();
        if(null == data || data.size() == 0){
            return;
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for(int i=0; i<data.size(); i++){
            final int index = i;
            View view = getView(index);
            if(view == null){
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }

            addView(view, index, layoutParams);
        }

    }

    private View getView(final int postion){
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(getContext());
        }

        OrderResult.Gitem good = data.get(postion);

        View view  = layoutInflater.inflate(R.layout.item_mine_order_list_goods_layout,null,false);
        //获取相应的控件
        NetworkImageView goodImg = (NetworkImageView) view.findViewById(R.id.niv_good);
        SuperTextView  stvTitle = (SuperTextView) view.findViewById(R.id.stv_title);
        SuperTextView stvNumber = (SuperTextView) view.findViewById(R.id.stv_number);
        //设置相应的数据
        if(null != good.getImg()) {
            VolleyUtils.loadImage(getContext(), goodImg, data.get(postion).getImg());
        }
        if(good.getName().isEmpty()){
            stvTitle.setLeftString("商品名称：智能车灯商品");
        }else{
            stvTitle.setLeftString("商品名称：".concat(good.getName()));
        }
        if(good.getPrice().isEmpty()){
            stvTitle.setRightString("¥ 00.00");
        }else{
            stvTitle.setRightString("¥ ".concat(good.getPrice()));
        }
//        if(good.getFig().isEmpty()){
//            stvNumber.setLeftString("型号规格：棕色001");
//        }else{
//            stvNumber.setLeftString("型号规格：".concat(good.getFig()));
//        }
        if(good.getNum().isEmpty()){
            stvNumber.setRightString("x 0");
        }else{
            stvNumber.setRightString("x ".concat(good.getNum()));
        }
        SupportMultipleScreensUtil.scale(view);
        return  view;
    }
}
