package com.estsoft.muvigram.ui.notify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JEONGYI on 2016. 10. 19..
 */

public class NotifyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM_FOLLOW = 0;
    private static final int TYPE_ITEM_LIKE = 1;
    private static final int TYPE_ITEM_REPLY = 2;

    private List<NotificationItem> mNotificationItemList;
    private NotificationItem mNotificationItem;

    public NotifyRecyclerAdapter(List<NotificationItem> mNotificationItemList) {
        this.mNotificationItemList = mNotificationItemList;
    }


    public void test(){
        for(int i=0; i<5; i++){
            if(mNotificationItemList.get(i).getType()==0){

            }else if(mNotificationItemList.get(i).getType()==1){

            }else if(mNotificationItemList.get(i).getType()==2){

            }else{
                return;
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM_FOLLOW) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_follow, parent, false);
            return new FollowItemViewHolder (v);
        } else if(viewType == TYPE_ITEM_LIKE) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_like, parent, false);
            return new LikeItemViewHolder (v);
        } else if(viewType == TYPE_ITEM_REPLY) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notify_fragment_item_reply, parent, false);
            return new ReplyItemViewHolder (v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if(holder instanceof FollowItemViewHolder) {
            FollowItemViewHolder followItemViewHolder = (FollowItemViewHolder) holder;
//            followItemViewHolder.tag.setText ("follow");

        } else if(holder instanceof LikeItemViewHolder) {
            LikeItemViewHolder likeItemViewHolder = (LikeItemViewHolder) holder;
//            likeItemViewHolder.tag.setText ("like");

        } else if(holder instanceof ReplyItemViewHolder) {
            ReplyItemViewHolder replyItemViewHolder = (ReplyItemViewHolder) holder;
//            replyItemViewHolder.tag.setText ("reply");
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

//        need to override this method
    @Override
    public int getItemViewType (int position) {
        if(position % 3 == 0) {
            return TYPE_ITEM_FOLLOW;
        } else if(position % 3 == 1) {
            return TYPE_ITEM_REPLY;
        }
        return TYPE_ITEM_LIKE;
    }
//
//    private boolean isPositionHeader (int position) {
//        return position == 0;
//    }
//
//    private boolean isPositionFooter (int position) {
//        return position == mNotifyFollowItemList.size () + mNotifyLikeItemList.size()+1;
//    }

    @Override
    public int getItemCount () {
        return mNotificationItemList.size();
    }

    class FollowItemViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.item_tag) TextView tag;
        public FollowItemViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    class LikeItemViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.item_tag) TextView tag;
        public LikeItemViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

    class ReplyItemViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.item_tag) TextView tag;
        public ReplyItemViewHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }
    }

}
