package com.estsoft.muvigram.ui.feed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by gangGongUi on 2016. 10. 26..
 */

@PerParentFragment
public class FeedAdapter extends RecyclerView.Adapter<FeedItemHolder> {

    private List<FeedItem> mFeedItems;
    private final Context mContext;

    @Inject
    public FeedAdapter(@ActivityContext Context mContext) {
        this.mContext = mContext;
    }

    public void setFeedItems(List<FeedItem> mFeedItems) {
        this.mFeedItems = mFeedItems;
    }

    public void addFeedItems(List<FeedItem> list) {
        mFeedItems.addAll(list);
    }

    @Override public FeedItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedpageritem, parent, false);
        return new FeedItemHolder(mContext, view);
    }

    @Override public void onBindViewHolder(FeedItemHolder feedItemHolder, int position) {
        FeedItem item = mFeedItems.get(position);
        feedItemHolder.onBindViewHolder(item);
    }

    @Override public int getItemCount() {
        return mFeedItems.size();
    }

}
