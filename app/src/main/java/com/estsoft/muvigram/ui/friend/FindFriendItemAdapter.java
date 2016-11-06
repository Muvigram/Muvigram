package com.estsoft.muvigram.ui.friend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.model.Friend;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 28..
 */

@PerPlainActivity
public class FindFriendItemAdapter extends RecyclerView.Adapter<FindFriendItemAdapter.FriendViewHolder> {

    private List<Friend> mFriends;

    @Inject
    public FindFriendItemAdapter() {
        this.mFriends = new ArrayList<>();
    }

    public void setFriends(List<Friend> friends) {
        mFriends = friends;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int position){
        View itemView = LayoutInflater.from (parent.getContext()).
                inflate (R.layout.notify_fragment_item_follow, parent, false);
        return new FindFriendItemAdapter.FriendViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        Friend friend = mFriends.get(position);
        holder.bindFriend(friend);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mFriends.size();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {

        boolean isClicked = false;

        @BindView(R.id.profile_image) ImageView profile;
        @BindView(R.id.id) TextView name;
        @BindView(R.id.subtitle) TextView id;
        @BindView(R.id.follow_button) ImageButton followButton;

        public FriendViewHolder(View itemView) {
            super(itemView);
            Timber.i("View holder Created");
            ButterKnife.bind(this, itemView);
        }

        public void bindFriend(Friend friend) {
            // setIsRecyclable(false);
            Picasso.with(profile.getContext())
                    .load(friend.profile())
                    .transform(new CircleTransform()).into(profile);
            name.setText (friend.name());
            id.setText(friend.id());

            followButton.setOnClickListener(v -> {
                isClicked = !isClicked;
                if (isClicked) {
                    v.setBackgroundResource(R.drawable.notify_follow_button_done);
                } else {
                    v.setBackgroundResource(R.drawable.notify_follow_button_event);
                }

            });
        }
    }

}
