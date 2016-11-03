package com.estsoft.muvigram.ui.videocut;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;
import com.estsoft.muvigram.ui.videoedit.VideoEditActivity;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoCutActivity extends BaseSingleFragmentActivity {
    private static final String TAG = "VideoCutActivity";

    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoCutActivity.video_path";

    public static Intent newIntent(Context packageContext, String videoPath) {
        Intent intent = new Intent(packageContext, VideoCutActivity.class);
        intent.putExtra(ARG_VIDEO_PATH, videoPath );
        return intent;
    }

    @Override
    protected Fragment createDefaultFragment() {
        String videoPath = getIntent().getStringExtra(ARG_VIDEO_PATH);
        return VideoCutFragment.newInstance( videoPath );
    }
}
