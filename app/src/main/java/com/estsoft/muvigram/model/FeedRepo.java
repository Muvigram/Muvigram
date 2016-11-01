package com.estsoft.muvigram.model;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.FeedMenuButton;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.estsoft.muvigram.customview.StreamTextView;
import com.estsoft.muvigram.customview.TransCircleView;
import com.estsoft.muvigram.customview.autolicktextview.AutoLinkMode;
import com.estsoft.muvigram.customview.autolicktextview.AutoLinkTextView;
import com.estsoft.muvigram.ui.feed.Holder;
import com.estsoft.muvigram.ui.feed.comment.CommentActivity;
import com.estsoft.muvigram.util.ViewUtils;
import com.estsoft.muvigram.util.transform.CircleTransform;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.visibility_utils.items.ListItem;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * Created by gangGongUi on 2016. 10. 24..
 */
public class FeedRepo implements ListItem {
    private static final int MAX_LOAD = 100;
    private static final int RUNNING_LOAD = 50;
    private static final String USERNAME_PREFIX = "@";
    private final Rect mCurrentViewRect = new Rect();
    private final Context mContext;
    private final ItemCallback mItemCallback;
    private Uri mUri;
    private String mUsername;
    private Drawable mThumbnail;
    private String mProfileImage;
    private String mSpecification;
    private boolean isFeatured = false;
    private int mPosition;

    public FeedRepo(Context mContext, String mUsername, Uri mUri, Drawable mThumbnail, String mProfileImage, String mSpecification,
                    boolean isFeatured, ItemCallback mItemCallback) {
        this.mUri = mUri;
        this.mUsername = mUsername;
        this.mThumbnail = mThumbnail;
        this.mItemCallback = mItemCallback;
        this.mContext = mContext;
        this.mProfileImage = mProfileImage;
        this.mSpecification = mSpecification;
        this.isFeatured = isFeatured;
    }

    public void onBindViewHolder(Holder holder, int position) {

        // video set
        this.mPosition = position;
        holder.mView.setBackground(mThumbnail);
        final IncreasVideoView mIncreasVideoView = holder.mIncreasVideoView;
        if (mUri != null) {
            mIncreasVideoView.setVideoURI(mUri);
            mIncreasVideoView.seekTo(0);
            mIncreasVideoView.setOnPreparedListener(mp -> mp.setLooping(true));
            mIncreasVideoView.setSaveEnabled(true);
        }

        // menu set
        final FeedMenuButton mFeedMenuButton = holder.mFeedMenuButton;
        final RelativeLayout.LayoutParams feedParams = (RelativeLayout.LayoutParams) mFeedMenuButton.getLayoutParams();
        int bottomMargin = ViewUtils.getDisplayPerHeightByRes(mContext, R.integer.feed_menu_button_margin_bottom_per);
        feedParams.setMargins(0, 0, 0, bottomMargin);
        RxView.clicks(mFeedMenuButton)
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((aVoid) -> {

                    final Resources res = mContext.getResources();
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                    CharSequence menuTitles[] = new CharSequence[] {
                            res.getString(R.string.feed_menu_item1),
                            res.getString(R.string.feed_menu_item2),
                            res.getString(R.string.feed_menu_item3),
                            res.getString(R.string.feed_menu_item4),
                            res.getString(R.string.feed_menu_item5),
                            res.getString(R.string.feed_menu_item6),
                            res.getString(R.string.feed_menu_item7)};
                    dialog.setItems(menuTitles, (dialog1, which) -> {
                    });
                    dialog.show();
                });

        // streamTextView set
        final StreamTextView mStreamTextView = holder.mStreamTextView;
        final RelativeLayout.LayoutParams streamParams = (RelativeLayout.LayoutParams) mStreamTextView.getLayoutParams();
        bottomMargin = ViewUtils.getDisplayPerHeightByRes(mContext, R.integer.stream_text_view_margin_bottom_per);
        int leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.stream_text_view_margin_left_per);
        streamParams.setMargins(leftMargin, 0, 0, bottomMargin);

