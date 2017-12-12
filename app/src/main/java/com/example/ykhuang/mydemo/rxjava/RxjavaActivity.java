package com.example.ykhuang.mydemo.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ykhuang.mydemo.R;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * rxjava适合用于一些费时的操作，evebus适合在不同组件中传递数据，例如activity和activity之间传递
 */
public class RxjavaActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_rx_click;
    public static final int RX_NOTIFY_MAIN_ACITIVITY = 0x02;


    public static void open(Context context){
        Intent intent = new Intent(context,RxjavaActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        findview();

    }

    private void findview() {
        tv_rx_click = (TextView) findViewById(R.id.tv_rx_click);
        tv_rx_click.setOnClickListener(this);
    }

    /***
     * 创建被观察者
     */
    private Observable createObservable(){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行一些其他操作
                Thread.sleep(10000);
                //执行完毕，触发回调，通知观察者
                e.onNext("10秒后产生的数据");
            }
        });
        return observable;
    }

    /**
     * 创建观察者
     */
    private Observer createObserver(){
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            //观察者接收到通知,进行相关操作
            public void onNext(String aLong) {
                tv_rx_click.setText(aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        return observer;
    }

    /**
     * 把观察者和被观察者通过订阅联系起来
     * 相当于用接口方式实现的setCall的方式
     */
    private void subscribe(Observable observable,Observer observer){
        observable.subscribe(observer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_rx_click:
                subscribe(createObservable(),createObserver());

//                Message msg = Message.obtain();
//                msg.obj = "接受到来自RXJAVA的消息";
//                msg.what = RX_NOTIFY_MAIN_ACITIVITY;
//                EventBus.getDefault().post(msg);
                break;

            default:
        }
    }
}
