package com.bigshark.smartlight.pro.market.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.bean.GoodDetail;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.presenter.BasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.market.presenter.MarketListPresenter;
import com.bigshark.smartlight.pro.market.view.navigation.GoodDetailsNavigationBuilder;
import com.bigshark.smartlight.utils.GlideImageLoader;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.bigshark.smartlight.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodDetailsActivity extends BaseActivity {

    @BindView(R.id.bn_goodImgs)
    Banner bnGoodImgs;
    @BindView(R.id.stv_goodTitle)
    SuperTextView stvGoodTitle;
    @BindView(R.id.tv_moeny)
    TextView tvMoeny;
    @BindView(R.id.stv_googDeatil)
    SuperTextView stvGoogDeatil;
    @BindView(R.id.bt_addCar)
    Button btAddCar;
    @BindView(R.id.bt_buy)
    Button btBuy;
    @BindView(R.id.activity_good_details)
    RelativeLayout activityGoodDetails;

    @BindView(R.id.web_view)
    WebView webView;
    private MarketListPresenter presenter;
    private GoodDetail.Goods good;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityGoodDetails);
        initData();
    }

    private void initData() {
     int id = getIntent().getIntExtra("id",0);
    presenter.getGoodDetail(id,new BasePresenter.OnUIThreadListener<GoodDetail.Goods>(){
        @Override
        public void onResult(GoodDetail.Goods result) {
            if(null == result){
                ToastUtil.showToast(GoodDetailsActivity.this,"加载失败");
            }else{
                good = result;
                setDatas();
            }
        }
        @Override
        public void onErro(String string) {
            showMsg("加载失败");
        }
    });
    }

    private void setDatas(){
        List<String> list = new ArrayList<>();
        if(good == null || good.getImg() == null){
            return;
        }
        for(String s:good.getImg()){
            list.add(s);
        }
        bnGoodImgs.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        bnGoodImgs.setIndicatorGravity(BannerConfig.CENTER);
        bnGoodImgs.setImageLoader(new GlideImageLoader());
        bnGoodImgs.setImages(list);
        bnGoodImgs.start();

        if(good.getContent() !=null) {
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setDefaultTextEncodingName("utf-8");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }
            webView.loadData(getHtmlData(good.getContent()), "text/html; charset=utf-8", "utf-8");
        }
        stvGoodTitle.setLeftString(good.getName());
        tvMoeny.setText("￥".concat(good.getPrice()));
        stvGoogDeatil.setLeftString("快递：".concat(good.getExpress()));
        stvGoogDeatil.setCenterString("库存：".concat(good.getNum()));
        stvGoogDeatil.setRightString("颜色：".concat(good.getColorTxt()+""));
    }

    private void initToolbar() {
        GoodDetailsNavigationBuilder toolbar = new GoodDetailsNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("商品详情").setRightIcon(R.drawable.main_bottom_market_press)
                .setRightIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CarActivity.openCarActivity(GoodDetailsActivity.this);
                    }
                }).createAndBind(activityGoodDetails);
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        presenter = new MarketListPresenter(this);
        return presenter;
    }

    @OnClick({R.id.bt_addCar, R.id.bt_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_addCar:
              addCar(false);
                break;
            case R.id.bt_buy:
                addCar(true);
                break;
        }
    }

    private void addCar(final boolean idBuy) {
        presenter.addGoodToCar(good.getId(),good.getPrice(), new BasePresenter.OnUIThreadListener<String>() {
            @Override
            public void onResult(String result) {
                if(idBuy){
                    CarActivity.openCarActivity(GoodDetailsActivity.this);
                    return;
                }
                showMsg("成功加入购物车");
            }
            @Override
            public void onErro(String string) {
                showMsg(string);
            }
        });
    }

    public static void openGoodDetailsActivity(Activity activity,int id){
        Intent intent = new Intent(activity,GoodDetailsActivity.class);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}
