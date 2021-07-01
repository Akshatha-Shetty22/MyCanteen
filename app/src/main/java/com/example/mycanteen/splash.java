package com.example.mycanteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash extends AppCompatActivity {
    private static  int SPLASH_SCREEN=5000;
    ImageView logo,slogan,hp;
    Animation topanim,leftanim,rightanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        topanim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        leftanim= AnimationUtils.loadAnimation(this,R.anim.left_animation);
        rightanim= AnimationUtils.loadAnimation(this,R.anim.right_animation);
        logo=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);
        hp=findViewById(R.id.hp);
        logo.setAnimation(topanim);
        slogan.setAnimation(rightanim);
        hp.setAnimation(leftanim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_SCREEN);

    }
}