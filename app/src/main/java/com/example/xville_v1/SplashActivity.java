package com.example.xville_v1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {

    private Button mBtnSkip;
    private Handler mHandler = new Handler();
    private Runnable mRunnableToLogin = new Runnable() {
        @Override
        public void run() {
            toMainActivity();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_splash);
        initView();
        ClickToLogin();
        mHandler.postDelayed(mRunnableToLogin, 8000);
    }

    private void ClickToLogin() {
        mBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mRunnableToLogin);
                toMainActivity();
            }
        });
    }

    private void initView() {
        mBtnSkip = findViewById(R.id.btn_skip);
    }

    private void toLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnableToLogin);
    }

}
