package com.estsoft.muvigram.ui.videoselect;

import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

/**
 * Created by jaylim on 10/17/2016.
 */

public class VideoSelectActivity extends BaseSingleFragmentActivity {

    @Override
    protected Fragment createDefaultFragment() {
        return VideoSelectFragment.newInstance();
    }
}
