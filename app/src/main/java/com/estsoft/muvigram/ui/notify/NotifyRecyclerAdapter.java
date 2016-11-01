package com.estsoft.muvigram.ui.notify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 19..
 */

public class NotifyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM_FOLLOW = 0;
    private static final int TYPE_ITEM_LIKE = 1;
    private static final int TYPE_ITEM_REPLY = 2;
    private static final String PROFILE_IMAGE = "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg";
    private static final String VIDEO_THUMBNAILS = "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg";

    boolean isClicked = false;

    private List<NotificationItem> mNotificationItemList;
    private NotificationItem mNotificationItem;

    public NotifyRecyclerAdapter(List<NotificationItem> mNotificationItemList) {
        this.mNotificationItemList = mNotificationItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int position){
            if(mNotificationItemList.get(position).getType()==0){
                View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_follow, parent, false);
                return new FollowItemViewHolder (v);
            }else if(mNotificationItemList.get(position).getType()==1){
                View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_like, parent, false);
                return new LikeItemViewHolder (v);
            }else if(mNotificationItemList.get(position).getType()==2){
                View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_reply, parent, false);
                return new ReplyItemViewHolder (v);
            }

        return null;
    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Context context;

        if(holder instanceof FollowItemViewHolder) {
            FollowItemViewHolder followItemViewHolder = (FollowItemViewHolder) holder;
            followItemViewHolder.id.setText ("like_id");

            context = followItemViewHolder.profile.getContext();
            Picasso.with(context)
                    .load(PROFILE_IMAGE)
                    .transform(new CircleTransform()).into(followItemViewHolder.profile);


            followItemViewHolder.followButton.setOnClickListener(v -> {

                isClicked = !isClicked;
                if(isClicked){
                    v.setBackgroundResource(R.drawable.notify_follow_button_done);
                }else if(!isClicked){
                    v.setBackgroundResource(R.drawable.notify_follow_button_event);
                }

            });

        } else if(holder instanceof LikeItemViewHolder) {
            LikeItemViewHolder likeItemViewHolder = (LikeItemViewHolder) holder;
            likeItemViewHolder.id.setText ("like_id");

            context = likeItemViewHolder.profile.getContext();
            Picasso.with(context)
                    .load(PROFILE_IMAGE)
                    .transform(new CircleTransform()).into(likeItemViewHolder.profile);

            context = likeItemViewHolder.thumbnail.getContext();
            Picasso.with(context)
                    .load(VIDEO_THUMBNAILS)
                    .resize(70, 70)
                    .into(likeItemViewHolder.thumbnail);

        } else if(holder instanceof ReplyItemViewHolder) {
            ReplyItemViewHolder replyItemViewHolder = (ReplyItemViewHolder) holder;
            replyItemViewHolder.id.setText("reply_id");
            replyItemViewHolder.reply.setText("reply yeah");


            context = replyItemViewHolder.profile.getContext();
            Picasso.with(context)
                    .load(PROFILE_IMAGE)
                    .transform(new CircleTransform()).into(replyItemViewHolder.profile);

            context = replyItemViewHolder.thumbnail.getContext();
            Picasso.with(context)
                    .load(VIDEO_THUMBNAILS)
                    .resize(70, 70)
                    .into(replyItemViewHolder.thumbnail);
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType (int position) {
        return position;
    }


    @Override
    public int getItemCount () {
        return mNotificationItemList.size();
    }

    class FollowItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id) TextView id;
        @BindView(R.id.profile_image) ImageView profile;
        @BindView(R.id.follow_button) ImageButton followButton;
        public FollowItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LikeItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id) TextView id;
        @BindView(R.id.profile_image) ImageView profile;
        @BindView(R.id.video_thumbnails) ImageView thumbnail;
        public LikeItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ReplyItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id) TextView id;
        @BindView(R.id.profile_image) ImageView profile;
        @BindView(R.id.reply) TextView reply;
        @BindView(R.id.video_thumbnails) ImageView thumbnail;
        public ReplyItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
