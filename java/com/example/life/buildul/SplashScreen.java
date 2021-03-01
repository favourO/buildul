package com.example.life.buildul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    ImageView truck, logo; //bottomCar, rightCar, leftCar, centerLogo;
    Animation truck_comes, logo_anim; //fromLeft, fromRight, Centerlogo;
    private Thread splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        startAnimation();
    }

    public void startAnimation(){
        truck = (ImageView) findViewById(R.id.truck);
        logo = (ImageView) findViewById(R.id.buidull);
        /*bottomCar = (ImageView) findViewById(R.id.up_car);
        rightCar = (ImageView) findViewById(R.id.right);
        leftCar = (ImageView) findViewById(R.id.left);
        centerLogo = (ImageView) findViewById(R.id.center);*/

        truck_comes = AnimationUtils.loadAnimation(this, R.anim.truck_comes);
        truck.setAnimation(truck_comes);

        logo_anim = AnimationUtils.loadAnimation(this, R.anim.image_scale);
        logo.setAnimation(logo_anim);

        /*fromRight = AnimationUtils.loadAnimation(this, R.anim.from_right);
        rightCar.setAnimation(fromRight);

        fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left);
        leftCar.setAnimation(fromLeft);

        Centerlogo = AnimationUtils.loadAnimation(this, R.anim.center_center);
        centerLogo.setAnimation(Centerlogo);*/

        splash = new Thread(){
            @Override
            public void run(){
                try {
                    int waited = 0;
                    //splash screen pause time
                    while(waited < 4500){
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                }catch (InterruptedException e){
                    // nothing happened so move man
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splash.start();
    }
}
