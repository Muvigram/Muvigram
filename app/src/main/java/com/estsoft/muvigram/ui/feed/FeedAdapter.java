package com.estsoft.muvigram.ui.feed;

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
public class FeedAdapter extends RecyclerView.Adapter<Holder> {

    private final ArrayList<FeedRepo> mFeedRepos;

    public FeedAdapter(@NonNull ArrayList<FeedRepo> mFeedRepos) {
        this.mFeedRepos = mFeedRepos;
    }

    @Override public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedpageritem, parent, false);
        return new Holder(view);
    }

    @Override public void onBindViewHolder(Holder holder, int position) {
        FeedRepo item = mFeedRepos.get(position);
        item.onBindViewHolder(holder, position);
    }

    @Override public int getItemCount() {
        return mFeedRepos.size();
    }

    public void addFeedItem(@NonNull FeedRepo mRepo) {
        mFeedRepos.add(mRepo);
        notifyItemChanged(mFeedRepos.size() - 1);
    }
}
