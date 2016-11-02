package com.estsoft.muvigram.ui.videocut;

import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoCutActivity extends BaseSingleFragmentActivity {

    @Override
    protected Fragment createDefaultFragment() {
        return VideoCutFragment.newInstance();
    }
}
