package com.estsoft.muvigram.ui.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.home.HomeActivity;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class FeedFragment extends Fragment {

    private FragmentTabHost mTabHost;

    //Mandatory Constructor
    public FeedFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
        actionBar.setShowHideAnimationEnabled(false);
        actionBar.hide();

        mTabHost = (FragmentTabHost)v.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        mTabHost.addTab(mTabHost.newTabSpec("FeedFollow").setIndicator("팔로우"),
                FeedFollowFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("FeedSpecial").setIndicator("특집"),
                FeedSpecialFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("FeedForYou").setIndicator("회원님을 위한"),
                FeedForYouFragment.class, null);

        return v;
    }

}
