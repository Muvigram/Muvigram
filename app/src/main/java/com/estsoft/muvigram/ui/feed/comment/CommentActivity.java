package com.estsoft.muvigram.ui.feed.comment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class CommentActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);




    }

    @Override protected void onResume() {
        super.onResume();
        //initBlurImageView();
    }





}