        // autoLinkTextView set
        final AutoLinkTextView mAutoLinkTextView = holder.mAutoLinkTextView;
        final RelativeLayout.LayoutParams autoTextParams = (RelativeLayout.LayoutParams) mAutoLinkTextView.getLayoutParams();
        leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.auto_link_text_view_margin_left_per);
        bottomMargin = ViewUtils.getDisplayPerHeightByRes(mContext, R.integer.auto_link_text_view_margin_bottom_per);
        autoTextParams.setMargins(leftMargin, 0, 0, bottomMargin);
        mAutoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_MENTION, AutoLinkMode.MODE_HASHTAG);
        mAutoLinkTextView.setHashtagModeColor(ContextCompat.getColor(mContext, R.color.white));
        mAutoLinkTextView.setMentionModeColor(ContextCompat.getColor(mContext, R.color.white));
        mAutoLinkTextView.setAutoLinkText(mSpecification);
        mAutoLinkTextView.setAutoLinkOnClickListener(((autoLinkMode, matchedText) -> Timber.e(matchedText)));

        // username set
        final TextView mUserNameView = holder.mUserNameView;
        final RelativeLayout.LayoutParams userNameTextParams = (RelativeLayout.LayoutParams) mUserNameView.getLayoutParams();
        leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.user_name_text_view_margin_left_per);
        userNameTextParams.setMargins(leftMargin, 0, 0, 0);
        mUserNameView.setText(USERNAME_PREFIX + mUsername);
        mUserNameView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf"));

        // featuredText set
        if (isFeatured) {
            final TextView mFeaturedTextView = holder.mFeaturedTextView;
            mFeaturedTextView.setVisibility(View.VISIBLE);
            final RelativeLayout.LayoutParams featuredTextParams = (RelativeLayout.LayoutParams) mFeaturedTextView.getLayoutParams();
            leftMargin = ViewUtils.getDisplayPerWidthByRes(mContext, R.integer.featured_text_view_margin_left_per);
            featuredTextParams.setMargins(leftMargin, 0, 0, 0);
            final int padding = mContext.getResources().getInteger(R.integer.featured_text_view_padding_left_right);
            mFeaturedTextView.setPadding(padding, 0, padding, 0);
        }

        // TransCircleView - Like set
        final TransCircleView mTransLikeView = holder.mTransLikeView;
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


        // TransCircleView - comment set
        final TransCircleView mTransCommentView = holder.mTransCommentView;
        RxView.clicks(mTransCommentView)
                .debounce(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( aVoid -> {
                    mContext.startActivity(new Intent(mContext, CommentActivity.class));
                });

        // profileImage set
        Picasso.with(mContext)
                .load(mProfileImage)
                .transform(new CircleTransform())
                .into(holder.mDynamicImageView);


    }

    @Override public int getVisibilityPercents(View view) {

        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);

        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;

        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        final IncreasVideoView mIncreasVideoView = ((IncreasVideoView) view.findViewById(R.id.videoview));
        final View placeHolder = view.findViewById(R.id.placeholder);

        if (percents == MAX_LOAD) {
            mIncreasVideoView.start();
            mIncreasVideoView.postDelayed(() -> placeHolder.setVisibility(View.GONE), 300);

        } else if (percents <= RUNNING_LOAD && placeHolder.getVisibility() != View.VISIBLE) {
            placeHolder.setVisibility(View.VISIBLE);
        }

        return percents;
    }

    @Override public void setActive(View newActiveView, int newActiveViewPosition) {
        mItemCallback.onActiveViewChangedActive(newActiveView, mPosition);
    }

    @Override public void deactivate(View currentView, int position) {
        currentView.findViewById(R.id.placeholder).setVisibility(View.VISIBLE);

    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }


    public interface ItemCallback {
        void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition);
    }
}
