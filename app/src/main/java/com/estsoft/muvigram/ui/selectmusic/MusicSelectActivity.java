package com.estsoft.muvigram.ui.selectmusic;

import android.os.Bundle;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.base.BaseActivity;

import butterknife.ButterKnife;

public class MusicSelectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_music_select);
        ButterKnife.bind(this);
    }
}
