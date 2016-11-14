package com.estsoft.muvigram.ui.feed;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.DynamicImageView;
import com.estsoft.muvigram.customview.FeedMenuButton;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.customview.TransCircleView;
import com.estsoft.muvigram.customview.autolicktextview.AutoLinkMode;
import com.estsoft.muvigram.customview.autolicktextview.AutoLinkTextView;
import com.estsoft.muvigram.ui.feed.comment.CommentActivity;
import com.estsoft.muvigram.util.ViewUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by gangGongUi on 2016. 11. 8..
 */
public class FeedItemHolder extends RecyclerView.ViewHolder {
    private final String USERNAME_PREFIX = "@";
    @BindView(R.id.circular_comment_view) public TransCircleView mTransCommentView;
    @BindView(R.id.auto_link_text_view) public AutoLinkTextView mAutoLinkTextView;
    @BindView(R.id.profile_image_view) public DynamicImageView mDynamicImageView;
    @BindView(R.id.circular_like_view) public TransCircleView mTransLikeView;
    @BindView(R.id.videoview) public IncreasVideoView mIncreasVideoView;
    @BindView(R.id.menu_button) public FeedMenuButton mFeedMenuButton;
    @BindView(R.id.user_name_text_view) public TextView mUserNameView;
    @BindView(R.id.placeholder) public View mPlaceHolderView;
    private Context mContext;

    public FeedItemHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBindViewHolder(@NonNull FeedItem item) {

        // Todo: set placeHolder
        //mPlaceHolderView.setBackground(mContext.getD);

        // Todo: set video
        final Uri videoFileUri = Uri.parse("android.resource://" + mContext.getPackageName() + "/raw/" + item.getUri());
        if (item.getUri() != null) {
            mIncreasVideoView.setVideoURI(videoFileUri);
            mIncreasVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
        }

        // Todo: set menu
        final RelativeLayout.LayoutParams feedParams = (RelativeLayout.LayoutParams) mFeedMenuButton.getLayoutParams();
        int bottomMargin = ViewUtils.getDisplayPerHeightByRes(mContext, R.integer.feed_menu_button_margin_bottom_per);
        feedParams.setMargins(0, 0, 0, bottomMargin);
        RxView.clicks(mFeedMenuButton)
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aVoid) -> {
                    final Resources res = mContext.getResources();
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    final CharSequence menuTitles[] = new CharSequence[]{
                            res.getString(R.string.feed_menu_item3),
                            res.getString(R.string.feed_menu_item7)};
                    dialog.setItems(menuTitles, (dialog1, which) -> {
                    });
                    dialog.show();
                });

//        // Todo: set streamTextView
//        final RelativeLayout.LayoutParams streamParams = (RelativeLayout.LayoutParams) mStreamTextView.getLayoutParams();
//        bottomMargin = ViewUtils.getDisplayPerHeightByRes(mContext, R.integer.stream_text_view_margin_bottom_per);
//        int leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.stream_text_view_margin_left_per);
//        streamParams.setMargins(leftMargin, 0, 0, bottomMargin);

        // Todo: set autoLinkTextView
        final RelativeLayout.LayoutParams autoTextParams = (RelativeLayout.LayoutParams) mAutoLinkTextView.getLayoutParams();
        int leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.auto_link_text_view_margin_left_per);
        bottomMargin = ViewUtils.getDisplayPerHeightByRes(mContext, R.integer.auto_link_text_view_margin_bottom_per);
        autoTextParams.setMargins(leftMargin, 0, 0, bottomMargin);
        mAutoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_MENTION, AutoLinkMode.MODE_HASHTAG);
        mAutoLinkTextView.setHashtagModeColor(ContextCompat.getColor(mContext, R.color.white));
        mAutoLinkTextView.setMentionModeColor(ContextCompat.getColor(mContext, R.color.white));
        mAutoLinkTextView.setAutoLinkText(item.getSpecification());
        mAutoLinkTextView.setAutoLinkOnClickListener(((autoLinkMode, matchedText) -> Timber.e(matchedText)));

        // Todo: set username
        final RelativeLayout.LayoutParams userNameTextParams = (RelativeLayout.LayoutParams) mUserNameView.getLayoutParams();
        leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.user_name_text_view_margin_left_per);
        userNameTextParams.setMargins(leftMargin, 0, 0, 0);
        mUserNameView.setText(USERNAME_PREFIX + item.getUsername());
        mUserNameView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf"));

        // Todo: set TransCircleView - Like
        RxView.clicks(mTransLikeView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    if (!mTransLikeView.isLike()) {
                        mTransLikeView.setIsLikeByBackgroundChanged(true);
                    } else {
                        mTransLikeView.setIsLikeByBackgroundChanged(false);
                    }
                });

        // Todo: set TransCircleView - comment
        RxView.clicks(mTransCommentView)
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    mContext.startActivity(new Intent(mContext, CommentActivity.class));
                });

        // Todo: set profileImage
        Picasso.with(mContext)
                .load(item.getProfileImage())
                .transform(new com.estsoft.muvigram.util.transform.CircleTransform())
                .into(mDynamicImageView);

    }
}