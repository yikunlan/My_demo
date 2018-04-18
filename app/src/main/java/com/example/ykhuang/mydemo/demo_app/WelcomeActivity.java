package com.example.ykhuang.mydemo.demo_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.ykhuang.mydemo.R;


/**
 * Created by hyk on 2018/4/18.
 */

public class WelcomeActivity extends Activity {

    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_layout_one, menu);
        return true;
    }

}
