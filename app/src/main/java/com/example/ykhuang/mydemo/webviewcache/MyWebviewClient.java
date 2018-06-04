package com.example.ykhuang.mydemo.webviewcache;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.ykhuang.mydemo.webviewcache.cache.LocalResourcesCache;
import com.example.ykhuang.mydemo.webviewcache.cache.LocalResourcesManager;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyk on 2018/4/10.
 */

public class MyWebviewClient extends WebViewClient {
    private final String TAG = "MyWebviewClient";

    private Activity mActivity;
    public final int START_WRITE = 0X002;
    private PoolTask mPoolTask;

//    private boolean mIsNeedCache = true;
    //华为机型如果缓存的话，会出现崩溃的情况，所有不缓存
//    private final String mNoCacheManufacturer="HUAWEI";
//    private CacheService mCacheService;
//    Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what){
//                case START_WRITE:{
//                    Uri uri = (Uri) msg.obj;
//                    newSingleThreadPool().execute(new PoolTask(uri,mActivity));
//                    break;
//                }
//            }

//            return false;
//        }
//    });

    public MyWebviewClient(Activity activity){
        this.mActivity = activity;
//        bindService();
        mPoolTask = new PoolTask(activity);
        new Thread(mPoolTask).start();
//        if(mNoCacheManufacturer.equals(PhoneInfo.getManufacturer())){
//            mIsNeedCache = false;
//        }
    }

//    private void bindService(){
//        CacheServiceConnection connection = new CacheServiceConnection();
//        mActivity.bindService(new Intent(mActivity,CacheService.class), connection, Context.BIND_AUTO_CREATE);
//    }
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, String url) {
        // 如下方案可在非微信内部WebView的H5页面中调出微信支付
        if (url.startsWith("weixin://wap/pay?") || url.startsWith("alipays://")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            try{
                mActivity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                if (url.startsWith("weixin://wap/pay?")){
//                    ToastUtil.showToast(mActivity,"您的手机没有安装微信，请安装微信后充值");
                }
            }

            return true;
        } else {
            Map<String, String> extraHeaders = new HashMap<String, String>();
            extraHeaders.put("Referer", "http://boboxia.cn");
            view.loadUrl(url, extraHeaders);
        }
        return true;
    }
    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if(mLoadCallback!=null){
            mLoadCallback.loadFinish();
        }
    }

    @Override
    public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
        super.onReceivedError(webView, errorCode, description, failingUrl);
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        if (sslError.getPrimaryError() == SslError.SSL_DATE_INVALID
                || sslError.getPrimaryError() == SslError.SSL_EXPIRED
                || sslError.getPrimaryError() == SslError.SSL_INVALID
                || sslError.getPrimaryError() == SslError.SSL_UNTRUSTED) {
            sslErrorHandler.proceed();
        } else {
            sslErrorHandler.cancel();
        }

        super.onReceivedSslError(webView, sslErrorHandler, sslError);
    }


    @Override
    public void onLoadResource(WebView webView, String s) {
        super.onLoadResource(webView, s);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
        Uri uri = Uri.parse(s);
        return creatThread(mActivity,uri);
//        if(mIsNeedCache){
//            Uri uri = Uri.parse(s);
//            return creatThread(mActivity,uri);
//        }else{
//            return super.shouldInterceptRequest(webView,s);
//        }

    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest request) {
        return creatThread(mActivity,request.getUrl());
//        if(mIsNeedCache){
//            return creatThread(mActivity,request.getUrl());
//        }else{
//            return super.shouldInterceptRequest(webView,request);
//        }

    }

    private WebResourceResponse creatThread(Activity activity, Uri uri){
        WebResourceResponse webResourceResponse = null;
        InputStream inputStream = LocalResourcesCache.getInstance(activity).getResourceByCache(uri);
        if (inputStream != null) {
            webResourceResponse = new WebResourceResponse(LocalResourcesManager.getMimeTypeByUrl(uri), "utf-8", inputStream);
            //使用inputStream之后释放资源
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }else{
            //首次加载游戏,在assets里面没有缓存
            Message msg = Message.obtain();
            msg.what = PoolTask.THREAD_CACHE;
            msg.obj = uri;
            mPoolTask.mThreadHandler.sendMessage(msg);

            //在service里面开始缓存
//            long cacheStartTime = System.currentTimeMillis();
//            if(mCacheService!=null){
//                mCacheService.saveCache(uri);
//            }
        }
        return webResourceResponse;
    }

//    private ExecutorService singleThreadPool;
//    public ExecutorService newSingleThreadPool (){
//        if(singleThreadPool == null){
//            singleThreadPool = Executors.newSingleThreadExecutor();
//        }
//
//        return singleThreadPool;
//    }

    public class PoolTask implements Runnable {
//        private Uri uriList;
        private Activity mActivity;

        public static final int THREAD_CACHE = 0X003;

        public Handler mThreadHandler;
//        int allCount;
//        int failCont;

        public PoolTask(Activity activity) {
            this.mActivity = activity;
        }

        @Override
        public void run() {
            /**
             * 子线程中定义Handler，Handler定义在哪个线程中，就跟那个线程绑定，在线程中绑定Handler需要调用Looper.prepare();
             * 方法，主线程中不调用是因为主线程默认帮你调用了；
             */
            Looper.prepare();
            mThreadHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case THREAD_CACHE: {
                            if(msg.obj == null){
                                break;
                            }
                            Uri uri = (Uri) msg.obj;
                            try {
                                String contextType = LocalResourcesManager.getMimeTypeByUrl(uri);
                                if (contextType != null) {
                                    LocalResourcesCache.getInstance(mActivity).saveRemoteResourceByURL3(uri);
//                                    allCount = allCount+1;
                                }
                            } catch (Exception e) {
//                                failCont = failCont+1;
//                                MDLogUtil.i(TAG,"缓存失败的链接:"+uri);
                                e.printStackTrace();
                            }

//                            MDLogUtil.i(TAG,"缓存成功次数："+allCount+",失败次数："+failCont);
                            break;
                        }
                        default:
                    }
                    return false;
                }
            });
            Looper.loop();
        }
    }
    // create ServiceConnetion
//    class CacheServiceConnection implements ServiceConnection {
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            Log.i(TAG,"onServiceConnected");
//            mCacheService = ((CacheService.MyBinder) service).getService();
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            mCacheService = null;
//        }
//
//
//    };
    private LoadCallBack mLoadCallback;
    public void setLoadCallback(LoadCallBack loadCallback){
        this.mLoadCallback = loadCallback;
    }

    public interface LoadCallBack{
        void loadFinish();
    }
}
