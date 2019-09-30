package com.example.ykhuang.mydemo.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.widget.RelativeLayout;

import com.example.ykhuang.mydemo.R;
import com.example.ykhuang.mydemo.util.DeviceUtil;

public class AnimationActivity extends Activity {

    private ImageView moveImg;
    private ImageView moveImg2;
    private View v_test;
    private Button btn_obj_ani;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        findViews();
    }

    private void findViews() {
        moveImg = findViewById(R.id.iv_img);
        moveImg2 = findViewById(R.id.iv_img2);
        v_test = findViewById(R.id.v_test);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(200,200);
        moveImg2.setLayoutParams(lp);
        moveImg2.setY(200);
        v_test.setY(500);
        btn_obj_ani = findViewById(R.id.btn_obj_ani);
        btn_obj_ani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator(moveImg);
                objectAnimator2(moveImg2);
            }
        });
    }


    private void objectAnimator(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationY",0F,500F);
        objectAnimator.setInterpolator(new DecelerateInterpolator(1.3F));
        objectAnimator.setDuration(500L);
        objectAnimator.start();
//        ObjectAnimator scalX = ObjectAnimator.ofFloat(view, "scaleX", 1.0F, 1.8F,0.6F,1.2F);
//        ObjectAnimator scalY = ObjectAnimator.ofFloat(view,"scaleY",1.0F, 1.8F,0.6F,1.2F);
//        AnimatorSet set = new AnimatorSet();
//        set.play(scalX).with(scalY);
//        set.setDuration(2000L);
//        set.start();

//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(view, "scaleY", new PathAnimator(),  1.0F,1.5F);
//        objectAnimator.setDuration(500L);
//        objectAnimator.start();
    }
    private void objectAnimator2(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"translationY",800F, 500F);
        objectAnimator.setInterpolator(new DecelerateInterpolator(1.3F));
        objectAnimator.setDuration(500L);
        objectAnimator.start();
//        ObjectAnimator scalX = ObjectAnimator.ofFloat(view, "scaleX", 1.0F, 1.8F,0.6F);
//        ObjectAnimator scalY = ObjectAnimator.ofFloat(view,"scaleY",1.0F, 1.8F,0.6F);
//        AnimatorSet set = new AnimatorSet();
//        set.play(scalX).with(scalY);
//        set.setDuration(2000L);
//        set.start();

//        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(view, "scaleY", new PathAnimator(),  1.0F,1.5F);
//        objectAnimator.setDuration(500L);
//        objectAnimator.start();
    }



    private void valueAnimator(){

//        Path path = new Path();
//        path.moveTo(0,0);
//        path.lineTo(300,300);
//        path.quadTo(50,500,300,700);
//        path.cubicTo(600,600,500,250,50,800);
//        path.quadTo(500,0,0,0);
//
//        ObjectAnimator mAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
//        mAnimator.setDuration(5000);
//        mAnimator.start();

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), new Point(0, 0), new Point(200, 200));

        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("tag","startAnimation");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("tag","animation end");

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();

    }
}
