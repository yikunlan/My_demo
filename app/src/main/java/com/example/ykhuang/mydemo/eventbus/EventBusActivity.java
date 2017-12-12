package com.example.ykhuang.mydemo.eventbus;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ykhuang.mydemo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * rxjava适合用于一些费时的操作，evebus适合在不同组件中传递数据，例如activity和activity之间传递
 */
public class EventBusActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_rx_click;
    public static final int NOTIFY_MAIN_ACITIVITY = 0x001;
    public static  String TAG = "TAG";


    public static void open(Context context){
        Intent intent = new Intent(context,EventBusActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        findView();
    }



    private void findView() {
        tv_rx_click = (TextView) findViewById(R.id.tv_rx_click);
        tv_rx_click.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_rx_click:
                Message msg = Message.obtain();
                msg.obj = "接受到来自eventBus的消息";
                msg.what = NOTIFY_MAIN_ACITIVITY;
                EventBus.getDefault().post(msg);
                break;

            default:
        }
    }
}
