package com.example.ykhuang.mydemo.webviewcache;

import android.app.Activity;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ykhuang.mydemo.webviewcache.cache.LocalResourcesCache;
import com.example.ykhuang.mydemo.webviewcache.cache.LocalResourcesManager;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hyk on 2018/4/3.
 */

public class MyWebviewClient extends WebViewClient {
    private final int START_WRITE = 0X002;
    private ExecutorService singleThreadPool;

    private Activity mActivity;
    public MyWebviewClient(Activity activity){
        this.mActivity = activity;
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case START_WRITE:{
                    Uri uri = (Uri) msg.obj;
                    newSingleThreadPool().execute(new PoolTask(uri,mActivity));
                    break;
                }

            }
            return false;
        }
    });
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
        Log.i("String:资源", s);
        Uri uri = Uri.parse(s);
        return creatThread(mActivity,uri);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {
//                    return getWebResourceResponse(request.getUrl(), activity);
        return creatThread(mActivity,request.getUrl());
    }

    /**
     * 第一次加载时候开启线程写缓存
     * @param uri
     * @return
     */
    private WebResourceResponse creatThread(Activity activity, Uri uri){
        WebResourceResponse webResourceResponse = null;
//                    Log.i("原始请求：",request.getUrl().toString());
        //第一次要去查一下asset里面是不是有资源文件
        InputStream inputStream = LocalResourcesCache.getInstance(activity).getResourceByCache(uri);
        if (inputStream != null) {
            webResourceResponse = new WebResourceResponse(LocalResourcesManager.getMimeTypeByUrl(uri), "utf-8", inputStream);
        }else{
            //首次加载游戏,在assets里面没有缓存
            Message msg = Message.obtain();
            msg.what = START_WRITE;
            msg.obj = uri;
            mHandler.sendMessage(msg);

        }
        return webResourceResponse;
    }

    /**
     * 开启线程来写入缓存
     */
    class PoolTask implements Runnable{
        private Uri uriList;
        private Activity mActivity;
        public PoolTask(Uri list,Activity activity){
            this.uriList = list;
            this.mActivity = activity;
        }
        @Override
        public void run() {
            try {
                String contextType = LocalResourcesManager.getMimeTypeByUrl(uriList);
                if (contextType != null) {
                    LocalResourcesCache.getInstance(mActivity).saveRemoteResourceByURL3(uriList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用同步多线程来写入缓存
     * @return
     */
    private ExecutorService newSingleThreadPool (){
        if(singleThreadPool == null){
            singleThreadPool = Executors.newSingleThreadExecutor();
        }

        return singleThreadPool;
    }
}
