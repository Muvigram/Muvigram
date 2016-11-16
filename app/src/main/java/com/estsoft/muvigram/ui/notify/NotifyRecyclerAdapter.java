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
import com.estsoft.muvigram.injection.PerParentFragment;
import com.estsoft.muvigram.injection.PerPlainActivity;
import com.estsoft.muvigram.injection.qualifier.ActivityContext;
import com.estsoft.muvigram.ui.profile.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by JEONGYI on 2016. 10. 19..
 */

@PerParentFragment
public class NotifyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM_FOLLOW = 0;
    private static final int TYPE_ITEM_LIKE = 1;
    private static final int TYPE_ITEM_REPLY = 2;
    private final Context mContext;
    private static final String PROFILE_IMAGE = "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg";
    private static final String VIDEO_THUMBNAILS = "https://pbs.twimg.com/media/CODCz6EUcAAvryE.jpg";

    boolean isClicked = false;

    private List<NotificationItem> mNotificationItemList;
    private NotificationItem mNotificationItem;

    @Inject
    public NotifyRecyclerAdapter(@ActivityContext Context mContext) {
        this.mContext = mContext;
    }

    public void setNotificationItemList(List<NotificationItem> mNotificationItemList){
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
            followItemViewHolder.id.setText (mNotificationItemList.get(position).getNotifyFollow().user());

            context = followItemViewHolder.profile.getContext();
            Picasso.with(context)
                    .load(mNotificationItemList.get(position).getNotifyFollow().profile())
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
            likeItemViewHolder.id.setText (mNotificationItemList.get(position).getNotifyLike().user());

            context = likeItemViewHolder.profile.getContext();
            Picasso.with(context)
                    .load(mNotificationItemList.get(position).getNotifyLike().profile())
                    .transform(new CircleTransform()).into(likeItemViewHolder.profile);

            context = likeItemViewHolder.thumbnail.getContext();
            Picasso.with(context)
                    .load(mNotificationItemList.get(position).getNotifyLike().thumbnail())
                    .resize(70, 70)
                    .into(likeItemViewHolder.thumbnail);

        } else if(holder instanceof ReplyItemViewHolder) {
            ReplyItemViewHolder replyItemViewHolder = (ReplyItemViewHolder) holder;
            replyItemViewHolder.id.setText(mNotificationItemList.get(position).getNotifyComment().user());
            replyItemViewHolder.reply.setText(mNotificationItemList.get(position).getNotifyComment().content());


            context = replyItemViewHolder.profile.getContext();
            Picasso.with(context)
                    .load(mNotificationItemList.get(position).getNotifyComment().profile())
                    .transform(new CircleTransform()).into(replyItemViewHolder.profile);

            context = replyItemViewHolder.thumbnail.getContext();
            Picasso.with(context)
                    .load(mNotificationItemList.get(position).getNotifyComment().thumbnail())
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

    public void clear() {
        mNotificationItemList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<NotificationItem> list) {
        mNotificationItemList.addAll(list);
        notifyDataSetChanged();
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
