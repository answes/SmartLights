package com.bigshark.smartlight.pro.mine.view;

import android.app.Activity;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.pro.mine.model.MineModel;
import com.bigshark.smartlight.pro.mine.view.navigation.MineNavigationBuilder;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.activity_about)
    LinearLayout activityAbout;

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initToolbar();
        SupportMultipleScreensUtil.scale(activityAbout);
        initWebView();
    }

    private void initWebView() {
        webView.requestFocus();
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        WebSettings web = webView.getSettings();
        web.setJavaScriptEnabled(true);
        web.setBuiltInZoomControls(false);
        web.setSupportZoom(false);
        web.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        web.setUseWideViewPort(true);
        web.setLoadWithOverviewMode(true);
        web.setSavePassword(true);
        web.setSaveFormData(true);
        //web.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染

        webView.loadUrl(new MineModel(this).getServerUrl()+"/web/aboutus");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // 重写此方法可以让webview处理https请求
                handler.proceed();
            }
        });
    }


    /***
     * 防止WebView加载内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }


    private void initToolbar() {
        MineNavigationBuilder toolbar = new MineNavigationBuilder(this);
        toolbar.setLeftIcon(R.drawable.left_back)
                .setLeftIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).setTitle("关于我们").createAndBind(activityAbout);
    }

    public static void openAboutActivity(Activity activity) {
        activity.startActivity(new Intent(activity, AboutActivity.class));
    }

}
