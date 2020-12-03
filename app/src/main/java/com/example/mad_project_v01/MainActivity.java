package com.example.mad_project_v01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    //Variables
    Animation topAnim,bottomAnim;
    ImageView logo_keells,logo_android;
    TextView lable_super,lable_android;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Read components
        logo_keells = findViewById(R.id.Logo_Keells);
        logo_android = findViewById(R.id.Logo_Android);
        lable_super = findViewById(R.id.Lable_Super);
        lable_android = findViewById(R.id.Lable_Android);

        //Process
        logo_keells.setAnimation(topAnim);
        lable_super.setAnimation(topAnim);
        logo_android.setAnimation(bottomAnim);
        lable_android.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( MainActivity.this, Signin_Page.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}