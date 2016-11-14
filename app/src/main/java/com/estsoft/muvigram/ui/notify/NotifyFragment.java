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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JEONGYI on 2016. 10. 11..
 */

public class NotifyFragment extends Fragment {

    @BindView(R.id.action_bar) LinearLayout mActionBar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.swipe_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_notify, container, false);
        ButterKnife.bind(this,view);

        initRecyclerView();

        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mActionBar.getLayoutParams();
        params.setMargins(0, ((MuvigramApplication) getActivity().getApplication()).getStatusBarHeight(), 0, 0);
        mActionBar.setLayoutParams(params);

        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        NotifyRecyclerAdapter adapter = new NotifyRecyclerAdapter(getNotificationItems());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 새로고침 코드
                adapter.clear();
                adapter.addAll(refreshTest());
                // 새로고침 완료
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public List<NotificationItem> getNotificationItems()
    {
        List<NotificationItem> listItems = new ArrayList<>();
        NotificationItem[] items = new NotificationItem[10];

        for(int i=0; i<5; i++){
            items[i] = new NotificationItem();
        }

        items[0].add(new NotifyFollowItem());
        items[1].add(new NotifyLikeItem());
        items[2].add(new NotifyReplyItem());
        items[3].add(new NotifyLikeItem());
        items[4].add(new NotifyFollowItem());

        for(int i=0; i<5; i++){
            listItems.add(items[i]);
        }

        return listItems;
    }

    public List<NotificationItem> refreshTest()
    {
        List<NotificationItem> listItems = new ArrayList<>();
        NotificationItem[] items = new NotificationItem[10];

        for(int i=0; i<6; i++){
            items[i] = new NotificationItem();
        }

        items[0].add(new NotifyFollowItem());
        items[1].add(new NotifyLikeItem());
        items[2].add(new NotifyReplyItem());
        items[3].add(new NotifyLikeItem());
        items[4].add(new NotifyFollowItem());
        items[5].add(new NotifyFollowItem());

        for(int i=0; i<6; i++){
            listItems.add(items[i]);
        }

        return listItems;
    }

}
