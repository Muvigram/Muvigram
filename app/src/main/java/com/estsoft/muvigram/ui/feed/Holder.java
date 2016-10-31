package com.estsoft.muvigram.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.estsoft.muvigram.customview.DynamicImageView;
import com.estsoft.muvigram.customview.FeedMenuButton;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.customview.TransCirclerView;

/**
 * Created by gangGongUi on 2016. 10. 26..
 */
public class Holder extends RecyclerView.ViewHolder {

    public final IncreasVideoView mIncreasVideoView;
    public final FeedMenuButton mFeedMenuButton;
    public final View mView;
    public final TransCirclerView mTransCommentView;
    public final TransCirclerView mTransLikeView;
    public final DynamicImageView mDynamicImageView;

    public Holder(View itemView) {
        super(itemView);
        this.mTransCommentView = ((TransCirclerView) itemView.findViewById(R.id.circular_comment_view));
        this.mTransLikeView = ((TransCirclerView) itemView.findViewById(R.id.circular_like_view));
        this.mDynamicImageView = (DynamicImageView) itemView.findViewById(R.id.profile_image_view);
        this.mFeedMenuButton = ((FeedMenuButton) itemView.findViewById(R.id.menu_button));
        this.mIncreasVideoView = ((IncreasVideoView) itemView.findViewById(R.id.videoview));
        this.mView = itemView.findViewById(R.id.placeholder);
    }
}
