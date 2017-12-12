package com.example.ykhuang.mydemo;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ykhuang.mydemo.eventbus.EventBusActivity;
import com.example.ykhuang.mydemo.rxjava.RxjavaActivity;

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

//    private void findView() {
//        tv_rxjava = (TextView) findViewById(R.id.tv_rxjava);
//        tv_rxjava.setOnClickListener(this);
//        tv_eventbus = (TextView) findViewById(R.id.tv_eventbus);
//        tv_eventbus.setOnClickListener(this);
//    }

    @OnClick({R.id.tv_rxjava,R.id.tv_eventbus})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rxjava:
                RxjavaActivity.open(this);
                break;
            case R.id.tv_eventbus:
                EventBusActivity.open(this);
                break;
            default:
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册
        EventBus.getDefault().unregister(this);
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
