package com.estsoft.muvigram.ui.videoselect;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

/**
 * Created by jaylim on 10/17/2016.
 */

public class VideoSelectActivity extends BaseSingleFragmentActivity {

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

    @Override
    protected Fragment createDefaultFragment() {
        return VideoSelectFragment.newInstance();
    }
}
