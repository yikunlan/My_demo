package com.example.ykhuang.mydemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ykhuang.mydemo.demo_app.DemoMainActivity;
import com.example.ykhuang.mydemo.demo_app.TabActivity;
import com.example.ykhuang.mydemo.demo_app.WelcomeActivity;
import com.example.ykhuang.mydemo.mvvp.simple_demo.SimpleMvvmActivity;
import com.example.ykhuang.mydemo.webviewcache.MyWebviewActivity;
import com.example.ykhuang.mydemo.download.MulThreadDownload;
import com.example.ykhuang.mydemo.eventbus.EventBusActivity;
import com.example.ykhuang.mydemo.loading.MProgressDialog;
import com.example.ykhuang.mydemo.rxjava.RxjavaActivity;
import com.example.ykhuang.mydemo.screenshot.GetLayoutScreen;
import com.example.ykhuang.mydemo.speek.SpeekActivity;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{
    @BindView(R.id.tv_rxjava)
    TextView tvRxjava;
    @BindView(R.id.tv_eventbus)
    TextView tvEventbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //注册eventBus
        EventBus.getDefault().register(this);

    }

    @OnClick({R.id.tv_rxjava,R.id.tv_eventbus,R.id.getLayoutScreen,R.id.download,R.id.progress_dialog,R.id.webveiw_cache,
    R.id.speek_btn,R.id.mvvm_btn,R.id.drawer_btn,R.id.tab_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rxjava:
                RxjavaActivity.open(this);
                break;
            case R.id.tv_eventbus:
                EventBusActivity.open(this);
                break;
            case R.id.getLayoutScreen:
                startActivity(new Intent(this, GetLayoutScreen.class));
                break;
            case R.id.download:
                startActivity(new Intent(this, MulThreadDownload.class));
                break;
            case R.id.progress_dialog:
//                startActivity(new Intent(this, MyProgressActivity.class));
                startActivity(new Intent(this, MProgressDialog.class));
                //添加activity之间的过度动画
                overridePendingTransition(R.anim.intent_int_ani, R.anim.intent_out_ani);
                break;
            case R.id.webveiw_cache:
                startActivity(new Intent(this, MyWebviewActivity.class));
                break;
            case R.id.speek_btn:
                startActivity(new Intent(this, SpeekActivity.class));
                break;
            case R.id.mvvm_btn:
                startActivity(new Intent(this, SimpleMvvmActivity.class));
                break;
            case R.id.drawer_btn:
                startActivity(new Intent(this, DemoMainActivity.class));
//                startActivity(new Intent(this, WelcomeActivity.class));
                break;
            case R.id.tab_btn:
                startActivity(new Intent(this, TabActivity.class));
                break;
            default:
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);

        MyApplication.getRefWatcher(this).watch(this);
    }

    /**
     * eventBus的订阅者
     *
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Message msg) {

        if (msg != null) {
            switch (msg.what) {
                case EventBusActivity.NOTIFY_MAIN_ACITIVITY:
                    String message = msg.obj.toString();
                    tvEventbus.setText(message);
                    break;
                case RxjavaActivity.RX_NOTIFY_MAIN_ACITIVITY:
                    String MSG = msg.obj.toString();
                    tvEventbus.setText(MSG);
                    break;
                default:
            }

        }
    }

}
