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
import android.widget.ImageView;
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
import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.model.Music;
import com.estsoft.muvigram.model.Tag;
import com.estsoft.muvigram.ui.base.fragment.BaseSingleFragment;
import com.estsoft.muvigram.ui.friend.FindFriendActivity;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.estsoft.muvigram.util.DialogFactory;
import com.squareup.picasso.Picasso;

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

    @Inject TrendingTagsPresenter mPresenter;

    @BindView(R.id.action_bar) RelativeLayout mActionBar;
    @BindView(R.id.search_bar) RelativeLayout mSearchBar;

    @BindView(R.id.trending_tag1) TextView tag1;
    @BindView(R.id.trending_tag2) TextView tag2;
    @BindView(R.id.trending_tag3) TextView tag3;
    @BindView(R.id.trending_sound1) TextView sound1;
    @BindView(R.id.trending_sound2) TextView sound2;
    @BindView(R.id.trending_sound3) TextView sound3;
    @BindView(R.id.trending_user_id1) TextView userId1;
    @BindView(R.id.trending_user_id2) TextView userId2;
    @BindView(R.id.trending_user_id3) TextView userId3;
    @BindView(R.id.trending_user_image1) ImageView userProfile1;
    @BindView(R.id.trending_user_image2) ImageView userProfile2;
    @BindView(R.id.trending_user_image3) ImageView userProfile3;

    @OnClick(R.id.search_bar) void clickSearchBar(){
        startActivity(new Intent(getActivity(), SearchBarActivity.class));
    }


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

        mPresenter.loadTrendingTags();
        mPresenter.loadTrendingSounds();
        mPresenter.loadTrendingUsers();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mPresenter.detachView();
    }


    @Override
    public void showTags(List<Tag> tags){
        tag1.setText("# "+tags.get(0).tagName());
        tag2.setText("# "+tags.get(1).tagName());
        tag3.setText("# "+tags.get(2).tagName());
    }

    @Override
    public void showTagsEmpty(){
        Toast.makeText(getContext(), "no tags", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTagsError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading tag.").show();
    }


    @Override
    public void showSounds(List<Music> musics){
        sound1.setText(musics.get(0).title()+ " - "+musics.get(0).artist());
        sound2.setText(musics.get(1).title()+ " - "+musics.get(1).artist());
        sound3.setText(musics.get(2).title()+ " - "+musics.get(2).artist());
    }

    @Override
    public void showSoundsEmpty(){
        Toast.makeText(getContext(), "no tags", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSoundsError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading tag.").show();
    }


    @Override
    public void showUsers(List<Friend> users){
        Picasso.with(getContext()).load(users.get(0).profile()).transform(new CircleTransform()).into(userProfile1);
        Picasso.with(getContext()).load(users.get(1).profile()).transform(new CircleTransform()).into(userProfile2);
        Picasso.with(getContext()).load(users.get(2).profile()).transform(new CircleTransform()).into(userProfile3);

        userId1.setText("@"+users.get(0).id());
        userId2.setText("@"+users.get(1).id());
        userId3.setText("@"+users.get(2).id());
    }

    @Override
    public void showUsersEmpty(){
        Toast.makeText(getContext(), "no tags", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUsersError(){
        DialogFactory.createGenericErrorDialog(getContext(),
                "There was an error loading tag.").show();
    }

}
