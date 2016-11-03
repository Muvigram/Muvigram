package com.estsoft.muvigram.ui.musicselect.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.qualifier.NestedFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 *
 * Created by jaylim on 11/2/2016.
 */

@PerParentFragment
public class MusicSelectPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFragments;


    @Inject
    public MusicSelectPagerAdapter(@NestedFragment FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
