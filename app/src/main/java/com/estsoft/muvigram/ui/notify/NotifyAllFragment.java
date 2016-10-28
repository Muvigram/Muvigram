package com.estsoft.muvigram.ui.notify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 12..
 */

public class NotifyAllFragment extends Fragment {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recyclerview_simple_list, container, false);
        ButterKnife.bind(this,v);

        initRecyclerView();

        return v;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        NotifyRecyclerAdapter adapter = new NotifyRecyclerAdapter(getNotificationItems());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
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

}
