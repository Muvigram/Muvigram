package com.estsoft.muvigram.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;

import butterknife.BindView;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class SearchFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        setCustomActionbar();

        return v;
    }

    private void setCustomActionbar(){
        ActionBar actionBar = ((HomeActivity)getActivity()).getSupportActionBar();
        actionBar.show();

        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        View mCustomView = LayoutInflater.from(getActivity()).inflate(R.layout.actionbar_search, null);
        actionBar.setCustomView(mCustomView);

        mCustomView.findViewById(R.id.search_bar).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchBarActivity.class));
        });

        mCustomView.findViewById(R.id.find_friend_button).setOnClickListener(v->{
           startActivity(new Intent(getActivity(), FindFriendActivity.class));
        });
    }

}
