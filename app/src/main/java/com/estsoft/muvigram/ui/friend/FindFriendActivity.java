package com.estsoft.muvigram.ui.friend;

import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Toast;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.ui.base.activity.BasePlainActivity;
import com.estsoft.muvigram.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 13..
 */

public class FindFriendActivity extends BasePlainActivity implements FindFriendView {

    @Inject FindFriendPresenter mPresenter;
    @Inject FindFriendItemAdapter mAdapter;

    @BindView(R.id.find_friend_recycler_view) RecyclerView mRecyclerView;

    @OnClick(R.id.find_friend_back_button) void clickBack(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and bind layout
        setContentView(R.layout.activity_find_friend);
        ButterKnife.bind(this);

        // Inject Dependency
        getPlainActivityComponent().inject(this);
        mPresenter.attachView(this);

        // set recycler view - layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadFriends();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /* Reactive view logic */
    @Override
    public void showFriends(List<Friend> friends) {
        mAdapter.setFriends(friends);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setNestedScrollingEnabled(false);
        initRecyclerView(friends.size());
    }


    @Override
    public void showFriendsEmpty() {
        mAdapter.setFriends(Collections.emptyList());
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setNestedScrollingEnabled(false);
        Toast.makeText(this, "There arn`t any friends",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this,
                "There was an error loading friends you may know.").show();
    }

    /* Static view logic */

    private void initRecyclerView(int friendNumber) {
        float pixels = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 71,
                getResources().getDisplayMetrics()
        );
        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        params.height = friendNumber * (int) pixels;
        mRecyclerView.setLayoutParams(params);
    }
}
