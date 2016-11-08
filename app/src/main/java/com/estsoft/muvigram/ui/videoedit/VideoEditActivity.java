package com.estsoft.muvigram.ui.videoedit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;
import com.estsoft.muvigram.ui.videocut.VideoCutActivity;
import com.estsoft.muvigram.ui.videoselect.VideoSelectActivity;

import java.io.IOException;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoEditActivity extends BaseSingleFragmentActivity {
    private static final String TAG = "VideoEditActivity";

    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoEditActivity.video_path";
    private static final String ARG_AUDIO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoEditActivity.audio_path";
    private static final String ARG_AUDIO_OFFSET
            = "com.estsoft.muvigram.ui.videoedit.VideoEditActivity.audio_offset";

    public static Activity thisActivity;

    public static Intent newIntent( Context packageContext, String cutVideoPath, String audioPath, int audioOffset ) {
        Intent intent = new Intent(packageContext, VideoEditActivity.class);
        intent.putExtra( ARG_VIDEO_PATH, cutVideoPath );
        intent.putExtra( ARG_AUDIO_PATH, audioPath );
        intent.putExtra( ARG_AUDIO_OFFSET, audioOffset);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        //navigation bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        VideoSelectActivity.finishActivity();
        VideoCutActivity.finishActivity();

    }

    @Override
    protected Fragment createDefaultFragment() {
        String videoPath = getIntent().getStringExtra(ARG_VIDEO_PATH);
        String audioPath = getIntent().getStringExtra(ARG_AUDIO_PATH);
        int audioOffset = getIntent().getIntExtra(ARG_AUDIO_OFFSET, 0);
        return VideoEditFragment.newInstance( videoPath, audioPath, audioOffset );
    }

    public static void finishActivity(){
        if (thisActivity == null) return;
        thisActivity.finish();
    }



}
