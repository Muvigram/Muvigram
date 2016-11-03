package com.estsoft.muvigram.ui.musicselect;

import android.support.v4.app.Fragment;

import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragmentActivity;
import com.estsoft.muvigram.ui.musicselect.pager.MusicSelectFragment;

public class MusicSelectActivity extends BaseSingleFragmentActivity {

    @Override
    protected Fragment createDefaultFragment() {
        return MusicSelectFragment.newInstance();
    }
}
