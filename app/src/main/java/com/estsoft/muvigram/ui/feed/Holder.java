package com.estsoft.muvigram.ui.feed;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.DynamicImageView;
import com.estsoft.muvigram.customview.FeedMenuButton;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.customview.StreamTextView;
import com.estsoft.muvigram.customview.TransCircleView;
import com.estsoft.muvigram.customview.autolicktextview.AutoLinkTextView;


/**
 * Created by gangGongUi on 2016. 10. 26..
 */
public class Holder extends RecyclerView.ViewHolder {

    public final IncreasVideoView mIncreasVideoView;
    public final FeedMenuButton mFeedMenuButton;
    public final View mView;
    public final TransCircleView mTransCommentView;
    public final TransCircleView mTransLikeView;
    public final DynamicImageView mDynamicImageView;
    public final StreamTextView mStreamTextView;
    public final AutoLinkTextView mAutoLinkTextView;
    public final TextView mUserNameView;
    public final TextView mFeaturedTextView;

    public Holder(View itemView) {
        super(itemView);
        this.mTransCommentView = ((TransCircleView) itemView.findViewById(R.id.circular_comment_view));
        this.mTransLikeView = ((TransCircleView) itemView.findViewById(R.id.circular_like_view));
        this.mDynamicImageView = (DynamicImageView) itemView.findViewById(R.id.profile_image_view);
        this.mFeedMenuButton = ((FeedMenuButton) itemView.findViewById(R.id.menu_button));
        this.mIncreasVideoView = ((IncreasVideoView) itemView.findViewById(R.id.videoview));
        this.mView = itemView.findViewById(R.id.placeholder);
        this.mStreamTextView = ((StreamTextView) itemView.findViewById(R.id.stream_text_view));
        this.mAutoLinkTextView = ((AutoLinkTextView) itemView.findViewById(R.id.auto_link_text_view));
        this.mUserNameView = ((TextView) itemView.findViewById(R.id.user_name_text_view));
        this.mFeaturedTextView = (TextView) itemView.findViewById(R.id.featured_text_view);
    }
}
