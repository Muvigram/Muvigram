package com.estsoft.muvigram.ui.feed;

import android.graphics.Rect;
import android.view.View;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;
import com.volokh.danylo.visibility_utils.items.ListItem;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by gangGongUi on 2016. 10. 24..
 */


@Data
@ToString
@Accessors(prefix = "m")
public class FeedItem implements ListItem {
    private static final int MAX_LOAD = 100;
    private static final int RUNNING_LOAD = 50;
    private static final String USERNAME_PREFIX = "@";
    private final Rect mCurrentViewRect = new Rect();
    private String mUri;
    private String mUsername;
    private String mThumbnailName;
    private String mThumbnailUri;
    private String mProfileImage;
    private String mSpecification;
    private String mAlbum_cover;
    private boolean mFeatured = false;
    private ItemCallback mItemCallback;


    public FeedItem(String mUri, String mUsername, String mThumbnailName, String mThumbnailUri, String mProfileImage, String mSpecification,String mAlbum_cover, boolean mFeatured) {
        this.mUri = mUri;
        this.mUsername = mUsername;
        this.mThumbnailName = mThumbnailName;
        this.mThumbnailUri = mThumbnailUri;
        this.mProfileImage = mProfileImage;
        this.mSpecification = mSpecification;
        this.mAlbum_cover = mAlbum_cover;
        this.mFeatured = mFeatured;
    }

    public void setItemCallback(ItemCallback mItemCallback) {
        this.mItemCallback = mItemCallback;
    }

    // Todo set placeholder for percents.
    @Override public int getVisibilityPercents(View view) {
        // RecyclerViewPager item percentage calculation for FeedFragment.
        int percents = 100;
        view.getLocalVisibleRect(mCurrentViewRect);
        int height = view.getHeight();
        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }
        // Place a placeholder according to percent.
        final IncreasVideoView mIncreasVideoView = ((IncreasVideoView) view.findViewById(R.id.videoview));
        final View placeHolder = view.findViewById(R.id.placeholder);
        if (percents == MAX_LOAD) {
            // Remove placeholder if it shows 100 percent.
            mIncreasVideoView.start();
            mIncreasVideoView.postDelayed(() -> placeHolder.setVisibility(View.GONE), 300);
        } else if (percents <= RUNNING_LOAD && placeHolder.getVisibility() != View.VISIBLE) {
            // Placeholder appears when less than 50 percent is seen.
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
