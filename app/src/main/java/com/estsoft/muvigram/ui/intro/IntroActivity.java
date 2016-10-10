package com.estsoft.muvigram.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;
import com.estsoft.muvigram.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends BaseActivity {

    @BindView(R.id.login_button) Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);




        mLoginButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } );

    }
}
