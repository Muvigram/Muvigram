package com.estsoft.muvigram.ui.search;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by gangGongUi on 2016. 10. 16..
 */

public class SearchFragment extends Fragment {

    private String[] tagItemList = new String[]{"WaterBalance","Korea","SideToSide","Noma","Kkoma","JeongYi"};
    private String[] tagColorList = new String[]{"#ff4081","#ff7997","#f9a825","#c0ca33","#26c6da","#5677fc"};

    @BindView(R.id.search_fragment_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.action_bar) RelativeLayout mActionBar;
    @BindView(R.id.search_bar) RelativeLayout mSearchBar;
    @BindView(R.id.search_fragment_increasevideoview) IncreasVideoView videoView;
    @BindView(R.id.items) LinearLayout itemLayout;

    @OnClick(R.id.search_bar) void clickSearchBar(){
        startActivity(new Intent(getActivity(), SearchBarActivity.class));
    }

    @OnClick(R.id.find_friend_button) void clickFindFriendButton(){
        startActivity(new Intent(getActivity(), FindFriendActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);
        initRecyclerView();

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mActionBar.setLayoutParams(params);

        return view;
    }

    private void initRecyclerView() {
        setVideo();
        setTagView();
    }

    //비디오
    private void setVideo(){
        SearchHeaderVideoItem videoItem = getVideoHeader();
        videoView.setVideoURI(videoItem.getVideoFile());
        videoView.setOnPreparedListener(mp -> videoPlay(videoView, mp));
        videoView.setOnCompletionListener(mp -> videoPlay(videoView, mp));
    }

    private SearchHeaderVideoItem getVideoHeader() {
        final String VIDEO_FILE_NAME = "test_intro_video1";
        final Uri videoFile = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/" + VIDEO_FILE_NAME);
        return new SearchHeaderVideoItem(videoFile, "Title");
    }

    private void videoPlay(final IncreasVideoView mVideoView ,final MediaPlayer mediaPlayer) {
        if(mVideoView != null) {
            mediaPlayer.setVolume(0, 0);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
    }

    private void setTagView(){
        for(int i=0; i<tagItemList.length; i++){

            RelativeLayout item = new RelativeLayout(getActivity());
            item.setBackgroundColor(Color.parseColor(tagColorList[i]));

            TextView tag = new TextView(getActivity());
            tag.setText("#"+tagItemList[i]);
            tag.setTextColor(Color.WHITE);
            tag.setTypeface(tag.getTypeface(), Typeface.BOLD);
            tag.setTextSize(20);
            item.addView(tag);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                                                                                , ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,10,10,10);

            tag.setLayoutParams(params);
            itemLayout.addView(item);
        }
    }

}
