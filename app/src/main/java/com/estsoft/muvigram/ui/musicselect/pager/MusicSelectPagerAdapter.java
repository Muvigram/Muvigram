package com.estsoft.muvigram.ui.musicselect.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.estsoft.muvigram.injection.PerSingleFragment;

import javax.inject.Inject;

/**
 * Created by jaylim on 11/2/2016.
 */

@PerSingleFragment
public class MusicSelectPagerAdapter extends FragmentStatePagerAdapter {

    @Inject
    public MusicSelectPagerAdapter() {
        super(null);

    }

    public MusicSelectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
