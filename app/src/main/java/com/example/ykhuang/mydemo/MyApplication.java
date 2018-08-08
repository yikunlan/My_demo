package com.example.ykhuang.mydemo;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechUtility;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class MyApplication extends Application {
	public static RefWatcher getRefWatcher(Context context) {
		MyApplication application = (MyApplication) context.getApplicationContext();
		return application.refWatcher;
	}

	private RefWatcher refWatcher;

	@Override
	public void onCreate() {
		// 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
		// 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
		// 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
		// 参数间使用半角“,”分隔。
		// 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符
		
		// 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
		
		SpeechUtility.createUtility(MyApplication.this, "appid=" + getString(R.string.app_id));
			
		// 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
		// Setting.setShowLog(false);
		super.onCreate();

		//检测内存泄露
		refWatcher = LeakCanary.install(this);


		//友盟第三方
		Config.DEBUG = true;//开启debug模式
		UMShareAPI.get(this);
	}
	{
		//波波侠
		//PlatformConfig.setWeixin("wxa78ad91ddf14515c", "e4fdcf8fd8a95fcf05b94487b5cde09d");
		//卓越之翼
		PlatformConfig.setWeixin("wx26ff6f8cd7314c18", "0e9558fd35edbe2666efe0dac1a866d3");
		PlatformConfig.setQQZone("101455248", "16bb85c613f93ecfc1bc7f90d9070cef");
	}
}
