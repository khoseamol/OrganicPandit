package com.everlastingseo.organicpandit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.utils.PrefManager;

public class SplasActivity extends AppCompatActivity {
    private static final int RESOLUTION_REQUEST = 9000;
    private static final String TAG = "SplashActivity";
    private static int SPLASH_TIME_OUT = 3000;
    PrefManager prefManager;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splas);
        bindViews();

    }

    private void bindViews() {
        mContext = this;
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        startTimer();
    }

    public void startTimer() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                prefManager = new PrefManager(SplasActivity
                        .this);
                if (PrefUtils.getFromPrefs(mContext, "Login", "").equals("true")) {
                    launchDashboardScreen();
                    finish();
                } else {
                    Intent i = new Intent(SplasActivity.this, LoginActvity.class);
                    startActivity(i);

                    SplasActivity.this.finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void launchDashboardScreen() {
        startActivity(new Intent(SplasActivity.this, DashBoardActivity.class));
        finish();
    }

}
