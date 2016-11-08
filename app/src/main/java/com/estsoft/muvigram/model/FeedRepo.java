package com.estsoft.muvigram.model;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.volokh.danylo.visibility_utils.items.ListItem;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by gangGongUi on 2016. 10. 24..
 */
@Data
@Accessors(prefix = "m")
public class FeedRepo implements ListItem {
    private static final int MAX_LOAD = 100;
    private static final int RUNNING_LOAD = 50;
    private static final String USERNAME_PREFIX = "@";
    private final Rect mCurrentViewRect = new Rect();
    private final Context mContext;
    private Uri mUri;
    private String mUsername;
    private Drawable mThumbnail;
    private String mProfileImage;
    private String mSpecification;
    private boolean mFeatured = false;
    private ItemCallback mItemCallback;


    public FeedRepo(Context mContext, String mUsername, Uri mUri, Drawable mThumbnail, String mProfileImage, String mSpecification,
                    boolean mFeatured, ItemCallback mItemCallback) {
        this.mUri = mUri;
        this.mUsername = mUsername;
        this.mThumbnail = mThumbnail;
        this.mItemCallback = mItemCallback;
        this.mContext = mContext;
        this.mProfileImage = mProfileImage;
        this.mSpecification = mSpecification;
        this.mFeatured = mFeatured;
    }


    // Todo set placeholder for percents.
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
            mIncreasVideoView.pause();
            placeHolder.setVisibility(View.VISIBLE);
        }
        return percents;
    }

    @Override public void setActive(View newActiveView, int newActiveViewPosition) {
        mItemCallback.onActiveViewChangedActive(newActiveView, newActiveViewPosition);
    }

    @Override public void deactivate(View currentView, int position) {
        currentView.findViewById(R.id.placeholder).setVisibility(View.VISIBLE);
        final IncreasVideoView mIncreasVideoView = (IncreasVideoView) currentView.findViewById(R.id.videoview);
        mIncreasVideoView.pause();
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
