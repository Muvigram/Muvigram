package com.estsoft.muvigram.ui.notify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.estsoft.muvigram.MuvigramApplication;
import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.component.ParentFragmentComponent;
import com.estsoft.muvigram.model.NotifyComment;
import com.estsoft.muvigram.model.NotifyFollow;
import com.estsoft.muvigram.model.NotifyLike;
import com.estsoft.muvigram.ui.home.HomeActivity;
import com.estsoft.muvigram.ui.profile.ProfileFragment;
import com.estsoft.muvigram.ui.profile.ProfileFragmentPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class NotifyFragment extends Fragment implements NotifyFragmentView{

    @Inject NotifyFragmentPresenter mPresenter;
    @Inject NotifyRecyclerAdapter mAdapter ;

    @BindView(R.id.action_bar) LinearLayout mActionBar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.swipe_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    List<NotificationItem> listItems = new ArrayList<>();
    boolean isFollowChecked = false;
    boolean isCommentChecked = false;
    boolean isLikeChecked = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ParentFragmentComponent activityComponent = ((HomeActivity) getActivity()).getSingleFragmentActivityComponent(this);

        activityComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind(this,view);

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mActionBar.setLayoutParams(params);
        mPresenter.attachView(this);

        Timber.e("before loading");
        mPresenter.loadNotifyComments();
        mPresenter.loadNotifyFollow();
        mPresenter.loadNotifyLikes();
        Timber.e("after loading");


        initRecyclerView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        Collections.sort(listItems, new DateDescCompare() );
        mAdapter.setNotificationItemList(listItems);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 새로고침 코드
//                mAdapter.clear();
//                mAdapter.addAll(refreshTest());
                mAdapter.notifyDataSetChanged();
                // 새로고침 완료
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    static class DateDescCompare implements Comparator<NotificationItem> {
        @Override
        public int compare(NotificationItem arg0, NotificationItem arg1) {
            return arg0.getDate() > arg1.getDate() ? -1 : arg0.getDate() < arg1.getDate() ? 1:0;
        }

    }

    public List<NotificationItem> refreshTest()
    {
        //TODO -- set new data
        return listItems;
    }

    @Override
    public void showComments(List<NotifyComment> comments){
        if(!isCommentChecked) {
            for (int i = 0; i < comments.size(); i++) {
                NotificationItem notificationItem = new NotificationItem();
                notificationItem.add(comments.get(i));
                listItems.add(notificationItem);
            }
            isCommentChecked = true;
        }
        Collections.sort(listItems, new DateDescCompare() );
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCommentsEmpty(){

    }

    @Override
    public void showCommentsError(){

    }

    @Override
    public void showFollow(List<NotifyFollow> follows){
        if(!isFollowChecked) {
            for (int i = 0; i < follows.size(); i++) {
                NotificationItem notificationItem = new NotificationItem();
                notificationItem.add(follows.get(i));
                listItems.add(notificationItem);
            }
            isFollowChecked = true;
        }
        Collections.sort(listItems, new DateDescCompare() );
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFollowEmpty(){

    }

    @Override
    public void showFollowError(){

    }

    @Override
    public void showLikes(List<NotifyLike> likes){
        if(!isLikeChecked) {
            for (int i = 0; i < likes.size(); i++) {
                NotificationItem notificationItem = new NotificationItem();
                notificationItem.add(likes.get(i));
                listItems.add(notificationItem);
            }
            isLikeChecked = true;
        }
        Collections.sort(listItems, new DateDescCompare() );
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLikesEmpty(){

    }

    @Override
    public void showLikesError(){

    }

}
