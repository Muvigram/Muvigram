package com.estsoft.muvigram.ui.videoedit;

import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;

/**
 * Created by jaylim on 10/31/2016.
 */

public class VideoEditActivity extends BaseSingleFragmentActivity {

    @Override
    protected Fragment createDefaultFragment() {
        return VideoEditFragment.newInstance();
    }
}
