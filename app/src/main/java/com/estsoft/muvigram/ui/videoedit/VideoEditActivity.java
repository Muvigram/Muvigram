package com.estsoft.muvigram.ui.videoedit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;
import com.estsoft.muvigram.ui.musicselectonline.MusicSelectOnlineListActivitiy;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoEditActivity extends BaseSingleFragmentActivity {
    private static final String TAG = "VideoEditActivity";

    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoEditActivity.video_path";

    public static Intent newIntent(Context packageContext, String cutVideoPath) {
        Intent intent = new Intent(packageContext, VideoEditActivity.class);
        intent.putExtra( ARG_VIDEO_PATH, cutVideoPath );
        return intent;
    }

    @Override
    protected Fragment createDefaultFragment() {
        String videoPath = getIntent().getStringExtra(ARG_VIDEO_PATH);
        return VideoEditFragment.newInstance( videoPath );
    }
}
