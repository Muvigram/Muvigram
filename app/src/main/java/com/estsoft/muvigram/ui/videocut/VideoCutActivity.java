package com.estsoft.muvigram.ui.videocut;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;
import com.estsoft.muvigram.ui.videoedit.VideoEditActivity;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoCutActivity extends BaseSingleFragmentActivity {
    private static final String TAG = "VideoCutActivity";

    private static final String ARG_VIDEO_PATH
            = "com.estsoft.muvigram.ui.videoedit.VideoCutActivity.video_path";

    public static Activity thisActivity;
    public static void finishActivity(){
        if(thisActivity == null) return;
        thisActivity.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // navigation bar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        thisActivity = this;
    }

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
