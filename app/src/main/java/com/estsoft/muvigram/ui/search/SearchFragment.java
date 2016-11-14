package com.estsoft.muvigram.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.injection.component.SingleFragmentComponent;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.util.DialogFactory;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by JEONGYI on 2016. 10. 11..
 * Edited by gangGongUi on 2016. 10. 16..
 */

public class SearchFragment extends Fragment implements TrendingTagsView {

    private String[] tagColorList = new String[]{"#ff4081","#ff7997","#f9a825","#c0ca33",
            "#26c6da","#5677fc","#ab47bc","#651fff","#0097a7","#ff7043"};

    @Inject TrendingTagsPresenter mPresenter;

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

    Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ParentFragmentComponent activityComponent = ((HomeActivity) getActivity()).getSingleFragmentActivityComponent(this);
        activityComponent.inject(this);

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter.attachView(this);

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mActionBar.setLayoutParams(params);

        mPresenter.loadVideo();
        mPresenter.loadTags();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showVideo(SearchHeaderVideoItem video){
        final Uri videoFile = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/" + video.getTitle());
        videoView.setVideoURI(videoFile);
        videoView.setOnPreparedListener(mp -> videoPlay(videoView, mp));
        videoView.setOnCompletionListener(mp -> videoPlay(videoView, mp));
    }

//    private SearchHeaderVideoItem getVideoHeader() {
//        final String VIDEO_FILE_NAME = "dummy_vodeo_0";
//        final Uri videoFile = Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/" + VIDEO_FILE_NAME);
//        return new SearchHeaderVideoItem(videoFile, "Title");
//    }

    private void videoPlay(final IncreasVideoView mVideoView ,final MediaPlayer mediaPlayer) {
        if(mVideoView != null) {
            mediaPlayer.setVolume(0, 0);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
    }

    @Override
    public void showVideoEmpty(){
        Toast.makeText(getContext(), "no video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVideoError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading video you may know.").show();
    }

    @Override
    public void showTags(List<Tag> tags){
        for(int i=0; i<tags.size(); i++){

            RelativeLayout item = new RelativeLayout(getActivity());
            int random = (int)( Math.random() * tagColorList.length);
            item.setBackgroundColor(Color.parseColor(tagColorList[random]));

            TextView tag = new TextView(getActivity());
            tag.setText("#"+tags.get(i).tagName());
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

    @Override
    public void showTagsEmpty(){
        Toast.makeText(getContext(), "no tags", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading tag.").show();
    }

}
