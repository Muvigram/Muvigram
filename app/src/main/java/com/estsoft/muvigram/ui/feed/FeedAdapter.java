package com.estsoft.muvigram.ui.feed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.model.FeedRepo;

import java.util.ArrayList;

/**
 * Created by gangGongUi on 2016. 10. 26..
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedItemHolder> {

    private final ArrayList<FeedRepo> mFeedRepos;
    private final Context mContext;

    public FeedAdapter(Context mContext, @NonNull ArrayList<FeedRepo> mFeedRepos) {
        this.mContext = mContext;
        this.mFeedRepos = mFeedRepos;
    }

    @Override public FeedItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedpageritem, parent, false);
        return new FeedItemHolder(mContext, view);
    }

    @Override public void onBindViewHolder(FeedItemHolder mFeedItemHolder, int position) {
        FeedRepo item = mFeedRepos.get(position);
        mFeedItemHolder.onBindViewHolder(item);
    }

    @Override public int getItemCount() {
        return mFeedRepos.size();
    }

    public void addFeedItem(@NonNull FeedRepo mRepo) {
        mFeedRepos.add(mRepo);
        notifyItemChanged(mFeedRepos.size() - 1);
    }
}
