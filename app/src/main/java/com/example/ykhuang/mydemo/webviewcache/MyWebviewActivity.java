package com.example.ykhuang.mydemo.webviewcache;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.example.ykhuang.mydemo.webviewcache.cache.LocalResourcesCacheIndex;
import com.example.ykhuang.mydemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hyk on 2018/4/3.
 */

public class MyWebviewActivity extends Activity {

//    @BindView(R.id.ll_loading)
//    LinearLayout llLoading;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        //循环遍历缓存,这一步最后卸载application中
        LocalResourcesCacheIndex.getInstance(getApplicationContext());

        webview.setWebViewClient(new MyWebviewClient(this));

        //大天使之剑的游戏链接
//        webview.loadUrl("https://boboxia.cn/channelid/10000/frame");
        webview.loadUrl("https://blog.csdn.net/qq_17265737/article/details/79287563");

    }








}
