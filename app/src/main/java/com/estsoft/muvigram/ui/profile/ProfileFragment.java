package com.estsoft.muvigram.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.setting.SettingsActivity;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class ProfileFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setCustomActionbar();

        return v;
    }

    private void setCustomActionbar(){
        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
        actionBar.show();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View mCustomView = LayoutInflater.from(getActivity()).inflate(R.layout.actionbar_profile, null);

        mCustomView.findViewById(R.id.find_friend_button).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), FindFriendActivity.class));
        });

        mCustomView.findViewById(R.id.setting_button).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        });

        actionBar.setCustomView(mCustomView);
    }

}
