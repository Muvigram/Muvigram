package com.estsoft.muvigram.ui.videoselect;

import android.os.Bundle;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by jaylim on 10/17/2016.
 */

public class VideoSelectActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_video_select);
        ButterKnife.bind(this);
    }
}
