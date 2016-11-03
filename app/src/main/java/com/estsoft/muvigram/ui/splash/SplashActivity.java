package com.estsoft.muvigram.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.data.DataManager;
import com.estsoft.muvigram.ui.base.activity.BaseActivity;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.ui.intro.IntroActivity;

import javax.inject.Inject;


public class SplashActivity extends BasePlainActivity {

    @Inject DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlainActivityComponent().inject(this);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(intent);
            finish();
        }, 0);


    }
}





